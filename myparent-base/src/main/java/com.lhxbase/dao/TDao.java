package com.lhxbase.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

public class TDao<T> {
    private Class<T> clazz;
    public TDao(){
//        System.out.println(clazz);//null
//        System.out.println("TDao constructing...");//TDao constructing...
//        System.out.println(this);//com.lhxbase.dao.PersonDao@9da77f5
//        System.out.println(this.getClass());//class com.lhxbase.dao.PersonDao

//        //获取TDao子类的父类
////        System.out.println(this.getClass().getSuperclass());//class com.lhxbase.dao.TDao
//
        //获取TDao子类带泛型参数的父类
        Type type = this.getClass().getGenericSuperclass();
//        System.out.println(type);//com.lhxbase.dao.TDao<com.lhxbase.entity.Person>

        //获取具体的泛型参数
        if(type instanceof ParameterizedType){
            ParameterizedType parameterizedType= (ParameterizedType) type;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            if(actualTypeArguments!=null&&actualTypeArguments.length>0){
//                System.out.println(Arrays.asList(actualTypeArguments));//[class com.lhxbase.entity.Person]

                Type arg=actualTypeArguments[0];
//                System.out.println(arg.getClass());//class java.lang.Class
//                System.out.println(arg);//class com.lhxbase.entity.Person
                if(arg instanceof Class){
                    clazz= (Class<T>) arg;
//                    System.out.println(clazz);//class com.lhxbase.entity.Person
                }
            }
        }
    }
    public T get(Integer id){
        T result=null;
        return result;
    }
    public void save(T entity){

    }
}
