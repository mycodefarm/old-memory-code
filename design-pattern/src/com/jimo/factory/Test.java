package com.jimo.factory;

/**
 * Created by root on 17-5-28.
 */
public class Test {
    public static void main(String[] args) {
        PizzaStore pizzaStore = new CDPizzaStore();
        pizzaStore.orderPizza("tomato");
        pizzaStore.orderPizza("egg");
        /**
         准备：成都土豆批萨
         面团：灰面
         酱料：麻辣
         鸡蛋   番茄   土豆
         烘烤20分钟
         切片
         打包成盒子
         准备：成都鸡蛋批萨
         面团：灰面
         酱料：麻辣
         鸡蛋   番茄   土豆
         烘烤20分钟
         切片
         打包成盒子
         */
    }
}
