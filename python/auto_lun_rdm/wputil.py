#-*-coding:utf-8-*-
'''
Author@jackpler
工具：日志，打印
'''
from model import Lun

def show_msg(msg):
    print('^v^'+str(msg))

def show_err(err):
    print('====================ERROR=========================')
    print('>.<'+str(err))

def show_cmd(cmd):
    print('****NOW exec cmd:'+cmd+' ****')

def debug(info):
    print('------------------DEBUG---------------------')
    print(info)

    
def log(info):
    '''
    写入日志
    '''
    pass

import os

def read_luns_from_file():
    '''从文件里读取LUN'''
    lun_infos = []
    path = os.path.join(os.path.abspath('.'),'luns.txt')
    with open(path,'r') as f:
        luns = f.read().strip().split('\n')
        for temp in luns:
            x = temp.split(',')
            lun = Lun(x[0],x[1],x[2],x[3])
            lun_infos.append(lun)
    return lun_infos


def my_ask(question):
    '''
    得到用户输入
    '''
    ok = False
    q = (100-len(question))*'>'+question
    while 1:
        cmd = raw_input(q)
        cmd = cmd.lower()
        if cmd=='y':
            ok = True
            break
        elif cmd=='n':
            ok = False
            break
    return ok
