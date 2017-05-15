package com;

import com.lifecycle.Car;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringLifecycleTest {
    private ClassPathXmlApplicationContext ac;
    @Before
    public void init(){
        ac=new ClassPathXmlApplicationContext("applicationLifecycle.xml");
    }
    @After
    public void destroy(){

    }
    @Test
    public void testReference(){
        Car car= (Car) ac.getBean("car");
        System.out.println(car);
        ac.close();
    }

}
