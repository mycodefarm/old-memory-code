# -*- coding: utf-8 -*-
import url_manager, html_downloader, html_parser,html_outputer


class SpiderMain(object):
    
    def __init__(self):
        self.urls = url_manager.UrlManager()
        self.downloader = html_downloader.HtmlDownloader()
        self.parser = html_parser.HtmlParser()
        self.outputer = html_outputer.HtmlOutputer()
    
    def craw(self, root_url):
        self.urls.add_new_url(root_url)
        count = 1
        try:
            while self.urls.has_new_url():
                new_url = self.urls.get_new_url()
                print 'craw %d,%s'%(count,new_url)
                html_content = self.downloader.download(new_url)
                new_urls,new_data=self.parser.parse(new_url,html_content)
                self.urls.add_new_urls(new_urls)
                self.outputer.collect_data(new_data)
                count = count+1
                if count >100:
                    break
                
                self.outputer.output_html()
        except Exception as e:
            print 'craw error',e
    
    


#主程序
if __name__=="__main__":
    root_url = "http://baike.baidu.com/link?url=BAsbDteKF0fuT_wxRqiSvPAXEg-3GmDO0GqOawr2Mz1En5G-aMSCuQ1VVhs9f6x0" 
    obj_spider = SpiderMain()
    obj_spider.craw(root_url)
    
    
    
    
    