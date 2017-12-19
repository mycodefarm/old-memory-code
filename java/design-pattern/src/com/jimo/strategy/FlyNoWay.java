package com.jimo.strategy;

/**
 * Created by root on 17-5-25.
 */
public class FlyNoWay implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("我不能飞");
    }
}
