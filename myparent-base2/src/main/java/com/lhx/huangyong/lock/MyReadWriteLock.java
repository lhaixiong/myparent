package com.lhx.huangyong.lock;

public class MyReadWriteLock {
    private int readingNum=0;//正在读的线程数目
    private int writingNum=0;//正在写的线程数目
    private int waitingToWriteNum=0;//等待写的线程数目
    private boolean writeFlag=true;//读写标记（true：可写，false：不可写，可读）

    /**
     * 读上锁
     */
    public synchronized void readLock() throws InterruptedException {
        // 若存在正在写入的线程，或当写入优先时存在等待写入的线程，则将当前线程设置为等待状态
        while(writingNum>0||(writeFlag&&waitingToWriteNum>0)){//一定要用while! 不能用if
            wait();
        }
        // 使正在读取的线程数加一
        readingNum++;
    }

    /**
     * 读解锁
     */
    public synchronized void readUnLock(){
        // 使正在读取的线程数减一
        readingNum--;
        // 写优先
        writeFlag=true;
        //  通知所有处于 wait 状态的线程
        notifyAll();
    }

    /**
     * 写上锁
     */
    public synchronized void writeLock() throws InterruptedException {
        // 使等待写入的线程数加一
        waitingToWriteNum++;
        try {
            // 若存在正在读取的线程，或存在正在写入的线程，则将当前线程设置为等待状态
            while (readingNum>0 || writingNum>0){
                wait();
            }
        }finally {
            // 使等待写入的线程数减一
            waitingToWriteNum--;
        }
        // 使正在写入的线程数加一
        writingNum++;
    }
    /**
     * 写解锁
     */
    public synchronized void writeUnLock(){
        // 使正在写入的线程数减一
        writingNum--;
        // 写入结束，可读取
        writeFlag=false;
        // 通知所有处于等待状态的线程
        notifyAll();
    }
}
