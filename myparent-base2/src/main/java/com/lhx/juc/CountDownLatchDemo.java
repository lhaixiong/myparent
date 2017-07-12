package com.lhx.juc;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch ：闭锁，在完成某些运算是，只有其他所有线程的运算全部完成，当前运算才继续执行
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
