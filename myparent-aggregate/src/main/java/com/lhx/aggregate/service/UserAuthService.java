package com.lhx.aggregate.service;

import com.lhx.aggregate.config.AppConstant;
import com.lhx.aggregate.config.AppStart;
import com.lhx.aggregate.dao.impl.UserAuthDao;
import com.lhx.aggregate.dao.impl.UserDao;
import com.lhx.aggregate.entity.User;
import com.lhx.aggregate.entity.UserAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class UserAuthService {
    private static final Logger log= LoggerFactory.getLogger(UserAuthService.class);
    @Autowired
    private UserAuthDao userAuthDao;
    @Autowired
    private UserDao userDao;

    /**
     * 通过用户id获取相应用户权限
     * @param userId
     * @return
     */
    public Map<Integer,UserAuth> getUserPermissions(int userId){
        // key：pid，value：UserAuth
        Map<Integer,UserAuth> result=new HashMap<>();
        String hql="from UserAuth where userId="+userId;
        List<UserAuth> list = userAuthDao.find(hql);
        if (list != null&&!list.isEmpty()) {
            for (UserAuth bean : list) {
                result.put(bean.getPermisssionId(),bean);
            }
        }else {
            log.info("用户权限关系为空!userId=" + userId);
        }
        return result;
    }

    public int updateUserAuth(int uid, String pIds, HttpServletRequest req) {
        int result = AppConstant.CODE_FAIL;
        User user = userDao.getById(uid);
        if (null == user) {
            log.info("用户不存在!权限更新失败!");
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
            Map<Integer, UserAuth> oldPermissions = getUserPermissions(uid);
            for (Integer newPermission : newPermissions) {
                if (!oldPermissions.containsKey(newPermission)) {// 原来没有，加上
                    UserAuth ua = new UserAuth();
                    ua.setCreater(loginUser.getId());
                    ua.setCreateTime(new Date());
                    ua.setPermisssionId(newPermission);
                    ua.setUserId(user.getId());
                    userAuthDao.save(ua);
                }
            }
            for (Integer oldPermission : oldPermissions.keySet()) {
                if (!newPermissions.contains(oldPermission)) {// 这些没用了，删掉
                    userAuthDao.delete(oldPermissions.get(oldPermission));
                }
            }
            result = AppConstant.CODE_SUC;
            return result;
        } catch (Exception e) {
            log.error("更新权限失败!userId:" + uid, e);
            e.printStackTrace();
            return result;
        }
    }
    /**
     * 把权限串转成Set
     *
     * @param pIds
     * @return
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

}
