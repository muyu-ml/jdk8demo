package com.example.jdk8demo;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Join {
    public static void main(String[] args) throws Exception{
        Thread previous = Thread.currentThread();
        for(int i=0;i<10;i++){
            Thread thread = new Thread(new Domino(previous),String.valueOf(i));
            thread.start();
            previous = thread;
        }
        TimeUnit.SECONDS.sleep(5);
        log.info("{}执行完毕【{}】",Thread.currentThread().getName(),LocalDateTime.now());
    }

    static class Domino implements Runnable{

        private Thread thread;
        public Domino(Thread thread){
            this.thread = thread;
        }

        @Override
        public void run(){
            try {
                log.info("【{}】===join线程===【{}】===【{}】",thread.getName(),Thread.currentThread().getName(),LocalDateTime.now());
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("【{}】执行完毕===【{}】",Thread.currentThread().getName(),LocalDateTime.now());
        }
    }
}
