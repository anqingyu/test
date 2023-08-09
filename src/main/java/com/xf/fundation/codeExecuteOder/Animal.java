package com.xf.fundation.codeExecuteOder;

public class Animal {
    static {
        System.out.println("父类: 静态代码块");
    }
    private static int weight = 73;
    {
        System.out.println("父类: 非静态代码块");
    }
    private int age = 18;

    public void method(){
        System.out.println("父类的普通方法");
    }

    public Animal() {
        System.out.println("父类：执行父类的无参构造方法");
    }

    public Animal(int age) {
        System.out.println("父类：执行父类的构造方法");
        this.age = age;
    }
}
