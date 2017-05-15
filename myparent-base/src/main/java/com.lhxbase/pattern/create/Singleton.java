package com.lhxbase.pattern.create;

public class Singleton {
    private static Singleton instance=null;
    private Singleton(){

    }
    public static Singleton getInstance(){
        if(instance==null){
            synInit();
        }
        return instance;
    }
    private static synchronized void synInit(){
        if(instance==null){
            instance=new Singleton();
        }
    }
}
