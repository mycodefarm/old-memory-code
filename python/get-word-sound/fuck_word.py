#!/usr/bin/env python
# coding=utf-8
import urllib2
import threading
import sys

#线程类
class MyThread(threading.Thread):
    def __init__(self,target,args):
        super(MyThread,self).__init__()
        self.target = target
        self.args = args
    
    def run(self):
        self.target(self.args)

def download(word):
    url = "http://www.chadanci.com//e/extend/s/file.php?type=0&world="+word

    req = urllib2.Request(url)
    req.add_header("User-Agent",
    "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")

    #延时
    res_data = urllib2.urlopen(req,timeout=3)
    mp3_url = res_data.read()
    #print mp3_url
    if mp3_url is None:
        return
    try:
        f = urllib2.urlopen(mp3_url) 
        with open("mp3/"+word+".mp3", "wb") as fword:
            fword.write(f.read()) 
        with open("done_word.txt","ab") as done:
            done.write(word+"\n")
    except:
        print "error1"

def process(file_name):
    #从文件把已下载单词加入列表里
    done = open('done_word.txt').read()
    done = done.split('\n')
    #继续下载
    with open(file_name) as f:
        words = f.readlines()
        num = len(words)
        i = 0
        width = num/100
        p = '#'
        while i<num:
            word = words[i].strip('\n')
            # print word
            try:
                if word not in done:
                    download(word)
                    #加入已下载列表
                    done.append(word)
            except:
                print "error2"
            i+=1
            if i%width==0:
                p+='#'
            sys.stdout.write(str((i*1.0/num)*100)+"% :"+p+"->"+"\r")
            sys.stdout.flush()

def main():
    t1 = MyThread(process,'word.txt')
    t1.start()
    t1.join()

if __name__=='__main__':
    main()
