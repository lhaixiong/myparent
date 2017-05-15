package com.lhxbase.dao;

public class Dao<T> {
    public <E> E getProperty2(Integer id){
        E property=null;
        return property;
    }

    public Object getProperty(Integer id){
        Object property=null;
        return property;
    }
    public T get(Integer id){
        T result=null;
        return result;
    }
    public void save(T entity){
        System.out.println(" Dao<T> save>>>>"+entity);
    }
}
