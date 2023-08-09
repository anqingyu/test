package com.xf.concurrentprogramming.createthread;

import java.util.concurrent.TimeUnit;

/**
 * 线程创建方法一（使用Thread）：线程和任务合并在了一起
 */
public class ThreadTest {
    public static void main(String[] args) {
        // 使用匿名内部类方式创建线程
        Thread thread = new Thread("T1") {
            @Override
            public void run() {
                System.out.println("Thread类创建线程测试……");
                System.out.println(Thread.currentThread().getName() + " running...");
            }
        };
//        TimeUnit
        thread.start();
        System.out.println("线程ID：" + thread.getId() + ", 线程名称：" + thread.getName() + ", 线程优先级：" + thread.getPriority()
            + ", 线程状态：" + thread.getState() + ", 是否存活:" + thread.isAlive());
    }
}
