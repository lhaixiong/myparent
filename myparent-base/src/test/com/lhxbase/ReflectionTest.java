package com.lhxbase;

import com.lhxbase.annotation.AgeValidator;
import com.lhxbase.dao.EmployeeDao;
import com.lhxbase.dao.PersonDao;
import com.lhxbase.dao.TDao;
import com.lhxbase.entity.Person;
import com.lhxbase.entity.Student;
import com.lhxbase.reflection.ReflectionUtil;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Date;

public class ReflectionTest {

//    @Test
//    public void testInstanceofClass(){
//        Person person=new Person();
//        System.out.println(( Person   instanceof Class));
//    }
    @Test
    public void testGetSupperClassGenericType(){
        EmployeeDao dao=new EmployeeDao();
        System.out.println(ReflectionUtil.getSupperClassGenericType(dao.getClass(), 0));
        System.out.println(ReflectionUtil.getSupperClassGenericType(dao.getClass(), 1));
        System.out.println(ReflectionUtil.getSupperClassGenericType(dao.getClass(), 2));
    }
    @Test
    public void testGenericAndReflection(){
//        null
//        TDao constructing...
//        com.lhxbase.dao.TDao@9da77f5
//        class com.lhxbase.dao.TDao
//       TDao<Person> dao=new TDao<Person>();


//        null
//        TDao constructing...
//        com.lhxbase.dao.PersonDao@5df23c6d
//        class com.lhxbase.dao.PersonDao
//        TDao<Person> dao=new PersonDao();

//        null
//        TDao constructing...
//        com.lhxbase.dao.PersonDao@5df23c6d
//        class com.lhxbase.dao.PersonDao
        PersonDao dao=new PersonDao();

        Person person=new Person("lll",33);
        dao.save(person);
        dao.get(3);
    }
    @Test
    public void testAnnotation() throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Class clazz=Person.class;
        Method method=clazz.getDeclaredMethod("setAge",Integer.class);
        Object object=clazz.newInstance();

        int age=44;

        Annotation annotation=method.getAnnotation(AgeValidator.class);
        if(annotation!=null){
            if(annotation instanceof AgeValidator){
                AgeValidator ageValidator= (AgeValidator) annotation;
                if(age<ageValidator.min()||age>ageValidator.max()){
                    throw new RuntimeException("年龄非法");
                }
                method.invoke(object,age);
            }
        }
        System.out.println(object);
    }
    @Test
    public void testConstructure() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class clazz=Person.class;
        Constructor[] constructors = clazz.getConstructors();
        for (Constructor item:constructors){
            System.out.println(item);
        }

        Constructor constructor=clazz.getConstructor(String.class,Integer.class);
        System.out.println(constructor);

        System.out.println(constructor.newInstance("llll",22));

        constructor=clazz.getConstructor();
        System.out.println(constructor);

        System.out.println(constructor.newInstance());
    }
    @Test
    public void testField() throws  Exception{
        Class clazz=Person.class;
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field:declaredFields){
            System.out.println(field.getName());
        }

        Field field=clazz.getDeclaredField("name");
        System.out.println(field);

        Person person=new Person("hhh",22);
        field.setAccessible(true);
        //获取指定对象的field值
        field.get(person);
        System.out.println("befor person>>>"+person);
        //设置指定对象的field值
        field.set(person,"jjjjjj");
        System.out.println("after person>>>"+person);

    }


    @Test
    public void testInvokePrivateMethod() throws Exception {
        Class clazz= Student.class;
        Method method=clazz.getDeclaredMethod("studentMethod",Integer.class);

        //访问私有方法！！！！！！！！！！！！！！！！！
        method.setAccessible(true);
        method.invoke(clazz.newInstance(),22);
    }
    @Test
    public void testGetSuperClass(){
        Class clazz=Student.class;
        System.out.println(clazz);
        System.out.println("getSuperclass>>>"+clazz.getSuperclass());
    }

    @Test
    public void testGetMehod() throws InvocationTargetException, IllegalAccessException {
//        Method method=ReflectionUtil.getMethod(Student.class, "personMethod1", Integer.class);//没有此方法
        Method method= ReflectionUtil.getMethod(Student.class, "personMethod", Integer.class);
        System.out.println(method);
        if(method!=null){
            method.invoke(new Student(),1);
        }

//        Method method=ReflectionUtil.getMethod(String.class,"split",String.class);
//        System.out.println(method);
//
//        method=ReflectionUtil.getMethod(new Date(),"setTime",long.class);
//        System.out.println(method);
//        method=ReflectionUtil.getMethod("java.util.Date","setTime",long.class);
//        System.out.println(method);
    }
    @Test
    public void testInvoke2(){
        ReflectionUtil.invokeMethod(new Student(), "setName", "lhxxx", new Integer(3));
        ReflectionUtil.invokeMethod("com.lhxbase.entity.Student", "personMethod", 77);
        ReflectionUtil.invokeMethod("com.lhxbase.entity.Student", "studentMethod", 4);
    }
    @Test
    public void testInvoke(){
        ReflectionUtil.invokeMethod(new Person(), "setName", "lhxxx", new Integer(3));
        ReflectionUtil.invokeMethod("com.lhxbase.entity.Person", "setName", "hhhhhh", 4);
    }
    @Test
    public void testMethod() throws Exception {
        Class clazz=Person.class;

        //不能获取private私有方法,能获取父类非private方法
        Method[] methods=clazz.getMethods();
        for (Method method:methods){
            System.out.println(">>>"+method.getName());
        }

        //获取当前类声明的方法，包括private私有方法，但不包括父类
        methods=clazz.getDeclaredMethods();
        for (Method method:methods){
            System.out.println("******"+method.getName());
        }

        //获取特定方法
        Method method=clazz.getDeclaredMethod("setName", String.class);
        System.out.println(method);
        method=clazz.getDeclaredMethod("setName",String.class,Integer.class);
        System.out.println(method);

        //执行方法
        method.invoke(clazz.newInstance(),"lhx",33);

    }

    @Test
    public void testLoader() throws FileNotFoundException {
        ClassLoader classLoader=ClassLoader.getSystemClassLoader();
        System.out.println("系统类加载器>>"+classLoader);

        classLoader=classLoader.getParent();
        System.out.println("扩展类加载器>>"+classLoader);

        classLoader=classLoader.getParent();
        System.out.println("引导类加载器>>"+classLoader);

        //检查当前类有哪个类加载器加载
        classLoader=ReflectionTest.class.getClassLoader();
        System.out.println("检查当前类有哪个类加载器加载>>"+classLoader);

        //检查Object类有哪个类加载器加载
        classLoader=Object.class.getClassLoader();
        System.out.println("检查Object类有哪个类加载器加载>>"+classLoader);

        //类加载器常用方法，获取类路径下（maven项目中的resources）输入流
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("test.properties");//src目录下(也就是resources)
        System.out.println(inputStream);

//        InputStream inputStream = new FileInputStream("test22.properties");//根目录下
//        System.out.println(inputStream);

//        InputStream inputStream = new FileInputStream("test33.properties");//src目录下，找不到
//        System.out.println(inputStream);

    }

    @Test
    public void testClass() throws Exception {
        Class clazz=null;
        clazz=Class.forName("com.lhxbase.entity.Person");
//        clazz= Person.class;
//        clazz=new Person().getClass();
        Person person=(Person)clazz.newInstance();
        Field[] fields=clazz.getDeclaredFields();
        System.out.println(person);
    }
}
