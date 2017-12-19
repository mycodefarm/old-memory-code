package org.jimo.proxy;

/**
 * Created by jimo on 17-9-2.
 */
public interface Printable {
    void setPrinterName(String name);

    String getPrinterName();

    void print(String txt);//打印，这里显示文字
}
