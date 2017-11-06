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


    /**
     * key:service.method上的注解AnnotationType,例如LogDot,v:有该注解的所有的Aspect类的通知集合，例如LogAspect、LogAspect2里的通知
     */
    public Map<Class, ProcessCollect> processCollectMap;

}
