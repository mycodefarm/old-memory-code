package com.jimo.strategy;

/**
 * Created by root on 17-5-25.
 */
public class FlyWithWings implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("我可以飞");
    }
}
