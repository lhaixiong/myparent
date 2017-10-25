package com.lhx.aggregate.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户组权限关系
 * 
 */
@Entity
@Table(name = "group_auth")
public class GroupAuth {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; // id
	private int groupId; // 用户组ID
	private int permissionId; // 权限id
	private Date createTime; // 授权时间
	private int creater; // 授权者

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getCreater() {
		return creater;
	}

	public void setCreater(int creater) {
		this.creater = creater;
	}

}
