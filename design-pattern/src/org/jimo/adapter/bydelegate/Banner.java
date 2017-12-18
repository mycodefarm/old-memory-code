package org.jimo.adapter.bydelegate;

/**
 * Created by root on 17-6-10.
 * 被适配者：原来的类不需要改变
 */
public class Banner {
    private String string;

    public Banner(String string) {
        this.string = string;
    }

    /**
     * @Date: 17-6-10 下午3:09
     * @Author:root
     * @Desc: 以括号包裹
     */

    public void showWithParen() {
        System.out.println("(" + string + ")");
    }

    /**
     * @Date: 17-6-10 下午3:10
     * @Author:root
     * @Desc: 以星号包裹
     */

    public void showWithAster() {
        System.out.println("*" + string + "*");
    }
}
