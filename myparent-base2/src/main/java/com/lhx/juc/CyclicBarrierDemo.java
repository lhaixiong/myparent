package com.lhx.juc;

import java.util.concurrent.CyclicBarrier;

/**
 * Java并发编程：CountDownLatch、CyclicBarrier和Semaphore
 * https://www.cnblogs.com/dolphin0520/p/3920397.html
 * 下面对上面说的三个辅助类进行一个总结：

 　　1）CountDownLatch和CyclicBarrier都能够实现线程之间的等待，只不过它们侧重点不同：

 　　　　CountDownLatch一般用于某个线程A等待若干个其他线程执行完任务之后，它才执行；

 　　　　而CyclicBarrier一般用于一组线程互相等待至某个状态，然后这一组线程再同时执行；

 　　　　另外，CountDownLatch是不能够重用的，而CyclicBarrier是可以重用的。

 　　2）Semaphore其实和锁有点类似，它一般用于控制对某组资源的访问权限。
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        System.out.println("main开始");
        int num=4;
        //如果说想在所有线程写入操作完之后，进行额外的其他操作可以为CyclicBarrier提供Runnable参数
        //当四个线程都到达barrier状态后，会从四个线程中选择一个线程去执行Runnable。
        CyclicBarrier barrier=new CyclicBarrier(4, new Runnable() {
            @Override
            public void run() {
                System.out.println("进行额外的其他操作....."+Thread.currentThread().getName());
            }
        });
        for (int i = 0; i < num; i++) {
            new Write(barrier).start();
        }


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("CyclicBarrier重用");

        for(int i=0;i<num;i++) {
            new Write(barrier).start();
        }
        System.out.println("main结束");
    }
    static class Write extends Thread{
        CyclicBarrier barrier;
        public Write(CyclicBarrier barrier){
            this.barrier=barrier;
        }
        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName()+" 开始写入数据...");
                Thread.sleep(1000);//模拟写入数据
                System.out.println("线程"+Thread.currentThread().getName()+"写入数据完毕，等待其他线程写入完毕");
                barrier.await();
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("所有线程写入数据完毕,"+Thread.currentThread().getName()+"可以继续干其他事情");
        }
    }
}
