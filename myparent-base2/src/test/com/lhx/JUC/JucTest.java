package com.lhx.JUC;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;


public class JucTest {
    public static void main(String[] args) throws InterruptedException {
        Timer timer=new Timer(true);//Deamon守护线程，这里主线程结束了，timerTask也跟著一起结束
//        Timer timer=new Timer();
        System.out.println(Thread.currentThread().isDaemon());
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                System.out.println(LocalDateTime.now().toString());
            }
        };
        timer.schedule(task, new Date(),2000);
//        timer.schedule(task, DateUtil.addSecond(new Date(),2),2*1000);
//        timer.scheduleAtFixedRate(task,DateUtil.addSecond(new Date(),3),2*1000);
        Thread.sleep(8000);
        System.out.println("主线程结束了");
    }
    @Test
    public void testAtomicInteger() throws Exception{
        final AtomicInteger value = new AtomicInteger(10);
        assertEquals(value.compareAndSet(1, 2), false);
        assertEquals(value.get(), 10);
        assertTrue(value.compareAndSet(10, 3));
        assertEquals(value.get(), 3);
        value.set(0);
        //
        assertEquals(value.incrementAndGet(), 1);
        assertEquals(value.getAndAdd(2),1);
        assertEquals(value.getAndSet(5),3);
        assertEquals(value.get(),5);
        //
        final int threadSize = 10;
        Thread[] ts = new Thread[threadSize];
        for (int i = 0; i < threadSize; i++) {
            ts[i] = new Thread() {
                public void run() {
                    value.incrementAndGet();
                }
            };
        }
        //
        for(Thread t:ts) {
            t.start();
        }
        for(Thread t:ts) {
            t.join();
        }
        //
        assertEquals(value.get(), 5+threadSize);
    }
    @Test
    public void test01(){
        int[] arr={1,2,3,4,5,6};
        List<Integer> list = Arrays.asList(1, 7, 2, 3, 4, 5, 6);
        System.out.println(Arrays.binarySearch(arr, 0, 6, 6));
        System.out.println("max:"+Collections.max(list));
        System.out.println("frequency:"+Collections.frequency(list,2));

        List<Integer> linkList=new LinkedList<>();
        List<Integer> arrList=new ArrayList<>();
        System.out.println(list.toArray());

        Integer a=100;
        Integer b=100;
        System.out.println(a==b);
    }
}
