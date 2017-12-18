package org.jimo.factory.idcard;

import org.jimo.factory.framework.Product;

/**
 * Created by root on 17-6-11.
 * 具体的产品：身份证
 */
public class IDCard extends Product {

    private String owner;

    /**
     * @Date: 17-6-11 下午6:36
     * @Author:root
     * @Desc: 构造方法并不是公有的，表示只能本包的类能new对象
     * 这里通过IDCardFactory来new
     */
    IDCard(String owner) {
        System.out.println("制作的" + owner + "身份证");
        this.owner = owner;
    }

    @Override
    public void use() {
        System.out.println("使用" + owner + "的身份证");
    }

    public String getOwner() {
        return owner;
    }
}
