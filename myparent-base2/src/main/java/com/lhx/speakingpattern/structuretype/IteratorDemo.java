package com.lhx.speakingpattern.structuretype;

import java.util.ArrayList;
import java.util.List;

/**
 * 想走？可以，先买票   --迭代器模式
 */
public class IteratorDemo {
    public static void main(String[] args) {
        Clt concretCltA=new ConcreteCltA();
        concretCltA.add("大鸟");
        concretCltA.add("小菜");
        concretCltA.add("老外");
        concretCltA.add("小偷");
        concretCltA.add("公务员");
        concretCltA.add("梁熬削");
        concretCltA.add("vv");
        Itr itr = concretCltA.iterator();
        System.out.println("first--"+itr.first());
        while (itr.hasNext()){
            itr.next();
            System.out.println(String.format("{%s},想走？哈？买飞啦!",itr.next()));
        }
    }
    private interface Itr{
        Object first();
        Object next();
        boolean hasNext();
        Object cur();
    }
    private static class ConcreteItrA implements Itr{
        private ConcreteCltA collection;
        private int count;
        public ConcreteItrA(ConcreteCltA collection) {
            this.collection = collection;
        }

        @Override
        public Object first() {
            return collection.get(0);
        }

        @Override
        public Object next() {
            return collection.get(count++);
        }

        @Override
        public boolean hasNext() {
            return (count<=collection.size()-1)?true:false;
        }

        @Override
        public Object cur() {
            return collection.get(count);
        }
    }
    private interface Clt{
        Itr iterator();
        int size();
        void add(Object o);
    }
    private static class ConcreteCltA implements Clt{
        private List<Object> list=new ArrayList<>();
        @Override
        public Itr iterator() {
            return new ConcreteItrA(this);
        }
        public Object get(int idx){
            return list.isEmpty()?null:list.get(idx);
        }

        @Override
        public int size() {
            return list.size();
        }

        @Override
        public void add(Object o) {
            list.add(o);
        }
    }
}
