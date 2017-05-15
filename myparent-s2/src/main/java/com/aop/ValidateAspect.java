package com.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(1)
public class ValidateAspect {
    @Before("com.aop.LoggingAspect.declareJointPointExpression()")
    public void before(){
        System.out.println(">>>>ValidateAspect before...");
    }
}
