#-*-coding:utf-8-*-
import BaseHTTPServer
'''
返回静态页面
'''
class RequestHandler(BaseHTTPServer.BaseHTTPRequestHandler):
    #要返回的页面
    Page = '''
    <html>
    <body>
    <h1>Hello World!</h1>
    </body>
    </html
    '''

    #Get请求处理
    def do_GET(self):
        self.send_response(200)
        self.send_header('Content-Type','text/html')
        self.send_header('Content-Length',str(len(self.Page)))
        self.end_headers()
        self.wfile.write(self.Page)
#-----------------------------------------------

if __name__=='__main__':
    server_address = ('',8081)
    server = BaseHTTPServer.HTTPServer(server_address,RequestHandler)
    server.serve_forever()