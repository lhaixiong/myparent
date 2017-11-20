package com.lhx.juc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * http://blog.csdn.net/pfnie/article/details/52755769
 *
 * 1.AbortPolicy:当有新任务添加到线程池被拒绝时,直接抛出异常
 * 2.CallerRunsPolicy:当有新任务添加到线程池被拒绝时，线程池会将被拒绝的任务添加到"线程池正在运行的线程"中去运行：
 * 3.DiscardPolicy：当有新任务添加到线程池被拒绝时，直接丢弃新任务
 * 4.DiscardOldestPolicy：当有新任务添加到线程池被拒绝时，线程池会丢弃阻塞队列中末尾的任务，然后将被拒绝的任务添加到末尾
 */
public class RejectedExecutionHandlerDemo {
    ////1. AbortPolicy 示例
    //public static void main(String[] args) {
    //    // 创建线程池。线程池的"最大池大小"和"核心池大小"都为1，"线程池"的阻塞队列容量为1。
    //    ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1));
    //    // 设置线程池的拒绝策略为AbortPolicy
    //    pool.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
    //    try {
    //        // 新建10个任务，并将它们添加到线程池中
    //        for (int i = 0; i < 10; i++) {
    //            Task task=new Task("task-"+i);
    //            pool.execute(task);
    //        }
    //    }catch (Exception e){
    //        e.printStackTrace();
    //        // 关闭线程池
    //        pool.shutdown();
    //    }
    //
    //}

    ////2. CallerRunsPolicy 示例
    //public static void main(String[] args) {
    //    // 创建线程池。线程池的"最大池大小"和"核心池大小"都为1，"线程池"的阻塞队列容量为1。
    //    ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 1, 0,
    //            TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1));
    //
    //    // 设置线程池的拒绝策略为CallerRunsPolicy
    //    pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    //
    //    // 新建10个任务，并将它们添加到线程池中
    //    for (int i = 0; i < 10; i++) {
    //        Runnable myTask = new Task("task-"+i);
    //        pool.execute(myTask);
    //    }
    //
    //    // 关闭线程池
    //    pool.shutdown();
    //    //输出结果
    //    //task-2 is running.
    //    //task-0 is running.
    //    //task-3 is running.
    //    //task-1 is running.
    //    //task-5 is running.
    //    //task-4 is running.
    //    //task-7 is running.
    //    //task-6 is running.
    //    //task-9 is running.
    //    //task-8 is running.
    //}

    ////3.DiscardPolicy 示例
    //public static void main(String[] args) {
    //    // 创建线程池。线程池的"最大池大小"和"核心池大小"都为1，"线程池"的阻塞队列容量为1。
    //    ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 1, 0,
    //            TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1));
    //
    //    // 设置线程池的拒绝策略为DiscardPolicy
    //    pool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
    //
    //    // 新建10个任务，并将它们添加到线程池中
    //    for (int i = 0; i < 10; i++) {
    //        Runnable myTask = new Task("task-"+i);
    //        pool.execute(myTask);
    //    }
    //
    //    // 关闭线程池
    //    pool.shutdown();
    //
    //    //输出结果
    //    //task-0 is running.
    //    //task-1 is running.
    //}

    ////4.DiscardOldestPolicy 示例
    public static void main(String[] args) {
        // 创建线程池。线程池的"最大池大小"和"核心池大小"都为1，"线程池"的阻塞队列容量为1。
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 1, 0,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1));

        // 设置线程池的拒绝策略为DiscardOldestPolicy
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());

        // 新建10个任务，并将它们添加到线程池中
        for (int i = 0; i < 10; i++) {
            Runnable myTask = new Task("task-"+i);
            pool.execute(myTask);
        }

        // 关闭线程池
        pool.shutdown();

        //输出结果
        //task-0 is running.
        //task-9 is running.
    }

    static class Task implements Runnable{
        private String name;
        public Task(String name){
            this.name=name;
        }
        @Override
        public void run() {
            try {
                System.out.println(this.name + " is running.");
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
