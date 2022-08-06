package com.xf.designpatterns.singleton;

/**
 * 饿汉式单例
 *
 * 优点，没有synchronized,效率高，不能延迟加载
 * 缺点，如果类中有 private byte[] bytes1=new byte[1024];
 *      这样占用内存资源的对象，又不会用，会占用内存资源，造成浪费。
 *适用于，类中没有开辟空间内存的对象。如：spring
 */
public class SingletonDemo1 {
    //私有化构造器
    private SingletonDemo1(){}
    //类初始化时候，立即加载该对象
    private static SingletonDemo1 instance=new SingletonDemo1();

    //提供获取该对象的方法，没有synchronized,效率高
    public static  SingletonDemo1 getInstance(){
        return instance;
    }
}

class SingletonDemo1Test{
    public static void main(String[] args) {
        SingletonDemo1 instance1 = SingletonDemo1.getInstance();
        SingletonDemo1 instance2 = SingletonDemo1.getInstance();
        System.out.println(instance1 == instance2);
        System.out.println(instance1.hashCode());
        System.out.println(instance2.hashCode());
    }
}