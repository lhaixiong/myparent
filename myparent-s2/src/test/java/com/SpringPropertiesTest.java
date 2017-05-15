package com;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;

public class SpringPropertiesTest {
    private ApplicationContext ac;
    @Before
    public void init(){
        ac=new ClassPathXmlApplicationContext("applicationProperties.xml");
    }
    @After
    public void destroy(){

    }
    @Test
    public void testReference(){
        try {
            DataSource dataSource2= (DataSource) ac.getBean("dataSource2");
            System.out.println(dataSource2.getConnection());

            DataSource dataSource= (DataSource) ac.getBean("dataSource");
            System.out.println(dataSource.getConnection());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
