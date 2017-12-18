

# 1.构造URL

```java
URL context = new URL("http://example.com/");
URL pageURL = new URL(context,"page.html");
```
处理特殊字符

```java
		try {
			URI uri = new URI("http", "example.com", "/hello world/", "");
			URL url = uri.toURL();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
```
# 2.解析URL

```java
	public static void parseURL() throws Exception {
		URL aURL = new URL("http://example.com:80/docs/books/tutorial" 
				+ "/index.html?name=networking#DOWNLOADING");
		System.out.println("protocol = " + aURL.getProtocol());
		System.out.println("authority = " + aURL.getAuthority());
		System.out.println("host = " + aURL.getHost());
		System.out.println("port = " + aURL.getPort());
		System.out.println("path = " + aURL.getPath());
		System.out.println("query = " + aURL.getQuery());
		System.out.println("filename = " + aURL.getFile());
		System.out.println("ref = " + aURL.getRef());
	}
```
结果如下：

```java
protocol = http
authority = example.com:80
host = example.com
port = 80
path = /docs/books/tutorial/index.html
query = name=networking
filename = /docs/books/tutorial/index.html?name=networking
ref = DOWNLOADING
```
# 3.直接从URL读取内容

```java
import java.io.*;
import java.net.URL;

public class URLReader {
	public static void main(String[] args) throws IOException {
		URL url = new URL("http://www.baidu.com");
		BufferedReader in = new BufferedReader(
				new InputStreamReader(url.openStream()));

		String line;
		while ((line = in.readLine()) != null) {
			System.out.println(line);
		}
		in.close();
	}
}
```
# 4.使用Connection连接

```java
	public static void main(String[] args) {
		try {
			URL url = new URL("http://www.baidu.com");
			URLConnection conn = url.openConnection();
			conn.connect();
		} catch (MalformedURLException e) {
			// URL 解析失败
		} catch (IOException e) {
			// 打开connection失败
		}
	}
```
# 5.使用URLConnection读取
读取，注意和前面的区别：

```java
public class URLConnectionReader {
	public static void main(String[] args) throws Exception {
		URL url = new URL("http://www.baidu.com");
		URLConnection conn = url.openConnection();
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(conn.getInputStream()));

		String line;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}
		reader.close();
	}
}
```
# 6.写入数据
写入流程如下：
1. Create a URL.
2. Retrieve the URLConnection object.
3. Set output capability on the URLConnection.
4. Open a connection to the resource.
5. Get an output stream from the connection.
6. Write to the output stream.
7. Close the output stream.

以一个从服务器反转字符串的请求和接受为例：
```java
public class URLConnectionWrite {
	public static void main(String[] args) {
		String urlStr = "http://example.com/reverseServlet";
		try {
			String reverseStr = "I need to be reverse";
			reverseStr = URLEncoder.encode(reverseStr, "utf-8");

			// 写入数据
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);// 设置为输出
			OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
			writer.write("string=" + reverseStr);// 传参数给servlet
			writer.close();

			// 接受服务器返回结果
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			reader.close();
		} catch (UnsupportedEncodingException e) {
			// 编码错误
		} catch (MalformedURLException e) {
			// URL解析出错
		} catch (IOException e) {
			// IO错误
		}
	}
}
```
servlet代码：
```java
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.net.*;

public class ReverseServlet extends HttpServlet
{
    private static String message = "Error during Servlet processing";
    
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            int len = req.getContentLength();
            byte[] input = new byte[len];
        
            ServletInputStream sin = req.getInputStream();
            int c, count = 0 ;
            while ((c = sin.read(input, count, input.length-count)) != -1) {
                count +=c;
            }
            sin.close();
        
            String inString = new String(input);
            int index = inString.indexOf("=");
            if (index == -1) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().print(message);
                resp.getWriter().close();
                return;
            }
            String value = inString.substring(index + 1);
            
            //decode application/x-www-form-urlencoded string
            String decodedString = URLDecoder.decode(value, "UTF-8");
            
            //reverse the String
            String reverseStr = (new StringBuffer(decodedString)).reverse().toString();
            
            // set the response code and write the response data
            resp.setStatus(HttpServletResponse.SC_OK);
            OutputStreamWriter writer = new OutputStreamWriter(resp.getOutputStream());
            
            writer.write(reverseStr);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            try{
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().print(e.getMessage());
                resp.getWriter().close();
            } catch (IOException ioe) {
            }
        }
        
    }  
        
}
```
