package com;

import com.collections.DataSource;
import com.collections.NewPerson;
import com.collections.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringCollectionTest {
    private ApplicationContext ac;
    @Before
    public void init(){
        ac=new ClassPathXmlApplicationContext("applicationCollection.xml");
    }
    @After
    public void destroy(){

    }
    @Test
    public void testReference(){
        Person person5 = (Person) ac.getBean("person5");
        System.out.println(person5);

        DataSource dataSource= (DataSource) ac.getBean("dataSource");
        System.out.println(dataSource);

        NewPerson newPerson = (NewPerson) ac.getBean("newPerson");
        System.out.println(newPerson);

        Person person3 = (Person) ac.getBean("person3");
        System.out.println(person3);

    }

}
