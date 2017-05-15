package com.beans;

public class HelloWorld {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public HelloWorld() {
        System.out.println("HelloWorld constructed");
    }
    public void sayHello(){
        System.out.println(this.getName());
    }

    @Override
    public String toString() {
        return "HelloWorld{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
