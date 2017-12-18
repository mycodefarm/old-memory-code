package com.jimo.test.apt;

/**
 * Created by jimo on 17-8-27.
 */
public class Adder {

    @ExtractInterface("add")
    public int add(int x, int y) {
        return x + y;
    }

    public static void main(String[] args) {
        System.out.println(new Adder().add(1, 2));
    }
}
