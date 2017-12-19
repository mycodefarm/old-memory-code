#!/usr/bin/env python
#-*-coding:utf-8-*-
"""
Author@jimolonely-jackpler
将新加的LUN直接映射成RDM硬盘并添加给相应的虚拟机
"""
from pyVmomi import vim
from pyVmomi import vmodl
from pyVim.connect import SmartConnect, Disconnect
import atexit
import argparse
import getpass
import ssl
import sys
import time
import paramiko
from wputil import *
from wpconfig import *
from model import RDMLun,Lun
from create_lun import exec_delete_lun

def get_obj(content, vimtype, name):
    obj = None
    container = content.viewManager.CreateContainerView(
        content.rootFolder, vimtype, True)
    for c in container.view:
        if c.name == name:
            obj = c
            break
    return obj

def list_vms_disk(vm):
    for d in vm.config.hardware.device:
        if isinstance(d,vim.vm.device.VirtualDisk)\
           or isinstance(d.backing,vim.vm.device.VirtualDisk.RawDiskMappingVer1BackingInfo):
            print(d)

def list_luns(vm):
    ss = vm.runtime.host.configManager.storageSystem
    print(ss.storageDeviceInfo.scsiLun[0])

def list_scsi_controllers(vm):
    for dev in vm.config.hardware.device:
        if isinstance(dev, vim.vm.device.VirtualSCSIController):
            debug(dev)

    
def get_lun(vm,canonicalName):
    '''
    canonicalName:naa.6346ac2100afeaf610bc01ac00000040
    '''
    ss = vm.runtime.host.configManager.storageSystem
    re = None
    for lun in ss.storageDeviceInfo.scsiLun:
        if canonicalName==lun.canonicalName:
            re = lun
            break
    return re

def get_datastore(si,store_name):
    '''
    获得数据存储对象
    '''
    content = si.RetrieveContent()
    return get_obj(content, [vim.Datastore], store_name)


def get_client(info):
    '''
    通过SSH连接linux系统
    '''
    try:
        show_msg('Try to login...')
        client = paramiko.SSHClient()
        client.set_missing_host_key_policy(paramiko.AutoAddPolicy())
        client.connect(info[0],22,info[1],info[2])
        show_msg('Login Success')
    except paramiko.SSHException as e:
        show_msg('连接shell出错，请检查网络或密码:')
        show_err(e)
        log(e)       
    return client

def exec_ok(stdout):
    '''
    根据错误码判断是否成功执行
    '''
    code = stdout.readlines()[-1]
    return True if code.strip()=='0' else False

def rescan_disks(client):
    '''
    扫描所有设备
    失败则终止程序
    '''
    stdin,stdout,stderr = client.exec_command(\
        'esxcli storage core adapter rescan --all ; echo $?')
    if not exec_ok(stdout):
        show_err('rescan disks error')
        sys.exit(1)
    time.sleep(1)
    show_msg('Scan disks successfully.')
        
def get_valid_luns(lun_infos,vm):
    '''
    根据扫描后判断与新创建的lun是否吻合
    返回封装后的lun信息
    '''
    luns = []
    try:
        ctrl_keys = get_ctrlrs(vm)
        
        controllerKey = ctrl_keys[0] if ctrl_keys else 1000
        # 不管手动还是自动，先把控制器生产了
        show_msg('Generate SCSI controller...')
        # 这里的问题是一次可能无法产生完4个控制器，所以采用循环直到创建完才往下         
        for bus in range(3):
                key = controllerKey+bus+1
                show_msg('Trying create scsi controller '+str(key))
                tryCnt = 3
                ok = False 
                while not ok and tryCnt>0:
                    add_scsi_controller_to_vm(vm,bus+1,key)
                    time.sleep(1)
                    ctrl_keys = get_ctrlrs(vm)
                    try:
                        ctrl_keys.index(key)
                        ok = True
                    except:
                        pass
                    tryCnt -= 1
                    debug(ctrl_keys)
        show_msg('Add scsi Controller %d success'%(bus+1))
        time.sleep(1) # 等待创建完
        
        # 如果手动配置，则加载配置信息
        manual = ADD_DISK_CMD.get('is_manual',False)
        if manual:
            # 检查控制器配置是否合法：
            # 1.不能超过4个
            # 2.unit_number不能为7和15，15留给临时磁盘
            # 3.是否已被使用
            ctlr_unit = ADD_DISK_CMD['ctlr_unit']
            for cu in ctlr_unit:
                if cu[0]>3 or cu[1]>14 or cu[1]==7 or not unit_number_valid(vm,cu[0]+controllerKey,cu[1]):
                    show_err('check config ctlr_unit error in (%s,%s).' \
                             %(str(cu[0]),str(cu[1])))
                    return None
                
            if len(ctlr_unit)!=len(lun_infos):
                show_err('get_valid_luns:config error,check the ctlr_unit list.')
                sys.exit(1)
            
            i = 0
            for lun in lun_infos:
                rlun = RDMLun(lun.wwn,ctrl_keys[ctlr_unit[i][0]],ctlr_unit[i][1])
                i = i + 1
                luns.append(rlun)
            
        # 否则自动生成编号
        else:
            lun_info = CREATE_LUN_CMD['lun_info']
            cnt = 0
            for x in lun_info:
                cnt += x[1]
            if cnt!=len(lun_infos):
                show_err('get_valid_luns:parameter [lun_infos] error')
                sys.exit(1)
            ctrl_units = []
            scsi = ctrl_keys[0]
            sc = 0
            for lun in lun_info:
                temp_unit = get_valid_unit_num(vm,ctrl_keys[sc],0)
                for i in range(lun[1]):
                    ck = (ctrl_keys[sc],temp_unit)
                    ctrl_units.append(ck)
                    temp_unit = get_valid_unit_num(vm,ctrl_keys[sc],temp_unit+1)
                sc = (sc+1)%4
            i = 0
            for lun in lun_infos:
                rlun = RDMLun(lun.wwn,ctrl_units[i][0],ctrl_units[i][1])
                i = i + 1
                luns.append(rlun)
    except Exception as e:
        show_err(e)
        sys.exit(1)
    return luns    

