package com.lhx.mvc.aop.process;

import com.lhx.mvc.aop.assist.JoinPoint;

import java.lang.reflect.Method;

/**
 * After处理对象
 * 含切面对象aspect，切面方法method，对应的切点信息JoinPoint
 * 相交于BeforeProcess，应该多一个返回结果的参数
 */
public class AfterProcess implements IAopProcess{
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
    private Object result;

    public AfterProcess(){

    }
    public AfterProcess(AfterProcess bp){
        this.aspect=bp.getAspect();
        this.method=bp.getMethod();
    }
    /**
     * 执行切面方法
     * @throws Throwable
     */
    public void process() throws Throwable{
        if(method.getParameters().length>1){
            method.invoke(aspect,joinPoint,result);
        }else {
            method.invoke(aspect,joinPoint);
        }
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

    public JoinPoint getJoinPoint() {
        return joinPoint;
    }

    public void setJoinPoint(JoinPoint joinPoint) {
        this.joinPoint = joinPoint;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
