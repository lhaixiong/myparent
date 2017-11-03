package com.lhx.mvc.test;

import com.lhx.mvc.aop.annotation.LogDot;
import com.lhx.mvc.ioc.annotation.type.Service;

@Service
public class AspectDemoService {
    @LogDot
    public void print(String ans) {
        System.out.println(ans);
    }

    @LogDot
    public int add(int a, int b) {
        System.out.println("add");
        return a + b;
    }
}
