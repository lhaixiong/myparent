package com.lhx.mvc.aop;

import com.lhx.mvc.aop.assist.JoinPoint;
import com.lhx.mvc.aop.assist.ProceedingJoinPoint;
import com.lhx.mvc.aop.process.AfterProcess;
import com.lhx.mvc.aop.process.AroundProcess;
import com.lhx.mvc.aop.process.BeforeProcess;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MyCglibProxy implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("cglib代理开始");

        JoinPoint joinPoint=new JoinPoint();
        joinPoint.setObj(o);
        joinPoint.setMethod(method);
        joinPoint.setParams(objects);

        processBeforeAdvice(joinPoint);

        Object result=processAroundAdvice(joinPoint,methodProxy);

        processAfterAdvice(joinPoint,result);
        
        System.out.println("cglib代理结束");
        return result;
    }

    private void processAfterAdvice(JoinPoint joinPoint,Object result) {
        System.out.println("processAfterAdvice");
        List<AfterProcess> afterList = new ArrayList<>();
        Annotation[] anos = joinPoint.getMethod().getAnnotations();
        for (Annotation ano : anos) {
            if (AspectHolder.getInstance().processCollectMap.containsKey(ano.annotationType())) {
                afterList.addAll(AspectHolder.getInstance().processCollectMap.get(ano.annotationType()).getAfterList());
            }
        }

        if (afterList==null|| afterList.isEmpty()) {
            return;
        }


        AfterProcess temp;
        for (AfterProcess f : afterList) {
            temp = new AfterProcess(f);
            temp.setJoinPoint(joinPoint);
            temp.setResult(result);

            try {
                temp.process();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    private Object processAroundAdvice(JoinPoint joinPoint,MethodProxy methodProxy) throws Throwable{
        System.out.println("processAroundAdvice");
        //...
        ProceedingJoinPoint pjp=new ProceedingJoinPoint();
        pjp.setObj(joinPoint.getObj());
        pjp.setMethod(joinPoint.getMethod());
        pjp.setParams(joinPoint.getParams());
        pjp.setMethodProxy(methodProxy);
        //...
        List<AroundProcess> aroundList = new ArrayList<>();
        Annotation[] anos = joinPoint.getMethod().getAnnotations();
        for (Annotation ano : anos) {
            if (AspectHolder.getInstance().processCollectMap.containsKey(ano.annotationType())) {
                aroundList.addAll(AspectHolder.getInstance().processCollectMap.get(ano.annotationType()).getAroundList());
            }
        }

        if (aroundList==null|| aroundList.isEmpty()) {
            return methodProxy.invokeSuper(joinPoint.getObj(), joinPoint.getParams());
        }


        Object result = null;
        AroundProcess temp;
        for (AroundProcess f : aroundList) {
            temp = new AroundProcess(f);
            temp.setJoinPoint(pjp);

            try {
                result = temp.process();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    private void processBeforeAdvice(JoinPoint joinPoint) {
        System.out.println("processBeforeAdvice");
        List<BeforeProcess> beforeList=new ArrayList<>();
        Annotation[] anos = joinPoint.getMethod().getAnnotations();
        for (Annotation ano : anos) {
            if (AspectHolder.getInstance().processCollectMap.containsKey(ano.annotationType())) {
                beforeList.addAll(AspectHolder.getInstance().processCollectMap.get(ano.annotationType()).getBeforeList());
            }
        }
        if (beforeList == null || beforeList.isEmpty()) {
            return;
        }
        for (BeforeProcess bp : beforeList) {
            BeforeProcess process=new BeforeProcess(bp);
            process.setJoinPoint(joinPoint);
            try {
                process.process();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }
}
