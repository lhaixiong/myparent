package com.lhx.mvc.ioc;

import java.util.Properties;

public class ApplicationContext {
    private static final String PACKAGE_NAME = "com.lhx.mvc";

    private Properties properties;

    public ApplicationContext(){
        BeanFactory.getInstance().init(PACKAGE_NAME);
    }

    public Object getBean(String name) {
        return BeanFactory.getInstance().getBeanOfName(name);
    }

    public <T> T getBean(Class<T> clz) {
        return BeanFactory.getInstance().getBeanOfType(clz);
    }

    public boolean containBean(String name) {
        return BeanFactory.getInstance().containBean(name);
    }

    /**
     * 注册
     * @param name
     * @param bean
     * @return
     */
    public boolean register(String name, Object bean) {
        return BeanFactory.getInstance().registerBean(name, bean);
    }
}
