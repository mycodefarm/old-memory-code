package com.jimo.dynamicprogram;

import com.jimo.common.Parent;

/**
 * Created by  on 17-8-26.
 * 切割钢条
 */
public class CutRod extends Parent {

    private int[] p;//价格数组
    private int n;//钢条长度

    public CutRod(int[] p) {
        this.p = p;
        n = 0;
        methodName = "CutRod";
    }

    public void setN(int n) {
        this.n = n;
    }

    public int cutRod() {
        return cutRod(p, n);
    }

    /**
     * 原生递归
     *
     * @param p
     * @param n
     * @return
     */
    private int cutRod(int[] p, int n) {
        if (n <= 0) {
            return 0;
        }
        int q = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            int tmp = 0;
            if (i < p.length) {
                tmp = p[i];
            }
            q = Math.max(q, tmp + cutRod(p, n - i - 1));
        }
        return q;
    }

    /**
     * 记录中间结果的递归
     *
     * @return
     */
    public int memorizedCutRod() {
        int[] r = new int[n + 1];//用于记录中间计算结果，避免重复计算
        for (int i = 0; i <= n; i++) {
            r[i] = Integer.MIN_VALUE;
        }
        return memorizedCutRod(p, n, r);
    }

    private int memorizedCutRod(int[] p, int n, int[] r) {
        if (r[n] >= 0) {
            return r[n];
        }
        int q = 0;
        if (n > 0) {
            q = Integer.MIN_VALUE;
            for (int i = 0; i < n; i++) {
                int tmp = 0;
                if (i < p.length) {
                    tmp = p[i];
                }
                q = Math.max(q, tmp + memorizedCutRod(p, n - i - 1, r));
            }
        }
        r[n] = q;
        return q;
    }

    /**
     * 自底向上得到递推
     *
     * @return
     */
    public int bottomUpCutRod() {
        int[] r = new int[n + 1];
        r[0] = 0;
        for (int j = 1; j <= n; j++) {
            int q = Integer.MIN_VALUE;
            for (int i = 0; i < j; i++) {
                int tmp = 0;
                if (i < p.length) {
                    tmp = p[i];
                }
                q = Math.max(q, tmp + r[j - i - 1]);
            }
            r[j] = q;
        }
        return r[n];
    }


    public void bottomUpCutRodAndPrintSolution() {
        int[] r = new int[n + 1];
        int[] s = new int[n + 1];//存放最优方案下第一段切割长度
        r[0] = 0;
        for (int j = 1; j <= n; j++) {
            int q = Integer.MIN_VALUE;
            for (int i = 0; i < j; i++) {
                int tmp = 0;
                if (i < p.length) {
                    tmp = p[i];
                }
                if (q < tmp + r[j - i - 1]) {
                    q = tmp + r[j - i - 1];
                    s[j] = i + 1;//record
                }
            }
            r[j] = q;
        }

        //print solution
        while (n > 0) {
            System.out.print(s[n] + ",");
            n -= s[n];
        }
    }
}
