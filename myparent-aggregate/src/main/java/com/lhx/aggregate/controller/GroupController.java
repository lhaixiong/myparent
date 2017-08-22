package com.lhx.aggregate.controller;

import com.lhx.aggregate.authority.Permission;
import com.lhx.aggregate.authority.PermissionInfo;
import com.lhx.aggregate.service.GroupService;
import com.lhx.aggregate.tools.ReqUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/group")
@Permission(id = 10,parent = 0,type = PermissionInfo.TYPE_NODE,name = "后台管理",icon = "",sort = 10)
public class GroupController {
    @Autowired
    private GroupService service;
    @RequestMapping("/list")
    @Permission(id = 1200,parent = 10,type = PermissionInfo.TYPE_MENU,name = "用户组管理",icon = "",sort = 1200)
    public ModelAndView list(HttpServletRequest req){
        Map<String, String> params = ReqUtil.getParamMap(req);
        ModelAndView mv=new ModelAndView("/group/group_list");
        mv.addObject("list", service.listGroup(params.get("name")));
        mv.addAllObjects(params);
        return mv;
    }
}
