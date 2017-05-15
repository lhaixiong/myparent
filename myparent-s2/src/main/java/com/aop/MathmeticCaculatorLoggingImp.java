package com.aop;

import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class MathmeticCaculatorLoggingImp implements MathmeticCaculator {
    @Override
    public int add(int a, int b) {
        System.out.println("add begins with "+ Arrays.asList(a,b).toString());
        int result=a+b;
        System.out.println("add ends with "+ result);
        return result;
    }

    @Override
    public int sub(int a, int b) {
        System.out.println("sub begins with "+ Arrays.asList(a,b).toString());
        int result=a-b;
        System.out.println("sub ends with "+ result);
        return result;
    }

    @Override
    public int multi(int a, int b) {
        System.out.println("multi begins with "+ Arrays.asList(a,b).toString());
        int result=a*b;
        System.out.println("multi ends with "+ result);
        return result;
    }

    @Override
    public int div(int a, int b) {
        System.out.println("div begins with "+ Arrays.asList(a,b).toString());
        int result=a/b;
        System.out.println("div ends with "+ result);
        return result;
    }
}
