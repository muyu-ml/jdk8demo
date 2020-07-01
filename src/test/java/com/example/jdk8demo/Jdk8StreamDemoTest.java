package com.example.jdk8demo;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
@Slf4j
class Jdk8StreamDemoTest {

    @Test
    public void test1(){

        //1、创建一个流对象  2、流操作    3、终止操作
        //创建一个流对象
        //1、通过Collection系列集合提供Strem()（串行流）和parellelStream()（并行流）得到一个流对象
       /* List list = new ArrayList();
        Stream stream = list.stream();
        Stream stream1 = list.parallelStream();
        //2、通过数组中的静态方法stream()获取数组流
        String[] arr = new String[0];
        Stream stream2 = Arrays.stream(arr);
        //3、通过Stream中的静态方法of()创建
        Stream stream3 = Stream.of(list);*/
        //4、创建无限流
        //无限流------迭代
        Stream stream4 = Stream.iterate(0, x->x+2);
        //无限流------迭代
        /*Stream stream6 = Stream.generate(()->Math.random());
        Stream stream5 = stream4.limit(10);*/
        //终止操作
        //stream4.forEach(System.out::println);
        //加中间操作的终止操作
        stream4.limit(3).forEach(System.out::println);
        
        //Stream.generate(()->Math.random()).limit(100).forEach(System.out::println);
    }


    /**
     * 筛选与切片
     * filter：接收lambda，从流中排除某些元素
     * limit(n)：截断流，使其元素不超过n
     * skip(n)：跳过元素，返回一个扔掉了n个元素的流，如果流中元素数不超过n，则返回一个空流，与limit(n)互补
     * distinct：筛选，通过流所生成的元素的hashCode和equals去重
     */
    @Test
    public void tst2(){
        log.info("filter测试==============================");
        students1.stream().filter((x)->x.getAge() < 10).forEach(System.out::println);

        //惰性求值：所有中间操作不会做任何处理，只有在终止操作时，才会处理
        //内部迭代，StreamAPI自己处理的迭代处理
        //短路，只要找到满足的数据后，后面的流程就不再处理
        log.info("limit测试==============================");
        students1.stream().filter((x)->{log.info("filter");return x.getAge() > 10;}).limit(3).forEach(System.out::println);

        log.info("skip测试==============================");
        students1.stream().skip(2).limit(3).forEach(System.out::println);

        log.info("distinct测试==============================");
        students1.stream().distinct().forEach(System.out::println);
    }


    /**
     * 映射：
     * map：接收一个lambda，将元素转换成其他形式或提取信息，接收一个函数作为参数，该函数会被应用到每一个元素上，并将其映射成一个新的元素
     * flatMap：接收一个函数作为参数，将其中的每个值都替换成另一个流，然后把所有流连接成一个流
     */
    @Test
    public void test3(){

        log.info("模拟map操作");
        List<String> list = Arrays.asList("aaa","bbb","ccc","ddd");
        list.stream().map((str)->str.toUpperCase()).forEach(System.out::println);
        students1.stream().map(Student::getName).forEach(System.out::println);


        log.info("模拟多重流操作");
        Stream<Stream<Character>> stream = list.stream().map(Jdk8StreamDemoTest::filterChaacter);
        stream.forEach(sm -> sm.forEach(System.out::println));

        log.info("模拟flatMap操作");
        list.stream().flatMap(Jdk8StreamDemoTest::filterChaacter).forEach(System.out::println);
    }


    public static Stream<Character> filterChaacter(String str){
        List<Character> list = new ArrayList<>();
        for (Character c : str.toCharArray()) {
            list.add(c);
        }
        return list.stream();
    }


    /**
     * 排序
     * sorted()自然排序：comparable
     * sorted(Comparator com)定制排序：comparator
     */
    @Test
    public void test4(){
        //自然排序
        Arrays.asList("uio","fghf","dsf","ewre","gffjf","poiklk").stream().sorted().forEach(System.out::println);
        //定制排序
        students1.stream().sorted((s1,s2)->s1.getAge().compareTo(s2.getAge())).forEach(System.out::println);
        //倒序排序
        students1.stream().sorted((s1,s2)->-s1.getAge().compareTo(s2.getAge())).forEach(System.out::println);
    }


    List<Student> students1 = Arrays.asList(
            new Student("lcl1",18, Status.BUSY)
            ,new Student("lcl2",30, Status.VOCATION)
            ,new Student("lcl3",50, Status.FREE)
            ,new Student("lcl4",20, Status.FREE)
            ,new Student("lcl5",25, Status.BUSY)
            ,new Student("lcl6",8, Status.FREE)
            ,new Student("lcl2",30, Status.FREE));

