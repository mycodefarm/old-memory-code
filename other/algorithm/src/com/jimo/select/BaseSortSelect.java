package com.jimo.select;

import com.jimo.sort.QuickSort2;
import org.junit.Assert;

/**
 * Created by jimo on 17-8-19.
 */
public class BaseSortSelect extends Select {

    public BaseSortSelect(int k) {
        super(k);
    }

    public BaseSortSelect() {
        methodName = "BaseSortSelect";
    }

    @Override
    protected int select() {
        check();
        QuickSort2 quickSort2 = new QuickSort2();
        quickSort2.setArray(A);
        quickSort2.sort();
        return A[k - 1];
    }

    public static void main(String[] args) {
        int[] a = {5, 3, 6, 8, 10, 0, 2, 1};
        BaseSortSelect baseSortSelect = new BaseSortSelect(a.length / 2);
        baseSortSelect.setArray(a);
        int select = baseSortSelect.select();
        Assert.assertEquals(3, select);
    }
}
