package com.lhxbase.thread;

public class CustomerThread implements Runnable {
    private Info info;

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    @Override
    public void run() {
        for (int i=0;i<50;i++){
            System.out.println(i);
            this.info.get();
        }
    }

    public CustomerThread(Info info) {
        this.info = info;
    }
}
