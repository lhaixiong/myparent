package com.lhxbase.thread;

public class Info {
    private String name;
    private String content;
    private boolean flag=true;//true 表示可以生产，false表示不能生产

    public synchronized void set(String name,String content){
        if(!flag){
            try {
                super.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.setName(name);
        try {
            Thread.sleep(90);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.setContent(content);
        flag=false;
        super.notify();//通知可以消费
    }
    public synchronized void  get(){
        if(flag){
            try {
                super.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(90);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag=true;
        System.out.println(this.getName()+">>>"+this.getContent());
        super.notify();//通知可以生产
    }

    public Info(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Info() {
    }
}
