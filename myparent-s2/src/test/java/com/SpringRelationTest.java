package com;

import com.beans.Car;
import com.collections.DataSource;
import com.collections.NewPerson;
import com.collections.Person;
import com.relations.Address;
import com.relations.People;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringRelationTest {
    private ApplicationContext ac;
    @Before
    public void init(){
        ac=new ClassPathXmlApplicationContext("applicationRelation.xml");
    }
    @After
    public void destroy(){

    }
    @Test
    public void testReference(){
        Car car= (Car) ac.getBean("car");
        System.out.println(car);

        People people= (People) ac.getBean("people");
        System.out.println(people);

//        Address address1= (Address) ac.getBean("address1");
//        System.out.println(address1);

        Address address2= (Address) ac.getBean("address2");
        System.out.println(address2);
    }

}
