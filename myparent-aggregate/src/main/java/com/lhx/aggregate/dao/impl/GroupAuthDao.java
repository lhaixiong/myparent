package com.lhx.aggregate.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lhx.aggregate.entity.GroupAuth;
import org.springframework.stereotype.Repository;


@Repository
public class GroupAuthDao extends BaseDaoImpl<GroupAuth> {
	/**
	 * 获取用户组权限关系
	 * 
	 */
	public Map<Integer, GroupAuth> getGroupPermission(int groupId) {
		// key：permissionId，value：GroupAuth
		Map<Integer, GroupAuth> result = new HashMap<Integer, GroupAuth>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("groupId", groupId);
		String hql = "from GroupAuth where groupId = :groupId";
		List<GroupAuth> gas = find(hql, params);
		if (null != gas && 0 != gas.size()) {
			for (GroupAuth ga : gas) {
				result.put(ga.getPermissionId(), ga);
			}
		}
		return result;
	}
}
