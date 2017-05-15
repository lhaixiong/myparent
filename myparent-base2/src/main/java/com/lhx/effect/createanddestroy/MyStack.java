package com.lhx.effect.createanddestroy;


import java.util.Arrays;
import java.util.EmptyStackException;

//会出现内存泄漏memory leak
public class MyStack {
    private Object[] elements;
    private int size=0;
    private static final int DEAFULT_INITIAL_CAPACITY=16;
    public MyStack(){
        elements=new Object[DEAFULT_INITIAL_CAPACITY];
    }
    public synchronized void push(Object e){
        ensureCapacity();
        elements[size++]=e;
    }
    public synchronized Object pop(){
        if(size==0){
            throw new EmptyStackException();
        }
//        return elements[--size];//内存泄漏，
        Object o=elements[--size];
        elements[size]=null;//解除过期引用
        return o;
    }
    private void ensureCapacity() {
        if(elements.length==size){
            elements= Arrays.copyOf(elements,2*size+1);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyStack myStack = (MyStack) o;

        if (size != myStack.size) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(elements, myStack.elements)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = elements != null ? Arrays.hashCode(elements) : 0;
        result = 31 * result + size;
        return result;
    }
}
