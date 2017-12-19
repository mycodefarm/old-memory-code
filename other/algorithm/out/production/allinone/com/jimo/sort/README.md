# 排序算法
## 堆排序
3步：构建堆，去堆顶，调整堆
```java
    private int heapSize;

    public HeapSort() {
        methodName = "HeapSort";
    }

    @Override
    public void sort() {
        check();
        buildMaxHeap();
        for (int i = A.length - 1; i > 0; i--) {
            Common.swap(A, 0, i);// 将最大值放在最后
            heapSize--;
            maxHeapify(0);
        }
    }

    /**
     * 从中间节点开始构建最大堆
     */
    private void buildMaxHeap() {
        heapSize = A.length - 1;
        for (int i = A.length / 2; i >= 0; i--) {
            maxHeapify(i);
        }
    }

    /**
     * 递归的调整堆
     *
     * @param
     */
    private void maxHeapify(int i) {
        int l = 2 * i;// 左孩子
        int r = l + 1;// 右孩子
        int largest = i;
        if (l < heapSize && A[l] > A[i]) {
            largest = l;
        }
        if (r < heapSize && A[r] > A[largest]) {
            largest = r;
        }
        if (largest != i) {
            Common.swap(A, largest, i);
            maxHeapify(largest);
        }
    }
```
## 快速排序
### 实现方式1
```java
public void sort() {
        check();
        quickSort(0, A.length - 1);
    }

    private void quickSort(int low, int high) {
        if (low < high) {
            int key = partition(low, high);
            quickSort(low, key - 1);
            quickSort(key + 1, high);
        }
    }

    private int partition(int low, int high) {
        int mid = (low + high) / 2;//这个哨兵的选择是关键，这里选中点，一般是头或尾，也可随机
        Common.swap(A, mid, high);
        int x = A[high];//划分的基准,其实是数组中间的值,这个实现需要基准在最后
        int i = low - 1;// i左边的（包括i）都<=x; i+1到j-1的都>=x
        for (int j = low; j < high; j++) {
            if (A[j] <= x) {
                i++;
                Common.swap(A, i, j);
            }
        }
        Common.swap(A, i + 1, high);
        return i + 1;
    }
```
### 实现方式2
```java
   private void quickSort(int low, int high) {
        if (low < high) {
            int x = A[(low + high) / 2];
            int i = low, j = high;
            while (i <= j) {
                while (A[i] < x) {
                    i++;
                }
                while (A[j] > x) {
                    j--;
                }
                if (i <= j) {
                    Common.swap(A, i, j);
                    i++;
                    j--;
                }
            }
            if (low < j) {
                quickSort(low, j);
            }
            if (high > i) {
                quickSort(i, high);
            }
        }
    }
```

## 插入排序
```java
    @Override
    public void sort() {
        check();
        for (int i = 1; i < A.length; i++) {
            int x = A[i];
            int j = i;
            for (; j > 0 && A[j - 1] > x; j--) { //从后往前插
                A[j] = A[j - 1];
            }
            A[j] = x;
        }
    }
```
## 希尔排序
```java
    @Override
    public void sort() {
        check();
        int j;
        //这里使用的增量序列是N/2，不是最优的
        for (int gap = A.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < A.length; i++) {
                int x = A[i];
                for (j = i; j >= gap && A[j - gap] > x; j -= gap) {
                    A[j] = A[j - gap];
                }
                A[j] = x;
            }
        }
    }
```

## 归并排序
利用分治的思想
```java
    @Override
    public void sort() {
        check();
        int[] tempArr = new int[A.length];
        mergeSort(tempArr, 0, A.length - 1);
    }

    private void mergeSort(int[] tempArr, int left, int right) {
        if (left < right) {
            int center = (left + right) / 2;
            mergeSort(tempArr, left, center);
            mergeSort(tempArr, center + 1, right);
            merge(tempArr, left, center + 1, right);
        }
    }

    /**
     * 合并两个有序数组，第一个为tempArr[left->center-1],第二个为tempArr[center->right]
     *
     * @param tempArr
     * @param left
     * @param center
     * @param right
     */
    private void merge(int[] tempArr, int left, int center, int right) {
        int leftEnd = center - 1;
        int num = right - left + 1;
        int i = left;
        while (left <= leftEnd && center <= right) {
            if (A[left] <= A[center]) {
                tempArr[i++] = A[left++];
            } else {
                tempArr[i++] = A[center++];
            }
        }
        while (left <= leftEnd) { //第一个剩下的
            tempArr[i++] = A[left++];
        }
        while (center <= right) {
            tempArr[i++] = A[center++];
        }
        //复制回去
        for (int k = 0; k < num; k++, right--) {
            A[right] = tempArr[right];
        }
    }
```

## 性能比较
### 快排

方法/时间/N | 50 | 500 | 5000 | 50000 | 500000 | 5000000 | 
--------- | --------- | --------- | --------- | --------- | --------- | --------- | 
QuickSort | 0ms | 1ms | 7ms | 6ms | 213ms | 13865ms | 
QuickSort2 | 0ms | 0ms | 4ms | 6ms | 35ms | 291ms |

### 总的比较

方法/时间/N | 50 | 500 | 5000 | 50000 | 500000 | 
--------- | --------- | --------- | --------- | --------- | --------- | 
QuickSort | 1ms | 1ms | 3ms | 7ms | 262ms | 
QuickSort2 | 0ms | 0ms | 1ms | 4ms | 38ms | 
MergeSort | 0ms | 1ms | 1ms | 6ms | 61ms | 
ShellSort | 0ms | 0ms | 4ms | 9ms | 64ms | 
HeapSort | 0ms | 1ms | 9ms | 12ms | 115ms | 
InsertSort | 0ms | 2ms | 11ms | 337ms | 27667ms | 

因为插入排序在大数据量下很慢，就不比了

方法/时间/N | 50 | 500 | 5000 | 50000 | 500000 | 5000000 | 
--------- | --------- | --------- | --------- | --------- | --------- | --------- | 
QuickSort | 0ms | 0ms | 1ms | 6ms | 207ms | 13513ms | 
QuickSort2 | 0ms | 0ms | 1ms | 9ms | 37ms | 288ms | 
MergeSort | 0ms | 1ms | 1ms | 8ms | 92ms | 646ms | 
ShellSort | 0ms | 0ms | 5ms | 14ms | 105ms | 884ms | 
HeapSort | 0ms | 0ms | 1ms | 19ms | 113ms | 1006ms |


