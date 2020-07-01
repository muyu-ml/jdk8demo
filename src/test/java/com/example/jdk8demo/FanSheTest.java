package com.example.jdk8demo;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@SpringBootTest
@Slf4j
class FanSheTest {

    @Test
    public void test() throws Exception{
        //获取反射对象
        Student student = new Student();
        Class studentClass1 = student.getClass();
        log.info("student.getClass()反射结果{}",studentClass1.getName());
        Class studentClass2 = Student.class;
        log.info("Student.class反射结果{}",studentClass2.getName());
        log.info("Student.class==student.getClass()结果{}",studentClass1==studentClass2);
        Class studentClass3 = Class.forName("com.example.jdk8demo.Student");
        log.info("Class.forName反射结果{}",studentClass3.getName());
        log.info("Student.class==Class.forName结果{}",studentClass3==studentClass2);


        //获取构造函数集合
        Constructor[] cons = studentClass1.getConstructors();
        log.info("反射获取所有构造函数{}", JSON.toJSONString(cons));
        //获取无参构造
        Constructor con = studentClass1.getConstructor();
        log.info("反射获取无参构造函数{}", JSON.toJSONString(con));
        //获取有参构造
        Constructor con1 = studentClass1.getConstructor(String.class, Integer.class);
        log.info("反射获取有参构造函数{}", JSON.toJSONString(con));

        //获取公有字段，此处字段必须是public修饰，否则会报错
        Field field = studentClass1.getField("name1");
        log.info("公有字段{}", JSON.toJSONString(con));

        //获取私有方法
        Method setName1 = studentClass1.getDeclaredMethod("setName1", String.class);
        setName1.setAccessible(true);
        String s = (String)setName1.invoke(Student.class.newInstance(),"lcl");
        log.info("========={}", s);

        //获取公有方法
        Method setName = studentClass1.getDeclaredMethod("setName", String.class);
        String ss = (String)setName1.invoke(Student.class.newInstance(),"mm");
        log.info("========={}", ss);
    }
}
