package com.lhx.aggregate.controller;

import com.alibaba.fastjson.JSONObject;
import com.lhx.aggregate.authority.Permission;
import com.lhx.aggregate.authority.PermissionInfo;
import com.lhx.aggregate.config.AppConstant;
import com.lhx.aggregate.config.AppStart;
import com.lhx.aggregate.entity.User;
import com.lhx.aggregate.service.GroupService;
import com.lhx.aggregate.service.UserAuthService;
import com.lhx.aggregate.service.UserService;
import com.lhx.aggregate.tools.MapUtil;
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
@RequestMapping("/user")
@Permission(id = 10,parent = 0,type = PermissionInfo.TYPE_NODE,name = "后台管理",icon = "",sort = 10)
public class UserController {
    private static final Logger log= LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService service;
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserAuthService userAuthService;
    @RequestMapping("/list")
    @Permission(id = 1100,parent = 10,type = PermissionInfo.TYPE_MENU,name = "用户管理",icon = "",sort = 1100)
    public ModelAndView list(HttpServletRequest req){
        Map<String, String> params = ReqUtil.getParamMap(req);
        ModelAndView mv=new ModelAndView("/user/user_list");
        mv.addObject("list", service.listUser(params.get("account")));
        mv.addAllObjects(params);
        return mv;
    }
    @RequestMapping("/detail")
    @Permission(id = 1101,parent = 1100,type = PermissionInfo.TYPE_BUTTON,name = "用户页面",icon = "",sort = 1101)
    public ModelAndView detail(Integer id,Integer opt){
        ModelAndView mv=new ModelAndView("/user/user_detail");
        User user=null;
        if (id == null) {
            user=new User();
        }else {
            user=service.getById(id);
            if (user == null) {
                mv.addObject("msg","该对象已经被删除了");
            }
        }
        mv.addObject("item", user);
        mv.addObject("opt", opt);
        mv.addObject("groupList",groupService.findAll());
        return mv;
    }
    @RequestMapping(value = "/saveOrUpdate",produces = {"text/html;charset=UTF-8"})
    @Permission(id = 1102,parent = 1100,type = PermissionInfo.TYPE_BUTTON,name = "添加或修改用户",icon = "",sort = 1102)
    @ResponseBody
    public String saveOrUpdate(Integer opt,HttpServletRequest req){
        JSONObject result=new JSONObject();
        result.put("code",200);
        result.put("info", "操作成功!");
        if (opt == null) {
            result.put("code",400);
            result.put("info", "操作失败!opt为空");
            return JSONObject.toJSONString(result);
        }
        try {
            service.saveOrUpdate(opt, req);
        } catch (Exception e) {
            result.put("code",400);
            result.put("info", "操作失败!"+e.getMessage());
            log.info("保存用户失败:{}", e.getMessage());
            e.printStackTrace();
        }

        return JSONObject.toJSONString(result);
    }

    @RequestMapping(value = "/delete",produces = {"text/html;charset=UTF-8"})
    @Permission(id = 1103,parent = 1100,type = PermissionInfo.TYPE_BUTTON,name = "删除用户",icon = "",sort = 1103)
    @ResponseBody
    public String delete(Integer id){
        JSONObject result=new JSONObject();
        result.put("code", 200);
        result.put("info", "操作成功!");
        try {
            if (id != null) {
                service.deleteById(id);
            }
        } catch (Exception e) {
            result.put("info", "操作失败!"+e.getMessage());
            log.info("删除用户失败:{}", e.getMessage());
            e.printStackTrace();
        }
        return JSONObject.toJSONString(result);
    }
    @RequestMapping(value = "/batDelete",produces = {"text/html;charset=UTF-8"})
    @Permission(id = 1104,parent = 1100,type = PermissionInfo.TYPE_BUTTON,name = "批量删除用户",icon = "",sort = 1104)
    @ResponseBody
    public String batDelete(String ids){
        JSONObject result=new JSONObject();
        result.put("code", 200);
        result.put("info", "操作成功!");

        try {
            if (StringUtils.isNotEmpty(ids)) {
                service.batDelete(ids);
            }
        } catch (Exception e) {
            result.put("info", "操作失败!"+e.getMessage());
            log.info("批量删除用户失败:{}", e.getMessage());
            e.printStackTrace();
        }
        return JSONObject.toJSONString(result);
    }
    @RequestMapping(value = "/toUserAuth")
    @Permission(id = 1105,parent = 10,type = PermissionInfo.TYPE_BUTTON,name = "编辑用户权限",icon = "",sort = 1105)
    public ModelAndView toUserAuth(int userId) {
        ModelAndView mav = new ModelAndView("user/user_auth");
        mav.addObject("nodeAuth", AppStart.nodeAuth);
        Set<Integer> userPidSet = userAuthService.getUserPermissions(userId).keySet();
        mav.addObject("userPidSet",JSONObject.toJSONString(userPidSet));
        mav.addObject("userId", userId);
        return mav;
    }
    /**
     * 权限更新
     *
     * @return
     * @since 2015年12月22日 下午2:55:35
     */
//    @RequestMapping(value = "/updateGroupAuth",produces = {"text/html;charset=UTF-8"})
//    @Permission(id = 1007,parent = 10,type = PermissionInfo.TYPE_BUTTON,name = "更新组权限",icon = "",sort = 1007)
//    @ResponseBody
//    public String updateGroupAuth(int gid, String pIds, HttpServletRequest req) {
//        JSONObject result=new JSONObject();
//        int code=AppConstant.CODE_SUC;
//        String info="操作成功!";
//        code = groupAuthService.updateGroupAuth(gid, pIds, req);
//        if(code!=AppConstant.CODE_SUC){
//            info="操作失败!";
//        }
//        result.put("code",code);
//        result.put("info",info);
//        return JSONObject.toJSONString(result);
//    }
    /**
     * 权限更新
     *
     * @return
     */
    @RequestMapping(value = "/updateUserAuth",produces = {"text/html;charset=UTF-8"})
    @Permission(id = 1106,parent = 10,type = PermissionInfo.TYPE_BUTTON,name = "更新用户权限",icon = "",sort = 1106)
    @ResponseBody
    public String updateUserAuth(int userId, String pIds, HttpServletRequest req) {
        JSONObject result=new JSONObject();
        int code=AppConstant.CODE_SUC;
        String info="操作成功!";
        code = userAuthService.updateUserAuth(userId, pIds, req);
        if(code!=AppConstant.CODE_SUC){
            info="操作失败!";
        }
        result.put("code",code);
        result.put("info",info);
        return JSONObject.toJSONString(result);
    }
}
