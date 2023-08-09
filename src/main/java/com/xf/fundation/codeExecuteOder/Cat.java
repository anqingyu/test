package com.xf.fundation.codeExecuteOder;

/**
 * 测试子父类代码的执行顺序：
 *      ①按顺序执行父类的静态代码（静态变量初始化、赋值，静态代码执行）---》②按顺序执行子类的静态代码（静态变量初始化、赋值，静态代码执行）
 *      ---》③父类非静态变量、代码块 ---》④父类构造函数 ---》⑤子类非静态变量、代码块 ---》⑥子类构造函数
 */
public class Cat extends Animal {
    private static int weight;
    static {
        System.out.println("子类: 静态代码块");
    }{
        num = 2;
    }
    private Integer num = 5;
    private int age = 18;
    private int age2;
    private double age3;

    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
    {
        System.out.println("子类: 非静态代码块");
    }

    public void method(){
        System.out.println("子类的普通方法");
    }

    public Cat(int age) {
        System.out.println(this.age);
        System.out.println("子类：执行子类的构造方法");
        this.age = age;
    }

    public static void main(String[] args) {
        Cat cat = new Cat(15);
        cat.method();

        // 成员变量有默认初始值
        System.out.println(cat.age2 == 0);
        System.out.println(cat.age3);

        System.out.println(DEFAULT_INITIAL_CAPACITY);
        int h;
        String key = new String("sfsdfsf");
        int h1 = key.hashCode();
        System.out.println(Integer.toBinaryString(h1));
        h = h1;
        h = h ^ (h >>> 16);
        System.out.println(Integer.toBinaryString(h1));

        int[] a = new int[5];
        System.out.println(a.length);
    }

    // 跳出多重循环
    /*public static void main(String[] args) {
        loop:
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.println("i=" + i + ",j=" + j);
                if (j == 5) {
                    break loop;
                }

            }
        }
    }*/
}





