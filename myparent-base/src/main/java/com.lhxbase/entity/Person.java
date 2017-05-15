package com.lhxbase.entity;

import com.lhxbase.annotation.AgeValidator;

public class Person {
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setName(String name,Integer age) {
        System.out.println("name>>"+name);
        System.out.println("age>>"+age);
    }


    public Integer getAge() {
        return age;
    }

    @AgeValidator(min = 18,max=50)
    public void setAge(Integer age) {
        System.out.println("setAge>>>>"+age);
        this.age = age;
    }

    private void test(){}

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Person() {
    }
    private String personMethod(Integer pp){
        System.out.println("private String personMethod(Integer pp)>>>"+pp);
        return "PersonMethod";
    }
}
