package com.aop;

import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

@Component
public class MathmeticCaculatorLoggingProxy {
    MathmeticCaculator target;

    public void setTarget(MathmeticCaculator target) {
        this.target = target;
    }

    public MathmeticCaculatorLoggingProxy(MathmeticCaculator target) {
        this.target = target;
    }
    public  MathmeticCaculatorLoggingProxy(){

    }

    public MathmeticCaculator getProxy(){
        ClassLoader classLoader=target.getClass().getClassLoader();
        Class[] interfaces=target.getClass().getInterfaces();
        InvocationHandler handler=new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("method "+method.getName()+" is invoked with args "+ Arrays.asList(args));
                Object result = method.invoke(target, args);
                System.out.println("method "+method.getName()+" is invoked with result "+ result);
                return result;
            }
        };
        MathmeticCaculator mathmeticCaculator= (MathmeticCaculator) Proxy.newProxyInstance(classLoader, interfaces, handler);
        return mathmeticCaculator;
    }
}
