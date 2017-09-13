package com.lhx.huangyong.lock;

public class MainClass {
    public static void main(String[] args) {
        DataA dataA=new DataA(10);
        new ReadThread(dataA).start();
        new ReadThread(dataA).start();
        new ReadThread(dataA).start();

        new WriteThread(dataA,"ABCDEFG").start();
        new WriteThread(dataA,"1234567").start();

        DataB dataB=new DataB(10);
        new ReadThreadB(dataB).start();
        new ReadThreadB(dataB).start();
        new ReadThreadB(dataB).start();

        new WriteThreadB(dataB,"ABCDEFG").start();
        new WriteThreadB(dataB,"1234567").start();
    }
}
