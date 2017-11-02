package com.lhx.blog.crawldemo.one;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleCrawlJob extends AbstractJob {
    private CrawlMeta crawlMeta;
    private CrawlResult crawlResult;

    @Override
    public void doFetchPage() throws Exception {
        MyReq req=new MyReq();
        HttpResponse response = MyHttpUtils.request(crawlMeta, req);
        doParse(response);
    }

    /**
     * 解释抓取结果并保存
     * @param response
     * @throws Exception
     */
    public void doParse(HttpResponse response) throws Exception{
        //遍历所有的响应头字段
        System.out.println("响应头信息：");
        for (Header header : response.getAllHeaders()) {
            System.out.println(header.getName()+">>>>"+header.getValue());
        }

        //中文转码
        String html=EntityUtils.toString(response.getEntity(),"utf-8");
        //解释文档
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
        StatusLine statusLine = response.getStatusLine();
        CrawlResult.Status status=new CrawlResult.Status(statusLine.getStatusCode(),statusLine.getReasonPhrase());
        crawlResult.setStatus(status);
        crawlResult.setUrl(crawlMeta.getUrl());
    }

    public SimpleCrawlJob(CrawlMeta crawlMeta) {
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
