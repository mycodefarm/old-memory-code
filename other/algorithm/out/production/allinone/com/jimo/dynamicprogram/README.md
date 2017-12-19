# 动态规划

## 切割钢条（CutRod）
给出长度和价格对应表，求给定长度下最优的切割方案以获得最大收入

| 长度i | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 |
| --- | --- | --- | --- | --- | --- |--- | --- | --- | --- | --- |
| 价格Pi | 1 | 5 | 8 | 9 | 10 | 17 | 17 | 20 | 24 | 30 |

#### 1.穷举 
穷举每种方案，计算量巨大
#### 2.递归的动态规划 
子结构分析，设收益为r，则r(n) = max(pi + r(n-i)),其中1<=i<=n
所以很容易写出递归程序：
```java
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
```

但是稍微一测试发现当n=30就需要几秒钟，40就需要几分钟了

方法/时间/N | 10 | 20 | 30 | 
--------- | --------- | --------- | --------- | 
CutRod | 1ms | 7ms | 3459ms |

为什么？因为每次都会重复计算上一步的值，那简单，保存起来就行了

#### 3.保存中间状态
```java
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
```

方法/时间/N | 10 | 20 | 30 | 300 | 3000 | 10000 | 
--------- | --------- | --------- | --------- | --------- | --------- | --------- | 
memorizedCutRod | 0ms | 0ms | 0ms | 5ms | 87ms | 208ms |

虽然是快了很多，但是在我的电脑上到达20000就会出现栈溢出。所以还是用递推的方式吧。

#### 4.递推实现
```java
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
```

方法/时间/N | 10 | 20 | 30 | 300 | 3000 | 10000 | 30000 | 100000 | 
--------- | --------- | --------- | --------- | --------- | --------- | --------- | --------- | --------- | 
bottomUpCutRod | 0ms | 0ms | 0ms | 4ms | 14ms | 121ms | 732ms | 6796ms |

#### 5.打印出最佳方案
```java
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
```

比如n=70，结果全是10：10,10,10,10,10,10,10,

77是：1,6,10,10,10,10,10,10,10,



