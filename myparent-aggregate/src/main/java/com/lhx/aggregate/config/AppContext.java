package com.lhx.aggregate.config;

import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContext;

/**
 * 包含servletcontext和spring的applicationContext
 * 应用全局变量,所有应用全局数据应置于application作用域中
 */
public class AppContext {
    private static ServletContext servletcontext;
    private static ApplicationContext applicationContext;

    public ServletContext getServletcontext() {
        return servletcontext;
    }

    protected static void setServletcontext(ServletContext servletcontext) {
        AppContext.servletcontext = servletcontext;
    }

    public  ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    protected static void setApplicationContext(ApplicationContext applicationContext) {
        AppContext.applicationContext = applicationContext;
    }
}
