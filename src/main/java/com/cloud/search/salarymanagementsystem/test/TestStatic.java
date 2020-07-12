package com.cloud.search.salarymanagementsystem.test;

public class TestStatic {
    static Integer id = getId();
    public TestStatic() {
        id = 1;
        throw new RuntimeException("");
    }
    private static Integer getId() {
        id = 1;
//        throw new RuntimeException("");
        return 1;
    }
}
