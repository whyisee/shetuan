package com.shetuan.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/4/13 19:07
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handle) throws IOException {
       // System.out.println("Test--------19:10--->:"+"111"+handle);
        HttpSession session = request.getSession(true);
        Object username=session.getAttribute("login");
        //System.out.println("Test--------22:22--->:"+username);
        if(null!=username) {//已登录
            return true;
        }else {//未登录
            //直接重定向到登录页面
            response.sendRedirect(request.getContextPath()+"/login.jsp");
            return false;
        }
        //return true;
    }


}
