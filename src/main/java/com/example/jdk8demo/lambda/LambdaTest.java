package com.example.jdk8demo.lambda;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class LambdaTest {

    /**
     * 模拟员工集合
     */
    private List<Employer> list = Arrays.asList(
            new Employer("张三",18,2222.22),
            new Employer("李四",32,3333.33),
            new Employer("王五",52,4444.44),
            new Employer("赵六",44,5555.55),
            new Employer("田七",8,4235.32),
            new Employer("牛八",28,3256.52)
    );

    /**
     * 查询年龄大于35岁的员工
     * @return
     */
    public List<Employer> getEmpolyerByAge(){
        List<Employer> employerList = new ArrayList<>();
        for (Employer employer : list){
            if(employer.getAge() > 35){
                employerList.add(employer);
            }
        }
        return employerList;
    }

    /**
     * 查询工资大于4000的员工
     * @return
     */
    public List<Employer> getEmpolyerBySalary(){
        List<Employer> employerList = new ArrayList<>();
        for (Employer employer : list){
            if(employer.getSalary() > 4000){
                employerList.add(employer);
            }
        }
        return employerList;
    }

    /**
     * 使用策略模式查询员工信息
     * @param empolyerService
     * @return
     */
    public List<Employer> getEmpolyerList(EmpolyerService<Employer> empolyerService){
        List<Employer> employerList = new ArrayList<>();
        for (Employer employer : list){
            if(empolyerService.filter(employer)){
                employerList.add(employer);
            }
        }
        return employerList;
    }



    //原来的匿名内部类实现方式
    public void test1(){
        //定义一个匿名内部类comparator
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1,o2);
            }
        };
        //将匿名内部类作为对象传入
        TreeSet<Integer> treeSet = new TreeSet<>(comparator);
    }

    //lambda表达式实现
    public void test2(){
        //lambda表达式
        Comparator<Integer> comparator = (x,y)->Integer.compare(x,y);
        //将匿名内部类作为对象传入
        TreeSet<Integer> treeSet = new TreeSet<>(comparator);
    }

    /**
     * 需求一：查询年龄大于35岁的员工
     */
    public void test3(){
        LambdaTest lambdaTest = new LambdaTest();
        List<Employer> employerList = lambdaTest.getEmpolyerByAge();
        for (Employer employer : employerList){
            log.info(employer.toString());
        }
    }

    /**
     * 需求二：查询工资大于4000的员工
     */
    public void test4(){
        LambdaTest lambdaTest = new LambdaTest();
        List<Employer> employerList = lambdaTest.getEmpolyerBySalary();
        for (Employer employer : employerList){
            log.info(employer.toString());
        }
    }

    /**
     * 优化一：采用策略模式
     */
    public void test5(){
        List<Employer> employerList = getEmpolyerList(new EmpolyerImplByage());
        for (Employer employer : employerList){
            log.info(employer.toString());
        }
        log.info("=============================================");
        employerList = getEmpolyerList(new EmpolyerImplBySalary());
        for (Employer employer : employerList){
            log.info(employer.toString());
        }
    }

    /**
     * 优化方式二：匿名内部类
     */
    public void test6(){
        List<Employer> employerList = getEmpolyerList(new EmpolyerService<Employer>() {
            @Override
            public boolean filter(Employer employer) {
                return employer.getAge() > 20;
            }
        });
        for (Employer employer : employerList){
            log.info(employer.toString());
        }
        log.info("=============================================");
        employerList = getEmpolyerList(new EmpolyerService<Employer>() {
            @Override
            public boolean filter(Employer employer) {
                return employer.getSalary() > 3000;
            }
        });
        for (Employer employer : employerList){
            log.info(employer.toString());
        }
    }


    /**
     * 优化方式七：lambda表达式
     */
    public void test7(){
        LambdaTest lambdaTest = new LambdaTest();
        List<Employer> employerList = lambdaTest.getEmpolyerList((e) -> e.getAge()>30);
        employerList.forEach(System.out::println);
        log.info("=============================================");
        employerList = lambdaTest.getEmpolyerList((e)->e.getSalary()>3000);
        employerList.forEach(System.out::println);
    }

    /**
     * 使用StreamAPI查询员工信息
     */
    public void test8(){
        list.stream()//流式处理
                .filter((e)->e.getSalary()>2000)//过滤出工资大于2000的员工
                .filter((e)->e.getAge()>30)//过滤出年龄大于30的员工
                .limit(2)//只查询前两条
                .forEach(System.out::println);//循环打印
    }
}
