package com.jimo.select;

import com.jimo.common.Parent;
import com.jimo.sort.QuickSort2;
import org.junit.Assert;

/**
 * Created by jimo on 17-8-19.
 * 比基础选择更高级点,需要额外的空间
 */
public class PartSortSelect extends Select {

    public PartSortSelect(int k) {
        super(k);
    }

    public PartSortSelect() {
        methodName = "PartSortSelect";
    }

    @Override
    protected int select() {
        check();
        int[] tmp = new int[k];
        int i;
        //1
        for (i = 0; i < k; i++) {
            tmp[i] = A[i];
        }
        //2
        QuickSort2 quickSort2 = new QuickSort2();
        quickSort2.setArray(tmp);
        quickSort2.sort();
        //3
        int x = tmp[k - 1];
        for (; i < A.length; i++) {
            if (A[i] >= x) {//大于就抛弃
                continue;
            }
            //类似插入排序
            int j = k - 1;
            for (; j > 0 && tmp[j - 1] > A[i]; j--) {
                tmp[j] = tmp[j - 1];
            }
            tmp[j] = A[i];
        }
        return tmp[k - 1];
    }

    public static void main(String[] args) {
        int[] a = {5, 3, 6, 8, 10, 0, 2, 1};
        PartSortSelect partSortSelect = new PartSortSelect(a.length / 2);
        partSortSelect.setArray(a);
        int select = partSortSelect.select();
        Assert.assertEquals(3, select);
    }
}
