package com.lhx.jdk8.sundry;

public class Employee {
    private String name;
    private int age;
    private double salory;

    public Employee() {
    }

    public Employee(String name, int age, double salory) {
        this.name = name;
        this.age = age;
        this.salory = salory;
    }
    public Employee( int age, double salory) {
        this.age = age;
        this.salory = salory;
    }
    public Employee(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", salory=" + salory +
                '}';
    }

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

    public double getSalory() {
        return salory;
    }

    public void setSalory(double salory) {
        this.salory = salory;
    }
}
