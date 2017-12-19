package com.jimo.strategy;

/**
 * Created by root on 17-5-25.
 */
public class Quack implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("鸭子嘎嘎叫");
    }
}
