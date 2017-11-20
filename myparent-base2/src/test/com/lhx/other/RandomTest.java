package com.lhx.other;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RandomTest {
    /**
     * 测试集合的add方法和addAll方法
     * @throws Exception
     */
    @Test
    public void testAddAllMethod() throws Exception{
        Info info1=new Info("a",1);
        Info info2=new Info("b",2);
        Info info3=new Info("c",3);

        List<Info> list1=new ArrayList<>();
        list1.add(info1);
        list1.add(info2);
        list1.add(info3);

        //info1.setAge(11);
        //System.out.println("info1:" + info1);
        //System.out.println("list1:"+list1);

        //List<Info> list2=new ArrayList<>();
        //list2.addAll(list1);
        //info1.setAge(11);
        //System.out.println("info1:" + info1);
        //System.out.println("list1:"+list1);
        //System.out.println("list2:"+list2);

        List<Info> list2=new ArrayList<>(list1);
        info1.setAge(11);
        System.out.println("info1:" + info1);
        System.out.println("list1:"+list1);
        System.out.println("list2:"+list2);
    }
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
    static class Info{
        private String name;
        private int age;

        public Info(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Info{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
