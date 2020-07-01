package com.example.jdk8demo;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.*;

@SpringBootTest
@Slf4j
class Jdk8demoTest2 {

    @Test
    public void test() throws Exception{
        //全新的时间API   都不是线程安全的
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

        Callable<Date> call = new Callable<Date>() {
            @Override
            public Date call() throws Exception {
                return simpleDateFormat.parse("20200601");
            }
        };

        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(10);

        List<Future<Date>> list = new ArrayList<>();

        for(int i=0;i<10;i++){
            list.add(newFixedThreadPool.submit(call));
        }

        for (Future<Date> future : list){
            log.info("============={}",future.get());
        }

        newFixedThreadPool.shutdown();
    }


    @Test
    public void test1() throws Exception{
        //全新的时间API   都不是线程安全的

        Callable<Date> call = new Callable<Date>() {
            @Override
            public Date call() throws Exception {
                return DateFormatThreadLocal.convert("20200601");
            }
        };

        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(10);

        List<Future<Date>> list = new ArrayList<>();

        for(int i=0;i<10;i++){
            list.add(newFixedThreadPool.submit(call));
        }

        for (Future<Date> future : list){
            log.info("============={}",future.get());
        }

        newFixedThreadPool.shutdown();
    }



    @Test
    public void test2() throws Exception{
        //全新的时间API   都不是线程安全的
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        Callable<LocalDate> call = new Callable<LocalDate>() {
            @Override
            public LocalDate call() throws Exception {
                return LocalDate.parse("20200601",dateTimeFormatter);
            }
        };

        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(10);

        List<Future<LocalDate>> list = new ArrayList<>();

        for(int i=0;i<10;i++){
            list.add(newFixedThreadPool.submit(call));
        }

        for (Future<LocalDate> future : list){
            log.info("============={}",future.get());
        }

        newFixedThreadPool.shutdown();
    }


    /**
     * 每次都新创建一个对象
     * @throws Exception
     */
    @Test
    public void test3() throws Exception{
/*        //创建日期
        //LocalDate  LocalTime  LocalDateTime
        LocalDateTime ld = LocalDateTime.now();
        log.info("LocalDateTime.now()=================={}", ld);

        LocalDateTime ld1 = LocalDateTime.of(2020,5,31,13,25,36);
        log.info("LocalDateTime=================={}", ld1);*/

/*        //日期运算  plus 加日期
        LocalDateTime ld = LocalDateTime.now();
        log.info("plusDays=================={}", ld.plusDays(2));
        log.info("plusHours=================={}", ld.plusHours(3));*/
        //get 返回值
        /*LocalDateTime ld = LocalDateTime.now();
        log.info("getMonthValue=================={}", ld.getMonthValue());
        log.info("getDayOfMonth=================={}", ld.getDayOfMonth());*/

        //时间戳
/*
        Instant instant = Instant.now();//默认是UTC时间（世界协调时间）
        log.info("时间戳===={}", instant);
        //转换为东八区时间（北京时间）
        OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.ofHours(8));
        log.info("转换为东八区时间（北京时间）=={}", offsetDateTime);
        //转换为时间戳
        long toEpochMilli = instant.toEpochMilli();
        log.info("时间戳=={}", toEpochMilli);

        //改变时间   距离时间戳的时间  1970-01-01 00：00：00
        Instant instant1 = Instant.ofEpochSecond(60*23);
        log.info("距离时间戳的时间=={}", instant1);
*/

        //计算时间差
        //Duration：获取两个时间的时间差
        //Period：获取两个日期的间隔

        Instant instant2 = Instant.now();
        Thread.sleep(1000);
        Instant instant3 = Instant.now();
        Duration duration = Duration.between(instant2,instant3);

        log.info("duration.getSeconds()============={}", duration.getSeconds());
        log.info("duration.toMillis()============={}", duration.toMillis());
        log.info("duration.toHours()============={}", duration.toHours());

        LocalDate localDate = LocalDate.of(2018,10,6);
        LocalDate localDate1 = LocalDate.now();
        log.info("LocalDate.now()============={}", localDate1);
        Period period = Period.between(localDate,localDate1);
        log.info("period============={}", period);
        log.info("period.getYears()============={}", period.getYears());
        log.info("period.getMonths()============={}", period.getMonths());
        log.info("period.getDays()============={}", period.getDays());


    }


    /**
     * 时间矫正器
     */
    @Test
    public void test4(){
        //TemporalAdjuster：时间矫正器
        LocalDate localDate2 = LocalDate.now();
        log.info("LocalDate.now()============={}", localDate2);
        //指定时间
        LocalDate localDate3 = localDate2.withDayOfYear(55);
        log.info("withDayOfYear============={}", localDate3);
        //获取下一个周日
        LocalDate localDate4 = localDate2.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        log.info("TemporalAdjuster============={}", localDate4);
        //自定义 下一个工作日
        LocalDate localDate5 = localDate2.with((x)->{
            LocalDate localDate6 = (LocalDate) x;
            DayOfWeek dayOfWeek = localDate6.getDayOfWeek();
            switch (dayOfWeek){
                case FRIDAY:
                    return localDate6.plusDays(3);
                case TUESDAY:
                    return localDate6.plusDays(2);
                default:
                    return localDate6.plusDays(1);
            }
        });
        log.info("下一个工作日============={}", localDate5);
    }

    /**
     * 时间日期格式化
     *
     */
    @Test
    public void test6(){
        DateTimeFormatter df = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime ld = LocalDateTime.now();
        String date = ld.format(df);
        log.info("format==================={}",date);

        //自定义
        DateTimeFormatter df1 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分ss秒");
        date = ld.format(df1);
        log.info("format==================={}",date);
        //解析回原格式，此处要注意一点， HH时mm分ss秒大小写一定注意，错了就会反解析失败
        LocalDateTime localDateTime = ld.parse(date,df1);
        log.info("parse==================={}",localDateTime);
    }


    /**
     * 时区操作
     * ZoneDate   ZoneTime    ZoneDateTime
     */
    @Test
    public void test7(){
        //获取所有时区
        Set<String> set = ZoneId.getAvailableZoneIds();
        log.info("ZoneId.getAvailableZoneIds()================={}",JSON.toJSONString(set));
        //根据时区获取时间
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Pacific/Fiji"));
        LocalDateTime localDateTime1 = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        log.info("根据时区获取日期=========Pacific/Fiji==={}==========Asia/Shanghai==={}",localDateTime, localDateTime1);
        //获取时差
        ZonedDateTime zonedDateTime = localDateTime1.atZone(ZoneId.of("Pacific/Fiji"));
        log.info("Shanghai与Fiji的时差========{}",zonedDateTime);
    }


    /**
     * 重复注解&类型注解
     */
    public void test8(){

    }

}
