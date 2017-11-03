package com.lhx.mvc.test;

import com.lhx.mvc.ioc.annotation.filed.Autowired;
import com.lhx.mvc.ioc.annotation.type.Repository;

@Repository
public class UserDao {
    @Autowired
    private User user;
}
