package com.lhx.aggregate.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lhx.aggregate.entity.UserAuth;
import org.springframework.stereotype.Repository;


@Repository
public class UserAuthDao extends BaseDaoImpl<UserAuth> {
	/**
	 * 获取用户权限关系
	 *
	 */
	public Map<Integer, UserAuth> getUserPermission(int userId) {
		Map<Integer, UserAuth> result = new HashMap<Integer, UserAuth>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		String hql = "from UserAuth where userId = :userId";
		List<UserAuth> uas = find(hql, params);
		if (null != uas && 0 != uas.size()) {
			for (UserAuth ua : uas) {
				result.put(ua.getPermisssionId(), ua);
			}
		}
		return result;
	}
}
