package com.lhx.jdk8.sundry;

@FunctionalInterface
public interface MyPredicate<T> {
    public boolean test(T t);
    //public boolean test2(T t);
}
