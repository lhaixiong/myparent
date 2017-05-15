package com.lhxbase;

import com.lhxbase.dao.Dao;
import com.lhxbase.entity.Person;
import com.lhxbase.entity.Student;
import com.lhxbase.enumeration.Season;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BaseTest {
    @Test
    public void testEnumeration(){
        System.out.println(Season.SPRING);
        System.out.println(Season.valueOf("SUMMER"));
    }
    @Test
    public void testGenericMethod(){
        Dao<Person> dao=new Dao<Person>();
        Person person = dao.get(1);

        String name=person.getName();
        Integer age=person.getAge();

        String name2= (String) dao.getProperty(1);
        Integer age2= (Integer) dao.getProperty(1);

        String name3= dao.getProperty2(1);
        Integer age3= dao.getProperty2(1);
    }
    @Test
    public void testExtend(){
        Object[] objects=new Object[10];
        Person[] persons=new Person[10];
        objects=persons;

        List<Object[]> objList=new ArrayList<Object[]>();
        List<Person[]> perList=new ArrayList<Person[]>();
//        objList=perList;//这里不行，编译时错误

        List<Person> personList=new ArrayList<Person>();
        List<Student> studentList=new ArrayList<Student>();
        printPerson(personList);
//        printPerson(studentList);//这里不行，编译时错误

        printPerson2(personList);
        printPerson2(studentList);

        printCollection(personList);
    }

    /**
     * 纯Collection<? >不安全，应结合extend/super使用
     * @param collection
     */
    public void printCollection(Collection<? super Person> collection){
        collection.add(null);
        collection.add(new Student());
    }
    /**
     * 纯Collection<? >不安全，应结合extend/super使用
     * 但是将任意元素加入到其中不是类型安全的：
     Collection<?> c = new ArrayList<String>();
     c.add(new Object()); // 编译时错误
     因为我们不知道c 的元素类型，我们不能向其中添加对象。
     add 方法有类型参数E 作为集合的元素类型。我们传给add 的任何参
     数都必须是一个未知类型的子类。因为我们不知道那是什么类型，所以我
     们无法传任何东西进去。唯一的例外是null，它是所有类型的成员。

     另一方面，我们可以调用get()方法并使用其返回值。返回值是一个未
     知的类型，但是我们知道，它总是一个Object
     * @param collection
     */
    public void printCollection2(Collection<?> collection){
        collection.add(null);//null除外，因为null是任何类型
//        collection.add(new Student());//这里不行，编译时错误
//        collection.add("");//这里不行，编译时错误
//        collection.add(1);//这里不行，编译时错误
    }
    //只要存在通配符，写入（添加）都是非法的，道理跟Collection<?>添加一样，取出可以
    public void addPerson(List<? extends Person> list){
//        list.add(0,new Student());////这里不行，编译时错误
    }
    public void printPerson2(List<? extends Person> list){
        for(Person person:list){
            System.out.println(person);
        }
    }
    public void printPerson(List<Person> list){
        for(Person person:list){
            System.out.println(person);
        }
    }
    @Test
    public void test01(){
        Dao<Person> dao=new Dao<Person>();
        Person person=new Person("lhx",22);
        dao.save(person);
    }
}
