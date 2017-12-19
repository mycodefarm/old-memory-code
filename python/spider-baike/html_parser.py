# -*- coding: utf-8 -*
from bs4 import BeautifulSoup
import re
import urlparse
class HtmlParser(object):
    
    
    def _get_new_urls(self, page_url, soup):
        new_urls = set()
        links = soup.find_all('a', href=re.compile(r'/view/\d+\.htm'))
#         links = soup.find_all('a',)
        for link in links:
            new_url = link['href']
#             print new_url
            new_url_full = urlparse.urljoin(page_url, new_url)  
#             print new_url_full
            # new_url_full = 'http://baike.baidu.com/'+
            new_urls.add(new_url_full)
        return new_urls
    
    
    def _get_new_data(self, page_url, soup):
        res_data = {}
        # url
        res_data['url'] = page_url
        try:
            title_node = soup.find('dd',class_='lemmaWgt-lemmaTitle-title').find('h1')
            res_data['title'] = title_node.get_text()
            
            content_node = soup.find('div', class_='lemma-summary')
            res_data['summary'] = content_node.get_text()
        except Exception as e:
            print 'get data error ',e  
        return res_data
    
    
    def parse(self, page_url, html_cont):
        if page_url is None or html_cont is None:
            return None
        
        soup = BeautifulSoup(html_cont, 'html.parser', from_encoding='utf-8')
        new_urls = self._get_new_urls(page_url, soup)
        new_data = self._get_new_data(page_url, soup)
        return new_urls, new_data
        
    
    



