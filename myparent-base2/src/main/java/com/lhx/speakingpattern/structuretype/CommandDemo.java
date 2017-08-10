package com.lhx.speakingpattern.structuretype;

import java.util.ArrayList;
import java.util.List;

/**
 * 烤羊肉串引来的思考  ---命令模式
 */
public class CommandDemo {
    public static void main(String[] args) {
        Waitress girl=new Waitress();
        girl.addCmd(new MuttonCmd());
        girl.addCmd(new MuttonCmd());
        girl.addCmd(new ChickenCmd());
        girl.addCmd(new MuttonCmd());

        girl.noticeBBQ();
    }
    /**
     * 抽象命令类
     */
    static abstract class Command{
        abstract void executeCmd(Barbecuer b);
    }
    /**
     * 烤羊肉命令
     */
    static class MuttonCmd extends Command{

        @Override
        void executeCmd(Barbecuer b) {
            b.bakeMutton();
        }
    }
    /**
     * 烤鸡翅膀命令
     */
    static class ChickenCmd extends Command{

        @Override
        void executeCmd(Barbecuer b) {
            b.bakeChicken();
        }
    }

    /**
     * 烧烤师傅
     */
    static class Barbecuer{
        public void bakeMutton(){
            System.out.println("烤羊肉");
        }
        public void bakeChicken(){
            System.out.println("烤鸡翅");
        }
    }
    /**
     * 美女服务员
     */
    static class Waitress{
        private List<Command> orders=new ArrayList<>();
        private Barbecuer barbecuer=new Barbecuer();
        public void addCmd(Command cmd){
            orders.add(cmd);
        }
        public void removeCmd(Command cmd){
            orders.remove(cmd);
        }

        /**
         * 下单，通知师傅烤肉
         */
        public void noticeBBQ(){
            orders.stream()
                    .forEach((x)->x.executeCmd(barbecuer));
        }
    }
}
