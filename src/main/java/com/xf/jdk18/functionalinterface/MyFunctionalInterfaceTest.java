package com.xf.jdk18.functionalinterface;

import org.junit.jupiter.api.Test;

/**
 * 自定义函数式接口测试
 */
public class MyFunctionalInterfaceTest {
    public void demo(MyFunctionalInterface functionalInterface){
        functionalInterface.execute();
    }

    @Test
    public void test(){
        //jdk8之前
        demo(new MyFunctionalInterface() {
            @Override
            public void execute() {
                System.out.println("jdk8 before");
            }
        });

        // 使用lambda表达式
        demo(() -> System.out.println("jdk8 latter"));
    }
}
