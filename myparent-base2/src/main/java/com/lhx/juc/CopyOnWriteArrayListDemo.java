package com.lhx.juc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CopyOnWriteArrayList/CopyOnWriteArraySet : “写入并复制”
 * 注意：添加操作多时，效率低，因为每次添加时都会进行复制，开销非常的大。并发迭代操作多时可以选择。
 */
public class CopyOnWriteArrayListDemo {
    public static void main(String[] args) {
        ThreadDemo td=new ThreadDemo();
        for (int i = 0; i < 10; i++) {
            new Thread(td).start();
        }
    }
    private static class ThreadDemo implements Runnable{
//        private static List<String> list= Collections.synchronizedList(new ArrayList<String>());
        private static List<String> list= new CopyOnWriteArrayList<>();
        static {
            list.add("aa");
            list.add("bb");
            list.add("cc");
        }
        @Override
        public void run() {
            //这样迭代会无限循环，why?
//            for (int i = 0; i < list.size(); i++) {
//                System.out.println(list.get(i));
//                list.add("AA");
//                System.out.println("-----------");
//            }
            for (String s : list) {
                System.out.println(s);
                list.add("BB");
                System.out.println("---------");
            }
        }
    }
}
