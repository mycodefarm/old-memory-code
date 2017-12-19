# -*- coding: utf-8 -*
import urllib2
class HtmlDownloader(object):
    
    
    def download(self,url):
        if url is None:
            return None
        rep = urllib2.urlopen(url)
        if rep.getcode()!=200:
            return None
        return rep.read()
    
    



