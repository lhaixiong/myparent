package com;

import com.factory.Car;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringFactoryTest {
    private ApplicationContext ac;
    @Before
    public void init(){
        ac=new ClassPathXmlApplicationContext("applicationFactory.xml");
    }
    @After
    public void destroy(){

    }
    @Test
    public void testReference(){
        Car car3= (Car) ac.getBean("car3");
        System.out.println(car3);

        Car car2= (Car) ac.getBean("car2");
        System.out.println(car2);

        Car car1= (Car) ac.getBean("car1");
        System.out.println(car1);
    }

}
