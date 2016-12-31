package com.banshion.portal.web.sys.controller;

import com.banshion.portal.web.sys.dao.SysUserMapper;
import com.banshion.portal.web.sys.domain.SysRole;
import com.banshion.portal.web.sys.domain.SysUser;
import com.banshion.portal.web.sys.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhang.rw on 16-4-13.
 */
@Controller
@RequestMapping("/sys/user")
public class UserController
{
    @Autowired
    SysUserMapper userMapper;

    @Autowired
    UserService userDao;

    @RequestMapping()
    @RequiresPermissions("sys:usermgp")
    public String index(){
        return "sys/user/list";
    }

    @RequiresPermissions("user:query")
    @RequestMapping("/getuserinfo")
    public @ResponseBody List<SysUser> getUserList(){

        List<SysUser> list = userMapper.selectByExample(null);

        return list;
    }


    @RequestMapping(value = "openmodaluserinput", method = RequestMethod.GET)
    public ModelAndView addAndUpdateUser(HttpServletRequest req, @RequestParam(value = "id", required = false) String id){
        ModelAndView mv = new ModelAndView("sys/user/userform");
        if( id == null || "".equals(id) ){
            mv.addObject("user",null);
            List<SysRole> list = userDao.getAllRoles();
            mv.addObject("roles",list);
        }else{
            SysUser user = userDao.getUserinfoByid(id);
            mv.addObject("user",user);
            List<SysRole> list = userDao.getAllRoles();
            mv.addObject("roles",list);
            List<SysRole> lrole = userDao.getOwnRolesById(id);
            mv.addObject("ownRoles",lrole);
        }
        return mv;
    }

    @RequestMapping(value = "/save",method=RequestMethod.POST,produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> createUser( SysUser user,@RequestParam(value="rIds",required=false)String rIds ){
       Map<String, Object> res = new HashMap<String, Object>();
       res.put("success", true);

       if(StringUtils.isNotBlank(rIds)){
          String[] rids = rIds.split(",");
         userDao.saveUserAndRole(user , rids);
       }
       else
       {
         userDao.save(user);
       }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @RequestMapping("checkloginnameExists")
    public @ResponseBody ResponseEntity<?> checkloginnameExists(@RequestParam("loginname")String loginname,
                                                                @RequestParam(value ="id" ,required = false)String id
                                                                ){
        Map<String, Object> res = new HashMap<String, Object>();

        boolean result =  userDao.checkloginnameExists(loginname , id);

        res.put("result", result);
        return new ResponseEntity(res, HttpStatus.OK);
    }


    @RequestMapping(value = "/update",method=RequestMethod.POST,produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> updateUser( SysUser user,@RequestParam(value="rIds",required=false)String rIds ){
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("success", true);
        if(StringUtils.isNotBlank(rIds)){
            String[] rids = rIds.split(",");
            userDao.UpdateUserRole(user , rids);
        }
        else
        {
            userDao.UpdateUserRole(user,null);
        }
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete",method=RequestMethod.POST,produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> deleteUser(@RequestParam(value="ids")String ids ){
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("success", true);

        String[] idArr = ids.split(",");
        userDao.deleteUser(idArr);
        return new ResponseEntity(res, HttpStatus.OK);
    }

    @RequestMapping("/resetpassword")
    @ResponseBody
    public ResponseEntity<?> resetPassword(@RequestParam(value="id")String id,
                                           @RequestParam(value="newPassword")String newPassword)
    {
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("success", true);
        userDao.resetPassword(id , newPassword);
        return new ResponseEntity(res, HttpStatus.OK);
    }
}
