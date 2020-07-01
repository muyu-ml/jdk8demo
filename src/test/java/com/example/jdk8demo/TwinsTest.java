package com.example.jdk8demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@SpringBootTest
@Slf4j
public class TwinsTest {
    @Test
    public void test() throws Exception{
        final Lock lock = new TwinsLock();
        class Worker extends Thread{
            @Override
            public void run() {
                while (true){
                    try {
                        lock.tryLock();
                        TimeUnit.SECONDS.sleep(1);
                        log.info("{}==={}",Thread.currentThread().getName(),LocalDateTime.now());
                        TimeUnit.SECONDS.sleep(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        lock.unlock();
                    }
                }
            }
        }
        for (int i=0;i<10;i++){
            Worker worker = new Worker();
            worker.setDaemon(true);
            worker.start();
        }

        for (int i=0;i<10;i++){
            TimeUnit.SECONDS.sleep(1);
            log.info("=======================");
        }
    }
}
