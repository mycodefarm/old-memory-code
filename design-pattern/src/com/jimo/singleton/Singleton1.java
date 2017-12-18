package com.jimo.singleton;

/**
 * Created by root on 17-5-29.
 * 懒汉式
 */
public class Singleton1 {
    private static Singleton1 singleton;

    private Singleton1() {

    }

    public static synchronized Singleton1 getInstance() {
        if (singleton == null) {
            singleton = new Singleton1();
        }
        return singleton;
    }
}
