package com.lhx.huangyong.lock;


/**
 * https://my.oschina.net/huangyong/blog/161419 中的lock那些事儿demo
 */
public class DataA{
    private char[] buffer;
    public DataA(int size){
        if(size<1){
            throw new IllegalArgumentException("参数错误,size必须大于0");
        }
        buffer=new char[size];
        for (int i = 0; i < size; i++) {
            buffer[i]='*';
        }
    }

    public synchronized String read(){
        StringBuilder sb=new StringBuilder();
        for (char c : buffer) {
            sb.append(c);
        }
        sleep(100);
        return sb.toString();
    }

    public synchronized void write(char c){
        for (int i = 0; i < buffer.length; i++) {
            buffer[i]=c;
            sleep(100);
        }
    }
    private void sleep(long ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
