package com.example.jdk8demo.lambda;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class Employer {
    private String name;
    private Integer age;
    private double salary;
}
