package com.lhx.JUC;

import org.junit.Test;

public class JucTest {
    @Test
    public void test01(){
        int i=10;
        i=i++;
        System.out.println(i);
    }
}
