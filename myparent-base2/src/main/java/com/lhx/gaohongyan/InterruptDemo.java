package com.lhx.gaohongyan;


public class InterruptDemo {
    public static void main(String[] args) {
        try {
            MyThread myThread=new MyThread();
            myThread.start();
            Thread.sleep(1000);
            myThread.interrupt();//这里方法停止不了myThred线程，依然打印
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    static class MyThread extends Thread{
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("i="+i);
            }
        }
    }
}
