package com.jimo.singleton;

/**
 * Created by root on 17-5-29.
 * 懒汉式-静态内部类
 */
public class Singleton3 {
    private static class LazyHolder {
        private static final Singleton3 INSTANCE = new Singleton3();
    }

    private Singleton3() {

    }

    public static final Singleton3 getInstance() {
        return LazyHolder.INSTANCE;
    }
}
