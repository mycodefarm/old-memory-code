package com.jimo.singleton;

/**
 * Created by root on 17-5-29.
 * 饿汉式
 */
public class Singleton4 {
    private static Singleton4 singleton = new Singleton4();

    private Singleton4() {

    }

    public static synchronized Singleton4 getInstance() {
        return singleton;
    }
}
