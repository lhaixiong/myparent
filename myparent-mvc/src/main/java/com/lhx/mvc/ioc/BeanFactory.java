package com.lhx.mvc.ioc;

import com.lhx.mvc.aop.AspectHolder;
import com.lhx.mvc.aop.ProcessCollect;
import com.lhx.mvc.aop.annotation.After;
import com.lhx.mvc.aop.annotation.Around;
import com.lhx.mvc.aop.annotation.Aspect;
import com.lhx.mvc.aop.annotation.Before;
import com.lhx.mvc.aop.process.AfterProcess;
import com.lhx.mvc.aop.process.AroundProcess;
import com.lhx.mvc.aop.process.BeforeProcess;
import com.lhx.mvc.exception.BeanAlreadyDefinedException;
import com.lhx.mvc.exception.BeanNotFoundException;
import com.lhx.mvc.ioc.annotation.filed.Autowired;
import com.lhx.mvc.ioc.annotation.type.Bean;
import com.lhx.mvc.util.PkgUtil;
import com.lhx.mvc.util.StrUtil;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BeanFactory {
    private static final BeanFactory instance=new BeanFactory();
    /**
     * 所有自动实例化的bean的映射表，
     * key为bean name
     * - (如果注解中有指定value值，则bean name就是value值；若没有指定，则是首字母小写的简单类名）
     * - bean name 区分大小写
     */
    private Map<String,Object> nameBeanMap;
    /**
     * class到bean的映射表
     */
    private Map<Class,Object> clzBeanMap;

    private Set<Class<?>> beanClasses;
    /**
     * 自定义注解 及 切该注解的所有切面的映射关系
     * <p>
     * key 为@Aspect标记的的class切面对象
     * value 为该注解所对应的所有通知集合
     */
    private Map<Class, ProcessCollect> aspectAnoMap;

    public  Object getBeanOfName(String name){
        return nameBeanMap.get(name);
    }

    public <T> T getBeanOfType(Class<T> clz){
        return (T) clzBeanMap.get(clz);
    }

    public boolean containBean(String name) {
        return nameBeanMap.containsKey(name);
    }
    public boolean containBean(Class clz) {
        return clzBeanMap.containsKey(clz);
    }

    public Set<Class<?>> getBeanClasses() {
        return beanClasses;
    }

    public static BeanFactory getInstance(){
        return instance;
    }
    private BeanFactory(){
        nameBeanMap=new ConcurrentHashMap<>();
        clzBeanMap=new ConcurrentHashMap<>();
        beanClasses=new LinkedHashSet<>();
        aspectAnoMap=new ConcurrentHashMap<>();
    }

    public void init(String pkg) {
        try {
            // 扫描指定包名自定义注解的类
            loadScanClass(pkg);

            // instance aspect and maintain aspect Map
            instanceAspect();

            // 实例化bean
            instanceBean();

            // 依赖注入
            ioc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void instanceAspect() throws Exception{
        Object aspect;
        for (Class clz : beanClasses) {
            if (!clz.isAnnotationPresent(Aspect.class)) {
                continue;
            }

            aspect = clz.newInstance();


            // 将aspect 丢入bean Map中， 因此aspect也支持ioc
            clzBeanMap.put(clz, aspect);

            // 扫描切面的方法
            Class ano;
            ProcessCollect processCollect;
            Method[] methods = clz.getDeclaredMethods();
            for (Method method : methods) {
                Before before = method.getAnnotation(Before.class);
                if (before != null) {
                    BeforeProcess beforeProcess = new BeforeProcess();
                    beforeProcess.setAspect(aspect);
                    beforeProcess.setMethod(method);
                    beforeProcess.setOrder(before.order());

                    ano = before.value();
                    processCollect = aspectAnoMap.computeIfAbsent(ano, k -> new ProcessCollect());
                    processCollect.addBeforeProcess(beforeProcess);
                }


                After after = method.getAnnotation(After.class);
                if (after != null) {
                    AfterProcess afterProcess = new AfterProcess();
                    afterProcess.setAspect(aspect);
                    afterProcess.setMethod(method);
                    afterProcess.setOrder(after.order());

                    ano = after.value();
                    processCollect = aspectAnoMap.computeIfAbsent(ano, k -> new ProcessCollect());
                    processCollect.addAfterProcess(afterProcess);
                }


                Around around = method.getAnnotation(Around.class);
                if (around != null) {
                    AroundProcess aroundProcess = new AroundProcess();
                    aroundProcess.setAspect(aspect);
                    aroundProcess.setMethod(method);
                    aroundProcess.setOrder(around.order());

                    ano = around.value();
                    processCollect = aspectAnoMap.computeIfAbsent(ano, k -> new ProcessCollect());
                    processCollect.addAroundProcess(aroundProcess);
                }

            }
        }

        // 排序
        for (ProcessCollect collect: aspectAnoMap.values()) {
            Collections.sort(collect.getBeforeList());
            Collections.sort(collect.getAfterList());
            Collections.sort(collect.getAroundList());
        }
        AspectHolder.getInstance().processCollectMap = aspectAnoMap;
    }


    /**
     * 实例化注解bean
     */
    private void instanceBean() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {

        Annotation[] typeAnos;
        String tmpBeanName;
        Method tmpMethod;
        Object tmpBean;

        for (Class<?> beanclz : beanClasses) {

            if (beanclz.isInterface()) {
                continue;
            }

            //获取类上的注解
            typeAnos=beanclz.getAnnotations();
            if (typeAnos==null || typeAnos.length==0) {
                continue;
            }

            for (Annotation ano : typeAnos) {
                if (ano instanceof Bean || ano.annotationType().isAnnotationPresent(Bean.class)) {
                    //当注解的value属性有被指定，则beanName即为指定的值
                    tmpMethod=ano.annotationType().getMethod("value",null);
                    if (tmpMethod != null) {
                        tmpBeanName = (String) tmpMethod.invoke(ano, null);
                    } else {
                        tmpBeanName = null;
                    }
                    //否则，根据class名，首字母小写即可
                    if (StringUtils.isEmpty(tmpBeanName)) {
                        tmpBeanName= StrUtil.lowerFirstChar(beanclz.getSimpleName());
                    }

                    if (nameBeanMap.containsKey(tmpBeanName)) {
                        throw new BeanAlreadyDefinedException("bean " + tmpBeanName + " class: " + beanclz.getName() + " has already defined!");
                    }
                    tmpBean=beanclz.newInstance();
                    nameBeanMap.put(tmpBeanName,tmpBean);
                    clzBeanMap.put(beanclz,tmpBean);
                }
            }
        }
    }
    /**
     * 依赖注入
     */
    private void ioc() throws IllegalAccessException {
        Field[] fields;
        String fieldBeanName;
        Object fieldBean;
        for (Object obj : nameBeanMap.values()) {
            fields=obj.getClass().getDeclaredFields();

            for (Field field : fields) {
                if (!field.isAnnotationPresent(Autowired.class)) {
                    continue;
                }

				Autowired ano = field.getAnnotation(Autowired.class);
				fieldBeanName = StringUtils.isEmpty(ano.value())
						? StrUtil.lowerFirstChar(field.getName()) : ano.value();
                fieldBean=nameBeanMap.get(fieldBeanName);
                if (fieldBean == null) {
                    throw new BeanNotFoundException("bean: " + fieldBeanName + " not found! bean class: " + field.getClass().getName());
                }
                // 强制设置可访问，这样私有的变量也可以修改其内容了
                field.setAccessible(true);
                field.set(obj,fieldBean);
			}
        }
    }

    /**
     * 扫描指定包
     * 多个包之间用英文逗号","隔开
     * @param packageName
     */
    private void loadScanClass(String packageName){
        if (StringUtils.isEmpty(packageName)) {
            return;
        }
        String[] pkgs=packageName.split(",");
        for (String pkg : pkgs) {
            beanClasses.addAll(PkgUtil.getClzFromPkg(pkg));
        }
    }

    /**
     * 运行后动态装载自定义生成的bean
     *
     */
    public boolean registerBean(String name, Object newBean) {
        if (nameBeanMap.containsKey(name)) {
            Object bean=nameBeanMap.get(name);
            throw new BeanAlreadyDefinedException("bean : " + name + " class: " + bean.getClass() + " has already defined and loaded!");
        }
        clzBeanMap.put(newBean.getClass(), newBean);
        nameBeanMap.put(name, newBean);
        return true;
    }
}
