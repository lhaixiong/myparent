package com.lhx.aggregate.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 */
@Entity
@Table(name = "user",uniqueConstraints = {@UniqueConstraint(columnNames = {"account"})})
public class User implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private int id;
    @Column(length = 20)
    private String account;//账号
    @Column(length = 32)
    private String password;//密码
    @Column(length = 20)
    private String nickname;//显示昵称
    private Date createTime;//创建时间
    @Column(length = 40)
    private String accessToken;//访问标示
    private int groupId;//所在组id
    @Column(columnDefinition = "int(11) default 1")
    private int status;//状态1可用（默认），0不可用
    private int errPassTimes;//本次登陆输入密码错误次数
    private Date lassErrPassTimes;//上次数错密码时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotBlank(message = "账号不能为空!")
    @Length(min = 3,max=20,message = "账号长度要大于3个字符小于20个字符")
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @NotBlank(message = "密码不能为空!")
    @Length(min = 3,max=32,message = "密码长度要大于3个字符小于32个字符")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getErrPassTimes() {
        return errPassTimes;
    }

    public void setErrPassTimes(int errPassTimes) {
        this.errPassTimes = errPassTimes;
    }

    public Date getLassErrPassTimes() {
        return lassErrPassTimes;
    }

    public void setLassErrPassTimes(Date lassErrPassTimes) {
        this.lassErrPassTimes = lassErrPassTimes;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", createTime=" + createTime +
                ", accessToken='" + accessToken + '\'' +
                ", groupId=" + groupId +
                ", status=" + status +
                ", errPassTimes=" + errPassTimes +
                ", lassErrPassTimes=" + lassErrPassTimes +
                '}';
    }
}
