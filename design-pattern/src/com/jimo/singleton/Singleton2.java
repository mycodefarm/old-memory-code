package com.jimo.singleton;

/**
 * Created by root on 17-5-29.
 * 懒汉式-双重检查
 */
public class Singleton2 {
    private static Singleton2 singleton;

    private Singleton2() {

    }

    public static Singleton2 getInstance() {
        if (singleton == null) {
            synchronized (Singleton2.class) {
                if (singleton == null) {
                    singleton = new Singleton2();
                }
            }
        }
        return singleton;
    }
}
