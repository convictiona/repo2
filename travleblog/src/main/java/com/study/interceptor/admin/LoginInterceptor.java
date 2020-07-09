package com.study.interceptor.admin;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import com.study.domain.admin.Menu;
import com.study.util.MenuUtil;
import net.sf.json.JSONObject;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*后台登录拦截器*/
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI=request.getRequestURI();
       Object admin= request.getSession().getAttribute("admin");
        if(admin==null){
            //表示未登录或登录失败
            System.out.println("链接"+requestURI+"进入拦截器:");
            String header=request.getHeader("X-Requsted-With");
            //判断是否是ajax请求
            if ("XMLHttpRequest".equals(header)){
                Map<String,String > map=new HashMap<String,String>();
                map.put("type","error");
                map.put("msg","登录会话超时或还未登录，请重新登录");
                response.getWriter().write(JSONObject.fromObject(map).toString());
                return false;
            }
            response.sendRedirect(request.getContextPath()+"/system/login");
            return false;
        }
             String mid=request.getParameter("_mid");
        if (!StringUtils.isEmpty(mid)){
           List<Menu> buttonMenu= MenuUtil.getButton((List< Menu >) request.getSession().getAttribute("userMenus"),Long.valueOf(mid));
           request.setAttribute("buttonMenus",buttonMenu);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
