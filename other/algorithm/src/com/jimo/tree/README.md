# 树
这种美丽的数据结构和生物

## 二叉搜索树
最简单的集搜索功能的二叉树

### 定义
节点
```java
public class BinaryNode {
    BinaryNode p;//父节点
    BinaryNode left;
    BinaryNode right;
    int key;

    public BinaryNode(int k) {
        key = k;
    }
}
```
树
```java
public class BinarySearchTree {

    BinaryNode root;//根节点
}
```

### 创建树
就是不断地插入
```java
    public void create(int[] a) {
        for (int k : a) {
            insert(new BinaryNode(k));
        }
    }
```

### 遍历
#### 中序遍历
```java
    public void inorderTreeWalk() {
        inorderTreeWalk(root);
    }

    /**
     * 中序遍历
     *
     * @param x
     */
    private void inorderTreeWalk(BinaryNode x) {
        if (x != null) {
            inorderTreeWalk(x.left);
            System.out.print(x.key + ",");
            inorderTreeWalk(x.right);
        }
    }
```

### 搜索
```java
/**
     * 供外部调用
     *
     * @param k
     * @return
     */
    public BinaryNode search(int k) {
        return search(root, k);
    }

    /**
     * 从x节点递归搜索k
     *
     * @param x
     * @param k
     * @return
     */
    private BinaryNode search(BinaryNode x, int k) {
        if (x == null || x.key == k) {
            return x;
        }
        if (k < x.key) {
            return search(x.left, k);
        } else {
            return search(x.right, k);
        }
    }

    /**
     * 迭代搜索k，效率更高
     *
     * @param x
     * @param k
     * @return
     */
    private BinaryNode searchIterative(BinaryNode x, int k) {
        while (x != null || x.key != k) {
            if (k < x.key) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        return x;
    }
```

### 最大最小值
```java
    /**
     * 最小值为最左子树叶节点
     *
     * @return
     */
    public BinaryNode minimum() {
        BinaryNode x = root;
        while (x.left != null) {
            x = x.left;
        }
        return x;
    }

    /**
     * 最大值为最右节点
     *
     * @return
     */
    public BinaryNode maximum() {
        BinaryNode x = root;
        while (x.right != null) {
            x = x.right;
        }
        return x;
    }
```

### 前驱和后继
```java
    /**
     * 中序遍历下x节点的后继
     *
     * @param x
     * @return
     */
    public BinaryNode successor(BinaryNode x) {
        //若右子树不为空，则是右子树最左边的节点
        if (x.right != null) {
            return minimum(x.right);
        }
        //否则，延x向上找到第一个有左孩子的祖先，可以举x在左子树的例子
        BinaryNode y = x.p;
        while (y != null && x == y.right) {
            x = y;
            y = y.p;
        }
        return y;
    }

    /**
     * 中序遍历下x的前驱
     *
     * @param x
     * @return
     */
    public BinaryNode precursor(BinaryNode x) {
        if (x.left != null) {
            return maximum(x.right);
        }
        BinaryNode y = x.p;
        while (y != null && x == y.left) {
            x = y;
            y = y.p;
        }
        return y;
    }
```
### 删除节点
```java
    /**
     * 删除x节点，分为3种情况
     *
     * @param x
     */
    public void delete(BinaryNode x) {
        //1.x只有右节点,接过右节点即可
        if (x.left == null) {
            subDelete(x, x.right);
        }
        //2.x只有左节点或没有孩子，接过左节点
        else if (x.right == null) {
            subDelete(x, x.left);
        }
        //3.x有两个孩子,使用x的后继接替x
        else {
            BinaryNode y = minimum(x.right);//x的后继
            if (y.p != x) {//x的后继不是x的直接孩子,这里画个图很直观
                subDelete(y, y.right);
                y.right = x.right;
                y.right.p = y;
            }
            subDelete(x, y);
            y.left = x.left;
            y.left.p = y;
        }
    }

    /**
     * 用新节点n替换旧节点o
     * 结果就是：o的父节点成为n的父节点
     *
     * @param o
     * @param n
     */
    private void subDelete(BinaryNode o, BinaryNode n) {
        if (o.p == null) {//o是根节点
            root = n;
        } else if (o == o.p.left) {
            o.p.left = n;
        } else {
            o.p.right = n;
        }
        if (n != null) {
            n.p = o.p;
        }
    }
```

