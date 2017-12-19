package com.jimo.tree;

/**
 * Created by jimo on 17-8-20.
 */
public class BinaryNode {
    BinaryNode p;//父节点
    BinaryNode left;
    BinaryNode right;
    int key;

    public BinaryNode(int k) {
        key = k;
    }
}
