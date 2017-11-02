package com.lhx.blog.crawldemo.one;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * http工具
 */
public class MyHttpUtils {
    public static HttpResponse request(CrawlMeta crawlMeta,MyReq req) throws Exception{
        switch (req.getRequestMethod()){
            case GET:
                return doGet(crawlMeta,req);
            case POST:
                return doPost(crawlMeta,req);
            default:return null;
        }
    }

    private static HttpResponse doGet(CrawlMeta crawlMeta, MyReq req) throws Exception{
//        HttpClient httpClient = HttpClients.createDefault();
        SSLContextBuilder builder=new SSLContextBuilder();
        //SSLContext的作用是？
//        全部信任 不做身份鉴定
        builder.loadTrustMaterial(null,(x509Certificates, s) -> true);//这是什么语法？ lamda？
        HttpClient httpClient = HttpClientBuilder.create().setSSLContext(builder.build()).build();

        //设置请求参数
        StringBuilder queryString=new StringBuilder(crawlMeta.getUrl()).append("?");
        for (Map.Entry<String, String> entry : req.getRequestParams().entrySet()) {
            queryString.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        HttpGet httpGet=new HttpGet(queryString.substring(0,queryString.length()-1));// 过滤掉最后一个无效字符

        //设置请求头
        for (Map.Entry<String, String> entry : req.getRequestHeaders().entrySet()) {
            httpGet.addHeader(entry.getKey(),entry.getValue());
        }
        //执行请求
        return httpClient.execute(httpGet);
    }

    private static HttpResponse doPost(CrawlMeta crawlMeta, MyReq req) throws Exception{
        //        HttpClient httpClient = HttpClients.createDefault();
        SSLContextBuilder builder=new SSLContextBuilder();
        builder.loadTrustMaterial(null,(x509Certificates, s) -> true);
        HttpClient httpClient = HttpClientBuilder.create().setSSLContext(builder.build()).build();

        HttpPost httpPost=new HttpPost(crawlMeta.getUrl());
        //设置post方法的请求参数
        List<NameValuePair> pairs=new ArrayList<>();
        for (Map.Entry<String, String> entry : req.getRequestParams().entrySet()) {
            NameValuePair pair=new BasicNameValuePair(entry.getKey(),entry.getValue());
            pairs.add(pair);
        }

        //设置请求头
        for (Map.Entry<String, String> entry : req.getRequestHeaders().entrySet()) {
            httpPost.addHeader(entry.getKey(),entry.getValue());
        }
        //执行请求
        return httpClient.execute(httpPost);
    }

}
