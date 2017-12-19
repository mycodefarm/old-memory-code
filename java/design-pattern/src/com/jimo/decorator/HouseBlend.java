package com.jimo.decorator;

/**
 * Created by root on 17-5-27.
 * 综合咖啡
 */
public class HouseBlend extends Beverage {
    public HouseBlend() {
        desc = "综合咖啡";
    }

    @Override
    public double cost() {
        return 0.89;
    }
}
