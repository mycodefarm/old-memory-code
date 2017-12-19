package com.jimo.decorator;

/**
 * Created by root on 17-5-27.
 */
public class Milk extends CandimentDecorator {
    private Beverage beverage;

    public Milk(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDesc() {
        return beverage.getDesc() + ",牛奶";
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.5;
    }
}
