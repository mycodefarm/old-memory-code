#-*-coding:utf-8-*-
import urllib
from bs4 import BeautifulSoup

url = 'http://tieba.baidu.com/p/2166231880'
html = urllib.urlopen(url).read()

soup = BeautifulSoup(html,'html.parser')

imgs = soup.findAll('img',pic_type='0')
count = 0
for img in imgs:
    name = 'img/'+str(count)+'.jpg'
    content = urllib.urlopen(img['src']).read()
    with open(name,'wb') as f:
        f.write(content)
    count+=1

