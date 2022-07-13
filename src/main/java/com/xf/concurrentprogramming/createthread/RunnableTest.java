package com.xf.concurrentprogramming.createthread;

/**
 * 线程创建方法二（使用Runnable）：是把线程和任务分开了，用 Runnable 更容易与线程池等高级 API 配合，用 Runnable 让任务类脱离了 Thread 继承体系，更灵活
 */
public class RunnableTest {

    public static void main(String[] args) {
        // 使用 lambda 表达式，因为 Runnable 接口标注了 @FunctionalInterface 这个注解，表示是一个函数式接口，可以使用 lambda 表达式
        Runnable r = () -> {
            System.out.println("Runnable 之 Lambda 测试……");
        };

        new Thread(r, "T1").start();
    }
}
