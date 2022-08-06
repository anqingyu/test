package com.xf.designpatterns.singleton;

/**
 * 单例模式之 双检锁/双重校验锁（DCL，即 double-checked locking）
 *
 * 优点，相较于懒汉式单例效率更高了
 *      volatile,关键字的作用：保证了变量的可见性（visibility）。
 *      被volatile关键字修饰的变量，如果值发生了变更，其他线程立马可见，避免出现脏读的现象
 *
 * 缺点，当一个线程还没走完同步锁，另一个线程进来会直接返回return instance;这是出现错误
 */
public class Singleton_Lazy_DOL {
    // 1:提供一个私有的空参构造器
    private Singleton_Lazy_DOL(){};
    // 2:指向当前类实例的私有静态引用,使用volatile关键字保证变量的可见性
    private static volatile Singleton_Lazy_DOL singleton_Lazy;

    // 3:提供一个公共的静态方法，当需要时才创建当前类对象
    public static Singleton_Lazy_DOL getInstance(){
        //进行双重检查，只需在第一次创建时才同步，创建成功后就不再需要获取同步锁
        if(singleton_Lazy==null){
            synchronized (Singleton_Lazy_DOL.class) {
                if(singleton_Lazy==null){
                    singleton_Lazy=new Singleton_Lazy_DOL();
                }
            }
        }
        return singleton_Lazy;
    }
}

/**
 * 测试 单例模式之双检锁/双重校验锁
 */
class SingletonTest{
    public static void main(String[] args) {
        Singleton_Lazy_DOL instance1 = Singleton_Lazy_DOL.getInstance();
        Singleton_Lazy_DOL instance2 = Singleton_Lazy_DOL.getInstance();
        System.out.println(instance1==instance2);
        System.out.println(instance1.hashCode());
        System.out.println(instance2.hashCode());
    }
}
