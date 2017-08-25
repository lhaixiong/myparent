package com.lhx.aggregate.service;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.lhx.aggregate.config.AppConstant;
import com.lhx.aggregate.dao.impl.GroupAuthDao;
import com.lhx.aggregate.dao.impl.GroupDao;
import com.lhx.aggregate.dao.impl.UserAuthDao;
import com.lhx.aggregate.dao.impl.UserDao;
import com.lhx.aggregate.entity.Group;
import com.lhx.aggregate.entity.GroupAuth;
import com.lhx.aggregate.entity.User;
import com.lhx.aggregate.entity.UserAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GroupAuthService {
	private static final Logger log = LoggerFactory
			.getLogger(GroupAuthService.class);
	@Autowired
	private GroupAuthDao groupAuthDao;
	@Autowired
	private GroupDao groupDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserAuthDao userAuthDao;

	/**
	 * 更新用户组权限
	 * 
	 */
	public int updateGroupAuth(int gid, String pIds, HttpServletRequest req) {
		int result = AppConstant.CODE_FAIL;
		Group group = groupDao.getById(gid);
		if (null == group) {
			log.info("用户组不存在!权限更新失败!");
			return result;
		}
		if (null == pIds || pIds.trim().length() == 0) {
			log.info("未选择任何权限,权限更新失败!");
			return result;
		}
		try {
			pIds=pIds.substring(1,pIds.length()-1);
			Set<Integer> newPermissions = toPermissionSet(pIds);
			User loginUser = (User) req.getSession().getAttribute(
					AppConstant.SESSION_LOGIN_USER);
			Map<Integer, GroupAuth> oldPermissions = getGroupPermission(gid);
			// =================组权限更新开始
			for (Integer newPermission : newPermissions) {
				if (!oldPermissions.containsKey(newPermission)) {// 原来没有，加上
					GroupAuth ga = new GroupAuth();
					ga.setCreater(loginUser.getId());
					ga.setCreateTime(new Date());
					ga.setPermissionId(newPermission);
					ga.setGroupId(group.getId());
					groupAuthDao.saveOrUpdate(ga);
				}
			}
			Set<Integer> oldShouldBeDeleted = new HashSet<Integer>();
			for (Integer oldPermission : oldPermissions.keySet()) {
				if (!newPermissions.contains(oldPermission)) {// 这些没用了，删掉
					groupAuthDao.delete(oldPermissions.get(oldPermission));
					oldShouldBeDeleted.add(oldPermission);
				}
			}
			// =================组权限更新结束

			// =================该组下所有用户权限更新
			updateGroupUserAuth(loginUser, gid, oldShouldBeDeleted,
					newPermissions);

			result = AppConstant.CODE_SUC;
			return result;
		} catch (Exception e) {
			log.error("更新权限失败!gid:" + gid, e);
			e.printStackTrace();
			return result;
		}
	}

	public void updateGroupUserAuth(User loginUser, int gid,
			Set<Integer> oldShouldBeDeleted, Set<Integer> newPidSet) {
		String hql = "from User where groupId = " + gid;
		List<User> groupUsers = userDao.find(hql);
		Group group = groupDao.getById(gid);
		if (null != groupUsers && 0 != groupUsers.size()) {
			for (User user : groupUsers) {
				String tempHql = "From UserAuth Where userId = " + user.getId();
				List<UserAuth> userAuths = userAuthDao.find(tempHql);
				if ((null == userAuths || 0 == userAuths.size())&&group.getType()!=AppConstant.GROUP_ADMIN) {
					continue;
				}
				for (UserAuth userAuth : userAuths) {
					if (oldShouldBeDeleted.contains(userAuth.getPermisssionId())) {
						userAuthDao.delete(userAuth);
					}
				}
				userAuths = userAuthDao.find(tempHql);
				Set<Integer> userAuthPidSet = new HashSet<Integer>();
				for (UserAuth userAuth : userAuths) {
					userAuthPidSet.add(userAuth.getPermisssionId());
				}
				for (Integer newPid : newPidSet) {
					if (!userAuthPidSet.contains(newPid)) {
						UserAuth ua = new UserAuth();
						ua.setCreater(loginUser.getId());
						ua.setCreateTime(new Date());
						ua.setPermisssionId(newPid);
						ua.setUserId(user.getId());
						userAuthDao.saveOrUpdate(ua);
					}
				}
			}
		}
	}

	/**
	 * 把权限串转成Set
	 * 
	 * @param pIds
	 * @return
	 * @since 2015年12月23日 上午11:54:18
	 */
	private Set<Integer> toPermissionSet(String pIds) {
		Set<Integer> result = new HashSet<Integer>();
		if (null != pIds && pIds.trim().length() != 0) {
			String[] permissions = pIds.split(",");
			for (String permission : permissions) {
				result.add(new Integer(permission));
			}
		}
		return result;
	}

	/**
	 * 获取用户组权限关系
	 * 
	 */
	public Map<Integer, GroupAuth> getGroupPermission(int groupId) {
		// key：pid，value：GroupAuth
		Map<Integer, GroupAuth> result = new HashMap<Integer, GroupAuth>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("groupId", groupId);
		String hql = "from GroupAuth where groupId = :groupId";
		List<GroupAuth> gas = groupAuthDao.find(hql, params);
		if (null != gas && 0 != gas.size()) {
			for (GroupAuth ga : gas) {
				result.put(ga.getPermissionId(), ga);
			}
		} else {
			log.info("用户组权限关系为空!gid=" + groupId);
		}
		return result;
	}
}
