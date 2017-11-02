package com.lhx.blog.crawldemo.one;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 使用原始jdk的类 来抓取网页
 */
public class SimplePrimitiveCrawlJob extends AbstractJob {
    private CrawlMeta crawlMeta;
    private CrawlResult crawlResult;


    //    //可以用更成熟的http框架来取代jdk的访问方式，维护和使用更加简单
    @Override
    public void doFetchPage() throws Exception {
        URL url=new URL(crawlMeta.getUrl());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        BufferedReader in=null;
        StringBuilder respSb=new StringBuilder();


        try {
            // 设置通用的请求属性
            connection.setRequestProperty("accept","*/*");
            connection.setRequestProperty("connection","Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            // 建立实际连接
            connection.connect();

            Map<String, List<String>> headers = connection.getHeaderFields();
            //遍历所有的响应头字段
            for (String key : headers.keySet()) {
                System.out.println(key+"--->"+headers.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line="";
            while (StringUtils.isNotEmpty(line = in.readLine())){
                respSb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (in != null) {
                in.close();
            }
        }

        doParseHtml(respSb.toString());
    }

    public void doParseHtml(String html) throws Exception{
        Document document = Jsoup.parse(html);
        Map<String,List<String>> ruleMap=new HashMap<>(crawlMeta.getSelectedRules().size());
        for (String rule : crawlMeta.getSelectedRules()) {
            List<String> list=new ArrayList<>();
            for (Element element : document.select(rule)) {
                list.add(element.text());
            }
            ruleMap.put(rule,list);
        }

        this.crawlResult.setUrl(crawlMeta.getUrl());
        this.crawlResult.setHtmlDoc(document);
        this.crawlResult.setResult(ruleMap);
        /*StatusLine statusLine = response.getStatusLine();
        CrawlResult.Status status=new CrawlResult.Status(statusLine.getStatusCode(),statusLine.getReasonPhrase());
        crawlResult.setStatus(status);
        crawlResult.setUrl(crawlMeta.getUrl());*/
    }

    public SimplePrimitiveCrawlJob(CrawlMeta crawlMeta) {
        this.crawlMeta = crawlMeta;
        this.crawlResult=new CrawlResult();
    }

    public CrawlMeta getCrawlMeta() {
        return crawlMeta;
    }

    public void setCrawlMeta(CrawlMeta crawlMeta) {
        this.crawlMeta = crawlMeta;
    }

    public CrawlResult getCrawlResult() {
        return crawlResult;
    }

    public void setCrawlResult(CrawlResult crawlResult) {
        this.crawlResult = crawlResult;
    }
}
