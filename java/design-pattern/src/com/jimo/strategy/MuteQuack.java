package com.jimo.strategy;

/**
 * Created by root on 17-5-25.
 */
public class MuteQuack implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("叫不出来");
    }
}
