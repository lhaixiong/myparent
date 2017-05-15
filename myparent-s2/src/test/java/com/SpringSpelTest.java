package com;

import com.spel.Address;
import com.spel.Car;
import com.spel.People;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringSpelTest {
    private ClassPathXmlApplicationContext ac;
    @Before
    public void init(){
        ac=new ClassPathXmlApplicationContext("applicationSpel.xml");
    }
    @After
    public void destroy(){

    }
    @Test
    public void testReference(){
        People people= (People) ac.getBean("people");
        System.out.println(people);

        Address address= (Address) ac.getBean("address");
        System.out.println(address);

        Car car= (Car) ac.getBean("car");
        System.out.println(car);
        ac.close();
    }

}
