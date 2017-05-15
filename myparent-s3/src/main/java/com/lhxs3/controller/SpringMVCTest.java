package com.lhxs3.controller;

import com.lhxs3.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/springmvc")
//@SessionAttributes(value = {"user"})
public class SpringMVCTest {
    private static final String SUCCESS="success";
    @ModelAttribute
    public void getUser(@RequestParam(value = "id",required = false) Integer id,Map<String,Object> map){
        System.out.println("ModelAttribute method");
        if(id!=null){
            System.out.println("从数据库中获取一个user");
            User user=new User(1,"lhx","lhx123","lhx@qq.com",22);

            map.put("user",user);
        }
    }

    @RequestMapping(value = "/testModelAttribute")
    public String testModelAttribute(User user){
        System.out.println("修改前user>>>"+user);
        user.setAge(555);
        System.out.println("修改后user>>>"+user);
        return SUCCESS;
    }
    @RequestMapping(value = "/testSessionAttributes")
    public String testSessionAttributes(Map<String,Object> map){
        System.out.println("testSessionAttributes");
        User user=new User("lhx","lhx123","lhx@qq.com",33);
        map.put("user",user);
        return SUCCESS;
    }
    @RequestMapping(value = "/testMap")
    public String testMap(Map<String,Object> map){
        System.out.println(map.getClass().getName());
        map.put("names", Arrays.asList("aa","bb","cc"));
        return SUCCESS;
    }
    @RequestMapping(value = "/testModelAndView")
    public ModelAndView testModelAndView(){
        ModelAndView mv=new ModelAndView(SUCCESS);
        System.out.println("testModelAndView");
        mv.addObject("time",new Date());
        return mv;
    }
    @RequestMapping(value = "/testServletAPI")
    public void testServletAPI(HttpServletRequest request,HttpServletResponse  response,HttpSession session,Writer out){
        System.out.println(request);
        System.out.println(response);
        System.out.println(session);
        System.out.println(out);
        try {
            out.write("testServletAPI");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value = "/testPojo")
    public String testPojo(User user){
        System.out.println("user>>>>"+user);
        return  SUCCESS;
    }
    @RequestMapping(value = "/testCookieValue")
    public String testCookieValue(@CookieValue(value = "JSESSIONID") String sessionId){
        System.out.println("CookieValue sessionId"+sessionId);
        return SUCCESS;
    }
    @RequestMapping(value = "/testRequestHeader")
    public String testRequestHeader(@RequestHeader(value = "Accept-Language") String header){
        System.out.println("testRequestHeader Accept-Language:"+header);
        return SUCCESS;
    }

    @RequestMapping(value = "/testRequestParam")
    public String testRequestParam(@RequestParam(value = "username") String username,
                                   @RequestParam(value = "age",required = false,defaultValue ="0" ) Integer age){
        System.out.println("testRequestParam username:"+username+",age:"+age);
        return  SUCCESS;
    }

    @RequestMapping(value = "/testRest/{id}",method = RequestMethod.PUT)
    public String testRestPut(@PathVariable(value = "id") Integer id){
        System.out.println("testRestPut>>"+id);
        return SUCCESS;
    }
    @RequestMapping(value = "/testRest/{id}",method = RequestMethod.DELETE)
    public String testRestDelete(@PathVariable(value = "id") Integer id){
        System.out.println("testRestDelete>>"+id);
        return SUCCESS;
    }
    @RequestMapping(value = "/testRest",method = RequestMethod.POST)
    public String testRestPost(){
        System.out.println("testRestPost>>");
        return SUCCESS;
    }
    @RequestMapping(value = "/testRest/{id}",method = RequestMethod.GET)
    public String testRestGet(@PathVariable(value = "id") Integer id){
        System.out.println("testRestGet>>"+id);
        return SUCCESS;
    }
    @RequestMapping(value = "/testPathVariable/{id}")
    public String testPathVariable(@PathVariable(value = "id") Integer id){
        System.out.println("testPathVariable>>"+id);
        return SUCCESS;
    }
    @RequestMapping(value = "/testAntPath/*/abc")
    public String testAntPath(){
        System.out.println("testAntPath");
        return SUCCESS;
    }
    @RequestMapping(value = "testParamsAndHeaders",params = {"username","age!=10"},headers = {"Accept-Language=zh-CN,zh;q=0.8"})
    public String testParamsAndHeaders(){
        System.out.println("testParamsAndHeaders");
        return SUCCESS;
    }
    @RequestMapping(value = "/testMethod",method = RequestMethod.POST)
    public String testMethod(){
        System.out.println("testMethod");
        return SUCCESS;
    }
    @RequestMapping("/testRequestMapping")
    public String testRequestMapping(){
        System.out.println("testRequestMapping");
        return SUCCESS;
    }
}
