package com.lhx.juc;

public class ProductorAndConsumer {
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

        /**
         * 进货
         */
        public synchronized void get(){
//            if(pro>=10){//虚假唤醒
            while(pro>=10){//虚假唤醒解决 ,切记1
                System.out.println("进货已满");
                try {
                    this.wait();
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
            this.notifyAll();
        }

        /**
         * 售货
         */
        public synchronized void sale(){
            //错误的原因在于同类唤醒，当一个消费者线程遇到产品为0时，等待，并释放锁标志，
            // 然后另外一个消费者线程获取到该锁标志，由于产品仍然为0，也等待，并释放锁标志。
            // 这时候，生产者线程获取到锁，在生产一个产品后，执行notifyAll()唤醒所有线程，
            // 这时候，一个消费者线程消费一个产品使得产品为0，执行notifyAll()唤醒所有线程
            // 同类唤醒，另外一个消费者线程再消费一个产品使得产品变为了负数，这种现象称为虚假唤醒。
            //if(pro<=0){
            while(pro<=0){//虚假唤醒解决 ,切记1
                System.out.println("缺货");
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //!!!!!!!!!!!!!!!!!!!!
            //切记2，生产者消费者这里不能用else，否则最后一次一直在wait，没有其他线程notice，导致该线程一直运行
            //这是因为，当循坏到最后一轮时，由于产品已满引发了wait()操作，
            // 然后生产者线程等待，随后消费者消费了一份产品，并唤醒等待的生产者线程，
            // 此时，被唤醒的生产者线程由于循环结束，直接结束了线程的执行，
            // 但是另一边，消费者线程没有结束，而且由于将产品消费完后再次进入了等待，
            // 但是生产者线程此时已经结束了，不能再唤醒消费者线程，所以便进入了死循环。
//            else {
//                System.out.println(Thread.currentThread().getName()+"售货:"+(--pro));
//                this.notifyAll();
//            }
            System.out.println(Thread.currentThread().getName()+"售货:"+(--pro));
            this.notifyAll();
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
