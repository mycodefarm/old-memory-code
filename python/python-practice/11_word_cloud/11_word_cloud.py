#-*-coding:utf-8-*-
import os
from wordcloud import WordCloud

text = open(u'me.txt').read().decode('utf-8')
font = '../01_add_num/DroidSansFallbackFull.ttf'

#方形词云
wordcloud = WordCloud(font_path=font).generate(text)

#show img
import matplotlib.pyplot as plt

plt.imshow(wordcloud)
plt.axis('off')
# plt.show()
wordcloud.to_file('wc1.png')

from PIL import Image
import numpy 

#加载背景图片
mask = numpy.array(Image.open('mask.jpg'))

wc = WordCloud(font_path=font,mask=mask)

wc.generate(text)

wc.to_file('wc2.png')

plt.figure()
plt.imshow(wc)
plt.axis("off")
plt.show()