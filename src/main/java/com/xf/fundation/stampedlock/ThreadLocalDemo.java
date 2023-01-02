package com.xf.fundation.stampedlock;

import java.util.concurrent.TimeUnit;

/**
 *  ThreadLocal是Thread的局部变量，同一个 ThreadLocal 所包含的对象，在不同的 Thread 中有不同的实例副本，且该副本只能由当前 Thread 使用，
 *      对其他线程而言是隔离的，故不存在多线程间共享的问题。
 *
 *  ThreadLocal 变量通常被private static修饰。当一个线程结束时，它所使用的所有 ThreadLocal 相对的实例副本都可被回收。
 *
 *  一句话理解ThreadLocal，ThreadLocal是作为当前线程中属性ThreadLocalMap集合中的某一个Entry的key值，Entry(ThreadLocal<?> k, Object v)
 */
public class ThreadLocalDemo {
    private static ThreadLocal<String> localVar = new ThreadLocal<>();

    static void print(String str) {
        //打印当前线程中本地内存中本地变量的值
        System.out.println(str + " :" + localVar.get());
        //清除本地内存中的本地变量
        localVar.remove();
    }

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            ThreadLocalDemo.localVar.set("A");
            print("Thread-1");
            //打印本地变量
            System.out.println("after remove : " + localVar.get());
        },"Thread-1").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            ThreadLocalDemo.localVar.set("B");
            print("Thread-2");
            System.out.println("after remove : " + localVar.get());
        }, "Thread-2").start();
    }
}
