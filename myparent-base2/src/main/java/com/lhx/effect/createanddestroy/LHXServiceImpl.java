package com.lhx.effect.createanddestroy;

public class LHXServiceImpl implements Service {
    @Override
    public void method1() {
        System.out.println(this.getClass().getName()+">>>>>method1");
    }

    @Override
    public void method2() {
        System.out.println(this.getClass().getName()+">>>>>method2");
    }
}
