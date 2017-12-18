package com.jimo.factory;

/**
 * Created by root on 17-5-28.
 */
public class CDPizzaStore extends PizzaStore {
    @Override
    protected Pizza createPizza(String type) {
        if (type.equals("tomato")) {
            return new CDTomatoPizza();
        } else if (type.equals("egg")) {
            return new CDEggPizza();
        }
        return null;
    }
}
