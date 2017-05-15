package com;

import com.annotation.TestObject;
import com.annotation.controller.UserController;
import com.annotation.repository.UserRopository;
import com.annotation.service.UserService;
import com.spel.Address;
import com.spel.Car;
import com.spel.People;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringAnnotationTest {
    private ClassPathXmlApplicationContext ac;
    @Before
    public void init(){
        ac=new ClassPathXmlApplicationContext("applicationAnnotation.xml");
    }
    @After
    public void destroy(){

    }
    @Test
    public void testReference(){

        UserController userController= (UserController) ac.getBean("userController");
        userController.save();

//        TestObject testObject= (TestObject) ac.getBean("testObject");
//        System.out.println(testObject);
//
//        UserController userController= (UserController) ac.getBean("userController");
//        System.out.println(userController);
//
//        UserService userService= (UserService) ac.getBean("userService");
//        System.out.println(userService);
//
//        UserRopository userRopository= (UserRopository) ac.getBean("userRepository");
//        System.out.println(userRopository);

    }

}
