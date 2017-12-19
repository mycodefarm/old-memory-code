#-*-coding:utf-8-*-
import BaseHTTPServer
import os
'''
处理返回本地的文件,同时构造错误处理页面
'''


class RequestHandler(BaseHTTPServer.BaseHTTPRequestHandler):
    def do_GET(self):
        try:
            full_path = os.getcwd() + self.path

            #若路径不存在
            if not os.path.exists(full_path):
                raise Exception("{0} not found".format(self.path))
            #如果是一个文件
            elif os.path.isfile(full_path):
                self.handle_file(full_path)
            else:
                raise Exception("Unknown Object {0}".format(self.path))
        except Exception as msg:
            self.handle_error(msg)

    def handle_file(self, full_path):
        try:
            with open(full_path, 'rb') as f:
                content = f.read()
            self.send_content(content)
        except IOError as msg:
            msg = "{0} cannot be read: {1}".format(self.path, msg)
            self.handle_error(msg)

    ErrorPage = '''
    <html>
    <body>
    <h1>Error Processing {path}</h1>
    <p>{msg}</p>
    </body>
    </html>
    '''

    def handle_error(self, msg):
        content = self.ErrorPage.format(path=self.path, msg=msg)
        self.send_content(content, 404)

    #默认状态码200,错误时改变
    def send_content(self, content, status=200):
        self.send_response(status)
        self.send_header('Content-Type', 'text/html')
        self.send_header('Content-Length', str(len(content)))
        self.end_headers()
        self.wfile.write(content)


#------------------------------------------------------------
if __name__ == '__main__':
    server_address = ('', 8081)
    server = BaseHTTPServer.HTTPServer(server_address, RequestHandler)
    server.serve_forever()