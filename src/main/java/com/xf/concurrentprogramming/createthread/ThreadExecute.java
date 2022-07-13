package com.xf.concurrentprogramming.createthread;

public class ThreadExecute {
    public static void main(String[] args) {
        new Thread(() -> {
            while (true){
                System.out.println(Thread.currentThread().getName() + "正在执行……");
            }
        }, "T1").start();

        new Thread(() -> {
            while (true){
                System.out.println(Thread.currentThread().getName() + "正在执行……");
            }
        }, "T2").start();
    }
}
