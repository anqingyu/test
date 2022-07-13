package com.xf.designpatterns.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;

public class CglibProxyTest {
    public static void main(String[] args) {
        // 通过CGLIB动态代理获取代理对象的过程
        Enhancer enhancer = new Enhancer();
        // 设置enhancer对象的父类
        enhancer.setSuperclass(User.class);
        // 设置enhancer的回调对象
        enhancer.setCallback(new UserMethodInterceptor());
        // 创建代理对象
        User user = (User) enhancer.create();
        // 通过代理对象调用目标方法
        String world = user.say("world");

        System.out.println(world);
    }
}
