package com.lhx.mvc.aop.assist;

import java.lang.reflect.Method;

/**
 * 包装执行方法相关的信息
 * 包含实际对象，执行方法，参数
 */
public class JoinPoint {
    private Object obj;
    private Method method;
    private Object[] params;

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}
