package com.lhxbase.entity;

public class Student extends Person {
    private String no;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Student() {
    }

    public Student(String name, Integer age, String no) {
        super(name, age);
        this.no = no;
    }
    private String studentMethod(Integer ss){
        System.out.println("private String studentMethod(Integer ss)>>>"+ss);
        return "studentMethod";
    }
}
