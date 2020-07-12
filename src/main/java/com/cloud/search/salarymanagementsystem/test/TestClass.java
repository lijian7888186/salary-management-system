package com.cloud.search.salarymanagementsystem.test;

public class TestClass {
    public static void main(String[] args) {
        try {
            Class.forName("com.cloud.search.salarymanagementsystem.test.TestStatic");
            TestStatic testStatic = new TestStatic();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
