package com.banshion.portal.web.sys.controller;

import com.banshion.portal.sys.authentication.MyAuthenticationToken;
import com.banshion.portal.web.sys.dao.UserInfoMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhang.rw on 16-4-9.
 */
@Controller
@RequestMapping("/login")
public class LoginController
{
    private final static Logger log = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private UserInfoMapper userDao;

    @RequestMapping
    public String index(HttpServletRequest request , Model model){
        String msg = "";
        String userName = request.getParameter("userName") == null ? "" : request.getParameter("userName");
        String password = request.getParameter("password") == null ? "" : request.getParameter("password");
        if( "".equals(userName) || "".equals(password) ){
            msg = "帐号或密码不能为空";
            model.addAttribute("message", msg);
            return "login/login";
        }
        //UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        MyAuthenticationToken token = new MyAuthenticationToken(userName , password);
        token.setRememberMe(true);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            if (subject.isAuthenticated()) {
//                return "redirect:/sys/user";
                return "redirect:/index";
            } else {
                return "login/login";
            }
        } catch (IncorrectCredentialsException e) {
            msg = "登录密码错误. Password for account " + token.getPrincipal() + " was incorrect.";
            model.addAttribute("message", msg);
            log.error(msg);
        } catch (ExcessiveAttemptsException e) {
            msg = "登录失败次数过多";
            model.addAttribute("message", msg);
            log.error(msg);
        } catch (LockedAccountException e) {
            msg = "帐号已被锁定. The account for username " + token.getPrincipal() + " was locked.";
            model.addAttribute("message", msg);
            log.error(msg);
        } catch (DisabledAccountException e) {
            msg = "帐号已被禁用. The account for username " + token.getPrincipal() + " was disabled.";
            model.addAttribute("message", msg);
            log.error(msg);
        } catch (ExpiredCredentialsException e) {
            msg = "帐号已过期. the account for username " + token.getPrincipal() + "  was expired.";
            model.addAttribute("message", msg);
            log.error(msg);
        } catch (UnknownAccountException e) {
            msg = "帐号不存在. There is no user with username of " + token.getPrincipal();
            model.addAttribute("message", msg);
            log.error(msg);
        } catch (UnauthorizedException e) {
            msg = "您没有得到相应的授权！" + e.getMessage();
            model.addAttribute("message", msg);
            log.error(msg);
        } catch (Exception e){
            msg = "登录出现异常，请稍后重试！" + e.getMessage();
            model.addAttribute("message", msg);
            log.error(msg);
            e.printStackTrace();
        }

        return "login/login";
    }


    @RequestMapping("/logout")
    public String logout(){
        SecurityUtils.getSubject().logout();
        return "/login/login";
    }
}
