package com.lhx.other;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RandomTest {
    private static int N=8;
    public static void main(String[] args) {
        int[] a = new int[N];
        Scanner s = new Scanner(System.in);
        int index1 = 0, index2 = 0;

        System.out.println("please input numbers");
        for (int i = 0; i < N; i++) {
            a[i] = s.nextInt();
            System.out.print(a[i] + " ");
        }

        int max = a[0], min = a[0];
        for (int i = 0; i < a.length; i++) {
            if (a[i] > max) {
                max = a[i];
                index1 = i;
            }
            if (a[i] < min) {
                min = a[i];
                index2 = i;
            }
        }

        if (index1 != 0) {
            int temp = a[0];
            a[0] = a[index1];
            a[index1] = temp;
        }

        if (index2 != a.length - 1) {
            int temp = a[a.length - 1];
            a[a.length - 1] = a[index2];
            a[index2] = temp;
        }
        System.out.println("after swop:");
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
    }
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
