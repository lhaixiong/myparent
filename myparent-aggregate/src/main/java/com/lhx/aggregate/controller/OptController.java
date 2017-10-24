package com.lhx.aggregate.controller;

import com.lhx.aggregate.config.PCons;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lhx.aggregate.authority.Permission;
import com.lhx.aggregate.authority.PermissionInfo;

@Controller
@RequestMapping("/opt")
@Permission(id = PCons.NODE_TWO,parent = PCons.ROOT,type = PermissionInfo.TYPE_NODE,name = "数据分析",icon = "",sort = PCons.NODE_TWO)
public class OptController {
    @RequestMapping("/list")
    @Permission(id = PCons.MENU_OPT,parent = PCons.NODE_TWO,type = PermissionInfo.TYPE_MENU,name = "opt列表",icon = "",sort = PCons.MENU_OPT)
    public ModelAndView list(){
        ModelAndView mv=new ModelAndView("/opt/opt_list");
        mv.addObject("msg","opt列表");
        return mv;
    }
    @RequestMapping("/add")
    @Permission(id = PCons.OPT1,parent = PCons.MENU_OPT,type = PermissionInfo.TYPE_BUTTON,name = "添加opt列表",icon = "",sort = PCons.OPT1)
    public ModelAndView add(){
        ModelAndView mv=new ModelAndView("/opt/opt_list");
        mv.addObject("msg","添加opt列表");
        return mv;
    }
    @RequestMapping("/del")
    @Permission(id = PCons.OPT2,parent = PCons.MENU_OPT,type = PermissionInfo.TYPE_BUTTON,name = "删除opt列表",icon = "",sort = PCons.OPT2)
    public ModelAndView del(){
        ModelAndView mv=new ModelAndView("/opt/opt_list");
        mv.addObject("msg","删除opt列表");
        return mv;
    }
}
