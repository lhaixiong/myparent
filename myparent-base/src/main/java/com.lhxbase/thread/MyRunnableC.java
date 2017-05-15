package com.lhxbase.thread;

public class MyRunnableC implements Runnable {
    private int ticket=10;
    private boolean synch=false;
    public MyRunnableC(boolean synch){
        this.synch=synch;
    }
    @Override
    public void run() {
        if(this.synch){
            this.sale();//同步方法
//            synchronized (this){//同步代码块
//                for (;ticket>0;){
//                    try {
//                        Thread.sleep(300);//加入延时操作
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println(Thread.currentThread().getName()+"(thread)线程正在运行，ticket="+ticket--);
//                }
//            }
        }
        if(!this.synch){
            for (;ticket>0;){
                try {
                    Thread.sleep(300);//加入延时操作
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"(thread)线程正在运行，ticket="+ticket--);
            }
        }
    }
    public synchronized void sale(){
        for (;ticket>0;){
            try {
                Thread.sleep(300);//加入延时操作
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"(thread)线程正在运行，ticket="+ticket--);
        }
    }
}
