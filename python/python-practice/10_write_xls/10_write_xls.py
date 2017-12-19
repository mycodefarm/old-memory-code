#-*-coding:utf-8-*-
import xlsxwriter

with open('test.txt','r') as f:
    content = f.read()

dic = eval(content)

print(dic)

file = xlsxwriter.Workbook()
table = file.add_worksheet('test')


