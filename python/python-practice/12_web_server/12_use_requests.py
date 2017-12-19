#-*-coding:utf-8-*-
import requests

resp = requests.get('http://aosabook.org/en/500L/web-server/testpage.html')
print 'status code:',resp.status_code
print 'content-length:',resp.headers['content-length']
print resp.text