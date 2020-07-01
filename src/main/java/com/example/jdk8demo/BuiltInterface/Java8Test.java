package com.example.jdk8demo.BuiltInterface;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Slf4j
public class Java8Test {
    /**
     *
     * java8 四大内置接口
     *
     * 1、消费性接口：Consumer<T>
     *     默认方法：void accept(T t)
     *
     * 2、供给型接口：Supplier<T>
     *     默认方法：T get()
     *
     * 3、函数型接口：Function<T, R>
     *     默认方法：R apply(T t)
     *
     * 4、断言型接口：Predicate<T>
     *     默认方法：boolean test(T t)
     */

    public void consumerMethod(double d, Consumer consumer){
        consumer.accept(d);
    }

    /**


     */


}
