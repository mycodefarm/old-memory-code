# 1.什么是网络接口
网络接口是计算机与私有或公共网络之间的互连点。 网络接口通常是网络接口卡（NIC），但不必具有物理形式。 相反，网络接口可以用软件实现。 例如，环回接口（IPv4的127.0.0.1和IPv6的:: 1）不是物理设备，而是一个模拟网络接口的软件。 环回接口通常用于测试环境。

NetworkInterface对于多宿主系统是有用的，多系统是具有多个NIC的系统。 使用NetworkInterface，您可以指定要用于特定网络活动的NIC。

# 2.获取接口信息
包括子接口

```java
	public static void main(String[] args) throws SocketException, UnknownHostException {

		Enumeration<NetworkInterface> nifs = NetworkInterface.getNetworkInterfaces();
		while (nifs.hasMoreElements()) {
			NetworkInterface nif = nifs.nextElement();
			System.out.println("DisplatName:" + nif.getDisplayName());
			System.out.println("Name:" + nif.getName());
			displaySubInterfaces(nif);
		}
	}

	private static void displaySubInterfaces(NetworkInterface nif) {
		Enumeration<NetworkInterface> subIfs = nif.getSubInterfaces();
		for (NetworkInterface subIf : Collections.list(subIfs)) {
			System.out.println("\tSub Interface DisplayName:" + subIf.getDisplayName());
			System.out.println("\tSub Interface Name:" + subIf.getName());
		}
	}
```
结果：

```shell
DisplatName:Software Loopback Interface 1
Name:lo
DisplatName:WAN Miniport (SSTP)
Name:net0
DisplatName:WAN Miniport (L2TP)
Name:net1
DisplatName:WAN Miniport (PPTP)
Name:net2
DisplatName:WAN Miniport (PPPOE)
Name:ppp0
DisplatName:WAN Miniport (IPv6)
Name:eth0
DisplatName:WAN Miniport (Network Monitor)
Name:eth1
...
```
查看源码知道接口的信息：

```java
public final class NetworkInterface {
    private String name;
    private String displayName;
    private int index;
    private InetAddress addrs[];
    private InterfaceAddress bindings[];
    private NetworkInterface childs[];
    private NetworkInterface parent = null;
    private boolean virtual = false;
    private static final NetworkInterface defaultInterface;
    private static final int defaultIndex; /* index of defaultInterface */
    ...
```
# 3.获取接口地址
再看InetAddress:

```java
	private static String getInetAddresses(Enumeration<InetAddress> inetAddresses) throws UnknownHostException {
		StringBuffer sb = new StringBuffer();
		while (inetAddresses.hasMoreElements()) {
			InetAddress ad = inetAddresses.nextElement();
			sb.append(ad.getHostAddress() + " " + ad.getLocalHost()).append(';');
		}
		return sb.toString();
	}
```

