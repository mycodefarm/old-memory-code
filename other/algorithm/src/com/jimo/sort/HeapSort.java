package com.jimo.sort;

import com.jimo.util.Common;

import static org.junit.Assert.assertArrayEquals;

/**
 * 以最大堆为例
 *
 * @author jimo
 */
public class HeapSort extends Sort {

    private int heapSize;

    public HeapSort() {
        methodName = "HeapSort";
    }

    @Override
    public void sort() {
        check();
        buildMaxHeap();
        for (int i = A.length - 1; i > 0; i--) {
            Common.swap(A, 0, i);// 将最大值放在最后
            heapSize--;
            maxHeapify(0);
        }
    }

    /**
     * 从中间节点开始构建最大堆
     */
    private void buildMaxHeap() {
        heapSize = A.length - 1;
        for (int i = A.length / 2; i >= 0; i--) {
            maxHeapify(i);
        }
    }

    /**
     * 递归的调整堆
     *
     * @param
     */
    private void maxHeapify(int i) {
        int l = 2 * i;// 左孩子
        int r = l + 1;// 右孩子
        int largest = i;
        if (l < heapSize && A[l] > A[i]) {
            largest = l;
        }
        if (r < heapSize && A[r] > A[largest]) {
            largest = r;
        }
        if (largest != i) {
            Common.swap(A, largest, i);
            maxHeapify(largest);
        }
    }

    public static void main(String[] args) {
        int[] a = {5, 2, 6, 8, 2, 1, 2, 1};
        Sort heapSort = new HeapSort();
        heapSort.setArray(a);
        heapSort.sort();
        int[] re = {1, 1, 2, 2, 2, 5, 6, 8};
        assertArrayEquals(re, a);
    }
}
