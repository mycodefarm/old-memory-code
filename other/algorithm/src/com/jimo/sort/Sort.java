package com.jimo.sort;

import com.jimo.common.Parent;

/**
 * Created by jimo on 17-8-15.
 * 父类
 */
public abstract class Sort extends Parent {
    protected int[] A;

    public void setArray(int[] a) {
        A = a;
    }

    protected void check() {
        if (A == null) {
            System.err.println("Array not initial");
            System.exit(1);
        }
    }

    public abstract void sort();
}
