package com.lhx.mvc.test;

import com.lhx.mvc.ioc.ApplicationContext;

public class Test {
    public static void main(String[] args) {
        ApplicationContext ctx=new ApplicationContext();
        AspectDemoService aspectDemoService= (AspectDemoService) ctx.getBean("aspectDemoService");
        System.out.println("------------\n");
        aspectDemoService.print("aspectdemoservice------");
        System.out.println("------------\n");


        System.out.println("------------\n");
        System.out.println(aspectDemoService.add(10, 20));
        System.out.println("-------------\n");


        //User userProxy = MyCglibProxyFactory.getProxy(User.class);
        //System.out.println(userProxy.getAge());

        //String pkg="com.lhx.mvc.test";
        //BeanFactory beanFactory=BeanFactory.getInstance();
        //
        //beanFactory.init(pkg);
        //for (Class<?> clz : beanFactory.getBeanClasses()) {
        //    System.out.println(beanFactory.getBeanOfType(clz));
        //}


        //System.out.println(beanFactory.getBeanOfName("aa"));
        //String pkg="org.slf4j";
        //Set<Class<?>> clzSet = PkgUtil.getClzFromPkg(pkg);
        //clzSet.stream()
        //        .forEach(System.out::println);
    }
}
