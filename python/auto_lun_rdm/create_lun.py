#-*-coding:utf-8-*-

import paramiko
import sys
import time
import re
from wpconfig import *
from wputil import *
from model import Lun


lun_names = [] # 新创建的LUN的名字
lun_infos = [] # 存放新加入的LUN的WWM和其他信息为了添加磁盘时识别，每个元素是个Lun类
total = 0 # 从创建语句里解析出的lun的数量，用来和实际成功创建的做对比，不相等则需要回退

def main():
    args = sys.argv
    if len(args)<=1:
        print('Usage:\n-c(create all)\n-d(delete all)')
        sys.exit(1)
    if args[1]=='-c':
        run()
    elif args[1]=='-d':
        del_created_luns()
    else:
        print('Uknown cmd')

def run():
    '''
    整个自动化工作:从创建LUN到加入组
    返回创建的lun信息
    '''
    
    global total
    channel,client = get_channel(NODE_INFO)

    pool_id = CREATE_LUN_CMD.get('pool_id',-1)
    check_pool_id(pool_id,channel)
    for lun in CREATE_LUN_CMD['lun_info']:
        total += lun[1]
        exec_create_lun(channel,lun,pool_id)
        time.sleep(0.5)
    # 将LUN加入到组里
    group_id = CREATE_LUN_CMD['group_id']
    exec_add_lun_group(channel,group_id)
    
    try:
        channel.close()
        client.close()
    except:
        pass
    return lun_infos
def check_pool_id(pool_id,channel):
    '''
    检查pool_id是否合法，不合法则退出程序
    '''
    cmd = 'show storage_pool general pool_id='+str(pool_id)
    out = exec_command(channel,cmd).strip()
    if out.find('[pool_id=?]')>-1:
        show_err('The pool %s is not exist.'%str(pool_id))
        sys.exit(1)
    
    
def del_created_luns():
    '''
    这个删除是单独执行，从文件读取lun再删除
    先询问是否删除映射，再询问是否删除LUN
    '''
    luns = read_luns_from_file()
    if not luns:
        show_msg('no luns can be deleted.check the [luns.txt] file')
    ok = my_ask('Do you want to delete the luns mapping which mapping to lun group %s(y/n):'\
                        %(luns[0].group_id))
    if ok:
        exec_delete_lungroup_mapping(luns) 
    # 如果用户不删除lun组，也就不问他是否删除lun了
    if ok:
        ok = my_ask('Do you want to delete all the luns(y/n):')
        if ok:
            exec_delete_lun(luns=luns,del_group=False)

    
def exec_create_lun(channel,lun,pool_id):
    #out = 'create fddsfdsfs\ncreate dsfsdfdsf successfully'
    # 构建语句
    # 若创建1个lun，则不能有number参数
    if lun[1]<=1:
        cmd = 'create lun name=%s pool_id=%s capacity=%s lun_type=thin compression_enabled=yes dedup_enabled=no'\
              %(lun[0],str(pool_id),str(lun[2]))     
    else:
        cmd = 'create lun name=%s number=%s pool_id=%s capacity=%s lun_type=thin compression_enabled=yes dedup_enabled=no'\
              %(lun[0],str(lun[1]),str(pool_id),str(lun[2]))
    show_cmd(cmd)
    cmd = cmd.strip()
    # 执行
    out = exec_command(channel,cmd)
    # 对结果的处理也得分开
    if lun[1]<=1:
        '''
        admin:/>create lun name=jimo pool_id=0 capacity=2GB lun_type=thin
        Command executed successfully.
        admin:/>create lun name=jimo pool_id=0 capacity=2GB lun_type=thin
        Error: The object name already exists.
        Suggestion: Enter a new name.
        '''
        if out.endswith('successfully.') or out.endswith('successfully.\r'):
            show_msg(out)
            lun_names.append(lun[0])
        elif out.startswith('Error: The object name already exists'):
                show_err('The object name already exists,retry another name')
                new_name = lun[0]+'0' # 新名字
                lun1 = (new_name,lun[1],lun[2])
                exec_create_lun(channel,lun1,pool_id) # 递归执行
        else:
            show_err(out)
    else:
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
        lines = out.split('\n')
        for line in lines:
            if line.endswith('successfully.') or line.endswith('successfully.\r'):
                show_msg(line)
                # 若成功创建LUN，则获得新创建LUN的名字
                tlun = re.sub(r'(Create|successfully\.)','',line)
                name = tlun.strip()[4:]
                lun_names.append(name)
            elif line.startswith('Error: The object name already exists'):
                show_err('The object name already exists,retry another name')
                new_name = lun[0]+'0' # 新名字
                lun1 = (new_name,lun[1],lun[2])
                exec_create_lun(channel,lun1,pool_id) # 递归执行
            else:
                show_err(line)

def exec_show_lun(channel):
    '''
    对LUN的字符串结果进行处理，描述为python的list结构
    '''
    out = exec_command(channel,'show lun general')
    luns = out.split('\n')
    return luns

def exec_delete_lungroup_mapping(luns,channel=None):
    # 删除到lun组的映射
    if not channel:
        channel,client = get_channel(NODE_INFO)
    
    list_ids = ''
    group_id = luns[0].group_id 
    for lun in luns:
        list_ids += lun.id+','
    list_ids = list_ids[:-1]
    
    del_lun_group_luns_cmd = 'remove lun_group lun lun_group_id='+str(group_id)+' lun_id_list='+list_ids
    show_cmd(del_lun_group_luns_cmd)
    out = exec_command(channel,del_lun_group_luns_cmd)
    if out.endswith('failed.'):
        show_err('remove lun from lun_group failed,please do it manually!')
    else:
        show_msg('remove luns from lun_group successfully.')

