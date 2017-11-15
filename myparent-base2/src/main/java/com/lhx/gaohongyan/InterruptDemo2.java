package com.lhx.gaohongyan;


public class InterruptDemo2 {
    public static void main(String[] args) {
        try {
            MyThread myThread=new MyThread();
            myThread.start();
            Thread.sleep(1000);
            myThread.interrupt();//myThread线程没有停止，依然打印
            //注意：isInterrupted()是对象方法
            System.out.println("myThread1 isInterrupted 是否停止?"+myThread.isInterrupted());
            System.out.println("myThread1 isInterrupted 是否停止?"+myThread.isInterrupted());

            ////interrupted()是静态方法
            //System.out.println("myThread2 interrupted 是否停止?"+myThread.interrupted());
            //System.out.println("myThread2 interrupted 是否停止?"+myThread.interrupted());
            //
            //System.out.println("Thread3 interrupted 是否停止?"+Thread.interrupted());
            //System.out.println("Thread3 interrupted 是否停止?"+Thread.interrupted());


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
