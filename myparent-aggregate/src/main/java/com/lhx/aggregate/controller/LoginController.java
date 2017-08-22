package com.lhx.aggregate.controller;

import com.alibaba.fastjson.JSONObject;
import com.lhx.aggregate.config.AppStart;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/login")
public class LoginController {
    //首页
    @RequestMapping("/")
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
}
