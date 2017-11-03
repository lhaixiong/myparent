package com.lhx.mvc.exception;

public class BeanAlreadyDefinedException extends RuntimeException {
    public BeanAlreadyDefinedException(String msg){
        super(msg);
    }
}
