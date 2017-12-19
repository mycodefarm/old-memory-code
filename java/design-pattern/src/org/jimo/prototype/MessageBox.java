package org.jimo.prototype;

import org.jimo.prototype.framework.Product;

/**
 * Created by root on 17-6-15.
 * 具体的产品类
 */
public class MessageBox implements Product {

    private char decoChar;

    public MessageBox(char decoChar) {
        this.decoChar = decoChar;
    }

    /**
     * @Date: 17-6-15 上午8:51
     * @Author:root
     * @Desc: 将传入的字符串s用decoChar包围起来
     */

    @Override
    public void use(String s) {
        int len = s.getBytes().length;
        for (int i = 0; i < len + 4; i++) {
            System.out.print(decoChar);
        }
        System.out.println();
        System.out.println(decoChar + " " + s + " " + decoChar);
        for (int i = 0; i < len + 4; i++) {
            System.out.print(decoChar);
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
