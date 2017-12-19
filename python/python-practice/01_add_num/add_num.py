# coding:utf-8
from PIL import Image,ImageDraw,ImageFont

def add_num(img):
    draw = ImageDraw.Draw(img)
    myfont = ImageFont.truetype(font='DroidSansFallbackFull.ttf', size=40) #也可以自定义字体
    fillColor = '#ff0000'
    width,height = img.size
    draw.text((width-40,0),'22',fill=fillColor,font=myfont)
    img.save('re.png','png')

if __name__=='__main__':
    img = Image.open('img.png')
    add_num(img)