    /**
     * 查找与匹配
     * allMatch：检查是否匹配所有元素
     * anyMatch：检查是否至少匹配一个元素
     * noneMatch：检查是否所有都不匹配
     * findFirst：返回第一个元素
     * findAny：返回当前流中任意元素
     * count：返回当前流中元素个数
     * max：返回当前流中最大值
     * min：返回流中最小值
     */
    @Test
    public void test5(){
        log.info("allMatch结果{}",students1.stream().allMatch((e)-> e.getStatus() == Status.BUSY));
        log.info("anyMatch结果{}",students1.stream().anyMatch((e)-> e.getStatus() == Status.BUSY));
        log.info("noneMatch结果{}",students1.stream().noneMatch((e)-> e.getStatus() == Status.BUSY));

        Optional<Student> optionalStudent = students1.stream().sorted((e1, e2)->e1.getAge().compareTo(e2.getAge())).findFirst();
        if(optionalStudent.isPresent()) log.info("findFirst结果{}",optionalStudent.get());

        Optional<Student> optionalStudent1 = students1.stream().filter((e)->e.getStatus()==Status.FREE).findAny();
        if(optionalStudent1.isPresent()) log.info("findAny结果{}",optionalStudent1.get());

        log.info("count结果{}",students1.stream().count());

        Optional<Student> optionalStudent2 = students1.stream().max((e1,e2)->e1.getAge().compareTo(e2.getAge()));
        if(optionalStudent2.isPresent()) log.info("max结果{}",optionalStudent2.get());

        Optional<Student> optionalStudent3 = students1.stream().min((e1,e2)->e1.getAge().compareTo(e2.getAge()));
        if(optionalStudent3.isPresent()) log.info("min结果{}",optionalStudent3.get());
    }


    /**
     * 归约
     * reduce(T identity, BinaryOperator) / reduce(BinaryOperator) 可以将流中元素反复结合起来，得到一个流
     */
    @Test
    public void test6(){
        List<Integer> list = Arrays.asList(1,2,3,4,5);
        log.info("reduce结果{}",list.stream().reduce(100, (x,y)->x+y));
        log.info("reduce计算年龄总和结果{}",students1.stream().map((x)->x.getAge()).reduce(10,(x,y)->x+y));
    }

    /**
     * 收集：collect
     * 将流转换为其他形式，接收一个Collector接口的实现，用于给Stream中元素做汇总的方法
     */
    @Test
    public void test7(){
        //将所有的名字获取，并添加到集合中
        List list = students1.stream().map(Student::getName).collect(Collectors.toList());
        log.info("收集：collect测试结果{}",JSON.toJSONString(list));

        Set set = students1.stream().map(Student::getName).collect(Collectors.toCollection(HashSet::new));
        log.info("收集：特殊集合collect测试结果{}",JSON.toJSONString(set));


        log.info("收集：collect总数测试结果{}",students1.stream().collect(Collectors.counting()));

        log.info("收集：collect计算年龄平均值测试结果{}",students1.stream().collect(Collectors.averagingInt(Student::getAge)));

        log.info("收集：collect计算年龄总和测试结果{}",students1.stream().collect(Collectors.summarizingInt(Student::getAge)));

        log.info("收集：collect获取年龄最大测试结果{}",students1.stream().collect(Collectors.maxBy((s1,s2)->s1.getAge().compareTo(s2.getAge()))).get());

        log.info("收集：collect获取最小的年龄值测试结果{}",students1.stream().map(Student::getAge).collect(Collectors.minBy((s1,s2)->s1.compareTo(s2))).get());


        Map map3 = students1.stream().collect(Collectors.groupingBy(Student::getStatus));
        log.info("收集：collect按照Status分组测试结果{}", JSON.toJSONString(map3));


        Map<Status,Map<String,List<Student>>> map = students1.stream().collect(Collectors.groupingBy(Student::getStatus
                ,Collectors.groupingBy((x)->{
                    if(((Student)x).getAge() > 26){
                        return "中老年";
                    }else{
                        return "青少年";
                    }
                })
        ));
        log.info("收集：collect按照Status分组后按照年龄二次分组测试结果{}", JSON.toJSONString(map));



        Map<Boolean,List<Student>> map1 = students1.stream().collect(Collectors.partitioningBy((s)->s.getAge()>30));
        log.info("收集：collect按照年龄为30分区测试结果{}", JSON.toJSONString(map1));


        IntSummaryStatistics intSummaryStatistics = students1.stream().collect(Collectors.summarizingInt(Student::getAge));
        log.info("收集：collect按照年龄平均值{}，总数{}，最大值{}，最小值{}，总和{}", intSummaryStatistics.getAverage(),intSummaryStatistics.getCount(),intSummaryStatistics.getMax(),intSummaryStatistics.getMin(),intSummaryStatistics.getSum());

        String s = students1.stream().map(Student::getName).collect(Collectors.joining("=="));
        log.info("收集：collect连接字符串测试结果{}", s);
    }









