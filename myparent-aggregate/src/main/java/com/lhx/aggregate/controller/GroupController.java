package com.lhx.aggregate.controller;

import com.alibaba.fastjson.JSONObject;
import com.lhx.aggregate.authority.Permission;
import com.lhx.aggregate.authority.PermissionInfo;
import com.lhx.aggregate.config.AppConstant;
import com.lhx.aggregate.config.AppStart;
import com.lhx.aggregate.config.PCons;
import com.lhx.aggregate.entity.Group;
import com.lhx.aggregate.entity.User;
import com.lhx.aggregate.service.GroupAuthService;
import com.lhx.aggregate.service.GroupService;
import com.lhx.aggregate.tools.ReqUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/group")
@Permission(id = PCons.NODE_ONE,parent = PCons.ROOT,type = PermissionInfo.TYPE_NODE,name = "后台管理",icon = "",sort = PCons.NODE_ONE)
public class GroupController {
    private static final Logger log= LoggerFactory.getLogger(GroupController.class);
    @Autowired
    private GroupService service;
    @Autowired
    private GroupAuthService groupAuthService;
    @RequestMapping("/list")
    @Permission(id = PCons.MENU_GROUP,parent = PCons.NODE_ONE,type = PermissionInfo.TYPE_MENU,name = "用户组管理",icon = "",sort = PCons.MENU_GROUP)
    public ModelAndView list(HttpServletRequest req){
        Map<String, String> params = ReqUtil.getParamMap(req);
        ModelAndView mv=new ModelAndView("/group/group_list");
        mv.addObject("list", service.listGroup(params.get("name")));
        mv.addAllObjects(params);
        return mv;
    }
    @RequestMapping("/detail")
    @Permission(id = PCons.GR1,parent = PCons.MENU_GROUP,type = PermissionInfo.TYPE_BUTTON,name = "用户组查看",icon = "",sort = PCons.GR1)
    public ModelAndView detail(Integer id,Integer opt){
        ModelAndView mv=new ModelAndView("/group/group_detail");
        Group group=null;
        if (id == null) {
           group=new Group();
        }else {
            group=service.getById(id);
            if (group == null) {
                mv.addObject("msg","该对象已经被删除了");
            }
        }
        mv.addObject("item", group);
        mv.addObject("opt", opt);
        return mv;
    }
    @RequestMapping(value = "/saveOrUpdate",produces = {"text/html;charset=UTF-8"})
    @Permission(id = PCons.GR2,parent = PCons.MENU_GROUP,type = PermissionInfo.TYPE_BUTTON,name = "添加或修改组",icon = "",sort = PCons.GR2)
    @ResponseBody
    public String saveOrUpdate(Integer opt,HttpServletRequest req){
        JSONObject result=new JSONObject();
        int code=AppConstant.CODE_SUC;
        String info="操作成功!";
        if (opt == null) {
            result.put("code",AppConstant.CODE_FAIL);
            result.put("info","操作失败!opt为空");
            return JSONObject.toJSONString(result);
        }
        try {
            User loginUser= (User) req.getSession().getAttribute(AppConstant.SESSION_LOGIN_USER);
            Map<String, String> paramMap = ReqUtil.getParamMap(req);
            if (opt== AppConstant.OPT_ADD) {
                Group group=new Group();
                group.setName(paramMap.get("name"));
                group.setType(Integer.parseInt(paramMap.get("type")));
                group.setStatus(Integer.parseInt(paramMap.get("status")));
                group.setCreateTime(new Date());
                group.setCreater(loginUser.getId());
                service.saveOrUpdate(group);
            }else if(opt==AppConstant.OPT_EDIT){
                String id=paramMap.get("id");
                Group group=service.getById(Integer.parseInt(id));
                group.setName(paramMap.get("name"));
                group.setType(Integer.parseInt(paramMap.get("type")));
                group.setStatus(Integer.parseInt(paramMap.get("status")));
                service.saveOrUpdate(group);
            }
        } catch (Exception e) {
            code=AppConstant.CODE_FAIL;
            info="操作失败!"+e.getMessage();
            log.info("保存用户组失败:{}", e.getMessage());
            e.printStackTrace();
        }
        result.put("code",code);
        result.put("info",info);
        return JSONObject.toJSONString(result);
    }
    @RequestMapping(value = "/delete",produces = {"text/html;charset=UTF-8"})
    @Permission(id = PCons.GR3,parent = PCons.MENU_GROUP,type = PermissionInfo.TYPE_BUTTON,name = "删除用户组",icon = "",sort = PCons.GR3)
    @ResponseBody
    public String delete(Integer id){
        JSONObject result=new JSONObject();
        int code=AppConstant.CODE_SUC;
        String info="操作成功!";
        try {
            if (id != null) {
                service.deleteById(id);
            }
        } catch (Exception e) {
            code=AppConstant.CODE_FAIL;
            info="操作失败!"+e.getMessage();
            log.info("删除用户组失败:{}", e.getMessage());
            e.printStackTrace();
        }
        result.put("code",code);
        result.put("info",info);
        return JSONObject.toJSONString(result);
    }
    @RequestMapping(value = "/batDelete",produces = {"text/html;charset=UTF-8"})
    @Permission(id = PCons.GR4,parent = PCons.MENU_GROUP,type = PermissionInfo.TYPE_BUTTON,name = "批量删除用户组",icon = "",sort = PCons.GR4)
    @ResponseBody
    public String batDelete(String ids){
        JSONObject result=new JSONObject();
        int code=AppConstant.CODE_SUC;
        String info="操作成功!";
        try {
            if (StringUtils.isNotEmpty(ids)) {
                service.batDelete(ids);
            }
        } catch (Exception e) {
            code=AppConstant.CODE_FAIL;
            info="操作失败!"+e.getMessage();
            log.info("批量删除用户组失败:{}", e.getMessage());
            e.printStackTrace();
        }
        result.put("code",code);
        result.put("info",info);
        return JSONObject.toJSONString(result);
    }
    @RequestMapping("/checkGroupUser")
    @Permission(id = PCons.GR5,parent = PCons.MENU_GROUP,type = PermissionInfo.TYPE_BUTTON,name = "查看组用户",icon = "",sort = PCons.GR5)
    public ModelAndView checkGroupUser(Integer groupId){
        ModelAndView mv=new ModelAndView("/group/group_user_list");
        if (groupId == null) {
            mv.addObject("msg","参数错误");
            return mv;
        }
        mv.addObject("list", service.listGroupUser(groupId));
        mv.addObject("groupName",service.getById(groupId).getName());
        return mv;
    }
    @RequestMapping(value = "/group_auth.html")
    @Permission(id = PCons.GR6,parent = PCons.MENU_GROUP,type = PermissionInfo.TYPE_BUTTON,name = "编辑组权限",icon = "",sort = PCons.GR6)
    public ModelAndView toAuthList(int id) {
        ModelAndView mav = new ModelAndView("group/group_auth");
        mav.addObject("nodeAuth", AppStart.nodeAuth);
        Set<Integer> groupPidSet = groupAuthService.getGroupPermission(id).keySet();
        mav.addObject("groupPidSet",JSONObject.toJSONString(groupPidSet));
        mav.addObject("gid", id);
        return mav;
    }
    /**
     * 权限更新
     *
     * @return
     * @since 2015年12月22日 下午2:55:35
     */
    @RequestMapping(value = "/updateGroupAuth",produces = {"text/html;charset=UTF-8"})
    @Permission(id = PCons.GR7,parent = PCons.MENU_GROUP,type = PermissionInfo.TYPE_BUTTON,name = "更新组权限",icon = "",sort = PCons.GR7)
    @ResponseBody
    public String updateGroupAuth(int gid, String pIds, HttpServletRequest req) {
        JSONObject result=new JSONObject();
        int code=AppConstant.CODE_SUC;
        String info="操作成功!";
        code = groupAuthService.updateGroupAuth(gid, pIds, req);
        if(code!=AppConstant.CODE_SUC){
            info="操作失败!";
        }
        result.put("code",code);
        result.put("info",info);
        return JSONObject.toJSONString(result);
    }
}
