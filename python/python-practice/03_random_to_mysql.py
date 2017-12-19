#-*-coding:utf-8-*-
'''
将 02 题生成的 200 个激活码（或者优惠券）保存到 MySQL 关系型数据库中
'''
import MySQLdb
import uuid

conn = MySQLdb.connect('localhost', 'root', '', 'test', charset='utf8')
cursor = conn.cursor()

sql = 'create table if not exists mykey(mykey char(40) not null)'
cursor.execute(sql)

for i in range(200):
    k = uuid.uuid1()
    cursor.execute("insert into mykey values('%s')" % str(k))

cursor.close()
conn.commit()
conn.close()