def unit_number_valid(vm,scsi_key,unit_number):
    '''
    检查配置文件里的口配置是否合法
    '''
    for dev in vm.config.hardware.device:
        if isinstance(dev, vim.vm.device.VirtualDisk):
            if dev.controllerKey==scsi_key and dev.unitNumber==unit_number:
                return False
    return True

def get_valid_unit_num(vm,scsi_key,unit_number):
    '''
    根据scsi确定unit number是否合法
    通过遍历这个控制器下的所有磁盘来确定
    '''
    for dev in vm.config.hardware.device:
        if isinstance(dev, vim.vm.device.VirtualDisk) \
            and dev.controllerKey==scsi_key:
            if unit_number==dev.unitNumber or unit_number == 7:
                unit_number = unit_number+1
            if unit_number >= 15: # 15保留为临时磁盘
                show_err("we don't support this many disks")
                sys.exit(1)
    return unit_number    
            
def get_ctrlrs(vm):
    '''
    取得所有控制器的key
    '''
    keys = []
    for dev in vm.config.hardware.device:
        if isinstance(dev, vim.vm.device.VirtualSCSIController):
            keys.append(dev.key)
    return keys

def add_lun_to_vm_by_rdm(vm,scsi_ctlr,unit_num,lun,datastore):        
    '''
    将LUN以RDM形式加入对应的vm
    scsi_ctlr,unit_num表示控制器和槽位号
    datastore：存入相应的数据存储
    '''
    disk_mode = 'Persistent'
    comp_mode = 'physicalMode'

    dev_changes = []
    spec = vim.vm.ConfigSpec()

    try:
        disk_spec = vim.vm.device.VirtualDeviceSpec()
        disk_spec.fileOperation = "create"
        disk_spec.operation = vim.vm.device.VirtualDeviceSpec.Operation.add
        # define device
        disk_spec.device = vim.vm.device.VirtualDisk()
        disk_spec.device.unitNumber = unit_num
        disk_spec.device.controllerKey = scsi_ctlr

        # rdm config
        disk_spec.device.backing = \
            vim.vm.device.VirtualDisk.RawDiskMappingVer1BackingInfo()
        disk_spec.device.backing.lunUuid = lun.uuid
        disk_spec.device.backing.compatibilityMode = comp_mode
        # 这里的文件名一定注意，不能有除数字字母和下划线外的其他字符,且一定要唯一
        # 搞不定，干脆不自己命名了
        #disk_spec.device.backing.fileName = '.vmdk'     
        disk_spec.device.backing.diskMode = disk_mode
        disk_spec.device.backing.deviceName = lun.deviceName
        disk_spec.device.backing.datastore = datastore
         
        dev_changes.append(disk_spec)
        spec.deviceChange = dev_changes
        #print(spec)
        vm.ReconfigVM_Task(spec=spec)
        show_msg('Add LUN %s success'%(lun.canonicalName))
        return True
    except Exception as e:
        show_err('add_lun_to_vm_by_rdm failed')
        show_err(e)
    return False

