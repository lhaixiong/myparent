package lhxbase;

import com.lhxbase.thread.*;
import org.junit.Test;

public class ThreadTest {
    @Test
    public void testProducerAndCostomer(){
        Info info=new Info();
        Thread pro=new Thread(new ProducerThread(info),"生产者");
        Thread cus=new Thread(new CustomerThread(info),"消费者");
        pro.start();
        cus.start();
        try {
            Thread.sleep(1000*60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testSyn(){
        MyRunnableC myRunnableC=new MyRunnableC(true);
        Thread thread1=new Thread(myRunnableC,"aaa");
        Thread thread2=new Thread(myRunnableC,"bbb");
        Thread thread3=new Thread(myRunnableC,"ccc");

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testNoneSyn(){
        MyRunnableC myRunnableC=new MyRunnableC(false);
        Thread thread1=new Thread(myRunnableC,"aaa");
        Thread thread2=new Thread(myRunnableC,"bbb");
        Thread thread3=new Thread(myRunnableC,"ccc");

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * 线程礼让
     */
    @Test
    public void testYield(){
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<10;i++){
                    System.out.println(Thread.currentThread().getName()+"线程运行，i="+i);
                    if(i==6){
                        System.out.println(Thread.currentThread().getName()+"线程礼让");
                        Thread.currentThread().yield();
                    }
                }
            }
        };
        Thread thread1=new Thread(runnable,"aaa");
        Thread thread2=new Thread(runnable,"bbb");
        Thread thread3=new Thread(runnable,"ccc");

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * 线程优先级
     */
    @Test
    public void testPriority(){
        Thread thread1=new Thread(new MyRunnable(),"aaa");
        Thread thread2=new Thread(new MyRunnable(),"bbb");
        Thread thread3=new Thread(new MyRunnable(),"ccc");

        thread1.setPriority(Thread.MIN_PRIORITY);
        thread2.setPriority(Thread.NORM_PRIORITY);
        thread3.setPriority(Thread.MAX_PRIORITY);

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"优先级》》》"+Thread.currentThread().getPriority());
    }
    /**
     * 后台线程***  jvm（main）进程结束后，线程仍可运行
     */
    /*
    守护线程（Daemon）
Java有两种Thread：“守护线程Daemon”与“用户线程User”。
守护线程是一种“在后台提供通用性支持”的线程，它并不属于程序本体。
任何线程都可以是“守护线程Daemon”或“用户线程User”。他们在几乎每个方面都是相同的，唯一的区别是判断虚拟机何时离开：
用户线程：Java虚拟机在它所有非守护线程已经离开后自动离开。
守护线程：守护线程则是用来服务用户线程的，如果没有其他用户线程在运行，那么就没有可服务对象，也就没有理由继续下去。
setDaemon(boolean on)方法可以方便的设置线程的Daemon模式，true为Daemon模式，false为User模式。
setDaemon(boolean on)方法必须在线程启动之前调用，当线程正在运行时调用会产生异常。isDaemon方法将测试该线程是否为守护线程。
值得一提的是，当你在一个守护线程中产生了其他线程，那么这些新产生的线程不用设置Daemon属性，都将是守护线程，用户线程同样。
 例：我们所熟悉的Java垃圾回收线程就是一个典型的守护线程，当我们的程序中不再有任何运行中的Thread，
 程序就不会再产生垃圾，垃圾回收器也就无事可做，所以当垃圾回收线程是Java虚拟机上仅剩的线程时，Java虚拟机会自动离开
     */
    @Test
    public void testSetDaemon(){
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                while (true){
                    System.out.println(Thread.currentThread().getName()+"线程运行");
                }
            }
        };
        Thread thread=new Thread(runnable,"aaaa");

        thread.setDaemon(true);//关键点，后台线程(守护线程) ，//后台线程是死循环，前台线程结束后台会自然结束
        System.out.println(thread.getName()+" 线程 isDaemon>>"+thread.isDaemon());

        thread.start();

        System.out.println(Thread.currentThread().getName()+"结束了");

    }

