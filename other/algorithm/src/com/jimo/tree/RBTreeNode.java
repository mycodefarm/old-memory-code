package com.jimo.tree;

/**
 * Created by jimo on 17-8-23.
 * 红黑树节点
 */
public class RBTreeNode {
    enum Color {
        RED, BLACK
    }

    RBTreeNode p;
    RBTreeNode left;
    RBTreeNode right;
    int key;
    Color color;

    public RBTreeNode(int key) {
        this.key = key;
    }

    public RBTreeNode() {
    }
}
