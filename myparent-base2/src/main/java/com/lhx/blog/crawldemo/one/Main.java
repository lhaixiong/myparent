package com.lhx.blog.crawldemo.one;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws Exception{
//        String url = "https://my.oschina.net/u/566591/blog/1047233";  //此url不允许抓取，相应502错
//        String url = "http://www.baidu.com";//百度url可以抓取，但响应html的body为空
        String url = "http://www.runoob.com/jsref/jsref-obj-array.html";//菜鸟教程
        Set<String> selectRule = new HashSet<>();
//        selectRule.add("div[class=title]"); // 博客标题
//        selectRule.add("div[class=blog-body]"); // 博客正文
        selectRule.add("a"); //这里提供css选择器 生效
//        selectRule.add("#footer"); //这里提供css选择器 ,为毛不生效
//        selectRule.add(".fixed-btn"); //这里提供css选择器  为毛不生效
//        selectRule.add("div"); //这里提供css选择器 生效

        CrawlMeta crawlMeta = new CrawlMeta();
        crawlMeta.setUrl(url); // 设置爬取的网址
        crawlMeta.setSelectedRules(selectRule); // 设置抓去的内容

//            SimplePrimitiveCrawlJob job=new SimplePrimitiveCrawlJob(crawlMeta);
        SimpleCrawlJob job=new SimpleCrawlJob(crawlMeta);
        Thread thread=new Thread(job,"SimpleCrawlJob");
        thread.start();
        thread.join();

        //输出结果
        CrawlResult crawlResult = job.getCrawlResult();
        System.out.println(crawlResult);
    }
}
