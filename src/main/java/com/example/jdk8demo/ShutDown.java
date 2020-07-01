package com.example.jdk8demo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class ShutDown {
    public static void main(String[] args) throws Exception{
        Runner a = new Runner();
        Thread countThread = new Thread(a,"countThread-A");
        countThread.start();
        TimeUnit.SECONDS.sleep(1);
        countThread.interrupt();//重要
        Runner b = new Runner();
        Thread countThread2 = new Thread(b,"countThread-B");
        countThread2.start();
        TimeUnit.SECONDS.sleep(1);
        b.cancel();
    }

    private static class Runner implements Runnable{
        private long i;
        private volatile boolean on = true;

        @Override
        public void run(){
            while (on && !Thread.currentThread().isInterrupted()){
                i++;
            }
            log.info("{}-Count i = {}",Thread.currentThread().getName(),i);
        }
        public void cancel(){
            on = false;
        }
    }
}
