package com.jimo.strategy;

/**
 * Created by root on 17-5-25.
 */
public class DuckTest {
    public static void main(String[] args) {
        Duck readDuck = new RealDuck();
        readDuck.setFlyBehavior(new FlyNoWay());
        readDuck.performFly();
        readDuck.performQuack();
    }
}
