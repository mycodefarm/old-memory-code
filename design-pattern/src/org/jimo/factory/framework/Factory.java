package org.jimo.factory.framework;

/**
 * Created by root on 17-6-11.
 * 抽象工厂:采用模板方法模式
 */
public abstract class Factory {
    protected abstract Product createProduct(String owner);

    protected abstract void registerProduct(Product p);

    public final Product create(String owner) {
        Product p = createProduct(owner);
        registerProduct(p);
        return p;
    }
}
