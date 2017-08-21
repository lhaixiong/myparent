package com.lhx.aggregate.controller;

import com.lhx.aggregate.authority.Permission;
import com.lhx.aggregate.authority.PermissionInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/operatelog")
@Permission(id = 11,parent = 0,type = PermissionInfo.TYPE_NODE,name = "运营管理",icon = "",sort = 11)
public class OperateLogController {
    @RequestMapping("/list")
    @Permission(id = 1100,parent = 20,type = PermissionInfo.TYPE_MENU,name = "操作日志列表",icon = "",sort = 1100)
    public ModelAndView list(){
        ModelAndView mv=new ModelAndView("/operatelog/operatelog_list");
        mv.addObject("msg","操作日志列表");
        return mv;
    }
    @RequestMapping("/add")
    @Permission(id = 1101,parent = 20,type = PermissionInfo.TYPE_BUTTON,name = "添加操作日志列表",icon = "",sort = 1101)
    public ModelAndView add(){
        ModelAndView mv=new ModelAndView("/operatelog/operatelog_list");
        mv.addObject("msg","添加操作日志列表");
        return mv;
    }
    @RequestMapping("/del")
    @Permission(id = 1102,parent = 20,type = PermissionInfo.TYPE_BUTTON,name = "删除操作日志列表",icon = "",sort = 1102)
    public ModelAndView del(){
        ModelAndView mv=new ModelAndView("/operatelog/operatelog_list");
        mv.addObject("msg","删除操作日志列表");
        return mv;
    }
}
