package com.lhx.aggregate.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "operateLog")
public class OperateLog implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private int id;// 主键
    private Date date;// 日期
    private int creater;// 操作用户
    private String accessPath;// 访问路径 如listUser.html
    private String accessName;// 访问名称，如更新、添加等等
    @Column(columnDefinition = "TEXT")
    private String params;// 记录参数，扩展需求记录特殊处理，比如直接修改玩家,GM发货等

    public OperateLog() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCreater() {
        return creater;
    }

    public void setCreater(int creater) {
        this.creater = creater;
    }

    public String getAccessPath() {
        return accessPath;
    }

    public void setAccessPath(String accessPath) {
        this.accessPath = accessPath;
    }

    public String getAccessName() {
        return accessName;
    }

    public void setAccessName(String accessName) {
        this.accessName = accessName;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
