package com.xf.designpatterns.proxy.jdk;

/**
 * 目标对象
 */
public class ManPerson implements IPerson{
    @Override
    public void eat() {
        System.out.println("吃饭中......");
    }

    @Override
    public void sleep() {
        System.out.println("睡觉中......");
    }
}
