package org.jimo.factory;

import org.jimo.factory.framework.Product;
import org.jimo.factory.idcard.IDCardFactory;
import org.jimo.factory.idcard.IDCardWithSerialFactory;

/**
 * Created by root on 17-6-11.
 */
public class Main {
    public static void main(String[] args) {
        IDCardFactory idCardFactory = new IDCardFactory();
        Product c1 = idCardFactory.create("jimo");
        Product c2 = idCardFactory.create("kali");
        Product c3 = idCardFactory.create("jone");
        c1.use();
        c2.use();
        c3.use();

        ////////////////
        IDCardWithSerialFactory idCardWithSerialFactory = new IDCardWithSerialFactory();
        Product c4 = idCardWithSerialFactory.create("小明");
        Product c5 = idCardWithSerialFactory.create("小花");
        Product c6 = idCardWithSerialFactory.create("小红");
        c6.use();
        c4.use();
        c5.use();
    }
}
