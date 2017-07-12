package com.lhx.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 一、用于解决多线程安全问题的方式：
 *
 * synchronized:隐式锁
 * 1. 同步代码块
 *
 * 2. 同步方法
 *
 * jdk 1.5 后：
 * 3. 同步锁 Lock
 * 注意：是一个显示锁，需要通过 lock() 方法上锁，必须通过 unlock() 方法进行释放锁
 */
public class LockDemo {
    public static void main(String[] args) {
        Ticket ticket=new Ticket();
        new Thread(ticket).start();
        new Thread(ticket).start();
        new Thread(ticket).start();
    }
    private static class Ticket implements Runnable{
        private int ticket=50;
        private Lock lock=new ReentrantLock();
        @Override
        public void run() {
            while (true){
                lock.lock();
                try {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(ticket>0){
                        System.out.println(ticket--);
                    }else {
                        break;
                    }
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
