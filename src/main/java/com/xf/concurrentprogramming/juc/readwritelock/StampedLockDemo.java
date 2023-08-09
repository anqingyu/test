package com.xf.concurrentprogramming.juc.readwritelock;

import org.junit.jupiter.api.Test;

/**
 * StampedLock:该类自 JDK 8 加入，是为了进一步优化读性能，它的特点是在使用读锁、写锁时都必须配合【戳】使用
 */
public class StampedLockDemo {

    /**
     * 为啥要加static？即内部类与静态内部类有什么区别：
     *      ①静态内部类相对与外部类是独立存在的，在静态内部类中无法直接访问外部类中的变量、方法。如果要访问的话，必须要new一个外部类的对象，
     *          使用new出来的对象来访问。 但是可以直接访问静态变量，调用静态的方法。
     *      ②普通内部类作为外部类一个成员而存在，在普通内部类中可以直接访问外部类属性，调用外部类的方法。
     *      ③如果外部类要访问内部类的属性或者调用内部类的方法，必须要创建一个内部类的对象，使用该对象访问属性或者调用方法。
     *      ④如果其他的类要访问普通内部类的属性或者调用普通内部类的方法，必须要在外部类中创建一个内部类的对象作为一个属性，外部类可以通过属性
     *          调用普通内部类的方法或者访问普通内部类的属性。
     *      ⑤如果其他的类要访问静态内部类的属性或者调用静态内部类的方法，直接创建一个静态内部类对象即可。
     */
    static class DataContainerStamped {
        int i;
    }

    @Test
    public void test(){
        DataContainerStamped dataContainerStamped = new DataContainerStamped();
    }
}
