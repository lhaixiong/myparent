package com.lhx.gaohongyan;


public class InterruptDemo2_1 {
    public static void main(String[] args) {
        Thread.currentThread().interrupt();
        //这里既然当前线程(main线程)已经停止，为毛下面两个语句依然打印？真系乱蜡蜡
        System.out.println("main 是否停止?"+Thread.interrupted());
        System.out.println("main 是否停止?"+Thread.interrupted());
    }

}
