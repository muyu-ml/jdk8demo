package com.example.jdk8demo;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Slf4j
public class WaitNotify {
    static boolean flag = true;
    static Object lock = new Object();

    public static void main(String[] arg) throws Exception{
        Thread waitThread = new Thread(new Wait(),"waitThread");
        waitThread.start();
        TimeUnit.SECONDS.sleep(1);
        Thread notifyThread = new Thread(new Notify(),"notifyThread");
        notifyThread.start();
    }

    static class Wait implements  Runnable{

        @Override
        public void run(){
            synchronized (lock){
                while (flag){
                    log.info("{} flag is true. wait @{}",Thread.currentThread(),LocalDateTime.now());
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.info("{} flag is false. running @{}",Thread.currentThread(),LocalDateTime.now());
            }
        }
    }


    static class Notify implements  Runnable{

        @Override
        public void run(){
            synchronized (lock){
                log.info("{} hold lock,notify @{}",Thread.currentThread(),LocalDateTime.now());
                lock.notifyAll();
                flag = false;
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            synchronized (lock){
                log.info("{} hold lock again,sleep @{}",Thread.currentThread(),LocalDateTime.now());
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
