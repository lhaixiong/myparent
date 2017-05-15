package com;

import com.aop.MathmeticCaculator;
import com.aop.MathmeticCaculatorLoggingProxy;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringAopTest {
    private ClassPathXmlApplicationContext ac;
    @Before
    public void init(){
        ac=new ClassPathXmlApplicationContext("applicationAop.xml");
    }
    @After
    public void destroy(){

    }
    @Test
    public void testAspect(){
        MathmeticCaculator mathmeticCaculator= (MathmeticCaculator)  ac.getBean("mathmeticCaculatorImp");
        System.out.println(mathmeticCaculator);
        System.out.println(mathmeticCaculator.getClass().getName());
        int result=mathmeticCaculator.add(6,2);
        System.out.println("add result>>>"+result);

        result=mathmeticCaculator.sub(6, 2);
        System.out.println("sub result>>>"+result);

        result=mathmeticCaculator.multi(6, 2);
        System.out.println("multi result>>>"+result);

        result=mathmeticCaculator.div(6, 0);
        System.out.println("div result>>>"+result);
    }
    @Test
    public void testProxy(){
        MathmeticCaculator mathmeticCaculator= (MathmeticCaculator)  ac.getBean("mathmeticCaculatorImp");
        MathmeticCaculatorLoggingProxy proxy= (MathmeticCaculatorLoggingProxy) ac.getBean("mathmeticCaculatorLoggingProxy");
        proxy.setTarget(mathmeticCaculator);
        int result=100;
        result=proxy.getProxy().add(8,4);
        System.out.println("add result>>>" + result);
        result=proxy.getProxy().sub(8, 4);
        System.out.println("sub result>>>" + result);
        result=proxy.getProxy().multi(8, 4);
        System.out.println("multi result>>>"+result);
        result=proxy.getProxy().div(8, 4);
        System.out.println("div result>>>"+result);
    }

    @Test
    public void testReference(){
        MathmeticCaculator mathmeticCaculator= (MathmeticCaculator)  ac.getBean("mathmeticCaculatorImp");
        int result=mathmeticCaculator.add(6,2);
        System.out.println("add result>>>"+result);

        result=mathmeticCaculator.sub(6, 2);
        System.out.println("sub result>>>"+result);

        result=mathmeticCaculator.multi(6, 2);
        System.out.println("multi result>>>"+result);

        result=mathmeticCaculator.div(6, 2);
        System.out.println("div result>>>"+result);

    }

}
