package com.lhx.aggregate.controller;

import com.lhx.aggregate.authority.Permission;
import com.lhx.aggregate.authority.PermissionInfo;
import com.lhx.aggregate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
@Permission(id = 10,parent = 0,type = PermissionInfo.TYPE_NODE,name = "后台管理",icon = "",sort = 10)
public class UserController {
    @Autowired
    private UserService service;
    @RequestMapping("/list")
    @Permission(id = 1000,parent = 10,type = PermissionInfo.TYPE_MENU,name = "用户列表",icon = "",sort = 1000)
    public ModelAndView list(){
        ModelAndView mv=new ModelAndView("/user/user_list");
//        mv.addObject("list", service.listUser());
        mv.addObject("msg", "用户列表");
        return mv;
    }
    @RequestMapping("/add")
    @Permission(id = 1001,parent = 10,type = PermissionInfo.TYPE_BUTTON,name = "添加用户",icon = "",sort = 1001)
    public ModelAndView add(){
        ModelAndView mv=new ModelAndView("/user/user_add");
        mv.addObject("msg", "添加用户");
        return mv;
    }
    @RequestMapping("/del")
    @Permission(id = 1002,parent = 10,type = PermissionInfo.TYPE_BUTTON,name = "删除用户",icon = "",sort = 1002)
    public ModelAndView del(){
        ModelAndView mv=new ModelAndView("/user/user_add");
        mv.addObject("msg", "删除用户");
        return mv;
    }
}
