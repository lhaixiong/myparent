package com.lhx.aggregate.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户权限关联表
 */
@Entity
@Table(name="user_auth")
public class UserAuth implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int userId;//用户id
    private int permisssionId;//权限id
    private int creater;//授权者
    private Date createTime;//授权时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPermisssionId() {
        return permisssionId;
    }

    public void setPermisssionId(int permisssionId) {
        this.permisssionId = permisssionId;
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
}
