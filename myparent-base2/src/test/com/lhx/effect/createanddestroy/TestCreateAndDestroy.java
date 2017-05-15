package com.lhx.effect.createanddestroy;


import org.junit.Test;

import java.math.BigInteger;
import java.util.*;

public class TestCreateAndDestroy {
    private final Integer MYINT=3;
    @Test
    public void testServiceFactory(){
        ServiceFactory.registerDefaultProvider(new LHXProviderImpl());
        ServiceFactory.registerProvider("lhx", new LHXProviderImpl());
        ServiceFactory.registerProvider("lhj", new LHJProviderImpl());
        System.out.println(ServiceFactory.newInstance());
        System.out.println(ServiceFactory.newInstance("lhj"));
        System.out.println(ServiceFactory.newInstance("lhp"));
    }
    @Test
    public void test01(){


        long start1=System.currentTimeMillis();
        Long sum1=0l;
        for(Long i=0l;i<Integer.MAX_VALUE;i++){
            sum1+=i;
        }
        long end1=System.currentTimeMillis();
        System.out.println(sum1+"  Long花费:"+(end1-start1));

        long start=System.currentTimeMillis();
        long sum=0l;
        for(long i=0l;i<Integer.MAX_VALUE;i++){
            sum+=i;
        }
        long end=System.currentTimeMillis();
        System.out.println(sum+"  long花费:"+(end-start));

        Boolean.valueOf(true);
        BigInteger.probablePrime(1, new Random());
        Map<String,List<Integer>> map=new HashMap<>();
        List<Integer> list=new ArrayList<>();
    }
}
