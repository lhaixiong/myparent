package com.lhxbase.other;

public class Obj {
    private static int a=1;
    private static String name;
    private int c;

    static {
        System.out.println("before static block a="+a+",name="+name);
        a=2;
        name="lhx";
        System.out.println("after static block a="+a+",name="+name);
    }
    public Obj(int c){
        this.c=c;
    }
    public Obj(int a, String name, int c){
        this.a=a;
        this.name=name;
        this.c=c;
    }

    @Override
    public String toString() {
        return "a="+a+",name="+name+",c="+c;
    }
}
