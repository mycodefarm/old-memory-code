package com.jimo.sort;

import com.jimo.util.Common;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by jimo on 17-8-14.
 */
public class QuickSort extends Sort {

    public QuickSort() {
        methodName = "QuickSort";
    }

    @Override
    public void sort() {
        check();
        quickSort(0, A.length - 1);
    }

    private void quickSort(int low, int high) {
        if (low < high) {
            int key = partition(low, high);
            quickSort(low, key - 1);
            quickSort(key + 1, high);
        }
    }

    private int partition(int low, int high) {
        int mid = (low + high) / 2;//这个哨兵的选择是关键，这里选中点，一般是头或尾，也可随机
        Common.swap(A, mid, high);
        int x = A[high];//划分的基准,其实是数组中间的值,这个实现需要基准在最后
        int i = low - 1;// i左边的（包括i）都<=x; i+1到j-1的都>=x
        for (int j = low; j < high; j++) {
            if (A[j] <= x) {
                i++;
                Common.swap(A, i, j);
            }
        }
        Common.swap(A, i + 1, high);
        return i + 1;
    }

    public static void main(String[] args) {
        int[] a = {5, 2, 6, 8, 2, 1, 2, 1};
        Sort quickSort = new QuickSort();
        quickSort.setArray(a);
        quickSort.sort();
        int[] re = {1, 1, 2, 2, 2, 5, 6, 8};
        assertArrayEquals(re, a);
    }
}
