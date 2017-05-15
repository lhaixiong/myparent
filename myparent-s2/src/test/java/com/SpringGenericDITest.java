package com;

import com.genericDI.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringGenericDITest {
    private ClassPathXmlApplicationContext ac;
    @Before
    public void init(){
        ac=new ClassPathXmlApplicationContext("applicationGenericDI.xml");
    }
    @After
    public void destroy(){

    }
    @Test
    public void testReference(){
        UserService userService= (UserService) ac.getBean("userService");
        userService.save();

    }
}
