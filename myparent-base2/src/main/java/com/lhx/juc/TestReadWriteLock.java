package com.lhx.juc;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 1. ReadWriteLock : 读写锁
 *
 * 写写/读写 需要“互斥”
 * 读读 不需要互斥
 *
 */
public class TestReadWriteLock {
    public static void main(String[] args) {
        RWDemo demo=new RWDemo();
        new Thread(new Runnable() {
            @Override
            public void run() {
                demo.set((int) (Math.random()*100));
            }
        },"写线程").start();
        for (int i = 0; i < 50; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    demo.get();
                }
            }).start();
        }
    }
    private static class RWDemo{
        private int number;
        private ReadWriteLock lock=new ReentrantReadWriteLock();
        public void get(){
            lock.readLock().lock();
            try {
                System.out.println(number);
            }finally {
                lock.readLock().unlock();
            }
        }
        public void set(int number){
            lock.writeLock().lock();
            try {
                this.number=number;
                System.out.println(number);
            }finally {
                lock.writeLock().unlock();
            }
        }
    }
}
