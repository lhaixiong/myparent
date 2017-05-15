package com.factory;

import java.util.HashMap;
import java.util.Map;

public class InstanceFactory {
    private Map<String,Car> map=new HashMap<String, Car>();
    public InstanceFactory(){
        map.put("audi",new Car("audi","ddd"));
        map.put("benz",new Car("benz","benz"));
        map.put("bmw",new Car("bmw","bmw"));
    }
    public Car getCar(String brand){
        return map.get(brand);
    }
}
