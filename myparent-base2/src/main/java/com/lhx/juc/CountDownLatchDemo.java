package com.lhx.juc;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch ：闭锁，在完成某些运算是，只有其他所有线程的运算全部完成，当前运算才继续执行
 * 异步转同步（join也是）
 *
 * 下面对上面说的三个辅助类进行一个总结：

 　　1）CountDownLatch和CyclicBarrier都能够实现线程之间的等待，只不过它们侧重点不同：

 　　　　CountDownLatch一般用于某个线程A等待若干个其他线程执行完任务之后，它才执行；

 　　　　而CyclicBarrier一般用于一组线程互相等待至某个状态，然后这一组线程再同时执行；

 　　　　另外，CountDownLatch是不能够重用的，而CyclicBarrier是可以重用的。

 　　2）Semaphore其实和锁有点类似，它一般用于控制对某组资源的访问权限。
 */
public class CountDownLatchDemo {
    public static void main(String[] args) {
        final CountDownLatch cdl=new CountDownLatch(10);
        LatchDemo ld=new LatchDemo(cdl);
        long start=System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            new Thread(ld).start();
        }
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end=System.currentTimeMillis();
        System.out.println("耗时："+(end-start));
    }
    private static class LatchDemo implements Runnable{
        private CountDownLatch cdl;

        public LatchDemo(CountDownLatch cdl) {
            this.cdl = cdl;
        }

        @Override
        public void run() {
           synchronized (cdl){
               try {
                   for (int i = 0; i < 50000; i++) {
                       if(i%2==0){
                           System.out.println(i);
                       }
                   }
               } finally {
                   cdl.countDown();
               }
           }
        }
    }
}
