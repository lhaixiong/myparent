package com.lhxbase.thread;

public class ProducerThread implements Runnable {
    private Info info;

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    @Override
    public void run() {
        boolean flag=true;
        for (int i=0;i<50;i++){
            if(flag){
                this.info.set("李兴华","java培训讲师");
                flag=false;
            }else {
                this.info.set("mldn","www.mldn.com");
                flag=true;
            }
        }
    }

    public ProducerThread(Info info) {
        this.info = info;
    }
}
