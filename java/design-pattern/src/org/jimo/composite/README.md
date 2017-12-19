# Composite复合模式
使容器和内容具有一致性，创造出递归结构。这里以文件和文件夹为例。

## 文件和目录的父类
```java
public abstract class Entry {
    abstract String getName();

    abstract int getSize();

    protected abstract void printList(String prefix);

    public Entry add(Entry entry) {
        throw new RuntimeException();
    }

    @Override
    public String toString() {
        return getName() + "(" + getSize() + ")";
    }
}
```

## 文件类
不实现add方法
```java
public class File extends Entry {
    private String name;
    private int size;

    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    String getName() {
        return name;
    }

    @Override
    int getSize() {
        return size;
    }

    @Override
    protected void printList(String prefix) {
        System.out.println(prefix + "/" + this);
    }
}
```

## 文件夹类
可以包含文件或文件夹
```java
public class Directory extends Entry {

    private String name;
    private List<Entry> dirs = new ArrayList<>();

    public Directory(String name) {
        this.name = name;
    }

    @Override
    String getName() {
        return name;
    }

    @Override
    int getSize() {
        int size = 0;
        Iterator<Entry> it = dirs.iterator();
        while (it.hasNext()) {
            Entry entry = it.next();
            size += entry.getSize();
        }
        return size;
    }

    @Override
    protected void printList(String prefix) {
        System.out.println(prefix + "/" + this);
        for (Entry entry : dirs) {
            entry.printList(prefix + "/" + name);
        }
    }

    @Override
    public Entry add(Entry entry) {
        dirs.add(entry);
        return this;
    }
}
```
## Main-测试
```java
public class Main {

    public static void main(String[] args) {
        Directory root = new Directory("root");
        Directory bin = new Directory("bin");
        Directory tmp = new Directory("tmp");
        Directory usr = new Directory("usr");
        root.add(bin);
        root.add(tmp);
        root.add(usr);

        bin.add(new File("vim", 1000));
        bin.add(new File("java", 10000));

        usr.add(new File("ja.txt", 1024));
        usr.add(new File("cdn.java", 100990));
        usr.add(new File("cdg.c", 10242343));

        root.printList("/");

        /**
         //root(10355357)
         //root/bin(11000)
         //root/bin/vim(1000)
         //root/bin/java(10000)
         //root/tmp(0)
         //root/usr(10344357)
         //root/usr/ja.txt(1024)
         //root/usr/cdn.java(100990)
         //root/usr/cdg.c(10242343)
         */
    }
}
```

## 总结
可以做什么：
```java
处理多个和单个的一致性结构。
```
实际举例：
```java
1. 测试用例：来自文件、输入和网络的不同输入结合成一个输入，一个输出。
2. GUI的界面包含都是递归结构。
```
