# -*- coding: UTF-8 -*

#存储登陆信息
NODE_INFO    = ("100.115.70.196","admin","Admin@storage1")

#linux登陆信息
LINUX_INFO = ('100.115.71.117','root','Huawei123')

'''
此命令是用于创建LUN并加入到相应组的
'''
CREATE_LUN_CMD = {
	'lun_info':[('LUN_Base',1,'10GB'),('LUN_DATA',8,'100GB'),('LUN_LOG',1,'10GB')],
	'group_id':0,
	'pool_id':0
}

'''
下面的命令是和上面的磁盘一一对应
is_manual:True 或则 False，表示下面的配置是否生效，若为False，则那些编号自动生成
自动生成的算法：上面的lun_info里有几种lun就会使用几个控制器，然后根据每种lun的数量往后排unit_number

ctlr_unit:如果开始is_manual为True，此项需写，对应的控制器和通道编号,按上面的创建顺序写
注意：unit_number不能填15，15留给临时创建控制器的硬盘

datastore:数据存储的名字，表示vmdk的存储位置
vm_name:虚拟机名字，用于将磁盘分配的虚拟机对象，程序目录有一些命令可以只配置vm_name就行，因为只针对虚拟机操作
'''
ADD_DISK_CMD = {
	'is_manual':True,
	'ctlr_unit':[(1,0),(2,0),(3,0),(1,1),(2,1),(3,1),(1,2),(2,2),(3,2),(3,3)],
	'datastore':'datastore1 (5)',
	'vm_name':'Win 2012 R2 234.30_DV3'
}
