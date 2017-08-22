package com.lhx.aggregate.tools;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求处理工具
 */
public class ReqUtil {
    /**
     * 获取请求参数
     * @param req
     * @return
     */
    public static Map<String,String> getParamMap(HttpServletRequest req){
        Map<String,String> result=new HashMap<>();
        Enumeration<String> keys = req.getParameterNames();
        while (keys.hasMoreElements()){
            String key= keys.nextElement();
            result.put(key,req.getParameter(key));
        }
        return  result;
    }
}
