package com.lhx.aggregate.service;

import com.lhx.aggregate.config.AppConstant;
import com.lhx.aggregate.dao.impl.GroupAuthDao;
import com.lhx.aggregate.dao.impl.GroupDao;
import com.lhx.aggregate.dao.impl.UserAuthDao;
import com.lhx.aggregate.dao.impl.UserDao;
import com.lhx.aggregate.entity.Group;
import com.lhx.aggregate.entity.GroupAuth;
import com.lhx.aggregate.entity.User;
import com.lhx.aggregate.entity.UserAuth;
import com.lhx.aggregate.tools.ReqUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserDao dao;
    @Autowired
    private UserAuthDao userAuthDao;
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private GroupAuthDao groupAuthDao;
    public List<User> listUser(String account) {
        String hql="from User ";
        if (StringUtils.isNotEmpty(account)) {
            hql+=" where account like '%"+account+"%'";
        }
        return dao.find(hql);
    }

    public User getById(Integer id) {
        return dao.getById(id,User.class);
    }

    public void saveOrUpdate(User user) {
        dao.saveOrUpdate(user);
    }

    public void deleteById(Integer id) {
        dao.delete(id);
    }

    public void batDelete(String ids) {
        String temp=ids.substring(1,ids.length()-1);
        String userIds=temp.replaceAll("\"","");
        String hql="delete from User t where t.id in ("+userIds+")";
        dao.executeHql(hql);
    }

    public User getLoginUser(String account, String password) {
        Map<String,Object> params=new HashMap<>();
        params.put("account",account);
        params.put("password",password);
        String hql="from User t where t.account=:account and password=:password";
        return dao.getOne(hql,params);
    }
    public void saveOrUpdate(Integer opt, HttpServletRequest req) {
        Map<String, String> paramMap = ReqUtil.getParamMap(req);
        if (opt== AppConstant.OPT_ADD) {
            User user=new User();
            user.setAccount(paramMap.get("account"));
            user.setPassword(paramMap.get("password"));
            user.setNickname(paramMap.get("nickname"));
            user.setStatus(Integer.parseInt(paramMap.get("status")));
            user.setGroupId(Integer.parseInt(paramMap.get("groupId")));
            user.setCreateTime(new Date());
            dao.save(user);
            //添加用户权限
            addUserAuth(user, req);
        }else if(opt==AppConstant.OPT_EDIT){
            int id=Integer.parseInt(paramMap.get("id"));
            int newGroupId=Integer.parseInt(paramMap.get("groupId"));
            User user=dao.getById(id,User.class);
            int oldGroupId=user.getGroupId();
            user.setAccount(paramMap.get("account"));
            user.setPassword(paramMap.get("password"));
            user.setNickname(paramMap.get("nickname"));
            user.setStatus(Integer.parseInt(paramMap.get("status")));
            user.setGroupId(newGroupId);
            dao.saveOrUpdate(user);
            //组改变，更新用户权限
            if(oldGroupId!=newGroupId){
                updateUserAuth(id,newGroupId,req);
            }
        }
    }

    /**
     * 添加用户时，添加起对应组拥有的权限
     * @param user
     * @param req
     */
    private void addUserAuth(User user, HttpServletRequest req) {
        if (null == user) {
            return;
        }
        Group group = groupDao.getById(user.getGroupId(),Group.class);
        if (null == group) {
            return;
        }
        User loginUser = (User) req.getSession()
                .getAttribute(AppConstant.SESSION_LOGIN_USER);
        Map<Integer, GroupAuth> gas = groupAuthDao
                .getGroupPermission(user.getGroupId());
        if (null != gas && 0 != gas.size()) {
            for (Integer pid : gas.keySet()) {
                UserAuth ua = new UserAuth();
                ua.setCreater(loginUser.getId());
                ua.setCreateTime(new Date());
                ua.setPermisssionId(pid);
                ua.setUserId(user.getId());
                userAuthDao.save(ua);
            }
        }
    }
    /**
     * 当用户的组发生改变时，其权限跟着变
     *
     * @param uid
     * @param groupId
     * @param req
     */
    private void updateUserAuth(int uid, int groupId, HttpServletRequest req) {
        User user = dao.getById(uid,User.class);
        if (null == user) {
            return;
        }
        Group group = groupDao.getById(groupId, Group.class);
        if (null == group) {
            return;
        }
        User loginUser = (User) req.getSession()
                .getAttribute(AppConstant.SESSION_LOGIN_USER);
        Map<Integer, UserAuth> oldPers = userAuthDao.getUserPermission(uid);
        for (UserAuth ua : oldPers.values()) {// 删掉之前的
            userAuthDao.delete(ua.getId());
        }
        Map<Integer, GroupAuth> newPers = groupAuthDao
                .getGroupPermission(groupId);
        // 添加新的
        if (null != newPers && 0 != newPers.size()) {
            for (Integer pid : newPers.keySet()) {
                UserAuth ua = new UserAuth();
                ua.setCreater(loginUser.getId());
                ua.setCreateTime(new Date());
                ua.setPermisssionId(pid);
                ua.setUserId(uid);
                userAuthDao.save(ua);
            }
        }
    }
}
