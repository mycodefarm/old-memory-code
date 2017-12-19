package com.jimo.select;

import com.jimo.util.Common;
import com.jimo.util.Record;

/**
 * Created by jimo on 17-8-19.
 */
public class Test {

    @org.junit.Test
    public void test1() {
        Select[] selects = {new BaseSortSelect(), new PartSortSelect()};
        selectTest(selects);
    }

    private void selectTest(Select[] selects) {
//        int size[] = {50, 500, 5000, 50000};
        int size[] = {50, 500, 5000, 50000, 500000};
        Record record = new Record(size);
        for (int i = 0; i < size.length; i++) {
            int[] a = Common.randomIntArray(size[i]);
            for (int j = 0; j < selects.length; j++) {
                selects[j].setK(a.length / 2);
                selects[j].setArray(Common.copyArray(a));
                int finalJ = j;
                record.testTimeForMarkdown(() -> {
                    selects[finalJ].select();
                    return null;
                }, selects[j].getMethodName());
            }
        }
        record.generateMarkdownTable();
    }
}
