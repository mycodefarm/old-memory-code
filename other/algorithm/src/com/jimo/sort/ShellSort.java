package com.jimo.sort;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by jimo on 17-8-17.
 */
public class ShellSort extends Sort {
    public ShellSort() {
        methodName = "ShellSort";
    }

    @Override
    public void sort() {
        check();
        int j;
        //这里使用的增量序列是N/2，不是最优的
        for (int gap = A.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < A.length; i++) {
                int x = A[i];
                for (j = i; j >= gap && A[j - gap] > x; j -= gap) {
                    A[j] = A[j - gap];
                }
                A[j] = x;
            }
        }
    }

    public static void main(String[] args) {
        int[] a = {5, 2, 6, 8, 2, 1, 2, 1};
        Sort shellSort = new ShellSort();
        shellSort.setArray(a);
        shellSort.sort();
        int[] re = {1, 1, 2, 2, 2, 5, 6, 8};
        assertArrayEquals(re, a);
    }
}