def exec_delete_lun(channel=None,luns=None,del_group=False):
    '''
    根据luns里的id删除lun
    del_group:是否删除lun到组的映射，不是删除lun组
    '''
    # 若luns为空，则使用全局lun_infos表示是在一起执行，否则是单独的，单独不需要回滚
    if not luns:
        luns = lun_infos
        if not luns:
            return
    
    if not channel:
        channel,client = get_channel(NODE_INFO)
        
    list_ids = ''
    group_id = luns[0].group_id 
    for lun in luns:
        list_ids += lun.id+','
    list_ids = list_ids[:-1]

    if del_group:
        exec_delete_lungroup_mapping(luns)

    # 然后删除lun
    
    delete_lun_cmd = 'delete lun lun_id_list='+list_ids
    show_cmd(delete_lun_cmd)
    out = exec_command(channel,delete_lun_cmd)
    '''
    admin:/>delete lun lun_id_list=100
    WARNING: You are about to delete LUN. This operation will delete the data on the LUN.
    Suggestion: Before performing this operation, ensure that the data on the LUN has been backed up or is no longer necessary.
    Have you read warning message carefully?(y/n)y

    Are you sure you really want to perform the operation?(y/n)y
    Error: The specified LUN does not exist.
    Suggestion: Enter a correct LUN ID.
    Delete LUN 100 failed.
    --------------------------------------------------------------------
    admin:/>delete lun lun_id_list=138
    WARNING: You are about to delete LUN. This operation will delete the data on the LUN.
    Suggestion: Before performing this operation, ensure that the data on the LUN has been backed up or is no longer necessary.
    Have you read warning message carefully?(y/n)y

    Are you sure you really want to perform the operation?(y/n)y
    Error: The specified LUN already exists in the LUN group.
    Suggestion: Remove the specified LUN from the LUN group.
    Delete LUN 138 failed.
    --------------------------------------------------------------------
    admin:/>delete lun lun_id_list=138
    WARNING: You are about to delete LUN. This operation will delete the data on the LUN.
    Suggestion: Before performing this operation, ensure that the data on the LUN has been backed up or is no longer necessary.
    Have you read warning message carefully?(y/n)y

    Are you sure you really want to perform the operation?(y/n)y
    Delete LUN 138 successfully.
    '''
    if out.endswith('successfully.'):
        show_msg('delete successfully')
    else:
        show_err('delete failed,maybe luns did not exist,please check manually')
        show_msg('Please check the output and correct the cmd and retry!')

def create_lun_group():
    '''
    随机一个名字，创建Lun组,返回group_id
    '''
    name = 'LUNGroup_'+str(time.time())[:10]
    cmd = 'create lun_group name='+name
    
    
def exec_add_lun_group(channel,group_id):
    '''
    将一些LUN加入LUN组：
    首先show出所有LUN，然后根据名字查出他们的id，然后构造命令执行，返回处理结果
    '''
    #debug(lun_names)
    
    luns = exec_show_lun(channel)
    # 构造id
    list_ids = ''
    for s in luns:
        lun = s.split() # 这又是一个list
        if len(lun)<8:
            continue
        lun_id,lun_name,lun_wwn = lun[0],lun[1],lun[7]
        if lun_name in lun_names:
            list_ids += lun_id+','
            info = Lun(lun_name,lun_wwn,lun_id,group_id)
            lun_infos.append(info)  #####

    if not list_ids.endswith(','):
        show_err('no lun create')
        return

    # 先判断LUN组是否存在，否则终止程序
    check_group_cmd = 'show lun_group lun lun_group_id='+str(group_id)
    out = exec_command(channel,check_group_cmd)
    if out.startswith('Error'):
        show_err(out)
        exec_delete_lun(channel,lun_infos)
        sys.exit(1)
    
    list_ids = list_ids[:-1] # 去掉末尾的逗号

    # 在加入组之前检查是否创建了和预期一样多的lun，否则需要回退
    id_len = len(list_ids.split(',')) # 创建成功的数量

    if id_len != total:
        show_err('Total %s,created %s,now rowback...'%(str(total),str(id_len)))
        exec_delete_lun(channel,lun_infos)
        sys.exit(1)

    add_group_cmd = 'add lun_group lun lun_group_id='+str(group_id)+' lun_id_list='
    add_group_cmd = add_group_cmd+list_ids
    show_cmd(add_group_cmd)
    
    #执行命令
    out = exec_command(channel,add_group_cmd)
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
    sucnt = 0 # 加入组成功的数量
    for i in range(cnt):
        line = re[i]
        if line.startswith('Error'):
            show_err(re[i+2]+'['+line+']')
        elif line.endswith('successfully.') or line.endswith('successfully.\r'):
            show_msg(line)
            sucnt += 1
        else:
            pass
        
    if sucnt != total:
        show_err('Total %s,add to group %s,now rowback...'%(str(total),str(sucnt)))
        exec_delete_lun(channel,lun_infos)
        sys.exit(1)     

    # 最后成功了，将lun信息写入文件，供单独使用
    with open('luns.txt','w') as f:
        txt = ''
        for lun in lun_infos:
            txt = txt + str(lun) + '\n'
        f.writelines(txt)
    
def exec_command(channel,cmd):
    #debug(channel)
    #debug(client)
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
        show_err('exec_command:'+e)
    finally:
        return output

    
def get_channel(info):
    try:
        show_msg('Trying to login...')
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
    return channel,client

if __name__ == '__main__':
    main()
    
