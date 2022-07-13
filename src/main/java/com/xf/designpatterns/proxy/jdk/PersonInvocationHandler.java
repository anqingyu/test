package com.xf.designpatterns.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 代理对象
 */
public class PersonInvocationHandler implements InvocationHandler {
    private Object target;

    public PersonInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("start");
        Object result = method.invoke(target, args);
        System.out.println("end");

        return result;
    }
}
