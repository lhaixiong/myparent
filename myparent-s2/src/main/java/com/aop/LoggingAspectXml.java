package com.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Arrays;

public class LoggingAspectXml {
    public void before(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("before method " + methodName + " begins with " + Arrays.asList( joinPoint.getArgs()));
    }
    public void after(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("after method " + methodName + " after ");
    }
    public void afterReturning(JoinPoint joinPoint,Object result){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("afterReturning method " + methodName + " afterReturning "+result);
    }
    public void afterThrowing(JoinPoint joinPoint,Exception ex){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("afterThrowing method " + methodName + " afterThrowing  excepction "+ex);
    }
//    public Object around(ProceedingJoinPoint pjp){
//        Object result=null;
//        String methodName=pjp.getSignature().getName();
//        try {
//            System.out.println("before "+methodName);
//            result=pjp.proceed();
//            System.out.println("afterReturning "+methodName);
//        } catch (Throwable throwable) {
//            System.out.println("afterThrowing "+methodName);
//        }
//        System.out.println("after "+methodName);
//        return result;
//    }
}
