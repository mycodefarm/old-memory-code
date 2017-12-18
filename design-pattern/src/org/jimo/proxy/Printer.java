package org.jimo.proxy;

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
