package com.lhxbase.thread;

public class MyThread extends Thread {

    @Override
    public void run() {
        for (int i=1;i<11;i++){
            System.out.println(Thread.currentThread().getName()+"(thread)线程正在运行，i="+i);
        }
    }
}
