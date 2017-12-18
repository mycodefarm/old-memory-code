package org.jimo.prototype.framework;

/**
 * Created by root on 17-6-15.
 * 继承Cloneable才能使用clone方法
 */
public interface Product extends Cloneable {
    void use(String s);

    Product createClone();
}
