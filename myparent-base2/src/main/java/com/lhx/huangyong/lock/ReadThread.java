package com.lhx.huangyong.lock;

public class ReadThread extends Thread {
    private DataA data;
    public ReadThread(DataA data){
        this.data=data;
    }
    @Override
    public void run() {
        while (true){
            long begin=System.currentTimeMillis();
            for (int i = 0; i < 3; i++) {
                String result=data.read();
                System.out.println(Thread.currentThread().getName()+" readA=>"+result);
            }
            System.out.println(Thread.currentThread().getName()+" 每读3次耗时:"+(System.currentTimeMillis()-begin));
        }
    }
}
