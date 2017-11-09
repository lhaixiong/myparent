package com.lhx.headfirstjava.structuretype;

public class FactoryMethodPizzaDemo {
    public static void main(String[] args) {
        PizzaStore nyPizzzStore=new NYPizzaStore();
        nyPizzzStore.orderPizza("cheese");

        PizzaStore laPizzaStore=new LAPizzaStore();
        laPizzaStore.orderPizza("clam");
    }

    static abstract class PizzaStore{
        public Pizza orderPizza(String type){
            Pizza pizza=createPizzs(type);//具体创建延迟到子类
            pizza.prepare();
            pizza.bake();
            pizza.cut();
            pizza.box();
            return pizza;
        }
        public abstract Pizza createPizzs(String type);//此方法就是工厂方法，具体创建延迟到子类，返回产品pizza
    }
    static class NYPizzaStore extends PizzaStore{

        @Override
        public Pizza createPizzs(String type) {
            Pizza pizza=null;
            if("cheese".equals(type)){
                pizza=new CheesePizza("newyorkstypecheesepizza");
            }else if("clam".equals(type)){
                pizza=new ClamPizza("newyorkstypeclampizza");
            }
            return pizza;
        }
    }
    static class LAPizzaStore extends PizzaStore{

        @Override
        public Pizza createPizzs(String type) {
            Pizza pizza=null;
            if("cheese".equals(type)){
                pizza=new CheesePizza("LAstypecheesepizza");
            }else if("clam".equals(type)){
                pizza=new ClamPizza("Lastypeclampizza");
            }
            return pizza;
        }
    }
    static class Pizza{
        private String name;
        public void prepare(){
            System.out.println(this.getName()+"pizza prepare()");
        }
        public void bake(){
            System.out.println(this.getName()+"pizza bake()");
        }
        public void cut(){
            System.out.println(this.getName()+"pizza cut()");
        }
        public void box(){
            System.out.println(this.getName()+"pizza box()");
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Pizza(String name){
            this.name=name;
        }
    }
    static class CheesePizza extends Pizza{
        public CheesePizza(String name){
            super(name);
        }
    }
    static class ClamPizza extends Pizza{
        public ClamPizza(String name){
            super(name);
        }
    }
    static class SimpleFactory{
        public Pizza createPizza(String type){
            Pizza pizza=null;
            if("cheese".equals(type)){
                pizza=new CheesePizza(type);
            }else if("clam".equals(type)){
                pizza=new ClamPizza(type);
            }
            return pizza;
        }
    }
}
