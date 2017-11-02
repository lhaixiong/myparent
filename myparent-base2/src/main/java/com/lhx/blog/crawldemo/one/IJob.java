package com.lhx.blog.crawldemo.one;

public interface IJob extends Runnable {
    void beforeRun();
    void afterRun();
}
