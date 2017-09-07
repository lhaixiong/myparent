package com.lhx.juc;

import com.sun.org.apache.bcel.internal.generic.DMUL;

/**
 * 一、volatile 关键字：当多个线程进行操作共享数据时，可以保证内存中的数据可见。
 * 					  相较于 synchronized 是一种较为轻量级的同步策略。
 *
 * 注意：
 * 1. volatile 不具备“互斥性”
 * 2. volatile 不能保证变量的“原子性”
 */
public class TestVolatile {
    public static void main(String[] args) throws InterruptedException {
        ThreadDemo td=new ThreadDemo();
        Thread demo=new Thread(td);
        demo.start();
        while (true){
            if (td.isFlag()) {
                //若果这里主线程不睡眠100ms，则会先打印主线程消息再打印demo线程消息
                //因为while的执行效率极高，检测到td.isFlag()==true后立刻执行打印了，比demo打印先
                //Thread.sleep(100);
                System.out.println("main -----flag="+td.isFlag());
                break;
            }
//            synchronized (td){
//                if (td.isFlag()) {
//                    System.out.println("main -----flag="+td.isFlag());
//                    break;
//                }
//            }

        }
    }
    static class ThreadDemo implements Runnable{
        private volatile boolean flag=false;
        //若果这里没有用volatile关键字，main线程一直运行，不结束
//        private  boolean flag=false;
        @Override
        public void run() {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            flag=true;
            System.out.println(getClass().getName()+" flag="+flag);
        }

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }
    }
}

