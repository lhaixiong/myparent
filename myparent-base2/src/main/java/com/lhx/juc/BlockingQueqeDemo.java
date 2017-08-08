package com.lhx.juc;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BlockingQueqeDemo {
    public static void main(String[] args) throws Exception {
        BlockingQueue<String> queue=new LinkedBlockingDeque<>(10);
        ExecutorService service= Executors.newCachedThreadPool();
        Productor productor1=new Productor(queue);
        Productor productor2=new Productor(queue);
        Productor productor3=new Productor(queue);
        Consumer consumer=new Consumer(queue);

        service.execute(productor1);
        service.execute(productor2);
        service.execute(productor3);
        service.execute(consumer);

        //执行4s
        Thread.sleep(4000);
        productor1.stop();
        productor2.stop();
        productor3.stop();
        //退出excutor
        service.shutdown();
    }
    private static class Productor implements Runnable{
        private BlockingQueue<String> queue;
        private boolean isRunning=true;
        private static AtomicInteger atomicInteger=new AtomicInteger();
        private static final int DEFAULT_SLEEP_TIME=1000;
        public Productor(BlockingQueue<String> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                Random r=new Random();
                while (isRunning){
                    String data="data"+atomicInteger.getAndIncrement();
                    System.out.println(Thread.currentThread().getName() + "生产数据：" + data);
                    Thread.sleep(r.nextInt(DEFAULT_SLEEP_TIME));
                    if (!queue.offer(data,2,TimeUnit.SECONDS)) {
                        System.out.println(Thread.currentThread().getName()+"数据："+data+"放入队列失败!");
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                System.out.println(Thread.currentThread().getName()+"生产者线程停止");
            }
        }
        public void stop(){
            this.isRunning=false;
        }
    }
    private static class Consumer implements Runnable{
        private BlockingQueue<String> queue;

        public Consumer(BlockingQueue<String> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                boolean isRunning=true;
                while (isRunning){
                    System.out.println("正从队列中获取数据....");
                    String data = queue.poll(2, TimeUnit.SECONDS);
                    if (data != null) {
                        System.out.println("消费者取出数据："+data);
                    }else {
                        // 超过2s还没数据，认为所有生产线程都已经退出，自动退出消费线程。
                        isRunning=false;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                System.out.println(Thread.currentThread().getName()+"消费者线程停止");
            }
        }
    }
}