    /**
     * 线程休眠
     *
     * 进程中线程之间的关系
     线程不像进程，一个进程中的线程之间是没有父子之分的，都是平级关系。即线程都是一样的, 退出了一个不会影响另外一个。
     但是所谓的"主线程"main,其入口代码是类似这样的方式调用main的：exit(main(...))。
     main执行完之后, 会调用exit()。
     exit() 会让整个进程over终止，那所有线程自然都会退出
     后台线程可以解决这个问题
     */
    @Test
    public void testThreadSleep(){
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<10;i++){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"线程运行,i="+i);
                }
            }
        };
        new Thread(runnable,"线程A").start();
        System.out.println(Thread.currentThread().getName());
    }
    /**
     * 解除线程的阻塞状态，使得线程重新回到可执行状态。
     */
    @Test
    public void testThreadInterrupt(){
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                System.out.println("进入run（）方法");
                for (int i=0;i<10;i++){
                    System.out.println(Thread.currentThread().getName()+"线程运行,i="+i);
                    if(i==4){
                        try {
                            Thread.sleep(5000);//休眠5秒，线程进入阻塞状态
                        } catch (InterruptedException e) {
                            System.out.println("休眠被终止，线程重新回到可执行状态");
                            e.printStackTrace();
//                            return;//返回被调用处（返回主线程），该线程停止执行。

                        }
                    }

                }
                System.out.println("run()方法完成");
            }
        };
        Thread thread=new Thread(runnable,"线程A");
        thread.start();
        try {
            Thread.sleep(2000);//主线程休眠2秒
        } catch (InterruptedException e) {
            System.out.println("主线程休眠被终止");
            e.printStackTrace();
        }
        thread.interrupt();//解除thread的阻塞状态，使得线程重新回到可执行状态。
        try {
            Thread.sleep(8000);//主线程休眠8秒
        } catch (InterruptedException e) {
            System.out.println("主线程休眠被终止");
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
    }
    /**
     * 线程强制运行
     * thread.Join()把指定的线程加入到当前线程，
     * 可以将两个交替执行的线程合并为顺序执行的线程。
     * 比如在线程A中调用了线程B的Join()方法，直到线程B执行完毕后，才会继续执行线程A。
     * 原来由AB并发，变成A-》B-》A执行了。
     */
    @Test
    public void testJoin(){
        Thread.currentThread().setName("线程-AAA");
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<50;i++){
                    System.out.println(Thread.currentThread().getName()+"线程运行,i="+i);
                }
            }
        };
        Thread thread=new Thread(runnable,"线程-BBB");
        thread.start();

        for (int i=0;i<50;i++){
            if(i>10){
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName()+"线程运行,i="+i);
        }
    }
    /**
     *测试部分线程方法
     */
    @Test
    public void testSomeThreadMethod(){
        //取得和设置线程名称
        MyThreadA thread1=new MyThreadA();
        thread1.setName("线程-AAA");
        System.out.println("线程名称："+thread1.getName());

        Thread thread=new Thread(thread1,"线程-BBB");
        System.out.println("线程名称："+thread.getName());

        System.out.println("取得当前线程>>>"+Thread.currentThread().getName());

        //判断线程是否启动
        System.out.println("判断线程是否启动>>"+thread.getName()+">>>"+thread.isAlive());
    }

    /**
     * 继承Thread的方式共享资源（ticket）
     */
    @Test
    public void testMemberShared(){
        System.out.println("test thread name>>>"+Thread.currentThread().getName());
        MyThreadA thread=new MyThreadA();
        Thread thread1=new Thread(thread);
        Thread thread2=new Thread(thread);
        thread1.setName("A");
        thread2.setName("B");
        thread1.start();
        thread2.start();
    }
    /**
     * 继承Thread的方式不共享资源（ticket）
     */
    @Test
    public void testMemberUnShared(){
        System.out.println("test thread name>>>"+Thread.currentThread().getName());
        MyThreadA thread1=new MyThreadA();
        MyThreadA thread2=new MyThreadA();
        thread1.setName("A");
        thread2.setName("B");
        thread1.start();
        thread2.start();
    }
    /**
     * 实现Runnable接口的方式可以共享资源（ticket），此方法尚未完善
     */
    @Test
    public void testMemberSharedWithoutSyn(){
        System.out.println("test thread name>>>"+Thread.currentThread().getName());
        MyRunnableA myRunnableA=new MyRunnableA();
        Thread thread1=new Thread(myRunnableA);
        Thread thread2=new Thread(myRunnableA);
        thread1.setName("A");
        thread2.setName("B");
        thread1.start();
        thread2.start();
    }
    @Test
    public void testMultiThread(){
        System.out.println("test thread name>>>"+Thread.currentThread().getName());
        Thread thread1=new Thread(new MyRunnable());
        Thread thread2=new Thread(new MyRunnable());
        thread1.setName("A");
        thread2.setName("B");
        thread1.start();
        thread2.start();
    }
    @Test
    public void testRunnable(){
        System.out.println("test thread name>>>"+Thread.currentThread().getName());
        Thread thread=new Thread(new MyRunnable());
        thread.setName("B");
        thread.start();
    }
    @Test
    public void testThread(){
        System.out.println("test thread name>>>"+Thread.currentThread().getName());
        MyThread myThread=new MyThread();
        myThread.setName("A");
        myThread.start();
    }
    @Test
    public void testRepeatStart(){
        System.out.println("test thread name>>>"+Thread.currentThread().getName());
        MyThread myThread=new MyThread();
        myThread.setName("A");
        myThread.start();
        myThread.start();
    }
}
