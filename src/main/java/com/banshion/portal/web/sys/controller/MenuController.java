package com.banshion.portal.web.sys.controller;

import com.banshion.portal.util.Securitys;
import com.banshion.portal.web.sys.domain.SysMenu;
import com.banshion.portal.web.sys.domain.SysPermission;
import com.banshion.portal.web.sys.service.MenuService;
import com.banshion.portal.web.sys.service.impl.MenuTreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 16-4-30.
 */
@Controller
@RequestMapping("sys/menu")
public class MenuController {

    @Autowired
    public MenuService menuDao;

    @RequestMapping
    public String index(){
        return "sys/menu/list";
    }

    @RequestMapping("getallmenu")
    public @ResponseBody List<SysMenu> getAllMenu(){
        return menuDao.getAllMenu();
    }

    @RequestMapping("/getmenubyid")
    public @ResponseBody List<SysMenu> getMenubyPId( @RequestParam(value = "id",defaultValue = "") String id){
            return menuDao.getMenuByPID(id);
    }

    @RequestMapping("getallpermission")
    public @ResponseBody List<SysPermission> getAllPermission(){
        return menuDao.getAllPermission();
    }

    @RequestMapping("getordercount")
    public @ResponseBody
    ResponseEntity<?> getOrderCount(String parentId){
        Map<String, Object> result = new HashMap<String, Object>();
        try{
            Integer order = menuDao.getOrderCount(parentId);
            result.put("order", order);
            result.put("success", true);
        }catch(Exception e){
            result.put("success", false);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @RequestMapping(value="checknameexists",method= RequestMethod.POST,produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Boolean checkNameExists(SysMenu menu){
        SysMenu m = menuDao.checkNameExists(menu);
        if(m==null){
            return false;
        }else{
            return true;
        }
    }


    @RequestMapping(value="add",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Boolean addMenu(@Valid SysMenu menu, Errors errors){
        if(errors.hasErrors()){
            return false;
        }
        try{
            menuDao.save(menu);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

//    @RequiresPermissions("menumgt:delete")
    @RequestMapping(value="delete",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Boolean delete(@RequestParam("id")String id){
        try{
            menuDao.delete(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }

//    @RequiresPermissions("menumgt:update")
    @RequestMapping(value="update",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Boolean update(@Valid  SysMenu menu, Errors errors){
        if(errors.hasErrors()){
            return false;
        }
        try{
            menuDao.update(menu);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @RequestMapping(value = "updateMenuOrder",  produces = MediaType.APPLICATION_JSON_VALUE) // method = RequestMethod.POST,
    public @ResponseBody ResponseEntity<?> updateMenuOrder(String menuOrderParam){
        Map<String, Object> result = new HashMap<String, Object>();
        try{
            menuDao.updateMenuOrder(menuOrderParam);
            result.put("success", true);
        }catch(Exception e){
            result.put("success", false);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @RequestMapping(value = "menutree", method = RequestMethod.GET)
    @ResponseBody
    public List<MenuTreeNode> menuTree() {

        if(Securitys.isAdmin()){
            return menuDao.getAllMenus();
        }
        return menuDao.getMenuByUserId(Securitys.getUserId());
    }
}
