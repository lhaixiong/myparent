package com.lhx.mvc.exception;

public class BeanNotFoundException extends RuntimeException {
    public BeanNotFoundException(String msg){
        super(msg);
    }
}
