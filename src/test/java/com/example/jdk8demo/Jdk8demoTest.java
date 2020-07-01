package com.example.jdk8demo;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import sun.security.provider.Sun;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.*;

@SpringBootTest
@Slf4j
class Jdk8demoTest {

    /**
     * 对象::实例方法名
     * 类::静态方法名
     * 类::实例方法名
     *
     */

    @Test
    public void test5(){
        //类::静态方法名
        Comparator<Integer> comparator = Integer::compare;
        log.info("=================={}",comparator.compare(10,20));
    }


    @Test
    public void test6(){
        //类::实例方法名
        //条件限制：第一个入参是调用该方法的调用者，第二个参数是方法引用的入参时，可以使用类的实例方法调用
        BiPredicate<String, String> biPredicate = (x, y) -> x.equals(y);
        log.info("=========================={}",biPredicate.test("111","111"));

        BiPredicate<String, String> biPredicate1 = String::equals;
        log.info("=========================={}",biPredicate.test("111","222"));

    }

    @Test
    public void test1(){
        //方法引用，lambda表达式中的方法已经在其他方法中已经有实现，可以直接使用双冒号进行方法引用，引用的前提是使用的出入参和引用方法的出入参必须一致
        //对象::实例方法名
        PrintStream ps = System.out;
        Consumer<String> consumer = (x)->ps.println(x);
        consumer.accept("000000000000000000000");

        PrintStream ps1 = System.out;
        Consumer<String> consumer1 = ps1::println;
        consumer1.accept("111111111111111111111");

        Consumer<String> consumer2 = System.out::println;
        consumer2.accept("2222222222222222222222");

    }

    @Test
    public void test4(){

        Student student = new Student();
        Supplier<String> supplier = ()-> student.getName();
        log.info("Supplier===================={}",supplier.get());

        Supplier<Integer> supplier1 = student::getAge;
        Integer age = supplier1.get();
        log.info("Supplier===================={}",age);
    }

    @Test
    public void test2(){
        Supplier<Student> supplier = ()->new Student();
        log.info("======================{}",JSON.toJSONString(supplier.get()));
        Supplier<Student> supplier1 = Student::new;
        log.info("======================{}",JSON.toJSONString(supplier1.get()));

        Function<String, Student> function = (x)->new Student(x);
        log.info("===================={}",JSON.toJSONString(function.apply("lcl")));

        Function<String, Student> function1 = Student::new;
        log.info("===================={}",JSON.toJSONString(function1.apply("mm")));
        BiFunction<String , Integer ,Student> function2 = Student::new;
        log.info("===================={}",JSON.toJSONString(function2.apply("xxx",25)));
    }


    @Test
    public void test3(){
        //数组引用
        Function<Integer,String[]> function = (x)-> new String[x];
        log.info("===================={}",function.apply(10).length);

        Function<Integer,String[]> function1 = String[]::new;
        log.info("===================={}",function1.apply(5).length);
    }

    class Student{
        private String name = "lcl";
        private Integer age = 18;

        public String getName() {
            return name;
        }

        public Integer getAge() {
            return age;
        }

        public Student(String name){
            this.name = name;
            age = 15;
        }

        public Student(String name, Integer age){
            this.name = name;
            this.age = age;
        }

        public Student(){
            this.name = "lcl";
            this.age = 15;
        }

    }

}
