package com.jimo.dynamicprogram;

import com.jimo.util.Record;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jimo on 17-8-26.
 */
public class CutRodTest {

    CutRod cutRod;

    @Test
    @Before
    public void init() throws Exception {
        int[] p = {1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
        cutRod = new CutRod(p);
    }

    @Test
    public void cutRod() throws Exception {
        cutRod.setN(10);
        int r = cutRod.cutRod();
        assertEquals(30, r);
    }

    @Test
    public void cutRodTime() throws Exception {
        int[] size = {10, 20, 30};
        Record record = new Record(size);
        for (int i = 0; i < size.length; i++) {
            cutRod.setN(size[i]);
            record.testTimeForMarkdown(() -> {
                return cutRod.cutRod();
            }, cutRod.getMethodName());
        }
        record.generateMarkdownTable();
    }

    @Test
    public void memorizedCutRod() throws Exception {
        cutRod.setN(10);
        assertEquals(30, cutRod.memorizedCutRod());
    }

    @Test
    public void memorizedCutRodTime() throws Exception {
        int[] size = {10, 20, 30, 300, 3000, 10000};
        Record record = new Record(size);
        for (int i = 0; i < size.length; i++) {
            cutRod.setN(size[i]);
            record.testTimeForMarkdown(() -> {
                return cutRod.memorizedCutRod();
            }, cutRod.getMethodName());
        }
        record.generateMarkdownTable();
    }

    @Test
    public void bottomUpCutRod() throws Exception {
        cutRod.setN(4);
        assertEquals(10, cutRod.bottomUpCutRod());
    }

    @Test
    public void bottomUpCutRodTime() throws Exception {
        int[] size = {10, 20, 30, 300, 3000, 10000, 30000, 100000};
        Record record = new Record(size);
        for (int i = 0; i < size.length; i++) {
            cutRod.setN(size[i]);
            record.testTimeForMarkdown(() -> {
                return cutRod.bottomUpCutRod();
            }, cutRod.getMethodName());
        }
        record.generateMarkdownTable();
    }

    @Test
    public void bottomUpCutRodAndPrintSolution() throws Exception {
        cutRod.setN(7);
        cutRod.bottomUpCutRodAndPrintSolution();//1,6,
    }
}