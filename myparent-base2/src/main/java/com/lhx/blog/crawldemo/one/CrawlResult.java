package com.lhx.blog.crawldemo.one;

import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Map;

public class CrawlResult {
    /**
     * 抓取的网址
     */
    private String url;
    /**
     * 爬取的网址对应的 DOC 结构
     */
    private Document htmlDoc;

    /**
     * 响应状态
     */
    private Status status;
    /**
     * 选择的结果，key为选择规则，value为根据规则匹配的结果
     */
    private Map<String,List<String>> result;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Document getHtmlDoc() {
        return htmlDoc;
    }

    public void setHtmlDoc(Document htmlDoc) {
        this.htmlDoc = htmlDoc;
    }

    public Map<String, List<String>> getResult() {
        return result;
    }

    public void setResult(Map<String, List<String>> result) {
        this.result = result;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "CrawlResult{" +
                "url='" + url + '\'' +
                ", htmlDoc=" + htmlDoc +
                ", status=" + status +
                ", result=" + result +
                '}';
    }

    public static class Status{
        private int code;
        private String msg;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Status(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        @Override
        public String toString() {
            return "Status{" +
                    "code=" + code +
                    ", msg='" + msg + '\'' +
                    '}';
        }
    }
}
