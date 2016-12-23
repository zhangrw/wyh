package com.banshion.portal.web.sys.controller;

import com.banshion.portal.web.sys.domain.SysPermission;
import com.banshion.portal.web.sys.filter.RoleFilter;
import com.banshion.portal.web.sys.domain.SysRole;
import com.banshion.portal.web.sys.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by zhang.rw on 16-4-21.
 */
@Controller
@RequestMapping("/sys/role")
public class RoleController
{
    @Autowired
    private RoleService roleService;

    @RequestMapping
    @RequiresPermissions("sysmgp:role")
    public String index(){
        return "sys/role/list";
    }

    @RequestMapping("/getList")
    public @ResponseBody
    List<SysRole> getAllRole(RoleFilter filter){
        return roleService.getAllRole(filter);
    }

    @RequiresPermissions(value={"role:add","role:update"},logical = Logical.OR)
    @RequestMapping("/openmodalroleinput")
    public ModelAndView roleInput(@RequestParam(value = "id", required = false) String id) {
        ModelAndView mav = new ModelAndView("sys/role/form");

        if (!StringUtils.isBlank(id)) {

            SysRole role = roleService.getRoleByid(id);

            mav.addObject("role", role);

            List<SysPermission> permissions = roleService.getPermissionsByRoleId(id);

            mav.addObject("ownPermissions", permissions);
        }
        return mav;
    }

    @RequiresPermissions("role:add")
    @RequestMapping("/save")
    public @ResponseBody boolean addRole(@Valid SysRole role, @RequestParam(value = "pIds",required = false)String pIds){
        String[] ids = new String[]{};
        if( StringUtils.isNotBlank(pIds) ){
            ids = pIds.split(",");
        }
        roleService.saveRolePermission(role,ids);
        return true;
    }

    @RequestMapping("/checkExists")
    public @ResponseBody boolean checkExists(@RequestParam(value = "rolename")String rolename ,
                                             @RequestParam(value = "id",required = false)String id){
        SysRole role = roleService.checkRepaeat(rolename , id);
        if( role == null ){
            return false;
        }
        return true;
    }

    @RequiresPermissions("role:update")
    @RequestMapping("/update")
    public @ResponseBody boolean updateRole(@Valid SysRole role,
                                            @RequestParam(value = "pIds",required = false)String pIds){
        String[] p = new String[]{};
        if( StringUtils.isNotBlank(pIds) ){
            p = pIds.split(",");
        }
        return roleService.updateRole(role , p );
    }

    @RequiresPermissions("role:del")
    @RequestMapping(value="delete",method= RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean delRole(@RequestParam(value="ids",required=true)String ids ){
        String[] sp = ids.split(",");

        roleService.deleteByIds(sp);

        return true;
    }

    @RequestMapping("/getUserRoleCount")
    public @ResponseBody long getUserRoleCount(@RequestParam("ids")String ids){
        String[] sp = ids.split(",");
        return roleService.getUserRoleCount(sp);
    }

}
