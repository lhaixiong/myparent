package com.lhx.mvc.aop.process;

import com.lhx.mvc.aop.assist.JoinPoint;

import java.lang.reflect.Method;

/**
 * before处理对象
 * 含切面对象aspect，切面方法method，对应的切点信息JoinPoint
 */
public class BeforeProcess implements IAopProcess,Comparable<BeforeProcess>{
    /**
     * 切面类
     */
    private Object aspect;
    /**
     * 切面类对应的before通知方法
     */
    private Method method;
    /**
     * 被切面拦截的切点信息
     */
    private JoinPoint joinPoint;

    /**
     * 优先级
     */
    private int order;

    public BeforeProcess(){

    }
    public BeforeProcess(BeforeProcess bp){
        this.aspect=bp.getAspect();
        this.method=bp.getMethod();
        this.joinPoint=null;
    }
    /**
     * 执行切面方法
     * @throws Throwable
     */
    public void process() throws Throwable{
        method.invoke(aspect,joinPoint);
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

    public void setMethod(Method Method) {
        this.method = Method;
    }

    public JoinPoint getJoinPoint() {
        return joinPoint;
    }

    public void setJoinPoint(JoinPoint joinPoint) {
        this.joinPoint = joinPoint;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }


    @Override
    public int compareTo(BeforeProcess o) {
        return this.order-o.getOrder();
    }

}
