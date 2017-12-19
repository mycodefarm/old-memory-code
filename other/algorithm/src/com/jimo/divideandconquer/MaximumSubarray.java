package com.jimo.divideandconquer;

import java.util.Random;

import com.jimo.util.CallBack;
import com.jimo.util.Record;

/**
 * 求最大子段和
 *
 * @author root
 */
public class MaximumSubarray {

    private int[] array = null;

    /**
     * 构造方法创建数组
     *
     * @param size
     */
    public MaximumSubarray(int size) {
        super();
        array = new int[size];
        // 初始化数组
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = 100 - random.nextInt(200);// 产生-100到100的整数
        }
    }

    /**
     * 暴力法
     */
    private long bruteForce() {
        int len = array.length;
        long max = Integer.MIN_VALUE;
        for (int i = 0; i < len; i++) {
            int t = 0;
            for (int j = i; j < len; j++) {
                t += array[j];
                if (t > max) {
                    max = t;
                }
            }
        }
        return max;
    }

    /**
     * 分治法
     *
     * @return
     */
    private long divideAndConquer() {
        return findMaxSubArr(0, array.length - 1);
    }

    private long findMaxSubArr(int low, int high) {

        if (low == high) {
            return array[low];
        } else {
            int mid = (low + high) / 2;
            long leftSum = findMaxSubArr(low, mid);
            long rightSum = findMaxSubArr(mid + 1, high);
            long crossSum = findMaxCrossSubArr(low, mid, high);
            if (leftSum >= rightSum && leftSum >= crossSum) {
                return leftSum;
            } else if (rightSum >= leftSum && rightSum >= crossSum) {
                return rightSum;
            } else {
                return crossSum;
            }
        }
    }

    private long findMaxCrossSubArr(int low, int mid, int high) {
        long leftSum = Integer.MIN_VALUE;
        long sum = 0;
        for (int i = mid; i >= low; i--) {
            sum += array[i];
            if (sum > leftSum) {
                leftSum = sum;
            }
        }
        long rightSum = Integer.MIN_VALUE;
        sum = 0;
        for (int j = mid + 1; j <= high; j++) {
            sum += array[j];
            if (sum > rightSum) {
                rightSum = sum;
            }
        }
        return leftSum + rightSum;
    }

    public static void main(String[] args) {
        // System.out.println(Arrays.toString(m.array));
        int[] size = {50, 500, 5000, 50000, 500000};
        Record r = new Record(size);
        for (int i = 0; i < size.length; i++) {
            MaximumSubarray m = new MaximumSubarray(size[i]);
            r.testTimeForMarkdown(() -> m.divideAndConquer(), "divideAndConquer");
            r.testTimeForMarkdown(() -> m.bruteForce(), "bruteForce");
        }
        r.generateMarkdownTable();
    }
}
