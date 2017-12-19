package com.jimo.sort;

import com.jimo.util.Common;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by jimo on 17-8-16.
 * 第二种实现方式
 */
public class QuickSort2 extends Sort {

    public QuickSort2() {
        methodName = "QuickSort2";
    }

    @Override
    public void sort() {
        check();
        quickSort(0, A.length - 1);
    }

    private void quickSort(int low, int high) {
        if (low < high) {
            int x = A[(low + high) / 2];
            int i = low, j = high;
            while (i <= j) {
                while (A[i] < x) {
                    i++;
                }
                while (A[j] > x) {
                    j--;
                }
                if (i <= j) {
                    Common.swap(A, i, j);
                    i++;
                    j--;
                }
            }
            if (low < j) {
                quickSort(low, j);
            }
            if (high > i) {
                quickSort(i, high);
            }
        }
    }

    public static void main(String[] args) {
        int[] a = {5, 2, 6, 8, 2, 1, 2, 1};
        Sort quickSort = new QuickSort2();
        quickSort.setArray(a);
        quickSort.sort();
        int[] re = {1, 1, 2, 2, 2, 5, 6, 8};
        assertArrayEquals(re, a);
    }
}
