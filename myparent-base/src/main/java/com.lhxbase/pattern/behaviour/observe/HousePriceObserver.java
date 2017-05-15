package com.lhxbase.pattern.behaviour.observe;

import java.util.Observable;
import java.util.Observer;

public class HousePriceObserver implements Observer {
    private String name;

    public HousePriceObserver(String name) {
        this.name = name;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof House){
            House house= (House) o;
            if(arg instanceof String){
                System.out.println(name+"观察到房子"+house.getName()+"的价格变为："+house.getPrice()+","+arg);
            }
        }
    }
}
