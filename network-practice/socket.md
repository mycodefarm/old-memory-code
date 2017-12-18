# 1.什么是Socket
Socket是在网络上运行的两个程序之间的双向通信链路的一个端点。
Socket绑定到端口号，以便TCP层可以识别数据被发送到的应用程序。

关键：双向通信和通过端口识别程序。

![1](http://docs.oracle.com/javase/tutorial/figures/networking/5connect.gif)
![2](http://docs.oracle.com/javase/tutorial/figures/networking/6connect.gif)

# 2.客户端
建立客户端的步骤：
1. Open a socket.
2. Open an input stream and output stream to the socket.
3. Read from and write to the stream according to the server's protocol.
4. Close the streams.
5. Close the socket.

除了第3步要根据实际修改，其他都一样。

# 3.服务端
遵循以下步骤：
1. Gets the socket's input and output stream and opens readers and writers on them.
2. Initiates communication with the client by writing to the socket.
3. Communicates with the client by reading from and writing to the socket.

# 4.例子：Echo
客户端：
```java
public class EchoClient {
	public static void main(String[] args) {
		// 服务端信息
		String host = "localhost";
		int port = 7;
		try (Socket echoSocket = new Socket(host, port);
				PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true); // autoFlash
				BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
				) {
			String userInput;
			while ((userInput = stdIn.readLine()) != null) {
				out.println(userInput);// 发送给服务端
				System.out.println(in.readLine());// 接收服务端响应
			}
		} catch (UnknownHostException e) {
			// host error
		} catch (IOException e) {
			// io error
		}
	}
}
```
服务端：
```java
public class EchoServer {
	public static void main(String[] args) {
		int port = 7;// echo 协议端口号
		try (ServerSocket server = new ServerSocket(port);
				Socket clientSocket = server.accept();
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				) {
			String input = null;
			while ((input = in.readLine()) != null) {
				out.println(input); // 回复客户端
			}
		} catch (IOException e) {
			// io error
			System.err.println(e);
		}
	}
}
```
**注意：**
1. unix下需要root权限，若是运行IDE，使用gksu或则kdesu打开程序
2. try(){}的用法从jdk7开始，()里的流会自动关闭


# 5.例子：KnockKnock
服务端：
```java
public class KnockKonckServer {
	public static void main(String[] args) {
		int port = 4444;

		try (
				//1.
				ServerSocket server = new ServerSocket(port);
				Socket client = server.accept();
				PrintWriter out = new PrintWriter(client.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				) {
			//2.
			String input, output;
			KnockKnockProtocol kkp = new KnockKnockProtocol();
			output = kkp.processInput(null);
			out.println(output);// welcome
			//3.
			while ((input = in.readLine()) != null) {
				output = kkp.processInput(input);
				out.println(output);
				if(output.equals("Bye.")) {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
```
客户端：
```java
public class KnockKnockClient {
	public static void main(String[] args) {
		String host = "localhost";
		int port = 4444;

		try (Socket socket = new Socket(host, port);
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
				) {
			String fromUser, fromServer;
			while ((fromServer = in.readLine()) != null) {
				System.out.println("Server: " + fromServer);
				if (fromServer.endsWith("Bye.")) {
					break;
				}
				//send to server
				fromUser = stdIn.readLine();
				if (fromUser != null) {
					System.err.println("Client: " + fromUser);
					out.println(fromUser);
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
```
KnockKnockProtocol：
```java
/**
 * 
 * @author www.oracle.com
 *
 */
public class KnockKnockProtocol {
	private static final int WAITING = 0;
	private static final int SENTKNOCKKNOCK = 1;
	private static final int SENTCLUE = 2;
	private static final int ANOTHER = 3;

	private static final int NUMJOKES = 5;

	private int state = WAITING;
	private int currentJoke = 0;

	private String[] clues = { "Turnip", "Little Old Lady", "Atch", "Who", "Who" };
	private String[] answers = { "Turnip the heat, it's cold in here!", "I didn't know you could yodel!", "Bless you!",
			"Is there an owl in here?", "Is there an echo in here?" };

	public String processInput(String theInput) {
		String theOutput = null;

		if (state == WAITING) {
			theOutput = "Knock! Knock!";
			state = SENTKNOCKKNOCK;
		} else if (state == SENTKNOCKKNOCK) {
			if (theInput.equalsIgnoreCase("Who's there?")) {
				theOutput = clues[currentJoke];
				state = SENTCLUE;
			} else {
				theOutput = "You're supposed to say \"Who's there?\"! " + "Try again. Knock! Knock!";
			}
		} else if (state == SENTCLUE) {
			if (theInput.equalsIgnoreCase(clues[currentJoke] + " who?")) {
				theOutput = answers[currentJoke] + " Want another? (y/n)";
				state = ANOTHER;
			} else {
				theOutput = "You're supposed to say \"" + clues[currentJoke] + " who?\"" + "! Try again. Knock! Knock!";
				state = SENTKNOCKKNOCK;
			}
		} else if (state == ANOTHER) {
			if (theInput.equalsIgnoreCase("y")) {
				theOutput = "Knock! Knock!";
				if (currentJoke == (NUMJOKES - 1))
					currentJoke = 0;
				else
					currentJoke++;
				state = SENTKNOCKKNOCK;
			} else {
				theOutput = "Bye.";
				state = WAITING;
			}
		}
		return theOutput;
	}
}
```
结果：
```java
Server: Knock! Knock!
Who's there?
Client: Who's there?
Server: Turnip
Turnip who?
Client: Turnip who?Server: Turnip the heat, it's cold in here! Want another? (y/n)

Client: Server: Bye.
```

