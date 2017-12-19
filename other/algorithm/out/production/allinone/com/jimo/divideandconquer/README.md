# 分治法 divide and conquer
## 最大子段和
1. 暴力法
```java
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
```
2. 分治法
```java
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
```
3. 性能比较

方法/时间/N | 50 | 500 | 5000 | 50000 | 500000 | 
--------- | --------- | --------- | --------- | --------- | --------- | 
divideAndConquer | 1ms | 0ms | 0ms | 2ms | 13ms | 
bruteForce | 0ms | 3ms | 17ms | 602ms | 62398ms | 

 
