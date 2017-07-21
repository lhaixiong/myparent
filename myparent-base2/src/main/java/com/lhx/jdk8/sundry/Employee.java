package com.lhx.jdk8.sundry;

public class Employee {
    private String name;
    private int age;
    private double salory;
    private Status status;

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

    public Employee(String name, int age, double salory, Status status) {
        this.name = name;
        this.age = age;
        this.salory = salory;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", salory=" + salory +
                ", status=" + status +
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (age != employee.age) return false;
        if (Double.compare(employee.salory, salory) != 0) return false;
        if (name != null ? !name.equals(employee.name) : employee.name != null) return false;
        if (status != employee.status) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        result = 31 * result + age;
        temp = Double.doubleToLongBits(salory);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    public enum Status{
        FREE,
        BUSY,
        VOCATION;
    }
}
