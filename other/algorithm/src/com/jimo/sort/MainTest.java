package com.jimo.sort;

import com.jimo.util.Common;
import com.jimo.util.Record;
import org.junit.Test;

/**
 * Created by jimo on 17-8-15.
 * 性能比较测试
 */
public class MainTest {

    /**
     * 2种快排比较
     */
    @Test
    public void quickComp() {
        Sort[] sorts = {new QuickSort(), new QuickSort2()};
        sortTest(sorts);
    }

    @Test
    public void sortTest() throws Exception {
        Sort[] sorts = {new QuickSort(), new QuickSort2(), new HeapSort(),
                /*new InsertSort(),*/new MergeSort(), new ShellSort()};
        sortTest(sorts);
    }

    @Test
    public void sortTest2() throws Exception {
        Sort[] sorts = {new InsertSort()};
        sortTest(sorts);
    }

    private void sortTest(Sort[] sorts) {
//        int size[] = {50, 500, 5000,50000,500000};
        int size[] = {50, 500, 5000, 50000, 500000, 5000000};
        Record record = new Record(size);
        for (int i = 0; i < size.length; i++) {
            int[] a = Common.randomIntArray(size[i]);
            for (int j = 0; j < sorts.length; j++) {
                sorts[j].setArray(Common.copyArray(a));
                int finalJ = j;
                record.testTimeForMarkdown(() -> {
                    sorts[finalJ].sort();
                    return null;
                }, sorts[j].getMethodName());
            }
        }
        record.generateMarkdownTable();
    }
}
