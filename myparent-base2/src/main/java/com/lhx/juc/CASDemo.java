package com.lhx.juc;

public class CASDemo {
    public static void main(String[] args) {
        CASAlgorithm cas=new CASAlgorithm();
        for (int i = 0; i < 10; i++) {
           new Thread(new Runnable() {
               @Override
               public void run() {
                   int v=cas.getV();
                   System.out.println(cas.cas(v, (int) (Math.random()*100)));
               }
           }).start() ;
        }
    }
    private static class CASAlgorithm{
        private int v;

        public synchronized int getV() {
            return v;
        }
        public synchronized boolean cas(int expectValue,int newValue){
            boolean flag=false;
            int old=v;
            if(old==expectValue){
                v=newValue;
                flag=true;
            }
            return flag;
        }
    }
}
