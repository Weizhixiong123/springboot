package com.controller;

import com.Model.PageHelper;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/")
public class CommonController {

@RequestMapping("/")
public ModelAndView Index(){
    ModelAndView modelAndView = new ModelAndView("Common/Index");
     modelAndView.addObject("Module",new PageHelper().GetMoudle());
    return modelAndView;
}
    @RequestMapping("/Login")
    public ModelAndView Login(){
        ModelAndView modelAndView = new ModelAndView("Common/Login");
        return modelAndView;
    }
    @RequestMapping("/Welcome")
    public ModelAndView Welcome(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("Common/Welcome");

        String LoginID="";
        Cookie[] cookies =request.getCookies();
        for(Cookie cookie :cookies) {
            if(cookie.getName().equals("login")) {
                LoginID = cookie.getValue();
            }
        }
        mv.addObject("cookies",LoginID);
        return mv;
    }
    @RequestMapping("/LoginCheck")
    public String LoginCheck(HttpServletRequest request, HttpServletResponse response) {
        JSONObject object = JSONObject.fromObject(new PageHelper().GetPost(request));

        if(object.get("login").equals("weizhixiong123") &&object.get("password").equals("1234")){
            Cookie cookie = new Cookie("login","Wei");
            cookie.setMaxAge(7*24*60*60);
            response.addCookie(cookie);
            return "1";

        }
        else {
            return "0";
        }

    }
    @RequestMapping("/EndLogin")
    public void EndLogin(HttpServletRequest request,HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals("login")) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        try {
            response.sendRedirect("/Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


  }
