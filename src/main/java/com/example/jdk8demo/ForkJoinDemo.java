package com.example.jdk8demo;

import java.util.concurrent.RecursiveTask;


public class ForkJoinDemo extends RecursiveTask<Long> {
    private long start;
    private long end;
    private static  final long  mm = 100;

    public ForkJoinDemo(long start,long end){
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long length = end - start;
        if(length <= mm){
            long sum = 0;
            for (long i=start;i<= end;i++){
                sum +=i;
            }
            return sum;
        }else{
            long mid = (start+end)/2;
            ForkJoinDemo left = new ForkJoinDemo(start,mid);
            left.fork();
            ForkJoinDemo right = new ForkJoinDemo(mid+1,end);
            right.fork();
            return left.join() + right.join();
        }
    }
}
