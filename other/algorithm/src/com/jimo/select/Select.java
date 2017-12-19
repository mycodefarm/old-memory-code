package com.jimo.select;

import com.jimo.common.Parent;

/**
 * Created by jimo on 17-8-19.
 */
public abstract class Select extends Parent {

    protected int[] A;
    protected int k;//第k个最小值

    public Select(int k) {
        this.k = k;
    }

    public Select() {

    }

    protected void setK(int k) {
        this.k = k;
    }

    protected void setArray(int[] a) {
        A = a;
    }

    protected void check() {
        if (A == null || k < 1 || k > A.length) {
            System.exit(1);
        }
    }

    protected abstract int select();
}
