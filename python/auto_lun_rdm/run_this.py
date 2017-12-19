#-*-coding:utf-8-*-
'''
Author@jackpler
整合：
将创建的lun映射成rdm，然后添加到vm
分别引入2个单独的模块：
1、创建lun
2、将lun加入vm
'''

import create_lun
import add_rdm_to_vm
from wputil import *

def auto_work():
    lun_infos = create_lun.run()
    if len(lun_infos)==0:
        show_err('Exit Program')
        return
    add_rdm_to_vm.run(lun_infos)
    
    show_msg('OK,Exit Program!')

if __name__=='__main__':
    auto_work()


    
