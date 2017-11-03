package com.lhx.mvc.test;

import com.lhx.mvc.ioc.annotation.filed.Autowired;
import com.lhx.mvc.ioc.annotation.type.Controller;

@Controller
public class UserController {
    @Autowired()
    private UserService userService;
}
