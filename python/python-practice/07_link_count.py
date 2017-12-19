#-*-coding:utf-8-*-
import urllib2
from bs4 import BeautifulSoup
'''
找出百度首页所有的链接
'''
html = urllib2.urlopen('http://www.baidu.com').read()

soup = BeautifulSoup(html, 'html.parser')
la = soup.findAll('a')
lk = soup.findAll('link')
li = soup.findAll('img')

for a in la:
    print(a['href'])
for k in lk:
    print(k['href'])
for i in li:
    print(i['src'])
