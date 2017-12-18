package org.jimo.builder;

/**
 * Created by root on 17-6-17.
 */
public abstract class Builder {
    abstract void makeTitle(String title);

    abstract void makeString(String str);

    abstract void makeItems(String[] items);

    abstract void close();
}
