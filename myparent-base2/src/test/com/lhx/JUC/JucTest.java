package com.lhx.JUC;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.*;


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
    public void test01(){
        int[] arr={1,2,3,4,5,6};
        List<Integer> list = Arrays.asList(1, 7, 2, 3, 4, 5, 6);
        System.out.println(Arrays.binarySearch(arr, 0, 6, 6));
        System.out.println("max:"+Collections.max(list));
        System.out.println("frequency:"+Collections.frequency(list,2));

        List<Integer> linkList=new LinkedList<>();
        List<Integer> arrList=new ArrayList<>();
        System.out.println(list.toArray());
    }
}
