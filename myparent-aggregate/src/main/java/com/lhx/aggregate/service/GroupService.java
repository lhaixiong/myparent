package com.lhx.aggregate.service;

import com.lhx.aggregate.dao.impl.GroupDao;
import com.lhx.aggregate.dao.impl.UserDao;
import com.lhx.aggregate.entity.Group;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private UserDao userDao;
    public void initData(){
        groupDao.initGroup();
        userDao.initUser();
    }

    public List<Group> listGroup(String name) {
        String hql="from Group ";
        if (StringUtils.isNotEmpty(name)) {
            hql+=" where name like '%"+name+"%'";
        }
        return groupDao.find(hql);
    }
}
