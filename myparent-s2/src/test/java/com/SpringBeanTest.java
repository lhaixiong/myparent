package com;

import com.beans.Car;
import com.beans.HelloWorld;
import com.beans.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringBeanTest {
    private ApplicationContext ac;
    @Before
    public void init(){
        ac=new ClassPathXmlApplicationContext("applicationBean.xml");
    }
    @After
    public void destroy(){

    }
    @Test
    public void testReference(){
        Person person3 = (Person) ac.getBean("person3");
        System.out.println(person3);

        Person person2 = (Person) ac.getBean("person2");
        System.out.println(person2);

        Person person = (Person) ac.getBean("person");
        System.out.println(person);
    }
    @Test
    public void testConstruct(){
        Car car= (Car) ac.getBean("car2");
        System.out.println(car);
    }
    @Test
    public void testHelloWorld(){
        HelloWorld helloWorld= (HelloWorld) ac.getBean("helloWorld");
        System.out.println(helloWorld);
//        System.out.println(helloWorld.getName());
//        helloWorld.sayHello();
//        Assert.assertNotNull(helloWorld.getAge());
//        Assert.assertEquals(0,helloWorld.getAge());
    }
}
