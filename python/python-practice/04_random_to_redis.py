#!/usr/bin/python
#-*-coding:utf-8-*-
'''
将 02 题生成的 200 个激活码（或者优惠券）保存到 redis 数据库中
'''
import redis
import uuid

r = redis.StrictRedis(host='localhost', port=6379)

for i in range(200):
    k = uuid.uuid1()
    r.set(str(i),k)


