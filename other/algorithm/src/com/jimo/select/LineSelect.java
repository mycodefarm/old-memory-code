package com.jimo.select;

import static java.util.Arrays.sort;

class LineSelect {


    public int select(int[] A, int low, int high, int i) {
        if (low == high) {
            return A[low];
        }
        int g = (high - low + 1) / 5 + 1;
        int[] cs = new int[g];
        int k = 0;
        for (int j = 0; j < g - 1; j++) {
            sort(A, j, (j + 1) * 5 - 1);// 对这五个元素使用选择排序
            int center = A[((j + 1) * 5 - j) / 2];
            cs[k++] = center;
        }
        sort(A, (g - 1) * 5 + low, high);
        cs[k++] = A[(g - 1) * 5 + low + (high - (g - 1) * 5 - low) / 2];
        int x = select(cs, 0, cs.length - 1, cs.length / 2);
        int q = partition(A, low, high, x);//partiton by x
        int kk = q - low + 1;
        if (i == kk) {
            return A[q];
        } else if (i < kk) {
            return select(A, low, q - 1, i);
        } else {
            return select(A, q + 1, high, i - kk);
        }
    }

    /**
     * 以x为主元进行划分
     * @param a
     * @param low
     * @param high
     * @param x
     * @return
     */
    private int partition(int[] a, int low, int high, int x) {

        return 0;
    }
}
