package com.lhx.aggregate.service;

import com.lhx.aggregate.dao.impl.GroupDao;
import com.lhx.aggregate.dao.impl.UserDao;
import com.lhx.aggregate.entity.Group;
import com.lhx.aggregate.entity.User;
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

    public Group getById(Integer id) {
        return groupDao.getById(id,Group.class);
    }

    public void saveOrUpdate(Group group) {
        groupDao.saveOrUpdate(group);
    }

    public void deleteById(Integer id) {
        groupDao.delete(id);
    }

    public void batDelete(String ids) {
        String temp=ids.substring(1,ids.length()-1);
        String groupIds=temp.replaceAll("\"", "");
        String hql="delete from Group t where t.id in ("+groupIds+")";
        groupDao.executeHql(hql);
    }

    public List<User> listGroupUser(Integer groupId) {
        String hql="from User t where t.groupId="+groupId;
        return userDao.find(hql);
    }

    public List<Group> findAll() {
        String hql="from Group order by id";
        return groupDao.find(hql);
    }
}
