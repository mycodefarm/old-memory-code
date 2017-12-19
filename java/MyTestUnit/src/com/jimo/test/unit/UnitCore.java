package com.jimo.test.unit;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jimo on 17-8-27.
 */
public class UnitCore {
    static Class<?> testClass;
    static List<String> failedTests = new ArrayList<>();
    static long testsRun = 0;
    static long failures = 0;

    public static void main(String[] args) {
        new UnitCore().run(UTestExample.class);
    }

    private static void printResult() {
        if (failures == 0) {
            System.out.println("OK (" + testsRun + " tests)");
        } else {
            System.out.println("(" + testsRun + " tests)");
            System.out.println("\n>>> " + failures + " FAILURE" + (failures > 1 ? "S" : "") + " <<<");
            for (String failed : failedTests) {
                System.out.println("   " + failed);
            }
        }
    }

    public void run(Class<?> klass) {
        //允许断言
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
        process(klass);
        printResult();
    }

    public void process(Class<?> cl) {
        testClass = cl;
        TestMethods testMethods = new TestMethods();

        for (Method m : testClass.getDeclaredMethods()) {
            testMethods.addIfTestMethod(m);
        }
        if (testMethods.size() > 0) {
            System.out.println(testClass.getName());
        }
        for (Method m : testMethods) {
            System.out.print("   . " + m.getName() + " ");
            try {
                Object testObject = createTestObject(m);
                boolean success = false;
                try {
                    if (m.getReturnType().equals(boolean.class)) {
                        success = (boolean) m.invoke(testObject);
                    } else {
                        m.invoke(testObject);
                        success = true;
                    }
                } catch (Exception e) {
                    System.out.println(e.getCause());
                }

                System.out.println(success ? "" : "(failed)");

                testsRun++;
                if (!success) {
                    failures++;
                    failedTests.add(testClass.getName() + ": " + m.getName());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class TestMethods extends ArrayList<Method> {
        void addIfTestMethod(Method m) {
            if (m.getAnnotation(UTest.class) == null) {
                return;
            }
            if (!m.getReturnType().equals(boolean.class) && !m.getReturnType().equals(void.class)) {
                throw new RuntimeException("@Test method must return boolean or void");
            }
            m.setAccessible(true);
            add(m);
        }
    }

    private static Object createTestObject(Method m) {
        /*if (m != null) {
            try {
                return m.invoke(testClass);
            } catch (Exception e) {
                throw new RuntimeException("Cannot run @TestObject(m) method");
            }
        } else { */
        //使用默认的构造器
        try {
            return testClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Cannot create a test object.");
        }
    }
}
