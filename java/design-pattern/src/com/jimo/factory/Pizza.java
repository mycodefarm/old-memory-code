package com.jimo.factory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 17-5-28.
 * 批萨抽象类
 */
public abstract class Pizza {
    protected String name;
    protected String douch;//面团类型
    protected String souce;//酱料
    protected List<String> toppings = new ArrayList<>();//成分

    public void prepare() {
        System.out.println("准备：" + name);
        System.out.println("面团：" + douch);
        System.out.println("酱料：" + souce);
        for (String t : toppings) {
            System.out.print("   " + t);
        }
        System.out.println();
    }

    public void bake() {
        System.out.println("烘烤20分钟");
    }

    public void cut() {
        System.out.println("切片");
    }

    public void box() {
        System.out.println("打包成盒子");
    }

    public String getName() {
        return name;
    }
}
