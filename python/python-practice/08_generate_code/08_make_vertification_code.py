#-*-coding:utf-8-*-
'''
生成随机验证码
'''
import string
import random
import Image
import ImageFont
import ImageFilter
import ImageDraw


#图片高度，默认50
def generate_code(h=50):
    w = h * 4
    img = Image.new('RGB', (w, h), (255, 255, 255))
    draw = ImageDraw.Draw(img)

    #画背景
    for i in range(w):
        for j in range(h):
            draw.point((i, j), rand_color1())

    #写随机字母
    font = ImageFont.truetype('../01_add_num/DroidSansFallbackFull.ttf', h / 2)
    for x in range(4):
        draw.text((h * x + 10, 10),
                  random.choice(string.digits), rand_color2(), font)

    #模糊化处理
    img = img.filter(ImageFilter.BLUR)
    #保存
    img.save('code.jpg')
    img.show()


#背景颜色
def rand_color1():
    return (random.randint(100, 255), random.randint(100, 255), random.randint(
        100, 255))


#字体颜色
def rand_color2():
    return (random.randint(0, 128), random.randint(0, 128), random.randint(
        0, 128))


if __name__ == '__main__':
    generate_code()
