package com.lhx.aggregate.dao.impl;

import com.lhx.aggregate.config.AppConstant;
import com.lhx.aggregate.entity.Group;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class GroupDao extends BaseDaoImpl<Group>{
    public void initGroup(){
        String hql="from Group where type="+ AppConstant.GROUP_ADMIN;
        List<Group> groups = this.find(hql);
        if (groups == null||groups.isEmpty()) {
            Group group=new Group();
            group.setName("超级管理员组");
            group.setCreater(1);
            group.setCreateTime(new Date());
            group.setType(AppConstant.GROUP_ADMIN);
            this.saveOrUpdate(group);
        }
    }
}
