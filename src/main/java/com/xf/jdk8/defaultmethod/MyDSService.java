package com.xf.jdk8.defaultmethod;

/**
 *  Java8之默认方法与静态方法：
 *    在Java8之前 ，接口中定义的方法都是抽象方法，被public abstract修饰，不能有方法实现
 *    Java8开始允许在接口中定义默认方法和静态方法，对于这两种方法，可以直接在接口对其进行实现
 *
 *    普通抽象方法必须实现，默认方法可以选择性重写，静态方法无法重写。
 */
public interface MyDSService {
    /**
     * 抽象方法
     */
    String abstractMethod();

    /**
     * 默认方法
     *   调用：接口实现类对象.默认方法()
     */
    default String defaultMethod(){
        return "execute default method";
    };

    /**
     * 静态方法:
     *   调用：接口.静态方法()
     */
    static String staticMethod(){
        return "execute static method";
    }

}
