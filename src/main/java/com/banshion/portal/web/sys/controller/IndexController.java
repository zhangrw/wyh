package com.banshion.portal.web.sys.controller;

import com.banshion.portal.sys.authentication.ShiroUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhang.rw on 16-4-9.
 */
@Controller
@RequestMapping("/index")
public class IndexController
{
    @RequestMapping
    public String login(Model  model , HttpServletRequest req){
        ShiroUser su =  (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("user", su );
        return "index";
    }
}
