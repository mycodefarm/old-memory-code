package com.jimo.util;

import java.util.Random;

/**
 * Created by jimo on 17-8-14.
 */
public class Common {

    public static void swap(int[] a, int j, int i) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    /**
     * 随机初始化数组
     */
    public static int[] randomIntArray(int size) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(1000);// 产生0到1000的整数
        }
        return array;
    }

    /**
     * 深度复制
     * @param a
     * @return
     */
    public static int[] copyArray(int[] a) {
        int[] copy = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            copy[i] = a[i];
        }
        return copy;
    }
}
