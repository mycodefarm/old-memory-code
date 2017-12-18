package com.jimo.decorator;

/**
 * Created by root on 17-5-27.
 */
public class Test {
    public static void main(String[] args) {
        //来杯浓缩咖啡加双倍摩卡和牛奶还有奶泡
        Beverage beverage = new Espresso();
        System.out.println(beverage.getDesc() + ":" + beverage.cost());

        Beverage beverage1 = new Mocha(beverage);
        beverage1 = new Mocha(beverage1);
        beverage1 = new Milk(beverage1);
        beverage1 = new Whip(beverage1);

        System.out.println(beverage1.getDesc() + ":" + beverage1.cost());
        /**
         * 浓缩咖啡:1.99
         浓缩咖啡,摩卡,摩卡,牛奶,奶泡:2.79
         */
    }
}
