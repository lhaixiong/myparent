package com.lhx.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProductorAndConsumerLock {
    public static void main(String[] args) {
        Clerk clerk=new Clerk();
        Productor pro=new Productor(clerk);
        Consumer con=new Consumer(clerk);
        new Thread(pro,"生产者A").start();
        new Thread(pro,"生产者b").start();
        new Thread(con,"消费者A").start();
        new Thread(con,"消费者b").start();
    }
    private static class Clerk{
        private int pro=0;
        private Lock lock=new ReentrantLock();
        private Condition condition=lock.newCondition();

        /**
         * 进货
         */
        public void get(){
            lock.lock();
            try {
                //            if(pro>=10){//虚假唤醒
                while(pro>=10){//虚假唤醒解决 ,切记1
                    System.out.println("进货已满");
                    try {
//                        this.wait();
                       condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //!!!!!!!!!!!!!!!!!!!!
                //切记2，生产者消费者这里不能用else，否则最后一次一直在wait，没有其他线程notice，导致该线程一直运行
//            else {
//                System.out.println(Thread.currentThread().getName()+"进货："+(++pro));
//                this.notifyAll();
//            }
                System.out.println(Thread.currentThread().getName()+"进货："+(++pro));
//                this.notifyAll();
                condition.signalAll();
            }finally {
                lock.unlock();
            }
        }

        /**
         * 售货
         */
        public void sale(){
            lock.lock();
            try {
                //            if(pro<=0){
                while(pro<=0){//虚假唤醒解决 ,切记1
                    System.out.println("缺货");
                    try {
//                        this.wait();
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //!!!!!!!!!!!!!!!!!!!!
                //切记2，生产者消费者这里不能用else，否则最后一次一直在wait，没有其他线程notice，导致该线程一直运行
//            else {
//                System.out.println(Thread.currentThread().getName()+"售货:"+(--pro));
//                this.notifyAll();
//            }
                System.out.println(Thread.currentThread().getName()+"售货:"+(--pro));
//                this.notifyAll();
                condition.signalAll();
            }finally {
                lock.unlock();
            }
        }
    }
    private static class Productor implements Runnable{
        private Clerk clerk;

        public Productor(Clerk clerk) {
            this.clerk = clerk;
        }

        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                clerk.get();
            }
        }
    }
    private static class Consumer implements Runnable{
        private Clerk clerk;

        public Consumer(Clerk clerk) {
            this.clerk = clerk;
        }

        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                clerk.sale();
            }
        }
    }
}
