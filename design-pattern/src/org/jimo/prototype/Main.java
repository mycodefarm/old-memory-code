package org.jimo.prototype;

import org.jimo.prototype.framework.Manager;
import org.jimo.prototype.framework.Product;

/**
 * Created by root on 17-6-15.
 */
public class Main {
    public static void main(String[] args) {
        //准备
        Manager manager = new Manager();
        MessageBox messageBox = new MessageBox('*');
        UnderlinePen underlinePen = new UnderlinePen('-');
        UnderlinePen underlinePen2 = new UnderlinePen('~');
        manager.register("msg strong", messageBox);
        manager.register("pen strong", underlinePen);
        manager.register("pen weak", underlinePen2);
        //生成
        Product product1 = manager.create("msg strong");
        product1.use("hello world");
        Product product2 = manager.create("pen strong");
        product2.use("hello world");
        Product product3 = manager.create("pen weak");
        product3.use("hello world");
        /**
         ***************
         * hello world *
         ***************
         "hello world"
         ---------------
         "hello world"
         ~~~~~~~~~~~~~~~
         */
    }
}
