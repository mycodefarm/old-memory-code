package com.jimo.factory;

/**
 * Created by root on 17-5-28.
 * 成都鸡蛋批萨
 */
public class CDEggPizza extends Pizza {
    public CDEggPizza() {
        name = "成都鸡蛋批萨";
        douch = "灰面";
        souce = "麻辣";
        toppings.add("鸡蛋");
        toppings.add("番茄");
        toppings.add("土豆");
    }
}
