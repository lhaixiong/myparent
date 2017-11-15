package com.lhx.blog.other;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * http://blog.csdn.net/coslay/article/details/45644929
 * 在线上Java程序中经常遇到进程程挂掉，一些状态没有正确的保存下来，这时候就需要在JVM关掉的时候执行一些清理现场的代码。Java中得ShutdownHook提供了比较好的方案。
   JDK在1.3之后提供了Java Runtime.addShutdownHook(Thread hook)方法，可以注册一个JVM关闭的钩子，这个钩子可以在以下几种场景被调用：

 1）程序正常退出
 2）使用System.exit()
 3）终端使用Ctrl+C触发的中断
 4）系统关闭
 5）使用Kill pid命令干掉进程
 注：在使用kill -9 pid是不会JVM注册的钩子不会被调用。
 在JDK中方法的声明：
 public void addShutdownHook(Thread hook)
 参数
 hook – 一个初始化但尚未启动的线程对象，注册到JVM钩子的运行代码。
 异常
 IllegalArgumentException – 如果指定的钩已被注册，或如果它可以判定钩已经运行或已被运行
 IllegalStateException – 如果虚拟机已经是在关闭的过程中
 SecurityException – 如果存在安全管理器并且它拒绝的RuntimePermission（“shutdownHooks”）

 代码示例：
 使用Timer模拟一个工作线程，该线程重复工作十次，使用System.exit()退出，在清理现场代码CleanWorkThread 中，取消timer运行，并输出必要的日志信息。
 */
public class ShutDownHookDemo {
    //简单模拟干活的定时器
    private static Timer timer=new Timer("job-timer");
    //计数干活次数
    private static AtomicInteger count=new AtomicInteger(0);

    //hook线程
    private static class CleanWorkThread extends Thread{
        @Override
        public void run() {
            System.out.println("clean work Thread Start Cleaning....");
            timer.cancel();
            try {
                Thread.sleep(2 * 1000);//sleep 2s
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        //将hook线程添加到运行时环境中去
        Runtime.getRuntime().addShutdownHook(new CleanWorkThread());
        System.out.println("main 运行....");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                count.getAndIncrement();
                System.out.println("doing job times "+count.get());
                if(count.get()==10){////干了10次退出
                    System.exit(1);
                }
            }
        },2000,2000);
    }


}
