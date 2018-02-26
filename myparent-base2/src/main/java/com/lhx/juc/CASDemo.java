package com.lhx.juc;

public class CASDemo {
    public static void main(String[] args) {
        CASAlgorithm cas=new CASAlgorithm();
        for (int i = 0; i < 10; i++) {
           new Thread(new Runnable() {
               @Override
               public void run() {
                   int v=cas.getV();
                   int newV=(int) (Math.random()*100);
                   System.out.println(Thread.currentThread().getName()+"before v:"+v+",newV:"+newV);
                   System.out.println(Thread.currentThread().getName()+" cas result,"+cas.cas(v, newV));
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
                System.out.println(Thread.currentThread().getName()+" old:"+v+",expectValue:"+expectValue+",new:"+newValue+" aaaaaaaaaaa");
                v=newValue;
                flag=true;
            }
            System.out.println(Thread.currentThread().getName()+" v:"+v+",expectValue:"+expectValue+",new:"+newValue);
            return flag;
        }
    }
}
