package com.lhx.speakingpattern.structuretype;

import java.util.Arrays;
import java.util.List;

/**
 * 加薪非要老总批？ ---责任链模式
 */
public class ChainDemo {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(15, 300, 20, 4000, 500, 30,10000);
        Handler director=new DirectorHandler();
        Handler manager=new ManagerHandler();
        Handler boss=new BossHandler();

        director.setNextHandler(manager);
        manager.setNextHandler(boss);
        list.stream()
                .forEach(x->director.handle(x));
    }
    static abstract class Handler{
        protected Handler next;
        public abstract void handle(int type);
        public void setNextHandler(Handler next){
            this.next=next;
        }
    }

    /**
     * 主管处理者
     */
    static class DirectorHandler extends Handler{
        @Override
        public void handle(int type) {
            if(type<100){
                System.out.println("我系主管，我来搞掂数目"+type);
            }else {
                if (next != null) {
                    next.handle(type);
                }
            }
        }
    }
    /**
     * 经理处理者
     */
    static class ManagerHandler extends Handler{
        @Override
        public void handle(int type) {
            if(type>100&&type<=1000){
                System.out.println("我系经理，我来搞掂数目"+type);
            }else {
                if (next != null) {
                    next.handle(type);
                }
            }
        }
    }
    /**
     * 终极boss处理者
     */
    static class BossHandler extends Handler{
        @Override
        public void handle(int type) {
            if(type>1000){
                System.out.println("我系boss，我来搞掂数目"+type);
            }else {
                if (next != null) {
                    next.handle(type);
                }
            }
        }
    }
}
