package com.lhx.headfirstjava.structuretype;

public class SimpleFactoryPizzaDemo {
    public static void main(String[] args) {
        PizzaStore pizzaStore=new PizzaStore(new SimpleFactory());
        pizzaStore.orderPizza("cheese");
        pizzaStore.orderPizza("clam");
    }

    static class PizzaStore{
        private SimpleFactory factory;
        public PizzaStore(SimpleFactory factory){
            this.factory=factory;
        }
        public Pizza orderPizza(String type){
            Pizza pizza=factory.createPizza(type);
            pizza.prepare();
            pizza.bake();
            pizza.cut();
            pizza.box();
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
