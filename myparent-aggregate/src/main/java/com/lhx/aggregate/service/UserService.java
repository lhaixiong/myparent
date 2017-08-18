package com.lhx.aggregate.service;

import com.lhx.aggregate.dao.impl.OperateLogDao;
import com.lhx.aggregate.dao.impl.UserDao;
import com.lhx.aggregate.entity.OperateLog;
import com.lhx.aggregate.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao dao;
    @Autowired
    private OperateLogDao operateLogDao;
    public void testOne(){
        for (int i = 0; i < 10; i++) {
            User user=new User();
            user.setName("lhx");
            user.setAge(13);
            user.setBirthday(new Date());
            dao.save(user);
        }
    }
    public void testTwo(){
        User u = dao.getCurrentSession().load(User.class, 1);
        u.setName("name");
        dao.saveOrUpdate(u);
        OperateLog operateLog=new OperateLog();
        operateLog.setAccessName("aaaaa");
        operateLog.setDate(new Date());
        operateLogDao.save(operateLog);
        System.out.println(dao.count());
//        int b=3/0;
    }

    public List<User> listUser() {
        return dao.findAll();
    }
}
