package com.xf.jdk8.defaultmethod;

public class MyDSServiceImpl implements MyDSService{
    /**
     * 重写普通抽象方法
     * @return
     */
    @Override
    public String abstractMethod() {
        return null;
    }

    /**
     * 默认方法选择性重写
     * @return
     */
    @Override
    public String defaultMethod() {
        return MyDSService.super.defaultMethod();
    }
}
