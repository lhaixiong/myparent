package com.lhx.speakingpattern.behavetype;

import java.util.ArrayList;
import java.util.List;

/**
 * 老板回来,我不知道-----观察者模式
 *
 * @since 2017年08月09日 上午10:01:31
 * @author lhx
 */
public class ObserverDemo {
    public static void main(String[] args) {
        Observer stockObserver1=new StockObserver("梁傲舜1");
        Observer stockObserver2=new StockObserver("梁傲舜2");
        Observer nbaObserver1=new NBAObserver("梁维伟1");
        Observer nbaObserver2=new NBAObserver("梁维伟2");

        Subject secretory=new Secretory();
        secretory.attach(stockObserver1);
        secretory.attach(stockObserver2);
        secretory.attach(nbaObserver1);
        secretory.attach(nbaObserver2);
        secretory.setSubjectData();
        secretory.notice();

        Subject reception=new Reception();
        reception.attach(stockObserver1);
        reception.attach(stockObserver2);
        reception.attach(nbaObserver1);
        reception.attach(nbaObserver2);
        reception.setSubjectData();
        reception.notice();
    }
    //通知者接口
    interface Subject{
        void attach(Observer o);
        void detach(Observer o);
        void notice();
        Object getSubjectData();
        void setSubjectData();
    }
    //具体秘书通知者
    static class Secretory implements Subject{
        private Object data;
        private List<Observer> clgList=new ArrayList<>();
        @Override
        public void attach(Observer o) {
            clgList.add(o);
        }

        @Override
        public void detach(Observer o) {
            clgList.remove(o);
        }

        @Override
        public void notice() {
            clgList.stream()
                    .forEach((x)->x.doSomething(this,data));
        }

        @Override
        public Object getSubjectData() {
            return data;
        }

        @Override
        public void setSubjectData() {
            setData("我是秘书，老细翻来喇，大家各就各位!!!");
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }
    //具体前台通知者
    static class Reception implements Subject{
        private Object data;
        private List<Observer> clgList=new ArrayList<>();
        @Override
        public void attach(Observer o) {
            clgList.add(o);
        }

        @Override
        public void detach(Observer o) {
            clgList.remove(o);
        }

        @Override
        public void notice() {
            clgList.stream()
                    .forEach((x)->x.doSomething(this,data));
        }

        @Override
        public Object getSubjectData() {
            return data;
        }

        @Override
        public void setSubjectData() {
            setData("我是前台，老细翻来喇，大家各就各位!!!");
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }
    //观察者接口
    interface Observer{
        void doSomething(Subject subject,Object data);
    }
    //具体股票观察者
    static class StockObserver implements Observer{
        private String name;
        @Override
        public void doSomething(Subject subject,Object data) {
            System.out.println(String.format("股票观察者[%s]收到通知:[%s]，渣渣林关闭股票行情先...",name,data));
        }

        public StockObserver(String name) {
            this.name = name;
        }
    }
    //具体NBA观察者
    static class NBAObserver implements Observer{
        private String name;
        @Override
        public void doSomething(Subject subject,Object data) {
            System.out.println(String.format("NBA观察者[%s]收到通知:[%s]，渣渣林关闭NBA直播先...",name,data));
        }

        public NBAObserver(String name) {
            this.name = name;
        }
    }
}
