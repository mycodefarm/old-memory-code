#-*-coding:utf-8-*-
'''
Author@jackpler
实体类
'''

class Lun:

    def __init__(self,name,wwn,lun_id,group_id):
        self.name = name
        self.wwn = wwn
        self.id = lun_id
        self.group_id = group_id

    def __str__(self):
        return str(self.name+','+self.wwn+','+str(self.id)+','+str(self.group_id))

class RDMLun:

    def __init__(self,wwn,ctrlr,unit_num):
        self.canonicalName = 'naa.'+wwn
        self.ctrlr = ctrlr
        self.unit_num = unit_num

    def __str__(self):
        return str('['+self.canonicalName+','+str(self.ctrlr)+','+str(self.unit_num)+']')
