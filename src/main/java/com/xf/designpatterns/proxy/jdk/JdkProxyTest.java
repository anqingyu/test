package com.xf.designpatterns.proxy.jdk;

import java.lang.reflect.Proxy;

public class JdkProxyTest {
    public static void main(String[] args) {
        IPerson target = new ManPerson();
        IPerson proxy = (IPerson) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new PersonInvocationHandler(target));
        proxy.eat();
        proxy.sleep();
    }
}
