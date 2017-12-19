#-*-coding:utf-8-*-
import string,random

str = string.ascii_letters+"0123456789"

def generate(count=200,len=20):
    for i in range(count):
        re = ''
        for j in range(len):
            re+=random.choice(str)
        print(re)

if __name__=='__main__':
    generate(10)