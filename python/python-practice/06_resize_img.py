#-*-coding:utf-8-*-
import Image
import os
'''
将一个目录的图片尺寸转为固定大小
'''
path = raw_input("input image dir:")
cnt = 0
try:
    for f in os.listdir(path):
        img = Image.open(os.path.join(path, f))
        width, height = img.size
        out = img
        if width > 640:
            width = 640
        if height > 480:
            height = 480
        out = img.resize((width, height), Image.ANTIALIAS)
        out.save(path + '/re/' + f)
        cnt+=1
except:
    print("open dir error!")
finally:
    print("共转换了%d张图片"%cnt)