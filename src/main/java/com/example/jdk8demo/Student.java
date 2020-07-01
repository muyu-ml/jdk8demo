package com.example.jdk8demo;

import com.alibaba.fastjson.JSON;

public class Student {
    private String name;
    private Integer age;
    private Status status;

    public String name1;
    public Integer age1;

    private String getName1(){
        return this.name1;
    }

    private String setName(String name){
        this.name = name;
        return "调用setName成功，设置为"+this.name;
    }

    private String setName1(String name){
        this.name1 = name;
        return "调用setName1成功，设置为"+this.name1;
    }

    public Student(String name,Integer age){
        this.name = name;
        this.age = age;
    }

    public Student(){

    }

    public Student(String name,Integer age, Status status){
        this.name = name;
        this.age = age;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Status getStatus(){
        return status;
    }



    public enum Status{
        FREE,
        BUSY,
        VOCATION
    }

}
