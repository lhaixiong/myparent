package com.lhx.aggregate.dao.impl;

import com.lhx.aggregate.config.AppConstant;
import com.lhx.aggregate.entity.Group;
import com.lhx.aggregate.entity.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class UserDao extends BaseDaoImpl<User> {
    @Autowired
    private GroupDao groupDao;
    public void initUser(){
        String hql = "From User Where account = '" + AppConstant.ACCOUNT_ADMIN + "'";
        List<?> adminResult = this.find(hql);
        if (adminResult == null || 0 == adminResult.size()) {// 还没存在根用户admin
            hql = "From Group Where type = " + AppConstant.GROUP_ADMIN;
            List<?> groupResult = groupDao.find(hql);
            Group adminGroup = null;
            if (groupResult != null && 0 != groupResult.size()) {
                adminGroup = (Group) groupResult.get(0);
            }
            User user = new User();
            user.setAccount("admin");
            user.setPassword("123");
            user.setNickname("超级管理员");
            user.setCreateTime(new Date());
            user.setGroupId(null == adminGroup ? 1 : adminGroup.getId());
            this.saveOrUpdate(user);
        }
    }
}
