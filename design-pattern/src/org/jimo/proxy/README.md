# Proxy代理模式
将部分功能委托给代理人做，代理无法解决时再请求主人。

## Printable-共同接口
```java
public interface Printable {
    void setPrinterName(String name);

    String getPrinterName();

    void print(String txt);//打印，这里显示文字
}
```

## Printer-主人
```java
/**
 * Created by jimo on 17-9-2.
 * 打印机类，真正执行打印工作
 */
public class Printer implements Printable {

    String name;

    public Printer() {
        System.out.println("正在生成打印机实例...");
    }

    public Printer(String name) {
        this.name = name;
        System.out.println("正在生成打印机(" + name + ")实例...");
    }

    @Override
    public void setPrinterName(String name) {
        this.name = name;
    }

    @Override
    public String getPrinterName() {
        return name;
    }

    @Override
    public void print(String txt) {
        System.out.println(txt);
        //延时模拟
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print("...");
        }
        System.out.println("Over");
    }
}
```

## PrinterProxy-代理人
```java
public class PrinterProxy implements Printable {
    private String name;
    private Printer real;//本人

    public PrinterProxy() {
    }

    public PrinterProxy(String name) {
        this.name = name;
    }

    @Override
    public synchronized void setPrinterName(String name) {
        if (real != null) {
            real.setPrinterName(name);
        }
        this.name = name;
    }

    @Override
    public String getPrinterName() {
        return name;
    }

    @Override
    public void print(String txt) {
        realize();
        real.print(txt);
    }

    //生成本人实例，相当于代理人搞不定了才来找本人
    private synchronized void realize() {
        if (real == null) {
            real = new Printer(name);
        }
    }
}
```
## Main-客户端
```java
public class Main {
    public static void main(String[] args) {
        PrinterProxy proxy = new PrinterProxy("Jimo");
        System.out.println("现在名字是：" + proxy.getPrinterName());
        proxy.setPrinterName("Hehe");
        System.out.println("现在名字是：" + proxy.getPrinterName());
        proxy.print("hello world");
        /**
         现在名字是：Jimo
         现在名字是：Hehe
         正在生成打印机(Hehe)实例...
         hello world
         ...............Over
         */
    }
}
```

## 总结
可以做什么：
```java
提升处理速度。比如一个应用程序初始化时需要加载许多组件，有些组件加载时间长且暂时是不必要的，
就可以使用代理进行加载，在需要时才实例化，相当于懒加载。例如：瀑布流加载。
```
实际举例：
```java
HTTP代理：
位于客户端和服务器之间的软件，比如充当高速缓存，类似CDN，在缓存过期时才向服务器请求更新。
```
