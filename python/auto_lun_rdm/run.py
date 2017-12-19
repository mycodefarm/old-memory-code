#-*-coding:utf-8-*-

import paramiko
import sys
import time
import re
from wpconfig import *

def show_msg(msg):
    s = '*' * 50
    print(s)
    print('^v^'+str(msg))
    print(s)

def show_err(err):
    print('=================================================')
    print('>.<'+str(err))
    print('=================================================')

def show_cmd(cmd):
    print('****NOW exec cmd:'+cmd+' ****')

def debug(info):
    print('------------------debug info---------------------')
    print(info)
    print('----------------------------------------------')

    
def log(info):
    '''
    写入日志
    '''
    pass

class Lun:

    def __init__(self,name,wwn,lun_id):
        self.name = name
        self.wwn = wwn
        self.id = lun_id

lun_names = [] # 新创建的LUN的名字
lun_infos = [] # 存放新加入的LUN的WWM和其他信息为了添加磁盘时识别，每个元素是个Lun类

def main():
    info = [('100.115.71.117',"root","Huawei123"),\
            ('100.115.70.196','admin','Admin@storage1')]
    try:
        
        #auto_work()
        test(info[0])        
        
    except paramiko.SSHException as e:
        show_err(e)

    finally:
        pass
        '''
        try:
            channel.close()
            client.close()
        except:
            pass
            '''

def test(info):
    client = get_client(info)

    # 1.扫描所有设备    
    stdin,stdout,stderr = client.exec_command(\
        'esxcli storage core adapter rescan --all ; echo $?')
    if not exec_ok(stdout):
        show_err('rescan all error')
        return
    # 2.扫描出所有磁盘
    
    
    try:
        client.close()
    except:
        pass    

def exec_ok(stdout):
    '''
    根据错误吗判断是否成功执行
    '''
    code = stdout.readlines()[-1]
    return True if code.strip()=='0' else False
    
def get_client(info):
    '''
    通过SSH连接linux系统
    '''
    try:
        client = paramiko.SSHClient()
        client.set_missing_host_key_policy(paramiko.AutoAddPolicy())
        client.connect(info[0],22,info[1],info[2])
        show_msg('Login Success')
    except paramiko.SSHException as e:
        show_msg('连接shell出错，请检查网络或密码:')
        show_err(e)
        log(e)       
    return client


def auto_work(info):
    '''
    整个自动化工作:从创建LUN到加入组
    '''
    channel = get_client(info)
    
    for cmd in AUTO_CMD['create_list']:
        show_cmd(cmd)
        cmd = cmd.strip()
        if cmd.startswith('create lun'):
            exec_create_lun(channel,cmd)
        else:
            show_err('Unknown Command')
    # 将LUN加入到组里
    group_id = AUTO_CMD['group_id']
    exec_add_lun_group(channel,group_id)
    
    try:
        channel.close()
    except:
        pass    
    
def exec_create_lun(channel,cmd):
    out = exec_command(channel,cmd)
    '''
    错误信息:
    admin:/>create lun name=LUN_Cache_Jrn0002 number=2 pool_id=0 capacity=120GB lun_type=thin compression_enabled=yes dedup_enabled=no
    Create LUN LUN_Cache_Jrn00020000 successfully.
    Create LUN LUN_Cache_Jrn00020001 successfully.
    -----------------------------------------------------
    admin:/>create lun name=LUN_Cache_Jrn0002 number=2 pool_id=0 capacity=120GB lun_type=thin compression_enabled=yes dedup_enabled=no
    Error: The object name already exists.
    Suggestion: Enter a new name.
    '''
    #out = 'create fddsfdsfs\ncreate dsfsdfdsf successfully'
    lines = out.split('\n')
    for line in lines:
        if line.endswith('successfully.'):
            show_msg(line)
            # 若成功创建LUN，则获得新创建LUN的名字
            lun = re.sub(r'(Create|successfully\.)','',line)
            name = lun.strip()[4:]
            lun_names.append(name)
        elif line.startswith('Error'):
            show_err(line) 

def exec_show_lun(channel):
    '''
    对LUN的字符串结果进行处理，描述为python的list结构
    '''
    out = exec_command(channel,'show lun general')
    luns = out.split('\n')
    return luns

