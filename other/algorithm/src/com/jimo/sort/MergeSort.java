package com.jimo.sort;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by jimo on 17-8-17.
 */
public class MergeSort extends Sort {
    public MergeSort() {
        methodName = "MergeSort";
    }

    @Override
    public void sort() {
        check();
        int[] tempArr = new int[A.length];
        mergeSort(tempArr, 0, A.length - 1);
    }

    private void mergeSort(int[] tempArr, int left, int right) {
        if (left < right) {
            int center = (left + right) / 2;
            mergeSort(tempArr, left, center);
            mergeSort(tempArr, center + 1, right);
            merge(tempArr, left, center + 1, right);
        }
    }

    /**
     * 合并两个有序数组，第一个为tempArr[left->center-1],第二个为tempArr[center->right]
     *
     * @param tempArr
     * @param left
     * @param center
     * @param right
     */
    private void merge(int[] tempArr, int left, int center, int right) {
        int leftEnd = center - 1;
        int num = right - left + 1;
        int i = left;
        while (left <= leftEnd && center <= right) {
            if (A[left] <= A[center]) {
                tempArr[i++] = A[left++];
            } else {
                tempArr[i++] = A[center++];
            }
        }
        while (left <= leftEnd) { //第一个剩下的
            tempArr[i++] = A[left++];
        }
        while (center <= right) {
            tempArr[i++] = A[center++];
        }
        //复制回去
        for (int k = 0; k < num; k++, right--) {
            A[right] = tempArr[right];
        }
    }

    public static void main(String[] args) {
        int[] a = {5, 2, 6, 8, 2, 1, 2, 1};
        Sort mergeSort = new MergeSort();
        mergeSort.setArray(a);
        mergeSort.sort();
        int[] re = {1, 1, 2, 2, 2, 5, 6, 8};
        assertArrayEquals(re, a);
    }
}
