package com.example.jdk8demo.lambda;

public class EmpolyerImplBySalary implements EmpolyerService<Employer> {
    @Override
    public boolean filter(Employer employer) {
        return employer.getSalary() > 4000;
    }
}
