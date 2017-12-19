# 飘起来吧
怎么把书读薄呢？我是这样做的

《算法导论》中文版接近800页，思想却总是那么多，全在代码里了。

《数据结构与算法分析Java描述》只有400页，先敲一遍再说。

下面是环境配置

配置 | CPU | JDK | OS | 
--------- | --------- | --------- | --------- | 
 xxx| i7 | 1.8 | Linux | 
 
 # 测试计时
 
 ## 代码
根据回调方法执行测试方法，然而表格根据信息生成markdown
```java
public class Record {
    private int[] sizes;// 测试数据数组
    private Map<String, List<Long>> times;// 记录每个方法每次执行的时间

    public Record(int[] sizes) {
        this.sizes = sizes;
        this.times = new HashMap<>();
    }

    public void testTimeForMarkdown(CallBack callBack, String methodName) {
        long begin = System.currentTimeMillis();
        callBack.execute();
        long end = System.currentTimeMillis();
        if (times.containsKey(methodName)) {
            times.get(methodName).add(end - begin);
        } else {
            List<Long> t = new ArrayList<>();
            t.add(end - begin);
            times.put(methodName, t);
        }
    }

    /**
     * 生成Markdown语法的table
     */
    public void generateMarkdownTable() {
        if (times.size() <= 0 || sizes.length <= 0) {
            return;
        }
        // table title
        System.out.print("方法/时间/N | ");
        for (int s : sizes) {
            System.out.print(s + " | ");
        }
        System.out.println();
        for (int i = 0; i <= sizes.length; i++) {
            System.out.print("--------- | ");
        }
        System.out.println();
        // table body
        for (String name : times.keySet()) {
            System.out.print(name + " | ");
            for (Long t : times.get(name)) {
                System.out.print(t + "ms | ");
            }
            System.out.println();
        }
    }
}
```

## 使用
以排序为例，使用lambda表达式代替回调方法

```java
/**
     * 2种快排和堆排比较
     */
    @Test
    public void quickHeapComp() {
        Sort[] sorts = {new QuickSort(), new QuickSort2(), new HeapSort()};
        int size[] = {50, 500, 5000, 50000, 500000, 5000000};
        Record record = new Record(size);
        for (int i = 0; i < size.length; i++) {
            int[] a = Common.randomIntArray(size[i]);
            for (int j = 0; j < sorts.length; j++) {
                sorts[j].setArray(a);
                int finalJ = j;
                record.testTimeForMarkdown(() -> {
                    sorts[finalJ].sort();
                    return null;
                }, sorts[j].getMethodName());
            }
        }
        record.generateMarkdownTable();
    }
```

# 涉及内容

 [分治法 divide and conquer](https://github.com/jimolonely/AllInOne/tree/master/src/com/jimo/divideandconquer)
1. 最大子段和

 [排序](https://github.com/jimolonely/AllInOne/tree/master/src/com/jimo/sort)
1. HeapSort
2. QuickSort
3. InsertSort
4. ShellSort
5. MergeSort

[顺序统计量](https://github.com/jimolonely/AllInOne/tree/master/src/com/jimo/select)

看复杂度如何从nlgn到lgn

[Tree](https://github.com/jimolonely/AllInOne/tree/master/src/com/jimo/tree)
1. 二叉排序树
2. 红黑树
3. B树


 
