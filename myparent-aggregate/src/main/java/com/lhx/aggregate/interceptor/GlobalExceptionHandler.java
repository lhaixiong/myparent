package com.lhx.aggregate.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理器
 */
public class GlobalExceptionHandler implements HandlerExceptionResolver{
    private static final Logger log= LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @Override
    public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse res, Object o, Exception e) {
        log.error("全局异常捕捉",e);
        String requestURI = req.getRequestURI();
        System.out.println(requestURI);
        ModelAndView mv=new ModelAndView("error");
        mv.addObject("ex",e);
        return mv;
    }
}
