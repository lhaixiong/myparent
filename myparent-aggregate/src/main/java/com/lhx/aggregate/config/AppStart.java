package com.lhx.aggregate.config;

import com.alibaba.fastjson.JSONObject;
import com.lhx.aggregate.authority.Permission;
import com.lhx.aggregate.authority.PermissionInfo;
import com.lhx.aggregate.service.GroupService;
import com.lhx.aggregate.tools.PackageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 应用启动
 */
@Component
public class AppStart implements ApplicationListener<ContextRefreshedEvent>,ServletContextAware{
    private static final Logger log= LoggerFactory.getLogger(AppStart.class);
    
    public static String PACKAGE_PATH="com.lhx.aggregate.controller";//要扫描的包
    public static Map<String,PermissionInfo> nodeAuth=new TreeMap<>();//左侧节点和菜单权限
    public static Map<String,PermissionInfo> allAuth=new HashMap<>();//左侧节点、菜单权限和按钮权限
    public static Set<String> checkUrls=new HashSet<>();//检测权限url是否冲突，启动用
    @Autowired
    private GroupService groupService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("应用启动中......");
        ApplicationContext ctx = event.getApplicationContext();
        AppContext.setApplicationContext(ctx);
        initUser();
        initAuth(ctx);
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        AppContext.setServletcontext(servletContext);
    }

    /**
     * 初始化用户组和用户
     */
    private void initUser(){
       groupService.initData();
    }

    /**
     * 初始化权限信息
     * @param ctx
     */
    private void initAuth(ApplicationContext ctx){
        try {
            log.info("初始化权限信息开始...");
            Set<Class<?>> classes = PackageUtil.getClasses(PACKAGE_PATH);

            for (Class<?> controllerClz : classes) {
                //1节点权限处理
                if (!controllerClz.isAnnotationPresent(Controller.class)) {//不是Ctroller注解的类
                    continue;
                }
                Permission pAnnotation = controllerClz.getAnnotation(Permission.class);
                if (pAnnotation==null||pAnnotation.type()!= PermissionInfo.TYPE_NODE) {//不是节点权限
                    continue;
                }
                RequestMapping ctrMapping = controllerClz.getAnnotation(RequestMapping.class);
                String[] ctrUrl={};
                if (ctrMapping != null) {
                   ctrUrl = ctrMapping.value();
                }
                PermissionInfo node=null;
                String nodeId=pAnnotation.id()+"";
                if (!allAuth.containsKey(nodeId)) {
                    node=new PermissionInfo(pAnnotation);
                    nodeAuth.put(nodeId,node);
                    allAuth.put(nodeId,node);
                    //Controller中权限注解可以重复
//                    throw new RuntimeException(controllerClz.getName()+"包含重复的节点权限ID:" +nodeId);
                }
                node=nodeAuth.get(nodeId);


                //2菜单权限处理
                //2.1主菜单入口处理
                Method[] methods = controllerClz.getDeclaredMethods();
                PermissionInfo menu=null;
                for (Method method : methods) {
                    Permission mp = method.getAnnotation(Permission.class);
                    if (mp!=null&&mp.type()== PermissionInfo.TYPE_MENU) {//菜单注解类型
                        RequestMapping methodMapping = method.getAnnotation(RequestMapping.class);
                        String[] methodUrls={};
                        if (methodMapping != null) {
                            methodUrls = methodMapping.value();
                        }
                        menu=new PermissionInfo(mp,node,ctrUrl,methodUrls);
                        String menuId=menu.getId()+"";
                        if (allAuth.containsKey(menuId)) {
                            throw new RuntimeException(controllerClz.getName()+"."+method.getName()+"包含重复的菜单权限ID:" +menuId);
                        }
                        String accessUrl=menu.getAccessUrl();
                        if (checkUrls.contains(accessUrl)) {
                            throw new RuntimeException(controllerClz.getName()+"."+method.getName()+"包含重复的菜单路径:" +accessUrl);
                        }
                        allAuth.put(menuId, menu);
                        checkUrls.add(accessUrl);
                        node.getSubPermission().add(menu);//菜单加入上级节点
                        break;
                    }

                }

                if (menu == null) {
                    throw new RuntimeException(controllerClz.getName()+"下没有菜单入口 (type = PermissionInfo.TYPE_MENU的注解)");
                }

                //2.2按钮入口处理
                for (Method method : methods) {
                    Permission mp = method.getAnnotation(Permission.class);
                    if (mp!=null&&mp.type()== PermissionInfo.TYPE_BUTTON) {//按钮注解类型
                        RequestMapping methodMapping = method.getAnnotation(RequestMapping.class);
                        String[] methodUrls={};
                        if (methodMapping != null) {
                            methodUrls = methodMapping.value();
                        }
                        PermissionInfo button=new PermissionInfo(mp,menu,ctrUrl,methodUrls);
                        String buttonId=button.getId()+"";
                        if (allAuth.containsKey(buttonId)) {
                            throw new RuntimeException(controllerClz.getName()+"."+method.getName()+"包含重复的按钮权限ID:" +buttonId);
                        }
                        String accessUrl=button.getAccessUrl();
                        if (checkUrls.contains(accessUrl)) {
                            throw new RuntimeException(controllerClz.getName()+"."+method.getName()+"包含重复的访问路径:" +accessUrl);
                        }
                        allAuth.put(buttonId, button);
                        checkUrls.add(accessUrl);
                        menu.getSubPermission().add(button);//按钮加入上级菜单
                    }
                }
            }
            //对菜单排序
            for (PermissionInfo info : nodeAuth.values()) {
                List<PermissionInfo> nodeSubPermission = info.getSubPermission();
                Collections.sort(nodeSubPermission, new Comparator<PermissionInfo>() {
                    @Override
                    public int compare(PermissionInfo o1, PermissionInfo o2) {
                        if (o1.getId() < o2.getId()) {
                            return -1;
                        } else if (o1.getId() > o2.getId()) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });
            }
            log.info("节点和菜单权限结构:{}", JSONObject.toJSONString(nodeAuth));
            log.info("初始化权限信息结束...");
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, PermissionInfo> getAllAuth() {
        return allAuth;
    }

    public static Map<String, PermissionInfo> getNodeAuth() {
        return nodeAuth;
    }
    public static PermissionInfo getPerByAccessUrl(String accessUrl){
        for (PermissionInfo info : allAuth.values()) {
            if(info.getType()==PermissionInfo.TYPE_NODE){
                continue;
            }
            if (info.getAccessUrl().equals(accessUrl)) {
                return info;
            }
            if(info.getBtnUrls().contains(accessUrl)){
                return info;
            }
        }
        return null;
    }
}
