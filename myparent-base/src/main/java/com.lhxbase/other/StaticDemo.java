package com.lhxbase.other;

public class StaticDemo {
    public static void main(String[] args) {
        Obj obj=new Obj(33,"aa",44);
        System.out.println(obj);
        obj=new Obj(55,"bb",66);
        System.out.println(obj);
    }
}