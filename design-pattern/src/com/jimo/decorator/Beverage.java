package com.jimo.decorator;

/**
 * Created by root on 17-5-27.
 * 星吧兹
 */
public abstract class Beverage {
    String desc = "未知星吧兹";

    public String getDesc() {
        return desc;
    }

    public abstract double cost();
}
