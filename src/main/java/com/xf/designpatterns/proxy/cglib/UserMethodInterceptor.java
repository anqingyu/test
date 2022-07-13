package com.xf.designpatterns.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 创建cglib代理类：代理类需要实现MethodInterceptor接口并重写intercept方法
 *
 * cglib动态代理的实现原理：CGLib采用底层的字节码技术ASM, 可以为一个类创建子类, 在子类中采用方法拦截的技术拦截所有父类方法的调用, 并织入横切逻辑。
 */
public class UserMethodInterceptor implements MethodInterceptor {
    /**
     *
     * @param o cglib生成的代理对象
     * @param method 被代理对象的方法
     * @param objects     传入方法的参数
     * @param methodProxy 代理的方法
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("这里可以插入执行关键代码之前的逻辑");
        Object o1 = methodProxy.invokeSuper(o, objects);//关键代码:
        System.out.println("这里可以插入执行关键代码之后的逻辑");
        return o1;
    }
}
