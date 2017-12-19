#-*-coding:utf-8-*-
import BaseHTTPServer
import os
'''
列出服务器目录
'''
'''
下面是3种case情况，每个有两个方法，test（检测是否满足处理条件）和act（处理请求）
'''


class case_no_file(object):
    '''文件不存在'''

    def test(self, handler):
        return not os.path.exists(handler.full_path)

    def act(self, handler):
        raise Exception("'{0}' not found ".format(handler.path))


class case_file_exist(object):
    '''文件存在'''

    def test(self, handler):
        return os.path.isfile(handler.full_path)

    def act(self, handler):
        handler.handle_file(handler.full_path)


class case_always_fail(object):
    '''当总是失败时'''

    def test(self, handler):
        return True

    def act(self, handler):
        raise Exception("Unknown Object '{0}'".format(handler.path))


class case_directory_index_file(object):
    '''给出当前目录下的index.html文件，如果有的话'''

    def index_path(self, handler):
        return os.path.join(handler.full_path, 'index.html')

    def test(self, handler):
        return os.path.isdir(handler.full_path) and \
        os.path.isfile(self.index_path(handler))

    def act(self, handler):
        handler.handle_file(self.index_path(handler))


class case_directory_no_index_file(object):
    '''列出当前所有目录'''

    def index_path(self, handler):
        return os.path.join(handler.full_path, 'index.html')

    def test(self, handler):
        return os.path.isdir(handler.full_path) and \
        not os.path.isfile(self.index_path(handler))

    def act(self, handler):
        handler.list_dir(handler.full_path)


class RequestHandler(BaseHTTPServer.BaseHTTPRequestHandler):
    '''构造处理情况,最后一个总是失败'''
    Cases = [
        case_no_file(), case_file_exist(), case_directory_index_file(),
        case_directory_no_index_file(), case_always_fail()
    ]

    def do_GET(self):
        try:
            #文件全路径
            self.full_path = os.getcwd() + self.path

            for case in self.Cases:
                if case.test(self):
                    case.act(self)
                    break
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
        print("error")
        content = self.ErrorPage.format(path=self.path, msg=msg)
        self.send_content(content, 404)

    #默认状态码200,错误时改变
    def send_content(self, content, status=200):
        self.send_response(status)
        self.send_header('Content-Type', 'text/html')
        self.send_header('Content-Length', str(len(content)))
        self.end_headers()
        self.wfile.write(content)

    '''列出该目录所有文件夹'''
    List_Dir_Page = '''
     <html>
        <body>
        <ul>
        {0}
        </ul>
        </body>
    </html>
    '''

    def list_dir(self, path):
        try:
            dirs = os.listdir(path)
            bullets = [
                '<li>{0}</li>'.format(e) for e in dirs if not e.startswith('.')
            ]
            page = self.List_Dir_Page.format('\n').join(bullets)
            self.send_content(page)
        except OSError as msg:
            msg = "'{0}' cannot be listed:{1}".format(self.path, msg)
            self.handle_error(msg)


if __name__ == '__main__':
    server_address = ('', 8081)
    server = BaseHTTPServer.HTTPServer(server_address, RequestHandler)
    server.serve_forever()