def run(lun_infos):

    client = get_client(LINUX_INFO)

    # 1. 扫描磁盘   
    rescan_disks(client)
    # 2. 获得虚拟机
    si,vm = get_si_vm()

    # 3. 取得需要映射的LUN
    luns = get_valid_luns(lun_infos,vm)

    if not luns:
        return False

    # 4. 映射成RDM并分给虚拟机
    lun_uuids = []
    cnt = 0
    datastore = get_datastore(si,ADD_DISK_CMD.get('datastore','datastore1 (5)'))
    for lun in luns:
        scsi_ctlr,unit_num = lun.ctrlr,lun.unit_num
        temp_lun = get_lun(vm,lun.canonicalName)
        if temp_lun:
            lun_uuids.append(temp_lun.uuid)
        else:
            show_err('lun %s didnot exists.'%(lun.canonicalName))
            continue
        ok = add_lun_to_vm_by_rdm(vm,scsi_ctlr,unit_num,temp_lun,datastore)
        cnt = cnt + 1 if ok else cnt
        time.sleep(1)

    # 5. 若有失败的，则回退
    if cnt!=len(luns):
        for uuid in lun_uuids:
            deviceName = 'vml.'+uuid
            del_virtual_disk(vm,deviceName)

    # 6. 清除临时磁盘
    del_temp_virtual_disk(vm)
    time.sleep(1)
    
    show_msg('Finish RDM')
    return True

def del_virtual_disk(vm,deviceName):
    '''
    根据deviceName删除虚拟磁盘
    '''
    virtual_hdd_device = None
    for dev in vm.config.hardware.device:
        if isinstance(dev, vim.vm.device.VirtualDisk) \
           and isinstance(dev.backing,vim.vm.device.VirtualDisk.RawDiskMappingVer1BackingInfo) \
           and dev.backing.deviceName == deviceName:
            #show_msg(dev.backing)
            virtual_hdd_device = dev
            break
            
    if not virtual_hdd_device:
        return False

    virtual_hdd_spec = vim.vm.device.VirtualDeviceSpec()
    virtual_hdd_spec.operation = \
        vim.vm.device.VirtualDeviceSpec.Operation.remove
    virtual_hdd_spec.device = virtual_hdd_device

    spec = vim.vm.ConfigSpec()
    spec.deviceChange = [virtual_hdd_spec]
    task = vm.ReconfigVM_Task(spec=spec)
    show_msg('del disk %s successfully.'%deviceName)
    time.sleep(1)
    return True

def del_all_rdm_disks(vm):
    ''' 
    删除所有RDM磁盘
    '''    
    cnt = 0
    for dev in vm.config.hardware.device:
        if isinstance(dev, vim.vm.device.VirtualDisk) \
           and isinstance(dev.backing,vim.vm.device.VirtualDisk.RawDiskMappingVer1BackingInfo):
            ok = del_virtual_disk(vm,dev.backing.deviceName)
            cnt = cnt + 1 if ok else cnt
    show_msg('Total deleled %s disks'%(str(cnt)))
            
    
def get_si_vm():
    '''
    获取vm
    '''
    # Disabling SSL certificate verification
    context = ssl.SSLContext(ssl.PROTOCOL_TLSv1)
    context.verify_mode = ssl.CERT_NONE
    
    # connect this thing
    si = SmartConnect(
        host=LINUX_INFO[0],
        user=LINUX_INFO[1],
        pwd=LINUX_INFO[2],
        port=443,
        sslContext=context)
    # disconnect this thing
    atexit.register(Disconnect, si)

    vm = None
    uuid = None
    vm_name = ADD_DISK_CMD['vm_name']
    if uuid:
        search_index = si.content.searchIndex
        vm = search_index.FindByUuid(None, uuid, True)
    elif vm_name:
        content = si.RetrieveContent()
        vm = get_obj(content, [vim.VirtualMachine], vm_name)

    if vm:
        return si,vm
    else:
        show_err("VM not found")
        exec_delete_lun(del_group=True)
        sys.exit(1)

def add_rdm_from_file():
    '''
    假如lun创建成功了，但这一个文件执行失败，或者删除了所有RDM，可以调用这个方法重新执行上一步
    '''
    lun_infos = read_luns_from_file()
    run(lun_infos)    
            
    
def test_del_disk():
    ok = my_ask('Are you sure to delete all the RDM Disks(y/n):')
    if ok:
        si,vm = get_si_vm()
        #deviceName = 'vml.02000500006346ac2100afeaf6149f8644000000a0585347312020'
        #del_virtual_disk(vm,deviceName)
        del_all_rdm_disks(vm)

