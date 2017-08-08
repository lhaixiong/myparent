package com.lhx.juc;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueqeDemo {
    public static void main(String[] args) {
        DelayQueue<DelayElement> queue=new DelayQueue<>();
        DelayElement e=new DelayElement(3000,"延迟3秒");
        queue.put(e);
        try {
            System.out.println(queue.take());
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }
    private static class DelayElement implements Delayed{
        private long expire;
        private long delay;
        private String name;

        public DelayElement(long delay, String name) {
            this.delay = delay;
            this.expire = delay+System.currentTimeMillis();
            this.name = name;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return (expire-System.currentTimeMillis());
        }

        @Override
        public int compareTo(Delayed o) {
            DelayElement other= (DelayElement) o;
            long e=this.expire-other.getExpire();
            return e==0?0:e>0?1:-1;
        }

        public long getExpire() {
            return expire;
        }

        public void setExpire(long expire) {
            this.expire = expire;
        }

        public long getDelay() {
            return delay;
        }

        public void setDelay(long delay) {
            this.delay = delay;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "DelayElement{" +
                    "expire=" + expire +
                    ", delay=" + delay +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}

