package com.jimo.decorator;

/**
 * Created by root on 17-5-27.
 * 奶泡
 */
public class Whip extends CandimentDecorator {
    private Beverage beverage;

    public Whip(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDesc() {
        return beverage.getDesc() + ",奶泡";
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.1;
    }
}
