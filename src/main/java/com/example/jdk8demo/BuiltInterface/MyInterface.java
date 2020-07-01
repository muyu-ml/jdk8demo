package com.example.jdk8demo.BuiltInterface;

public interface MyInterface {
    default String getName(){
        return "test";
    }
}