def exec_add_lun_group(channel,group_id):
    '''
    将一些LUN加入LUN组：
    首先show出所有LUN，然后根据名字查出他们的id，然后构造命令执行，返回处理结果
    '''
    luns = exec_show_lun(channel)
    # 构造命令
    cmd = 'add lun_group lun lun_group_id='+str(group_id)+' lun_id_list='
    for s in luns:
        lun = s.split() # 这又是一个list
        lun_id,lun_name,lun_wwn = lun[0],lun[1],lun[7]
        if lun_name in lun_names:
            cmd += lun_id+','
            info = Lun(lun_name,lun_wwn,lun_id)
            lun_infos.append(info)  #####

    if not cmd.endswith(','):
        show_err('no lun create')
        return
    
    cmd = cmd[:-1] # 去掉末尾的逗号

    show_cmd(cmd)
    
    #执行命令
    out = exec_command(channel,cmd)
    # 执行结果考虑2种：group_id错误；lun_id错误
    '''
    -----------------------1--------------------------
    admin:/>add lun_group lun lun_group_id=1000 lun_id_list=72,71
    Error: The specified LUN group does not exist.
    Suggestion: Input a correct LUN group ID.
    Add LUN 72 to LUN group failed.
    Error: The specified LUN group does not exist.
    Suggestion: Input a correct LUN group ID.
    Add LUN 71 to LUN group failed.
    ------------------------2-------------------------
    admin:/>add lun_group lun lun_group_id=0 lun_id_list=72,71
    Error: The specified LUN already exists in the LUN group.
    Suggestion: Remove the specified LUN from the LUN group.
    Add LUN 72 to LUN group failed.
    Add LUN 71 to LUN group successfully.
    '''
    re = out.split('\n')
    cnt = len(re)
    for i in range(cnt):
        line = re[i]
        if line.startswith('Error'):
            show_err(re[i+2]+'['+line+']')
        elif line.endswith('successfully.'):
            show_msg(line)
        else:
            pass
            
    
    
def exec_command(channel,cmd):
    '''
    执行命令，返回输出
    '''
    output = ''
    try:
        tempOutput = ''
        if channel.send_ready():      
            channel.send(cmd + "\n")
        while not (tempOutput.strip().endswith("~>") or \
                   tempOutput.strip().endswith("#") or \
                   tempOutput.endswith("password:") or \
                   tempOutput.endswith("again:") or \
                   tempOutput.find("/>")>=0) or \
                   tempOutput.endswith("(y/n)") or \
                   tempOutput.endswith("--More--"):
            if tempOutput.endswith("--More--") and boolSend:
                # 空格表示下一页
                channel.send(" ")
                # logger.info("++++++++++++++send space for --More-- ++++++++++++++++++++")
            if (tempOutput.endswith("(press RETURN)") or tempOutput.split('\n')[-1]==':' )and boolSend:
                channel.send("\n")               
                #logger.info("++++++++++++++send space for --RETURN-- ++++++++++++++++++++")
            elif tempOutput.endswith("(y/n)") :
                if channel.send_ready():
                    #logger.debug('++++++++send key n for (y/n) ++++++++')
                    channel.send("y\n")

            if channel.recv_ready():
                tempOutput = tempOutput + (channel.recv(1024)).decode("UTF-8")
                #debug(tempOutput)
            time.sleep(0.1)
            tempOutput = tempOutput.lstrip()

        tempOutput = tempOutput.replace(cmd,'')
        tempOutput = tempOutput.replace('admin:/>','')
        tempOutput = tempOutput.replace('cli:/>','')
        tempOutput = tempOutput.strip()
        output = tempOutput
    
    except Exception as e:
        show_err(e)
    finally:
        return output

    
def get_channel(info):
    try:
        client = paramiko.SSHClient()
        client.set_missing_host_key_policy(paramiko.AutoAddPolicy())
        client.connect(info[0],22,info[1],info[2])
        channel = client.invoke_shell('shellName',1920,1080)
        if channel is None:
            raise Exception('open channel error')
        exec_command(channel,'\n') # 去掉前面的一串登陆提示
        show_msg('Login Success')
    except Exception as e:
        show_msg('连接shell出错，请检查网络或密码:')
        show_err(e)
        log(e)       
    return channel

if __name__ == '__main__':
    main()
    
