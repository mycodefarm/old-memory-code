package com.jimo.tree;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jimo on 17-8-19.
 */
public class BinarySearchTreeTest {

    BinarySearchTree tree;

    @Test
    @Before
    public void init() throws Exception {
        tree = new BinarySearchTree();
        int[] a = {8, 3, 5, 6, 7, 1, 0, 2, 9};
        tree.create(a);
    }

    @Test
    public void create() throws Exception {
        tree.inorderTreeWalk();//0,1,2,3,5,6,7,8,9,
    }

    @Test
    public void insert() throws Exception {
        tree.insert(new BinaryNode(1));
        tree.inorderTreeWalk();//1,
    }

    @Test
    public void search() throws Exception {
        BinaryNode re = tree.search(3);
        assertEquals(8, re.p.key);
    }

    @Test
    public void maxAndMinimum() throws Exception {
        BinaryNode maximum = tree.maximum();
        assertEquals(9, maximum.key);
        BinaryNode minimum = tree.minimum();
        assertEquals(0, minimum.key);
    }

    @Test
    public void successor() throws Exception {
        BinaryNode successor = tree.successor(tree.search(8));
        assertEquals(9, successor.key);
    }

    @Test
    public void precursor() throws Exception {
        BinaryNode precursor = tree.precursor(tree.search(9));
        assertEquals(8, precursor.key);
    }

    @Test
    public void delete() throws Exception {
        tree.delete(tree.search(3));
        tree.delete(tree.search(8));
        tree.inorderTreeWalk();//0,1,2,5,6,7,9,
    }
}