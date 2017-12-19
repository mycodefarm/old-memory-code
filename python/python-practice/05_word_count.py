#-*-coding:utf-8-*-
import re

file = open('02_random.py','r')

count = 0
for line in file.readlines():
    words = re.findall(r"\b\w+\w*\b",line)
    # print(words)
    count+=len(words)

print(count)
