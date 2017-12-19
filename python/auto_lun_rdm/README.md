# 说明
这是一个针对华为OceanStor存储和vmware的虚拟机平台，执行创建LUN和将这些LUN映射成RDM盘再分配给指定的虚拟机的自动化程序。

使用者需要存储和python相关知识。

# 运行环境
此脚本保证在python2.7下正常运行，python3未兼容；

依赖的python库有：
1. paramiko,用于SSH连接；
2. pyvmomi-6.5.0,vmware官方python库，可在[pyvmomi](https://github.com/vmware/pyvmomi)下载
3. 上面的库所依赖的其他库：requests-2.18.4,six-1.10.0,urllib3-1.22,idna-2.6,chardet-3.0.4,certifi-2017.7.27.1

# 如何使用

1. 可以看到目录下是一些bat脚本和一个source-code文件夹；配置在source-code目录下的wpconfig.py里，里面有详细说明，下面说一下bat脚本的功能；
2. 可以看到有3类脚本：run开头的，clear开头的，del开头的；
3. 创建用run，删除用del，清除整个环境用clear；
4. 一般来说，只需要执行run-all就能够创建整个流程，运行clear-envirnment就可以根据提示清除整个环境；
5. run-create-lun-and-group:只创建配置文件里CREATE_LUN_CMD声明的LUN和加入LUN组；
6. run-add-rdm-from-file：将LUN映射出RDM加入配置里ADD_DISK_CMD定义的虚拟机里；这个什么时候用呢？
   假如使用del-all-rdm-disks脚本删除了RDM盘，可以不再重新创建lun，使用上次创建好的再加入vm；
7. del-created-luns：根据提示删除创建的LUN或LUN组；
8. del-temp-virtual-disk：这个是因为需要创建scsi控制器临时创建的虚拟磁盘，本身无用，再创建好RDM后会自动删除，
   假如发现有残留，可以执行此命令删除。

# 配置
所有的配置都在wpconfig.py里进行，里面都有说明：
```python
#存储登陆信息
NODE_INFO    = ("100.115.70.196","admin","Admin@storage1")

#linux登陆信息
LINUX_INFO = ('100.115.71.117','root','Huawei123')

'''
此命令是用于创建LUN并加入到相应组的

'''
CREATE_LUN_CMD = {
	'lun_info':[('LUN_Cache_Jrn',2,'4GB'),('LUN_Cache_Inst',2,'4GB'),('LUN_Cache_Data',2,'6GB')],
	'group_id':0
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
	'ctlr_unit':[(0,1),(0,2),(1,0),(1,1),(2,0),(3,0)],
	'datastore':'datastore1 (5)',
	'vm_name':'test'
}
```


# 关于输出

### 提示信息

```shell
^v^Add LUN naa.6346ac2100afeaf6181681b1000000de success
```
### 错误信息
```shell
========================ERROR====================
>.<get_valid_luns:config error,check the controll
```

### debug信息
```shell
------------------DEBUG--------------------
[u'LUN_Cache_Jrn0000', u'LUN_Cache_Jrn0001', u'L
```
### 命令信息
表示正在执行的命令
```shell
'****NOW exec cmd:'+cmd+' ****'
```
### 输入提示
```shell
>>>>>>>>>>>>>>>>>>>>>>>>>>>>Are you sure to delete all the Disks(include virtual and RDM disk)(y/n):y
```

### 清除环境的输出示例
```shell
**********************************************************************
"run add_rdm_to_vm.py -clear ......"
**********************************************************************
>>>>>>>>>>>>>>>>>>>>>>>>>>>>Are you sure to delete all the Disks(include virtual and RDM disk)(y/n):y
^v^now delete temp virtual disks...
^v^del temp virtual disk successfully.
^v^del disk vml.02000b00006346ac2100afeaf61d9d7fce00000174585347312020 successfully.
^v^del disk vml.02000c00006346ac2100afeaf61d9d812d00000175585347312020 successfully.
^v^del disk vml.02000d00006346ac2100afeaf61d9d819b00000176585347312020 successfully.
^v^del disk vml.02000e00006346ac2100afeaf61d9d81dd00000177585347312020 successfully.
^v^del disk vml.02000f00006346ac2100afeaf61d9d822800000178585347312020 successfully.
^v^del disk vml.02001000006346ac2100afeaf61d9d827400000179585347312020 successfully.
^v^del disk vml.02001100006346ac2100afeaf61d9d82bf0000017a585347312020 successfully.
^v^del disk vml.02001200006346ac2100afeaf61d9d83130000017b585347312020 successfully.
^v^del disk vml.02001300006346ac2100afeaf61d9d83550000017c585347312020 successfully.
^v^del disk vml.02001400006346ac2100afeaf61d9d84b10000017d585347312020 successfully.
^v^Total deleled 10 disks
>>>>>>>>>>>>>>>>>>>>>>>>>>>Do you want to delete the luns mapping which mapping to lun group 0(y/n):y
^v^Trying to login...
^v^Login Success
****NOW exec cmd:remove lun_group lun lun_group_id=0 lun_id_list=372,373,374,375,376,377,378,379,380,381 ****
^v^remove luns from lun_group successfully.
>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Do you want to delete all the luns(y/n):y
^v^Trying to login...
^v^Login Success
****NOW exec cmd:delete lun lun_id_list=372,373,374,375,376,377,378,379,380,381 ****
^v^delete successfully
**********************************************************************
run completed
**********************************************************************
请按任意键继续. . .
```

# 实现细节

主要功能是创建一系列LUN，加入LUN组，然后映射成RDM磁盘分配给虚拟机。

分成了2个单独的部分实现。

### create_lun.py

创建LUN和加入LUN组，这一功能可以单独运行run-create-lun-and-group.bat实现。

### add_rdm_to_vm.py

加入虚拟机

