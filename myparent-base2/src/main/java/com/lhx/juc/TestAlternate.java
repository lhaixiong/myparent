package com.lhx.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 编写一个程序，开启 3 个线程，这三个线程的 ID 分别为 A、B、C，每个线程将自己的 ID 在屏幕上打印 10 遍，要求输出的结果必须按顺序显示。
 *	如：ABCABCABC…… 依次递归
 */
public class TestAlternate {
    public static void main(String[] args) {
        AlternateDemo demo=new AlternateDemo();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <=10; i++) {
                    demo.aWork(i);
                }
            }
        },"A").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <=10; i++) {
                    demo.bWork(i);
                }
            }
        },"B").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <=10; i++) {
                    demo.cWork(i);
                }
            }
        },"C").start();
    }
    private static class AlternateDemo{
        private int flag=1;
        private Lock lock=new ReentrantLock();
        private Condition conditionA=lock.newCondition();
        private Condition conditionB=lock.newCondition();
        private Condition conditionC=lock.newCondition();
        public void aWork(int time){
            lock.lock();
            try {
                if(flag!=1){
                  conditionA.await();
                }
                for (int i = 1; i <= 5; i++) {
                    System.out.println(Thread.currentThread().getName()+" "+time+" "+i);
                }
                flag=2;
                conditionB.signal();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
        public void bWork(int time){
            lock.lock();
            try {
                if(flag!=2){
                    conditionB.await();
                }
                for (int i = 1; i <= 10; i++) {
                    System.out.println(Thread.currentThread().getName()+" "+time+" "+i);
                }
                flag=3;
                conditionC.signal();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
        public void cWork(int time){
            lock.lock();
            try {
                if(flag!=3){
                    conditionC.await();
                }
                for (int i = 1; i <= 15; i++) {
                    System.out.println(Thread.currentThread().getName()+" "+time+" "+i);
                }
                System.out.println(String.format("结束第%s轮循环--------------",time));
                flag=2;
                conditionA.signal();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }
}
