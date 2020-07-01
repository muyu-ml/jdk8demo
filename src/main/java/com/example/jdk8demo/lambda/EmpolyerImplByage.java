package com.example.jdk8demo.lambda;

public class EmpolyerImplByage implements EmpolyerService<Employer> {
    @Override
    public boolean filter(Employer employer) {
        return employer.getAge() > 35;
    }
}
