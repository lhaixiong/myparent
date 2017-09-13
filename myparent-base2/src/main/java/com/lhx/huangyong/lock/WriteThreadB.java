package com.lhx.huangyong.lock;

public class WriteThreadB extends Thread {
    private DataB data;
    private String content;
    private int idx;
    public WriteThreadB(DataB data, String content){
        this.data=data;
        this.content=content;
    }
    @Override
    public void run() {
        while (true){
            char c = nextChar();
            data.write(c);
        }
    }
    private char nextChar(){
        char c = content.charAt(idx);
        idx++;
        if(idx>=content.length()){
            idx=0;
        }
        return c;
    }
}
