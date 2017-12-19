package com.jimo.tree;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jimo on 17-8-24.
 */
public class RBTreeTest {

    RBTree tree;

    @Test
    @Before
    public void create() throws Exception {
        int[] a = {4, 3, 5, 6, 7, 1, 0, 2, 9};
        tree = new RBTree();
        tree.create(a);
    }

    @Test
    public void inorderWalk() throws Exception {
        tree.inorderWalk();
    }

    @Test
    public void insert() throws Exception {
    }

}