package com.lhx.mvc.aop;

import java.util.Map;

public class AspectHolder {
    private static class Inner {
        private static AspectHolder instance = new AspectHolder();
    }

    public static AspectHolder getInstance() {
        return Inner.instance;
    }

    private AspectHolder() {
    }


    public Map<Class, ProcessCollect> processCollectMap;

}
