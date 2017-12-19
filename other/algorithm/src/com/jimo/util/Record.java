package com.jimo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 用于查看结果和时间
     *
     * @param callBack
     * @param methodName
     */
    public void testTimeObvious(CallBack callBack, String methodName) {
        long begin = System.currentTimeMillis();
        System.out.println("方法[" + methodName + "]结果:" + callBack.execute()); /// 进行回调操作
        long end = System.currentTimeMillis();
        System.out.println("方法[" + methodName + "]时间:" + (end - begin) + " ms");
    }
}
