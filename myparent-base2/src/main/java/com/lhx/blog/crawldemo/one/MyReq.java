package com.lhx.blog.crawldemo.one;

import java.util.HashMap;
import java.util.Map;

/**
 * 包含请求头、请求方法、请求参数
 */
public class MyReq {
    /**
     * 默认请求头信息
     */
    private static Map<String,String> DEFAULT_HEADERS;
    /**
     * 请求头
     */
    private Map<String,String> requestHeaders=new HashMap<>();
    /**
     * 请求方法,默认为get
     */
    private MyHttpMethod requestMethod=MyHttpMethod.GET;
    /**
     * 请求参数
     */
    private Map<String,String> requestParams=new HashMap<>();
    static {
        DEFAULT_HEADERS=new HashMap<>();
        DEFAULT_HEADERS.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        DEFAULT_HEADERS.put("connection", "Keep-Alive");
        DEFAULT_HEADERS.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
    }

    public Map<String, String> getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(Map<String, String> requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public MyHttpMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(MyHttpMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    public Map<String, String> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(Map<String, String> requestParams) {
        this.requestParams = requestParams;
    }
}
