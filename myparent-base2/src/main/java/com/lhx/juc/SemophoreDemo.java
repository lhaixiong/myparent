package com.lhx.juc;

import java.util.concurrent.Semaphore;

/**
 * Java并发编程：CountDownLatch、CyclicBarrier和Semaphore
 * https://www.cnblogs.com/dolphin0520/p/3920397.html
 *
 * 下面对上面说的三个辅助类进行一个总结：

 　　1）CountDownLatch和CyclicBarrier都能够实现线程之间的等待，只不过它们侧重点不同：

 　　　　CountDownLatch一般用于某个线程A等待若干个其他线程执行完任务之后，它才执行；

 　　　　而CyclicBarrier一般用于一组线程互相等待至某个状态，然后这一组线程再同时执行；

 　　　　另外，CountDownLatch是不能够重用的，而CyclicBarrier是可以重用的。

 　　2）Semaphore其实和锁有点类似，它一般用于控制对某组资源的访问权限。
 */
public class SemophoreDemo {
    public static void main(String[] args) {
        Semaphore semaphore=new Semaphore(5);//5台机器
        for (int i = 1; i <= 8; i++) {//8个工人
            new Worker(i,semaphore).start();
        }
    }
    static class Worker extends Thread{
        private int workerNum;
        private Semaphore semaphore;
        public Worker(int workerNum,Semaphore semaphore){
            this.workerNum=workerNum;
            this.semaphore=semaphore;
        }
        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println("工人"+this.workerNum+"占用一个机器在生产...");
                Thread.sleep(2000);
                System.out.println("工人"+this.workerNum+"释放出机器");
                semaphore.release();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
