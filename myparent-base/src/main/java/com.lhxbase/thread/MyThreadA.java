package com.lhxbase.thread;

public class MyThreadA extends Thread {
    private int ticket=10;
    @Override
    public void run() {
        for (;ticket>0;){
            System.out.println(Thread.currentThread().getName()+"(thread)线程正在运行，ticket="+ticket--);
        }
    }
}
