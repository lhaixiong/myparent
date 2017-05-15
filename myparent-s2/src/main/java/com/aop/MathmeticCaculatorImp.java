package com.aop;

import org.springframework.stereotype.Component;


@Component
public class MathmeticCaculatorImp implements MathmeticCaculator {
    @Override
    public int add(int a, int b) {
        int result=a+b;
        return result;
    }

    @Override
    public int sub(int a, int b) {
        int result=a-b;
        return result;
    }

    @Override
    public int multi(int a, int b) {
        int result=a*b;
        return result;
    }

    @Override
    public int div(int a, int b) {
        int result=a/b;
        return result;
    }
}
