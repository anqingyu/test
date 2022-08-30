package com.xf.jdk8.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多线程下SimpleDateFormat的线程安全问题：
 * SimpleDateFormat本身是线程不安全的，在多线程环境下，如果多个线程使用同一个类解析日期，最后的结果是无法预期的。同时继承了它的DateFormat类也不是线程安全的。
 *
 *   多线程问题的出现，是因为将SimpleDateFormat定义为static，所以在多线程下它的实例会被多线程共享，线程之间相互读取时间，所以才出现时间差异和其他的那些异常。
 *
 *   在Java8之前的解决方案，对于SimpleDateFormat不会通过static进行修饰，而是在使用时每次都新创建一个实例，但是这种方式会造成频繁的垃圾回收。
 *      或者使用Synchronized加锁，但是会造成线程阻塞。或者把它放入到ThreadLocal中。但是这些方式用起来都感觉有一些复杂。
 */
public class SimpleDateFormatDemo {
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String format(Date date) {
        return simpleDateFormat.format(date);
    }

    public static Date parse(String date) {
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        // 单线程下SimpleDateFormat测试
//        Date date = new Date();
//        String format = format(date);
//        Date parse = parse(format);
//        System.out.println(format);
//        System.out.println(parse);

        // 多线程下SimpleDateFormat的安全问题测试
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        Date date = new Date();
        for (int i = 0; i < 30; i++) {
            executorService.execute(() -> {
                for (int j = 0; j < 10; j++) {
                    String format = format(date);
                    Date parse = parse(format);

                    System.out.println(Thread.currentThread().getName() + ":" + format);
                    System.out.println(Thread.currentThread().getName() + ":" + parse);
                }
            });

        }
        executorService.shutdown();
    }
}
