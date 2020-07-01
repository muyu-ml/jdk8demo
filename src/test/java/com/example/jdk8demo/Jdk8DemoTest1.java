package com.example.jdk8demo;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@SpringBootTest
@Slf4j
class Jdk8DemoTest1{


    /**
     * 原有多线程缺点，有可能会不很好的使用系统资源，举例：
     * 并行流
     *  jdk7：使用的是fork/join处理，fork将大任务拆分成一个个小任务，每个小任务单独处理，处理完毕后使用join将各个任务的结果进行汇总
     *  fork/join效率高，使用了工作窃取模式
     *
     */

    @Test
    public void test(){
        long num = 50*10000*10000L;
        test1(num);
        test3(num);
        test4(num);
        test2(num);
    }


    public void test1(long num){
        Instant start = Instant.now();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Long> forkJoinTask = new ForkJoinDemo(0,num);
        long sum = forkJoinPool.invoke(forkJoinTask);
        Instant end = Instant.now();
        log.info("fork/join运行时间【{}】,运行结果【{}】",Duration.between(start,end).toMillis(),sum);
    }


    public void test2(long num){
        long sum = 0;
        Instant start = Instant.now();
        for(int i=0;i<=num;i++){
            sum+=i;
        }
        Instant end = Instant.now();
        log.info("单线程for循环运行时间【{}】,运行结果【{}】",Duration.between(start,end).toMillis(),sum);
    }


    public void test3(long num){
        Instant start = Instant.now();
        OptionalLong optionalLong = LongStream.rangeClosed(0,num).sequential().reduce((x,y)->x+y);
        Instant end = Instant.now();
        log.info("串行流运行时间【{}】,运行结果【{}】",Duration.between(start,end).toMillis(),optionalLong.getAsLong());
    }

    public void test4(long num){
        Instant start = Instant.now();
        OptionalLong optionalLong = LongStream.rangeClosed(0,num).parallel().reduce((x,y)->x+y);
        Instant end = Instant.now();
        log.info("并行流运行时间【{}】,运行结果【{}】",Duration.between(start,end).toMillis(),optionalLong.getAsLong());
    }



}
