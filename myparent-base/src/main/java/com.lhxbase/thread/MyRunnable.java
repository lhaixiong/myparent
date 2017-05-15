package com.lhxbase.thread;

public class MyRunnable implements Runnable {
    @Override
    public void run() {
        for (int i=1;i<11;i++){
            System.out.println(Thread.currentThread().getName()+"(runnable)线程正在运行，i="+i);
        }
    }
}
