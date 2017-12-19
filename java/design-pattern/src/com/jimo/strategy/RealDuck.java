package com.jimo.strategy;

/**
 * Created by root on 17-5-25.
 */
public class RealDuck extends Duck {
    @Override
    public void display() {
        System.out.println("我是个真鸭子");
    }

    public RealDuck() {
        flyBehavior = new FlyWithWings();
        quackBehavior = new Quack();
    }
}
