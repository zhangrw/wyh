package com.banshion.portal.web.index.controller;

import com.banshion.portal.sys.authentication.ShiroUser;
import com.banshion.portal.util.Securitys;
import com.banshion.portal.web.index.dao.TindexUserMapper;
import com.banshion.portal.web.sys.dao.SysUserMapper;
import com.banshion.portal.web.sys.domain.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/16.
 */
@Controller
@RequestMapping("basic")
public class IndexUserController {

    @Autowired
    private TindexUserMapper tuserDao;

    @Autowired
    private SysUserMapper userDao;

    @RequestMapping
    public String index(Model model){
        ShiroUser user = Securitys.getUser();
        if( user.isAdmin() ){
            System.out.println(Securitys.getSubject().isPermitted("sys:menu"));
        }else{
            System.out.println(Securitys.getSubject().isPermitted("sys:menu"));
        }
    return "basic/index";
    }

    @RequestMapping("getdata")
    @ResponseBody
    public List<ShiroUser> getData(){
        if( Securitys.isAdmin() ){
            List<ShiroUser> list = userDao.getShiroUser();
            return list;
        }else{
        List<ShiroUser> list  = new ArrayList<ShiroUser>();
        list.add(Securitys.getUser());
        return list;
        }
    }

    @RequestMapping("openmodaluserinfo")
    public String modify(Model model,@RequestParam(value = "id",required = false,defaultValue = "") String id){
        ShiroUser user;
        if(StringUtils.isNotBlank(id)){
            user = userDao.getShiroUser(id);
        }
        else{
            user = new ShiroUser();
        }
        model.addAttribute("shirouser",user);
        return "basic/form";
    }

    @RequestMapping("save")
    public @ResponseBody ResponseEntity<?> save(ShiroUser shiroUser){
        Map<String,Object> result = new HashMap<String,Object>();



        return new ResponseEntity(result, HttpStatus.OK);
    }

    @RequestMapping("delete")
    public  @ResponseBody ResponseEntity<?> delete(@RequestParam(value="ids")String ids ){
        Map<String,Object> result = new HashMap<String,Object>();
        userDao.deleteByIds(ids.split(","));
        tuserDao.deleteByIds(ids.split(","));
        return new ResponseEntity(result, HttpStatus.OK);
    }

}
