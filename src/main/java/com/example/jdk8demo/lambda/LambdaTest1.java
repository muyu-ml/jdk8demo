package com.example.jdk8demo.lambda;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Consumer;

@Slf4j
public class LambdaTest1 {

    /**
     * lambda表达式一
     */
    public void test1(){
        //jdk1.7之前
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.print("hello runnable");
            }
        };
        runnable.run();

        System.out.println("=============================");

        //使用lambda表达式
        Runnable runnable1 = () -> System.out.println("hello lambda");
        runnable1.run();
    }


    public void test2(){
        Comparator<Integer> comparator = (Integer x, Integer y) -> Integer.compare(x,y);
    }

    public void test3(){
        Comparator<Integer> comparator = (x, y) -> Integer.compare(x, y);
    }

    public void test4(){
        Consumer<String> consumer = x -> System.out.println(x);
        consumer.accept("hello");
    }

    public void test5(){
        Comparator<Integer> comparator = (Integer x, Integer y) -> {
            log.info("hello test5");
            return Integer.compare(x,y);
        };
    }


    public void test6(){
        //获取10的平方
        log.info("============" + getInteger(10,(x) -> x*x));
    }

    public Integer getInteger(Integer i, MyInterface mi){
        return mi.getInteger(i);
    }
}
