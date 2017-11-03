package com.lhx.mvc.aop.assist;

import net.sf.cglib.proxy.MethodProxy;

/**
 * 继承JoinPoint, 此外包含代理类，返回结果
 */
public class ProceedingJoinPoint extends JoinPoint {
    private MethodProxy methodProxy;
    private Object result;
    // 是否已经执行过方法的标记
    private volatile boolean executed;

    public Object proceed() throws Throwable{
        if (!executed) {
            synchronized (this){
                if (!executed) {
                    result=methodProxy.invokeSuper(getObj(),getParams());
                    executed=true;
                }
            }
        }
        return result;
    }

    public MethodProxy getMethodProxy() {
        return methodProxy;
    }

    public void setMethodProxy(MethodProxy methodProxy) {
        this.methodProxy = methodProxy;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
