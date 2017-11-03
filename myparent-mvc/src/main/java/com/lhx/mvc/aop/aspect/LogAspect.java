package com.lhx.mvc.aop.aspect;

import com.lhx.mvc.aop.annotation.*;
import com.lhx.mvc.aop.assist.JoinPoint;
import com.lhx.mvc.aop.assist.ProceedingJoinPoint;

import java.util.Arrays;

@Aspect
public class LogAspect {
    @Before(LogDot.class)
    public void before1(JoinPoint joinPoint) {
        System.out.println("before1! point: " + Arrays.asList(joinPoint.getParams()));
    }

    @Before(LogDot.class)
    public void before2(JoinPoint joinPoint) {
        System.out.println("before2! point: " + Arrays.asList(joinPoint.getParams()));
    }

    @After(LogDot.class)
    public void after1(JoinPoint joinPoint) {
        System.out.println("after! point: " + Arrays.asList(joinPoint.getParams()));
    }

    @After(LogDot.class)
    public void after2(JoinPoint joinPoint) {
        System.out.println("after2! point: " + Arrays.asList(joinPoint.getParams()));
    }


    @Around(LogDot.class)
    public Object around1(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around1! point: " + Arrays.asList(joinPoint.getParams()));
        Object result = joinPoint.proceed();
        System.out.println("around1 over! ans: " + result);
        return result;
    }

    @Around(LogDot.class)
    public Object around2(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around2! point: " + Arrays.asList(joinPoint.getParams()));
        Object result = joinPoint.proceed();
        System.out.println("around2 over! ans: " + result);
        return result;
    }
}
