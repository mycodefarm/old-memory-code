# Python
## 01_add_num
给图片加上文字

![img](01_add_num/img.png?raw=true)---> ![result](01_add_num/re.png?raw=true)

## 02_random
生成随机数，这里选择了2种方式
## 03_random_to_mysql
将 02 题生成的 200 个激活码（或者优惠券）保存到 MySQL 关系型数据库中
## 04_random_to_redis
将 02 题生成的 200 个激活码（或者优惠券）保存到 redis 数据库中
## 05_word_count
使用正则统计英文单词个数
## 06_resize_img
批量改变图片大小
```
# python 06_resize_img.py
input image dir:/home/download/img
open dir error!
共转换了3张图片
```
## 07_link_count
找出网页的链接
## 08_generate_code
生成验证码
![code](08_generate_code/code.jpg?raw=true)
## 09_crawl_img
爬取一些网页上的图片
## 11_word_cloud
词云生成：

![wc2](11_word_cloud/wc2.png?raw=true)
![wc1](11_word_cloud/wc1.png?raw=true)
## 12_web_server
### 12_use_requests
requests库的简单使用，用来测试
### 12_web_server_1
最简单的一个web服务器框架，返回Hello World
### 12_web_server_2
获取一些客户端请求的值并返回，一个简单的动态页面
```shell
Header	Value
Date and Time	Thu, 20 Apr 2017 01:01:15 GMT
Client Host	127.0.0.1
Client Port	33136
Command	GET
Path	/
```
### 12_web_server_3
能够返回本地的文件,同时构造错误处理页面
```shell
# python 12_web_server_3.py
127.0.0.1 - - [20/Apr/2017 09:57:56] "GET /hah HTTP/1.1" 404 -
127.0.0.1 - - [20/Apr/2017 09:57:56] "GET /favicon.ico HTTP/1.1" 404 -
127.0.0.1 - - [20/Apr/2017 09:58:26] "GET /12_web_server_1.py HTTP/1.1" 200 -
127.0.0.1 - - [20/Apr/2017 09:59:59] "GET /12_web_server_2.py HTTP/1.1" 200 -
```
### 12_web_server_4
列出服务器目录,如果有index.html则呈现此页面

### 13_WordCloud_2
python wordcloud

![santi](13_word_cloud_2/santi.png)