    public enum Status{
        FREE,
        BUSY,
        VOCATION
    }




    class Student{
        private String name;
        private Integer age;
        private Status status;

        public Student(String name,Integer age){
            this.name = name;
            this.age = age;
        }

        public Student(){

        }

        public Student(String name,Integer age, Status status){
            this.name = name;
            this.age = age;
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public Integer getAge() {
            return age;
        }

        public Status getStatus(){
            return status;
        }

        public String toString(){
            return JSON.toJSONString(this);
        }

        public int hashCode(){
            int result = 1;
            result = this.getAge().hashCode() + this.getName().hashCode();
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof Student){
                if(this.name.equals(((Student) obj).getName()) && this.age == ((Student) obj).getAge()){
                    return true;
                }
            }else {
                return super.equals(obj);
            }
            return false;
        }
    }




    /**
     * Optional的常用操作：
     * Optional.of(T value)    创建一个Optional实例
     * Optional.empty()     创建一个Optional空实例
     * Optional.ofNullable(T value)  不为null，创建一个Optional实例，否则创建一个Optional空实例
     * isPresent  判断是否包含值
     * orElse(T t)   如果调用的对象包含值，则返回值，否则返回t
     * orElseGet(Supplier s) 如果调用对象包含值，则返回值，否则返回s获取的值
     * map(Function f)  如果有值对其处理，并返回处理后的Optional，否则返回Optional.empty()
     * flatMap(Function f)   与mapleis，要求返回值必须是Optional
     */
    @Test
    public void test10(){
      /*  //Optional.of(T value)    创建一个Optional实例，不能创建null实例
        Optional<Student> optional = Optional.of(new Student("lcl",16));
        log.info("optional.get()======================{}",optional.get());
        //可以快速定位NullPointExecption位置
        Optional<Student> optional1 = Optional.of(null);
        log.info("optional1.get()======================{}",optional1.get());*/

        /*//Optional.empty()     创建一个Optional空实例
        Optional<Student> optional2 = Optional.empty();
        log.info("optional2.get()======================{}",optional2.get());*/

       /* //Optional.ofNullable(T value)  不为null，创建一个Optional实例，否则创建一个Optional空实例
        Optional<Student> optional2 = Optional.ofNullable(new Student("lcl",16));
        log.info("optional2.get()======================{}",optional2.get());
        Optional<Student> optional3 = Optional.ofNullable(null);
        log.info("optional3.get()======================{}",optional3.get());*/

        //isPresent  判断是否包含值

        /*Optional<Student> optional = Optional.of(new Student("lcl",16));
        Optional<Student> optional2 = Optional.ofNullable(new Student("lcl",16));
        log.info("optional.isPresent()======================{}",optional.isPresent());
        log.info("optional1.isPresent()======================{}",optional2.isPresent());*/

        //ofElse(T t)   如果调用的对象包含值，则返回值，否则返回t
        /*Optional<Student> optional = Optional.ofNullable(new Student("mm",25));
        Optional<Student> optional2 = Optional.ofNullable(null);

        Student student = optional.orElse(testNew());
        log.info("orElse1======================{}",student);
        student = optional2.orElse(testNew());
        log.info("orElse2======================{}",student);*/


        //ofElseGet(Supplier s) 如果调用对象包含值，则返回值，否则返回s获取的值

        Optional<Student> optional2 = Optional.empty();
        Student student1 = optional2.orElseGet(()->testNew());
        log.info("orElseGet1======================{}",student1);
        optional2 = Optional.of(new Student("kk",10));
        Student student2 = optional2.orElseGet(()->testNew());
        log.info("orElseGet2======================{}",student2);



/*        Optional<Student> optional2 = Optional.of(new Student("kk",10));
        Optional<String> name = optional2.map((x)->x.getName());
        log.info("map======================{}",name);*/

        /*Optional<Student> optional2 = Optional.of(new Student("kk",10));
        Optional<String> name1 = optional2.flatMap((x)->Optional.of(x.getName()));
        log.info("flatMap======================{}",name1.get());*/




    }


    public Student testNew(){
        log.info("调用了=======================");
        return new Student("lcl",18);
    }
}
