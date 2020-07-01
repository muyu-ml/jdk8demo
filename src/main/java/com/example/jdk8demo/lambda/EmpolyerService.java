package com.example.jdk8demo.lambda;

@FunctionalInterface
public interface EmpolyerService<T> {
    boolean filter(T t);
}
