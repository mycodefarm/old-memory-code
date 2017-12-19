package com.jimo.tree;

/**
 * Created by jimo on 17-8-19.
 */
public class BinarySearchTree {

    BinaryNode root;//根节点

    public void create(int[] a) {
        for (int k : a) {
            insert(new BinaryNode(k));
        }
    }

    public void insert(BinaryNode z) {
        BinaryNode x = root;
        BinaryNode y = null;
        while (x != null) {
            y = x;
            if (x.key > z.key) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        z.p = y;
        if (y == null) {
            root = z; //树是空的，z作为根节点
        } else if (y.key > z.key) {
            y.left = z;
        } else {
            y.right = z;
        }
    }

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

    public BinaryNode minimum() {
        return minimum(root);
    }

    /**
     * 最小值为最左子树叶节点
     *
     * @return
     */
    private BinaryNode minimum(BinaryNode x) {
        while (x.left != null) {
            x = x.left;
        }
        return x;
    }

    public BinaryNode maximum() {
        return maximum(root);
    }

    /**
     * 最大值为最右节点
     *
     * @return
     */
    private BinaryNode maximum(BinaryNode x) {
        while (x.right != null) {
            x = x.right;
        }
        return x;
    }

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
}
