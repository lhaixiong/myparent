package com.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        System.out.println("postProcessBeforeInitialization>>"+o+">>"+s);
        if(o instanceof Car){
            System.out.println("postProcessBeforeInitialization bbbcaaaaaaaaaaaar");
        }
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        System.out.println("postProcessAfterInitialization>>"+o+">>"+s);
        if(o instanceof Car){
            System.out.println("postProcessAfterInitialization aaaafcaaaaaaaaaaaar");
            Car car= (Car) o;
            car.setBrand("fuckbrand");
            return car;
        }
        return o;
    }
}
