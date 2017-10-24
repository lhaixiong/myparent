package com.lhx.aggregate.authority;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 权限实体类
 */
public class PermissionInfo {
    public static final int TYPE_NODE=1;
    public static final int TYPE_MENU=2;
    public static final int TYPE_BUTTON=3;
    private int id;
    private int parent;
    /**
     * 节点还是菜单还是方法
     */
    private int type;
    /**
     * 权限名
     */
    private String name;
    /**
     * 显示图标
     */
    private String icon;
    /**
     * 显示顺序
     */
    private int sort;

    /**
     * 首页左侧菜单用url
     */
    private String accessUrl;
    /**
     * 访问按钮urls
     */
    private Set<String> btnUrls;
    /**
     * 该权限(节点)下的子Permission
     */
    private List<PermissionInfo> subPermission;

    /**
     * 节点权限构造
     */
    public PermissionInfo(Permission p) {
        super();
        this.id = p.id();
        this.parent = p.parent();
        this.type = p.type();
        this.name = p.name();
        this.icon = p.icon();
        this.sort = p.sort();
        this.btnUrls=new HashSet<>();
        this.subPermission = new ArrayList<PermissionInfo>();
    }

    /**
     * 菜单及方法权限构造
     */
    public PermissionInfo(Permission p,PermissionInfo parentNode,String[] ctrUrls,String[] methodUrls) {
        super();
        this.id = p.id();
        this.parent = parentNode.getId();
        this.type = p.type();
        this.name = p.name();
        this.icon = p.icon();
        this.sort = p.sort();
        this.btnUrls=new HashSet<>();
        if(ctrUrls.length<1){//控制器上没有@RequestMapping注解
            this.accessUrl=methodUrls[0];
            for (String methodUrl : methodUrls) {
                btnUrls.add(methodUrl);
            }
        }else {
            this.accessUrl=ctrUrls[0]+methodUrls[0];
            for (String ctrUrl : ctrUrls) {
                for (String methodUrl : methodUrls) {
                    btnUrls.add(ctrUrl+methodUrl);
                }
            }
        }
        this.subPermission = new ArrayList<PermissionInfo>();
    }
    /**
     * 该权限是否包含某个url
     *
     * @param url
     *            需要匹配的url("/"开始,不带后缀)
     */
    public boolean containsUrl(String url) {
        if (!StringUtils.isEmpty(url)) {
            return btnUrls.contains(url);
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getAccessUrl() {
        return accessUrl;
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }

    public Set<String> getBtnUrls() {
        return btnUrls;
    }

    public void setBtnUrls(Set<String> btnUrls) {
        this.btnUrls = btnUrls;
    }

    public List<PermissionInfo> getSubPermission() {
        return subPermission;
    }

    public void setSubPermission(List<PermissionInfo> subPermission) {
        this.subPermission = subPermission;
    }

    @Override
    public String toString() {
        return "PermissionInfo{" +
                "id=" + id +
                ", parent=" + parent +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", sort=" + sort +
                ", accessUrl='" + accessUrl + '\'' +
                ", btnUrls=" + btnUrls +
                ", subPermission=" + subPermission +
                '}';
    }
}
