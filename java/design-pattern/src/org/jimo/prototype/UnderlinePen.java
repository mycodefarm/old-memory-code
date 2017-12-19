package org.jimo.prototype;

import org.jimo.prototype.framework.Product;

/**
 * Created by root on 17-6-15.
 * 在给定字符串下画线
 */
public class UnderlinePen implements Product {

    private char underChar;

    public UnderlinePen(char underChar) {
        this.underChar = underChar;
    }

    @Override
    public void use(String s) {
        int len = s.getBytes().length;
        System.out.println("\"" + s + "\"");
        for (int i = 0; i < len + 4; i++) {
            System.out.print(underChar);
        }
        System.out.println();
    }

    @Override
    public Product createClone() {
        Product p = null;
        try {
            p = (Product) clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return p;
    }
}
