package com.lhx.juc;

import java.util.concurrent.Exchanger;

/**
 * Exchanger类源于java.util.concurrent包，它可以在两个线程之间传输数据
 */
public class ExchangerDemo {
    public static void main(String[] args) {
        Exchanger<String> ex=new Exchanger<>();
        new Student(ex).start();
        new Teacher(ex).start();
    }

    static class Teacher extends Thread{
        private Exchanger<String> exchanger;
        public Teacher(Exchanger<String> ex){
            this.exchanger=ex;
        }
        @Override
        public void run() {
            try {
                System.out.println("老师收到学生的信息："+exchanger.exchange("no way"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    static class Student extends Thread{
        private Exchanger<String> exchanger;
        public Student(Exchanger<String> ex){
            this.exchanger=ex;
        }
        @Override
        public void run() {
            try {
                System.out.println("学生收到老师的信息："+exchanger.exchange("play"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
