package com.lhx;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

public class A {
    public static void main(String[] args) {
        Map<String, String> envs = System.getenv();
        for (String key : envs.keySet()) {
            System.out.println(key+":"+envs.get(key));
        }

        System.out.println("------------------System.getProperties()");
        Properties ps = System.getProperties();
        Enumeration<Object> keys = ps.keys();
        if (keys.hasMoreElements()) {
            System.out.println(keys.nextElement());
        }
        System.out.println(System.getProperty("user.dir"));
    }
}
