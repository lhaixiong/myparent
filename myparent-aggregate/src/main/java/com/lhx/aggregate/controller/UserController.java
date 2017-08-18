package com.lhx.aggregate.controller;

import com.lhx.aggregate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;
    @RequestMapping("/list")
    public ModelAndView list(){
        ModelAndView mv=new ModelAndView("/user/user_list");
        mv.addObject("list", service.listUser());
        return mv;
    }

}