## 红黑树
没有一条路径会比其他路径长出2倍，近似平衡的二叉搜索树,满足5个要求：
1. 每个节点要么是黑色，要么是红色
2. 根节点是黑色
3. 叶子节点是黑色
4. 如果一个节点是红色，则它的2个孩子节点是黑色
5. 对每个节点，从该节点到其所有后代叶节点的简单路径上，均含有相同数目的黑色节点

仔细看看很容易理解的，只是为什么要这样需要费一番劲。

### 定义
```java
public class RBTreeNode {
    enum Color {
        RED, BLACK
    }

    RBTreeNode p;
    RBTreeNode left;
    RBTreeNode right;
    int key;
    Color color;
}
```
```java
public class RBTree {
    private RBTreeNode root;
    private RBTreeNode nil;//这个结点不做什么，为黑色，相当于null
    ...
}
```

### 左旋右旋
这是调整树必须的，也很简单
```java
    /**
     * 对结点进行左旋,脑中建立一种图像思维就很容易写出来
     * 既然是左旋，我只关心x和它右孩子的指针怎么变化
     *
     * @param x
     */
    private void leftRotate(RBTreeNode x) {
        RBTreeNode y = x.right;
        x.right = y.left;
        if (y.left != nil) {
            y.left.p = x;
        }
        y.p = x.p;
        if (x.p == nil) {
            root = y;
        } else if (x == x.p.left) {
            x.p.left = y;
        } else {
            x.p.right = y;
        }
        y.left = x;
        x.p = y;
    }

    /**
     * 相比左旋反过来就行了
     *
     * @param y
     */
    private void rightRotate(RBTreeNode y) {
        RBTreeNode x = y.left;
        y.left = x.right;
        if (x.right != nil) {
            x.right.p = y;
        }
        x.p = y.p;
        if (y.p == nil) {
            root = x;
        } else if (y.p.left == y) {
            y.p.left = x;
        } else {
            y.p.right = x;
        }
        x.right = y;
        y.p = x;
    }
```

### 插入
举几个例子对着代码理思路，会发现其实很简单，这是我的方法，在那个下雨的 夜晚。

```java
    /**
     * 插入基本和排序二叉树一样，只是最后来调整
     *
     * @param z
     */
    public void insert(RBTreeNode z) {
        RBTreeNode y = nil;
        RBTreeNode x = root;
        while (x != nil) {
            y = x;
            if (z.key < x.key) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        z.p = y;
        if (y == nil) {
            root = z;//这里隐藏了root.p = nil
        } else if (z.key < y.key) {
            y.left = z;
        } else {
            y.right = z;
        }
        z.left = nil;
        z.right = nil;
        z.color = RED;//初始插入设为红色
        insertFixUp(z);
    }

    /**
     * 考虑3种情况来调整以保持红黑性质
     *
     * @param z
     */
    private void insertFixUp(RBTreeNode z) {
        while (z.p.color == RED) {
            if (z.p == z.p.p.left) {
                RBTreeNode y = z.p.p.right;
                if (y.color == RED) {
                    z.p.color = BLACK; //case 1
                    y.color = BLACK; //case 1
                    z.p.p.color = RED;//case 1
                    z = z.p.p; //case 1
                    continue;
                } else if (z == z.p.right) {
                    z = z.p;//case 2
                    leftRotate(z);//case 2
                }
                z.p.color = BLACK;//case 3
                z.p.p.color = RED;//case 3
                rightRotate(z.p.p);//case 3
            } else { //右边情况反过来
                RBTreeNode y = z.p.p.left;
                if (y.color == RED) {
                    z.p.color = BLACK;
                    y.color = BLACK;
                    z.p.p.color = RED;
                    z = z.p.p;
                    continue;
                } else if (z == z.p.left) {
                    z = z.p;
                    rightRotate(z);
                }
                z.p.color = BLACK;
                z.p.p.color = RED;
                leftRotate(z.p.p);
            }
        }
        root.color = BLACK;
    }
```