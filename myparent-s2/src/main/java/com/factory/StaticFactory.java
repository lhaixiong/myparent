package com.factory;

import java.util.HashMap;
import java.util.Map;

public class StaticFactory {
    private static Map<String,Car> map=new HashMap<String, Car>();
    static {
        map.put("audi",new Car("audi","beijing"));
        map.put("benz",new Car("benz","shanghai"));
        map.put("bmw",new Car("bmw","shenzhen"));
    }
    public static Car getCar(String brand){
        return map.get(brand);
    }
}
