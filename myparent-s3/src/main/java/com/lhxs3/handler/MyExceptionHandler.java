package com.lhxs3.handler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class MyExceptionHandler {
    private static final String ERROR="error";
    @ExceptionHandler(value = {Exception.class})
    public ModelAndView handleException(Exception ex){
        System.out.println("........(全局异常处理器？？？)MyExceptionHandler handleException出异常了>>>"+ex);
        ex.printStackTrace();
        ModelAndView mv=new ModelAndView(ERROR);
        mv.addObject("errorMsg", ex);
        return mv;
    }
}
