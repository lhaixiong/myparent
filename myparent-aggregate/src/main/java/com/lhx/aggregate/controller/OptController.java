package com.lhx.aggregate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lhx.aggregate.authority.Permission;
import com.lhx.aggregate.authority.PermissionInfo;

@Controller
@RequestMapping("/opt")
@Permission(id = 20,parent = 0,type = PermissionInfo.TYPE_NODE,name = "基础管理",icon = "",sort = 20)
public class OptController {
    @RequestMapping("/list")
    @Permission(id = 2100,parent = 20,type = PermissionInfo.TYPE_MENU,name = "opt列表",icon = "",sort = 2100)
    public ModelAndView list(){
        ModelAndView mv=new ModelAndView("/opt/opt_list");
        mv.addObject("msg","opt列表");
        return mv;
    }
    @RequestMapping("/add")
    @Permission(id = 2001,parent = 20,type = PermissionInfo.TYPE_BUTTON,name = "添加opt列表",icon = "",sort = 2001)
    public ModelAndView add(){
        ModelAndView mv=new ModelAndView("/opt/opt_list");
        mv.addObject("msg","添加opt列表");
        return mv;
    }
    @RequestMapping("/del")
    @Permission(id = 2002,parent = 20,type = PermissionInfo.TYPE_BUTTON,name = "删除opt列表",icon = "",sort = 2002)
    public ModelAndView del(){
        ModelAndView mv=new ModelAndView("/opt/opt_list");
        mv.addObject("msg","删除opt列表");
        return mv;
    }
}