def add_scsi_controller_to_vm(vm,busNumber,controllerKey):
    '''
    修复：将硬盘和控制器一起创建
    '''
    dev_changes = []
    spec = vim.vm.ConfigSpec()

    unit_number = 15

    # add disk here
    disk_type='thin'
    new_disk_kb = 2 * 1024 * 1024
    disk_spec = vim.vm.device.VirtualDeviceSpec()
    disk_spec.fileOperation = "create"
    disk_spec.operation = vim.vm.device.VirtualDeviceSpec.Operation.add
    disk_spec.device = vim.vm.device.VirtualDisk()
    disk_spec.device.backing = \
        vim.vm.device.VirtualDisk.FlatVer2BackingInfo()
    if disk_type == 'thin':
        disk_spec.device.backing.thinProvisioned = True
    disk_spec.device.backing.diskMode = 'persistent'
    disk_spec.device.unitNumber = unit_number
    disk_spec.device.capacityInKB = new_disk_kb
    disk_spec.device.controllerKey = controllerKey
    # add scsi controller
    scsi_disk = vim.vm.device.VirtualDeviceSpec()
    scsi_disk.operation = vim.vm.device.VirtualDeviceSpec.Operation.add
    scsi_disk.device = vim.vm.device.ParaVirtualSCSIController()
    scsi_disk.device.busNumber = busNumber
    scsi_disk.device.key = controllerKey
    #scsi_disk.device.deviceInfo = vim.Description()
    #scsi_disk.device.deviceInfo.label = 'SCSI controller xx'
    #scsi_disk.device.deviceInfo.summary = 'VMware ps '
    scsi_disk.device.controllerKey = 100
    scsi_disk.device.hotAddRemove = True
    scsi_disk.device.sharedBus = 'noSharing'
    scsi_disk.device.scsiCtlrUnitNumber = 7
    
    dev_changes.append(scsi_disk)
    dev_changes.append(disk_spec)
    spec.deviceChange = dev_changes
    tasks = vm.ReconfigVM_Task(spec=spec)
    
    
def del_temp_virtual_disk(vm):
    '''
    这些磁盘是为了创建控制器而产生的，我定义他们都使用每个控制器的0号口
    然后，完事后清理所有控制器的15号口，除了0号控制器
    '''
    show_msg('now delete temp virtual disks...')
    controllerKey0 = get_ctrlrs(vm)[0] # 一般为1000
    device_spec = []
    for dev in vm.config.hardware.device:
        if isinstance(dev, vim.vm.device.VirtualDisk) \
           and isinstance(dev.backing,vim.vm.device.VirtualDisk.FlatVer2BackingInfo) \
           and dev.controllerKey != controllerKey0 \
           and dev.unitNumber==15:

            virtual_hdd_spec = vim.vm.device.VirtualDeviceSpec()
            virtual_hdd_spec.operation = \
                vim.vm.device.VirtualDeviceSpec.Operation.remove
            virtual_hdd_spec.device = dev
            device_spec.append(virtual_hdd_spec)

    spec = vim.vm.ConfigSpec()
    spec.deviceChange = device_spec
    task = vm.ReconfigVM_Task(spec=spec)
    show_msg('del temp virtual disk successfully.')
    time.sleep(0.5)
    return True

def clear_vm_env():
    '''
    清理虚拟机环境，这里删除临时磁盘和RDM磁盘
    '''
    ok = my_ask('Are you sure to delete all the Disks(include virtual and RDM disk)(y/n):')
    if ok:
        si,vm = get_si_vm()
        del_temp_virtual_disk(vm)
        del_all_rdm_disks(vm)

def clear_temp_virtual_disk():
    '''
    删除临时磁盘
    '''
    ok = my_ask('Are you sure to delete all the temp virtual disks(y/n):')
    if ok:
        si,vm = get_si_vm()
        del_temp_virtual_disk(vm)

    
def test():
    si,vm = get_si_vm()
    '''
    controllerKey = 1000
    for bus in range(3):
        add_scsi_controller_to_vm(vm,bus+1,controllerKey+bus+1)
        
    time.sleep(1)   
    list_scsi_controllers(vm)
    #list_vms_disk(vm)
    '''
    #del_temp_virtual_disk(vm)
    #key = get_ctrlrs(vm)
    #print(key[0])
    #ok = unit_number_valid(vm,1000,1)
    #print(ok)
    #add_scsi_controller_to_vm(vm,3,1003)
    lun_infos = read_luns_from_file()
    luns = get_valid_luns(lun_infos,vm)
        
    
# start this thing
if __name__ == "__main__":
    args = sys.argv
    if len(args)<=1:
        print('Usage:\n-dard(delete all rdm disks)\n-arff(add rdm from file)\n-clear(clear vm env)')
        print('-dtvd(del temp virtual disk)')
        sys.exit(1)
    if args[1]=='-dard':
        test_del_disk()
    elif args[1]=='-arff':
        add_rdm_from_file()
    elif args[1]=='-clear':
        clear_vm_env()
    elif args[1]=='-dtvd':
        clear_temp_virtual_disk()
    else:
        show_err('Uknown command')
        test()
