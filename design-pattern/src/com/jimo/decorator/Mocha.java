package com.jimo.decorator;

/**
 * Created by root on 17-5-27.
 * 摩卡配料
 */
public class Mocha extends CandimentDecorator {
    private Beverage beverage;

    public Mocha(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDesc() {
        return beverage.getDesc() + ",摩卡";
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.10;
    }
}
