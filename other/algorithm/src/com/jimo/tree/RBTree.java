package com.jimo.tree;

import static com.jimo.tree.RBTreeNode.Color.BLACK;
import static com.jimo.tree.RBTreeNode.Color.RED;

/**
 * Created by jimo on 17-8-23.
 * 红黑树
 */
public class RBTree {
    private RBTreeNode root;
    private RBTreeNode nil;//这个结点不做什么，为黑色，相当于null

    public RBTree() {
        nil = new RBTreeNode();
        nil.color = BLACK;
        root = nil;
    }

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

    public void create(int[] a) {
        for (int x : a) {
            insert(new RBTreeNode(x));
        }
    }

    public void inorderWalk() {
        inorderWalk(root);
    }

    private void inorderWalk(RBTreeNode x) {
        if (x != nil) {
            inorderWalk(x.left);
            System.out.print(x.key + ",");
            inorderWalk(x.right);
        }
    }
}
