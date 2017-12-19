package org.jimo.proxy;

/**
 * Created by jimo on 17-9-2.
 */
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
