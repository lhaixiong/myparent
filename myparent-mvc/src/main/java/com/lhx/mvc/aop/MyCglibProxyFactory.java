package com.lhx.mvc.aop;

import net.sf.cglib.proxy.Enhancer;

public class MyCglibProxyFactory {
    private static Enhancer enhancer=new Enhancer();
    private static MyCglibProxy cglibProxy=new MyCglibProxy();

    public static <T> T getProxy(Class<T> clz){
        enhancer.setSuperclass(clz);
        enhancer.setCallback(cglibProxy);
        return (T) enhancer.create();
    }
}
