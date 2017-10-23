package com.lhx.other;

import org.junit.Test;

public class RandomTest {
    @Test
    public void test01() throws Exception{
//        String temp="aA";//utf-8 编码一个字母字符一个字节
        String temp="梁海雄";//uft-8编码 一个汉字字符三个字节
        byte[] bytes = temp.getBytes();
        System.out.println(bytes.length);
        for (byte aByte : bytes) {
            System.out.println(aByte);
        }
    }
}
