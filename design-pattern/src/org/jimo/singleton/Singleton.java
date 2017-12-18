package org.jimo.singleton;

/**
 * Created by root on 17-6-12.
 */
public class Singleton {
    private static Singleton singleton = new Singleton();

    private Singleton() {
        System.out.println("生成实例");
    }

    public static Singleton getInstance() {
        return singleton;
    }
}
