package com.xf.jdk18.defaultmethod;

/**
 * 测试Java8默认方法与静态方法
 */
public class MyDSDemo {
    public static void main(String[] args){
        MyDSService myDSService = new MyDSServiceImpl();
        // 1.调用默认方法
        String defaultMethodResult = myDSService.defaultMethod();
        System.out.println(defaultMethodResult);

        // 2.调用静态方法
        System.out.println(MyDSService.staticMethod());
    }
}
