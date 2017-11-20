package com.lhx.juc;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 一、线程池：提供了一个线程队列，队列中保存着所有等待状态的线程。避免了创建与销毁额外开销，提高了响应的速度。
 * 
 * 二、线程池的体系结构：
 * 	java.util.concurrent.Executor : 负责线程的使用与调度的根接口
 * 		|--**ExecutorService 子接口: 线程池的主要接口
 * 			|--ThreadPoolExecutor 线程池的实现类
 * 			|--ScheduledExecutorService 子接口：负责线程的调度
 * 				|--ScheduledThreadPoolExecutor ：继承 ThreadPoolExecutor， 实现 ScheduledExecutorService
 * 
 * 三、工具类 : Executors 
 * ExecutorService newFixedThreadPool() : 创建固定大小的线程池
 * ExecutorService newCachedThreadPool() : 缓存线程池，线程池的数量不固定，可以根据需求自动的更改数量。
 * ExecutorService newSingleThreadExecutor() : 创建单个线程池。线程池中只有一个线程
 * 
 * ScheduledExecutorService newScheduledThreadPool() : 创建固定大小的线程，可以延迟或定时的执行任务。
 */
public class TestScheduledThreadPool {

	public static void main(String[] args) throws Exception {
		ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);
		
		for (int i = 0; i < 5; i++) {
			Future<Integer> result = pool.schedule(new Callable<Integer>(){

				@Override
				public Integer call() throws Exception {
					int num = new Random().nextInt(100);//生成随机数
					System.out.println(Thread.currentThread().getName() + " : " + num);
					return num;
				}

			}, 1, TimeUnit.SECONDS);

			System.out.println(result.get());
		}
		pool.shutdown();

		//pool.scheduleAtFixedRate(new Runnable() {
		//	@Override
		//	public void run() {
		//		int num = new Random().nextInt(100);//生成随机数
		//		System.out.println(Thread.currentThread().getName() + " : " + num);
		//	}
        //
		//}, 1, 2, TimeUnit.SECONDS);

		//ScheduledFuture<?> future = pool.scheduleWithFixedDelay(new Runnable() {
		//	@Override
		//	public void run() {
		//		int num = new Random().nextInt(100);//生成随机数
		//		System.out.println(Thread.currentThread().getName() + " : " + num);
		//	}
        //
		//}, 1, 2, TimeUnit.SECONDS);
		//注意：这里周期性定时任务无须调用pool.shutdown()方法，否则定时器不执行，原因是因为定时任务延迟1秒提交执行，而在提交前先shutdown了，定时器也就不执行了
		//pool.shutdown();
		//这里可以用future.get()无限阻塞main线程，故maiiiii永远不打印
		//future.get();
		//System.out.println("maiiiiii");

	}
	
}
