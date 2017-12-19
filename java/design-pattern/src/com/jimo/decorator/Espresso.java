package com.jimo.decorator;

/**
 * Created by root on 17-5-27.
 * 浓缩咖啡
 */
public class Espresso extends Beverage {
    public Espresso() {
        desc = "浓缩咖啡";
    }

    @Override
    public double cost() {
        return 1.99;
    }
}
