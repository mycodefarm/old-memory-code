package com.jimo.test.unit;

/**
 * Created by jimo on 17-8-27.
 */
public class UTestExample {

    public String methodOne() {
        return "This is method one";
    }

    public int methodTwo() {
        System.out.println("This is method 2");
        return 2;
    }

    //成功的
    @UTest
    boolean methodOneTest() {
        return methodOne().equals("This is method one");
    }

    @UTest
    boolean methodTwoTest() {
        return methodTwo() == 2;
    }

    //失败的
    @UTest
    boolean failureOne() {
        return false;
    }

    @UTest
    boolean failureTwo() {
        return false;
    }

    public static void main(String[] args) {
        new UnitCore().run(UTestExample.class);
        /**
         com.jimo.test.unit.UTestExample
         . methodOneTest
         . methodTwoTest This is method 2

         . failureOne (failed)
         . failureTwo (failed)
         (4 tests)

         >>> 2 FAILURES <<<
         com.jimo.test.unit.UTestExample: failureOne
         com.jimo.test.unit.UTestExample: failureTwo
         */
    }
}
