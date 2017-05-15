package com.lhxbase.pattern.behaviour.observe;

import java.util.Observable;

public class House extends Observable {
    private float price;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public House(float price, String name) {
        this.price = price;
        this.name = name;
    }

    public void setPrice(float price) {
        this.price = price;
        super.setChanged();//设置变化点
        super.notifyObservers("房价又涨啦，大家不要买房啦");//通知观察者
//        super.notifyObservers(price);//通知观察者
//        super.notifyObservers(true);//通知观察者
    }

    @Override
    public String toString() {
        return "House{" +
                "price=" + price +
                ", name='" + name + '\'' +
                '}';
    }
}
