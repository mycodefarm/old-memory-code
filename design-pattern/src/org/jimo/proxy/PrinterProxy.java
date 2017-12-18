package org.jimo.proxy;

/**
 * Created by jimo on 17-9-2.
 */
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
