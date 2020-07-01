package com.example.jdk8demo;

public class InitDemo {
    private static class InitDemoFactory{
        public static InitDemo instance = new InitDemo();
    }

    public static InitDemo getInstance(){
        return InitDemoFactory.instance;
    }
}
