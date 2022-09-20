package com.Model;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(1)
@WebFilter(filterName = "loginFilter",urlPatterns = "/*") //拦截器
public class LoginFilter implements Filter {
    String[] NoNeedConfirm={"/Login","/css","/img","/js"};

    @Override
 public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
 HttpServletRequest req=(HttpServletRequest)request;

        boolean loginState =false;
        boolean urlState =false;

        Cookie[] cookies = req.getCookies();
        if(req.getCookies() != null){
             for(Cookie cookie : cookies){
                 if(cookie.getName().equals("login")){
                     loginState = true;
                 }
             }
        }

        for(String item : NoNeedConfirm){
             if (req.getRequestURI().contains(item)){
                 urlState = true;
             }
        }

        if(urlState ==false && loginState ==false){
            HttpServletResponse resp = (HttpServletResponse)response;
            resp.sendRedirect("/Login");
        }
        chain.doFilter(request,response);
    }
}
