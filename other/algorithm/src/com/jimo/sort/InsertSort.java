package com.jimo.sort;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by jimo on 17-8-17.
 */
public class InsertSort extends Sort {
    public InsertSort() {
        methodName = "InsertSort";
    }

    @Override
    public void sort() {
        check();
        for (int i = 1; i < A.length; i++) {
            int x = A[i];
            int j = i;
            for (; j > 0 && A[j - 1] > x; j--) { //从后往前插
                A[j] = A[j - 1];
            }
            A[j] = x;
        }
    }

    public static void main(String[] args) {
        int[] a = {5, 2, 6, 8, 2, 1, 2, 1};
        Sort insertSort = new InsertSort();
        insertSort.setArray(a);
        insertSort.sort();
        int[] re = {1, 1, 2, 2, 2, 5, 6, 8};
        assertArrayEquals(re, a);
    }
}
