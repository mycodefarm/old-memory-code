package com.jimo.select;

import com.jimo.util.Common;
import org.junit.Assert;

/**
 * Created by jimo on 17-8-22.
 */
public class QuickSelect extends Select {

    public QuickSelect() {
        methodName = "QuickSelect";
    }

    @Override
    protected int select() {
        return quickSelect(0, A.length - 1, k);
    }

    /**
     * 从low到high范围选择第i个小的数
     *
     * @param low
     * @param high
     * @param i
     * @return
     */
    private int quickSelect(int low, int high, int i) {
        if (low == high) {
            return A[low];
        }
        int q = partition(low, high);
        int x = q - low + 1;
        if (i == x) {
            return A[q];
        } else if (i < x) {
            return quickSelect(low, q - 1, i);
        } else {
            return quickSelect(q + 1, high, i - x);//这里i-x很好理解，现在不是第i个数了
        }
    }

    /**
     * 分区，以中点为哨兵
     *
     * @param low
     * @param high
     * @return
     */
    private int partition(int low, int high) {
        int mid = (low + high) / 2;
        Common.swap(A, mid, high);
        int x = A[high];
        while (low < high) {
            while (low < high && A[low] <= x) {
                low++;
            }
            A[high] = A[low];
            while (low < high && A[high] >= x) {
                high--;
            }
            A[low] = A[high];
        }
        A[high] = x;
        return high;
    }

    public static void main(String[] args) {
//        int[] a = {5, 3, 6, 8, 10, 0, 2, 1};
//        int[] a = {0, 1, 2, 3, 4};
        int[] a = {0, 0, 0, 0};
        QuickSelect quickSelect = new QuickSelect();
        quickSelect.setArray(a);
        quickSelect.setK(a.length / 2);
        int select = quickSelect.select();
        Assert.assertEquals(0, select);
    }
}
