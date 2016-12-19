package com.banshion.portal.web.sys.controller;

import com.banshion.portal.web.sys.domain.SysPermission;
import com.banshion.portal.web.sys.service.PermissionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by zhang.rw on 16-4-20.
 */
@Controller
@RequestMapping("/sys/permission")
public class PermissionController
{
    @Autowired
    private PermissionService permissionDao;

    @RequestMapping
    public String index(){
        return "sys/permission/list";
    }

    @RequestMapping(value = "/getallpermission" )//, method = RequestMethod.POST
    public @ResponseBody
    List<SysPermission> getAllPermission(){
        return permissionDao.getAllPermission();
    }

    @RequiresPermissions("permission:add")
    @RequestMapping(value="add",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean addPermission(@Valid SysPermission permission ,
                                               Errors errors)
    {
        if( errors.hasErrors() ){
            return false;
        }
        try
        {
            permissionDao.addPermission(permission);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @RequiresPermissions("permission:del")
    @RequestMapping(value="delete",method=RequestMethod.POST,produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean delPermission(@RequestParam("id")String id){
        try
        {
            permissionDao.delPermission(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @RequiresPermissions("permission:update")
    @RequestMapping(value="update",method=RequestMethod.POST,produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody  boolean updatePermission(@Valid SysPermission permission,Errors errors){
        if( errors.hasErrors() ){
            return false;
        }
        try
        {
            permissionDao.updatePermission(permission);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping(value="checknameexists",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean checknameexists(SysPermission permission){

        SysPermission per = permissionDao.checkRepeat(permission);
        if( per == null ){
            return false;
        }
        return true;
    }

    @RequestMapping("/getparents")
    public @ResponseBody List<SysPermission> getParents(){
        return permissionDao.getParents();
    }
}
