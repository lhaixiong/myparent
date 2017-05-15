package com.lhx.effect.createanddestroy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceFactory {
    private static final Map<String,Provider> PROVIDERS=new ConcurrentHashMap<>();
    private static final String DEFAULT_PROVIDER="lhx";
    private ServiceFactory(){}
    public static void registerDefaultProvider(Provider p){
        registerProvider(DEFAULT_PROVIDER,p);
    }

    /**
     * 注册提供者
     * @param name
     * @param p
     */
    public static void registerProvider(String name,Provider p){
        //省略参数校验...
        if(PROVIDERS.containsKey(name)){
            try {
                throw new Exception(String.format("<%s>提供者名称已经被注册,请换另一个提供者名称",name));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        PROVIDERS.put(name,p);
    }

    /**
     * 获得默认服务实例
     * @return
     */
    public static Service newInstance(){
        return newInstance(DEFAULT_PROVIDER);
    }
    /**
     * 获得指定提供者的服务实例
     * @return
     */
    public static Service newInstance(String providerName){
        Provider p=PROVIDERS.get(providerName);
        if (p == null) {
            throw new IllegalArgumentException(String.format("没有名字为<%s>的提供者",providerName));
        }
        return p.newService();
    }
}
