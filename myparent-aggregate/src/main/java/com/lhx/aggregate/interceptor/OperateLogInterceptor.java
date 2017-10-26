package com.lhx.aggregate.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.lhx.aggregate.authority.PermissionInfo;
import com.lhx.aggregate.config.AppConstant;
import com.lhx.aggregate.config.AppStart;
import com.lhx.aggregate.entity.OperateLog;
import com.lhx.aggregate.entity.User;
import com.lhx.aggregate.service.OperateLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.*;

/**
 * 操作日志拦截器
 */
public class OperateLogInterceptor implements HandlerInterceptor{
    private static final Logger log= LoggerFactory.getLogger(OperateLogInterceptor.class);
    @Autowired
    private OperateLogService logService;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object o) throws Exception {
        String reqURI = req.getRequestURI();
        HttpSession session = req.getSession(true);
        System.out.println("OperateLogInterceptor.uri:"+reqURI);
        User loginUser= (User) session.getAttribute(AppConstant.SESSION_LOGIN_USER);
        Set<Integer> pIdSet= (Set<Integer>) session.getAttribute(AppConstant.SESSION_USER_AUTH);

        PermissionInfo p = AppStart.getPerByAccessUrl(reqURI);
        if(p!=null){
            boolean hasPermission=pIdSet.contains(p.getId());

            if (!hasPermission) {
                String info="没有权限["+p.getName()+"]";
                log.info(info);
                JSONObject result=new JSONObject();
                result.put("code",AppConstant.CODE_FAIL);
                result.put("info", info);
                PrintWriter pw = res.getWriter();
                pw.println(JSONObject.toJSONString(result));
                pw.flush();
                pw.close();
                return false;
            }

            //若权限允许，记录访问日志
            String paramStr=getAllRequestParams(req);
            if (hasPermission&&paramStr.length()>2) {
                OperateLog log=new OperateLog();
                System.out.println("OperateLogInterceptor保存日志:"+p.getName());
                log.setAccessName(p.getName());
                log.setAccessPath(reqURI);
                log.setParams(paramStr);
                log.setCreater(loginUser.getId());
                log.setDate(new Date());
                logService.save(log);
            }
        }


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse res, Object o, ModelAndView mv) throws Exception {
        //在执行完controller代码后,渲染视图之前执行
    }

    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object o, Exception e) throws Exception {

    }
    /**
     * 获取请求的所有参数
     *
     * @param request
     * @return
     * @since 2015年12月24日 下午1:53:30
     */
    private String getAllRequestParams(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder("{");
        Map<?, ?> allParams = request.getParameterMap();
        for (Map.Entry<?, ?> entry : allParams.entrySet()) {
            String key = (String) entry.getKey();
            StringBuilder value = new StringBuilder("");
            Object[] values = (Object[]) entry.getValue();
            if (null == values || 0 == values.length) {
                continue;
            }
            for (Object object : values) {
                value.append(object.toString()).append(",");
            }
            value = value.deleteCharAt(value.length() - 1);
            sb.append("\"" + key + "\":").append("\"" + value.toString() + "\"")
                    .append(",");
        }
        if (sb.length() > 2) {
            sb = sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("}");
        return sb.toString();
    }
}
