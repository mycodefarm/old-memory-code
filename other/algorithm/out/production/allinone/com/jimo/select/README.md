# 选择问题
从一组N个数的序列中找出第K个最小值

## 排序解决
以递增顺序排序，返回第k个
```java
    @Override
    protected int select() {
        check();
        QuickSort2 quickSort2 = new QuickSort2();
        quickSort2.setArray(A);
        quickSort2.sort();
        return A[k - 1];
    }
```

## 改进
准备一个容量为k的数组，先读取k个数递增排序，接着将剩下的元素读入，当新的元素读入时，若它大于数组的第k个元素则忽略，
否则将它放在合适的位置，挤出一个元素，最后位于数组第k个位置的就是了。

```java
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
```

## 分治选择算法
利用快排的分区思想来选择，不过其期望运行时间为O(n),最坏时间为O(nlgn)
```java
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
```


## 性能比较

方法/时间/N | 50 | 500 | 5000 | 50000 | 500000 | 
--------- | --------- | --------- | --------- | --------- | --------- | 
PartSortSelect | 0ms | 1ms | 19ms | 113ms | 9968ms | 
BaseSortSelect | 1ms | 1ms | 3ms | 4ms | 38ms |

因为改进后有一半是插入排序，所以更慢
