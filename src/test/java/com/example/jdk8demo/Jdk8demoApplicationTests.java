package com.example.jdk8demo;

import com.alibaba.fastjson.JSON;
import com.example.jdk8demo.BuiltInterface.Java8Test;
import com.example.jdk8demo.lambda.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@SpringBootTest
@Slf4j
class Jdk8demoApplicationTests {

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

    @Test
    public void consumerTest(){
        log.info("consumerTest输出============={}");
        consumerMethod(100, m -> log.info("{}*5={}", m, (double)m*5));
    }

    public List supplierMethod(int num, Supplier supplier){
        List list = new ArrayList<>();
        for (int i=0;i< num;i++){
            list.add(supplier.get());
        }
        return list;
    }

    @Test
    public void supplierTest(){
        List<Integer> s = supplierMethod(5,()-> (int)(Math.random()*100));
        log.info("supplierTest输出============={}",JSON.toJSONString(s));
    }

    public String functionMethod(String num, Function<String,String> function){
        return function.apply(num);
    }

    @Test
    public void functionTest(){
        String s = functionMethod("354sd654sdasd",(x)->x.split("s")[0]);
        log.info("functionTest输出============={}",s);
    }

    public List<String> preticateMethod (List<String> list, Predicate<String> predicate){
        Iterator iterator = list.iterator();
        while (iterator.hasNext()){
            if(!predicate.test((String) iterator.next())){
                iterator.remove();
            }
        }
        return list;
    }

    @Test
    public void preticateTest(){
        List<String> list = new ArrayList<>();
        list.add("654564");
        list.add("874984");
        list.add("adsfdf");
        list.add("asfd");
        list.add("ghgfhgfh");
        list.add("54ads");
        log.info("functionTest输出============={}", JSON.toJSONString(preticateMethod(list,(x)->x.contains("a"))));
    }
}
