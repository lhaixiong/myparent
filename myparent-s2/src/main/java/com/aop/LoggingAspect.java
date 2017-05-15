package com.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Order(2)
public class LoggingAspect {
    @Pointcut("execution(* com.aop.MathmeticCaculatorImp.*(..))")
    public void declareJointPointExpression(){}

    @Before("declareJointPointExpression()")
    public void before(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("before method " + methodName + " begins with " + Arrays.asList( joinPoint.getArgs()));
    }
    @After("declareJointPointExpression()")
    public void after(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("after method " + methodName + " after ");
    }
    @AfterReturning(value = "declareJointPointExpression()",returning = "result")
    public void afterReturning(JoinPoint joinPoint,Object result){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("afterReturning method " + methodName + " afterReturning "+result);
    }
    @AfterThrowing(value = "declareJointPointExpression()",throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint,Exception ex){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("afterThrowing method " + methodName + " afterThrowing  excepction "+ex);
    }
//    @Around(value = "declareJointPointExpression()")
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
