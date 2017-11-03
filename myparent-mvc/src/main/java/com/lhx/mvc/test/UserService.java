package com.lhx.mvc.test;

import com.lhx.mvc.ioc.annotation.filed.Autowired;
import com.lhx.mvc.ioc.annotation.type.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
}
