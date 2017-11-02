package com.lhx.blog.crawldemo.one;

public abstract class AbstractJob implements IJob {
    @Override
    public void beforeRun() {

    }

    @Override
    public void afterRun() {

    }

    @Override
    public void run() {
        beforeRun();
        try {
            doFetchPage();
        }catch (Exception e){
            e.printStackTrace();
        }
        afterRun();
    }

    /**
     * 执行抓取网页
     */
    public abstract void doFetchPage() throws Exception;
}
