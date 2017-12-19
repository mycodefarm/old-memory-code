#!/usr/bin/env python
#-*- coding: utf-8 -*-

from os import path
from PIL import Image
import numpy as np
import matplotlib.pyplot as plt
import os

from wordcloud import WordCloud, STOPWORDS

d = path.dirname(__file__)

font = os.path.join(os.path.dirname(__file__), "DroidSansFallbackFull.ttf")

# GBK解码汉字
text = open(path.join(d, 'santi.txt')).read().decode('gbk')

# 位于当前目录
mask = np.array(Image.open(path.join(d, "mask.jpg")))

#stopwords = set(STOPWORDS)
#stopwords.add("said")

wc = WordCloud(font_path=font,background_color="black", max_words=2000, mask=mask)
#               stopwords=stopwords)
# generate word cloud
wc.generate(text)

# 保存图片
wc.to_file(path.join(d, "santi.png"))

# show
plt.imshow(wc)
plt.axis("off")
plt.figure()
plt.imshow(mask, cmap=plt.cm.gray)
plt.axis("off")
plt.show()
