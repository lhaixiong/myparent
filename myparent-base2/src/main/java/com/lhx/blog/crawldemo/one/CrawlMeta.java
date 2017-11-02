package com.lhx.blog.crawldemo.one;

import java.util.HashSet;
import java.util.Set;

/**
 * blog:https://my.oschina.net/u/566591/blog/1047233
 *
 * 一个配置项，包含塞入的 url 和 获取规则
 */
public class CrawlMeta {
    /**
     * 指定爬取的网址
     */
    private String url;
    /**
     * 获取指定内容的规则, 因为一个网页中，你可能获取多个不同的内容， 所以放在集合中
     */
    private Set<String> selectedRules=new HashSet<>();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<String> getSelectedRules() {
        return selectedRules;
    }

    public void setSelectedRules(Set<String> selectedRules) {
        this.selectedRules = selectedRules;
    }

    @Override
    public String toString() {
        return "CrawlMeta{" +
                "url='" + url + '\'' +
                ", selectedRules=" + selectedRules +
                '}';
    }
}
