package com.xf.designpatterns.proxy.cglib;

/**
 * 创建目标类
 */
public class User {

    public String say(String msg){
        System.out.println("早上好"+msg);
        return  msg;
    }

}
