package org.jimo.templatemethod;

/**
 * Created by root on 17-6-11.
 * 抽象的显示类：定义了应该显示的流程，让子类去实现
 */
public abstract class AbstractDisplay {
    abstract void open();

    abstract void print();

    abstract void close();

    /**
     * @Date: 17-6-11 下午1:05
     * @Author:root
     * @Desc: 显示流程如下
     */

    final void display() {
        open();
        for (int i = 0; i < 5; i++) {
            print();
        }
        close();
    }
}
