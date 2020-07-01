package com.example.jdk8demo;

import lombok.extern.slf4j.Slf4j;
import sun.java2d.cmm.Profile;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Profiler {
    private static final ThreadLocal<Instant> TIME_THREADLOCAL = new ThreadLocal<Instant>(){
        @Override
        protected Instant initialValue(){
            return Instant.now();
        }
    };

    public static final void bagin(){
        TIME_THREADLOCAL.set(Instant.now());
    }

    public static final long end(){
        return Duration.between(TIME_THREADLOCAL.get(),Instant.now()).toMillis();
    }

    public static void main(String[] args) throws Exception{
        Profiler.bagin();;
        TimeUnit.SECONDS.sleep(1);
        log.info("执行时间：{}",Profiler.end());

    }
}
