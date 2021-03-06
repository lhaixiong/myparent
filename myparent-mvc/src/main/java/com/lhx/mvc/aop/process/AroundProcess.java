package com.lhx.mvc.aop.process;

import com.lhx.mvc.aop.assist.ProceedingJoinPoint;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AroundProcess implements IAopProcess,Comparable<AroundProcess>{
    private Object aspect;

    private Method method;

    private ProceedingJoinPoint joinPoint;

    /**
     * 优先级
     */
    private int order;

    public AroundProcess() {
    }

    public AroundProcess(AroundProcess process) {
        aspect = process.getAspect();
        method = process.getMethod();
    }

    public Object process() throws InvocationTargetException, IllegalAccessException {
        return method.invoke(aspect, joinPoint);
    }

    public Object getAspect() {
        return aspect;
    }

    public void setAspect(Object aspect) {
        this.aspect = aspect;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public ProceedingJoinPoint getJoinPoint() {
        return joinPoint;
    }

    public void setJoinPoint(ProceedingJoinPoint joinPoint) {
        this.joinPoint = joinPoint;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int compareTo(AroundProcess o) {
        return this.order-o.getOrder();
    }
}
