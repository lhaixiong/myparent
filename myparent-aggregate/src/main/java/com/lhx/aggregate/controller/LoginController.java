package com.lhx.aggregate.controller;

import com.alibaba.fastjson.JSONObject;
import com.lhx.aggregate.config.AppConstant;
import com.lhx.aggregate.config.AppStart;
import com.lhx.aggregate.entity.Group;
import com.lhx.aggregate.entity.User;
import com.lhx.aggregate.entity.UserAuth;
import com.lhx.aggregate.patchca.color.SingleColorFactory;
import com.lhx.aggregate.patchca.filter.predefined.CurvesRippleFilterFactory;
import com.lhx.aggregate.patchca.service.ConfigurableCaptchaService;
import com.lhx.aggregate.patchca.utils.encoder.EncoderHelper;
import com.lhx.aggregate.service.GroupService;
import com.lhx.aggregate.service.UserAuthService;
import com.lhx.aggregate.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private GroupService groupService;
    //登陆
    @RequestMapping("/login")
    public ModelAndView login(String account,String password,String code,HttpServletRequest req){
        try {
            HttpSession session = req.getSession();
            User loginUser= (User) session.getAttribute(AppConstant.SESSION_LOGIN_USER);
            if (loginUser != null) {
                return index();
            }
            String scode= (String) session.getAttribute("code");
            if (!StringUtils.endsWithIgnoreCase(code, scode)) {
                req.setAttribute("msg","验证码不正确");
                ModelAndView mv=new ModelAndView("login");
                return mv;
            }
            //1认证
            loginUser = userService.getLoginUser(account, password);
            if (loginUser == null) {
                req.setAttribute("msg","账号或密码错误!");
                ModelAndView mv=new ModelAndView("login");
                return mv;
            }
            session.setAttribute(AppConstant.SESSION_LOGIN_USER,loginUser);
            //2授权信息
            Map<Integer, UserAuth> uas = userAuthService.getUserPermissions(loginUser.getId());
            Set<Integer> pIdSet = uas.keySet();
            Group group = groupService.getById(loginUser.getGroupId());
            if(group.getType()==AppConstant.GROUP_ADMIN&&pIdSet.isEmpty()){//用户属于管理员组并且上没有分配权限
                pIdSet=new HashSet<>();
                for (String pid : AppStart.allAuth.keySet()) {
                    pIdSet.add(Integer.parseInt(pid));
                }
            }
            session.setAttribute(AppConstant.SESSION_USER_AUTH,pIdSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return index();
    }

    //登出
    @RequestMapping("/logout")
    public ModelAndView logout(HttpSession session){
        if (session != null) {
            Enumeration<String> keys = session.getAttributeNames();
            while (keys.hasMoreElements()){
                String attr = keys.nextElement();
                System.out.println("session存在的属性有:"+attr);
                session.removeAttribute(attr);
            }
        }
        ModelAndView mv=new ModelAndView("login");
        return mv;
    }
//    //首页
//    @RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView mv =new ModelAndView("/index");
        mv.addObject("menuMap", JSONObject.toJSONString(AppStart.nodeAuth));
        return mv;
    }
    //控制面板
    @RequestMapping("/main")
    public ModelAndView main(){
        ModelAndView mv=new ModelAndView("/main/main");
        mv.addObject("menuMap", AppStart.nodeAuth);
        return mv;
    }
    //生成验证码
    @RequestMapping(value = "/generateImage")
    public void ImageCaptcha(HttpSession session,
                             HttpServletResponse response) {
        try {
            ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
            cs.setColorFactory(new SingleColorFactory(new Color(25, 60, 170)));
            cs.setFilterFactory(new CurvesRippleFilterFactory(cs.getColorFactory()));
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/png");
            ServletOutputStream sos = response.getOutputStream();
            String code = EncoderHelper.getChallangeAndWriteImage(cs, "png",
                    sos);
            session.setAttribute("code", code);
            sos.flush();
            sos.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
