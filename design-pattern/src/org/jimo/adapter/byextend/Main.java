package org.jimo.adapter.byextend;

/**
 * Created by root on 17-6-10.
 * 以继承实现适配器模式
 */
public class Main {
    public static void main(String[] args) {
        Print bannerAdapter = new BannerAdapter("hello");
        bannerAdapter.printStrong();
        bannerAdapter.printWeak();
    }
}
