#-*-coding:utf-8-*-
import BaseHTTPServer
'''
能够构造动态页面
'''
class RequestHandler(BaseHTTPServer.BaseHTTPRequestHandler):
    def do_GET(self):
        page = self.create_page()
        self.send_page(page)

    #动态页面
    Page = '''
    <html>
    <body>
    <table>
    <tr><td>Header</td><td>Value</td></tr>
    <tr><td>Date and Time</td><td>{date_time}</td></tr>
    <tr><td>Client Host</td><td>{client_host}</td></tr>
    <tr><td>Client Port</td><td>{client_port}</td></tr>
    <tr><td>Command</td><td>{command}</td></tr>
    <tr><td>Path</td><td>{path}</td></tr>
    </table>
    </body>
    </html>
    '''

    def create_page(self):
        value = {
            'date_time': self.date_time_string(),
            'client_host': self.client_address[0],
            'client_port': self.client_address[1],
            'command': self.command,
            'path': self.path
        }
        page = self.Page.format(**value)
        return page

    def send_page(self, page):
        self.send_response(200)
        self.send_header('Content-Type', 'text/html')
        self.send_header('Content-Length', str(len(page)))
        self.end_headers()
        self.wfile.write(page)

#----------------------------------------------------------
if __name__=='__main__':
    server_address = ('',8081)
    server = BaseHTTPServer.HTTPServer(server_address,RequestHandler)
    server.serve_forever()