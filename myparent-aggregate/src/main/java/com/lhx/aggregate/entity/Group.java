package com.lhx.aggregate.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户组表
 */
@Entity
@Table(name = "sys_group")//不能用group关键字作为表名
public class Group implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;//
    private String name;//用户组名称
    private int creater;//创建者
    private Date createTime;//创建时间
    @Column(columnDefinition = "int(11) default 1")//废物，默认不生效
    private int status=1;//用户组状态1可用（默认），0不可用
    @Column(columnDefinition = "int(11) default 0")
    private int type;//用户组类型，0普通组(默认)，1超级组

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCreater() {
        return creater;
    }

    public void setCreater(int creater) {
        this.creater = creater;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
