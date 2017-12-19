# -*- coding: utf-8 -*
class HtmlOutputer(object):
    
    def __init__(self):
        self.datas = []
    
    def collect_data(self,new_data):
        if new_data is None:
            return None
        self.datas.append(new_data)

    
    def output_html(self):
        fout = open('output.html','w')
        fout.write('<html>')
        fout.write("<head><meta http-equiv='content-type' content='text/html;charset=utf-8'></head>")
        fout.write('<body>')
        
        
        
        #因为ascii编码，所以要转换
        for data in self.datas:
            d = data['summary'].encode('utf-8')
            if d.find('长征精神')>=0:
                fout.write('<p>%s</p>'%data['summary'].encode('utf-8'))
        
        fout.write('</body>')
        fout.write('</html>')
#         fout.close()
        
    
    
    
    



