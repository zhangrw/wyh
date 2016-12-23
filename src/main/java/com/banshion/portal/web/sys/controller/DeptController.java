package com.banshion.portal.web.sys.controller;

import com.banshion.portal.exception.ServiceException;
import com.banshion.portal.util.Securitys;
import com.banshion.portal.web.sys.dao.SysDeptMapper;
import com.banshion.portal.web.sys.domain.SysDept;
import com.banshion.portal.web.sys.domain.SysDeptExample;
import com.banshion.portal.web.sys.domain.SysMenuExample;
import com.banshion.portal.web.sys.service.impl.MenuTreeNode;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.*;

/**
 * 部门维护
 */
@Controller
@RequestMapping("sys/dept")
public class DeptController {

    @Autowired
    private SysDeptMapper deptdao;

    @RequestMapping
    @RequiresPermissions("sys:dept")
    public String index(Model model){
        System.out.println(Securitys.getSubject().isPermitted("sys:dept"));
        System.out.println(Securitys.getSubject().isPermitted("sys:dept1"));

        model.addAttribute("deptlist",deptdao.selectByExample(null));
        return "sys/dept/list";
    }

    @RequestMapping("getallDept")
    public @ResponseBody
    List<SysDept> getAllDept(){
        return deptdao.selectByExample(null);
    }

    @RequestMapping("/getDeptbyid")
    public @ResponseBody List<SysDept> getDeptbyPId( @RequestParam(value = "id",defaultValue = "") String id){
        SysDeptExample se = new SysDeptExample();
        se.createCriteria().andParentIdEqualTo(id);
        return deptdao.selectByExample(se);
    }


    @RequestMapping("getordercount")
    public @ResponseBody
    ResponseEntity<?> getOrderCount(String parentId){
        Map<String, Object> result = new HashMap<String, Object>();
        try{
            Integer order = deptdao.getOrderCount(parentId);
            result.put("order", order);
            result.put("success", true);
        }catch(Exception e){
            result.put("success", false);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @RequestMapping(value="checknameexists",method= RequestMethod.POST,produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Boolean checkNameExists(SysDept Dept){
        SysDept m = deptdao.checkNameExists(Dept);
        if(m==null){
            return false;
        }else{
            return true;
        }
    }


    @RequestMapping(value="add",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Boolean addDept(@Valid SysDept dept, Errors errors){
        if(errors.hasErrors()){
            return false;
        }
        try{
            dept.setId(UUID.randomUUID().toString());
            deptdao.insert(dept);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

//        @RequiresPermissions("Deptmgt:delete")
    @RequestMapping(value="delete",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Boolean delete(@RequestParam("id")String id){
        try{
            Securitys.getSubject().getPrincipals();
            SysDeptExample se = new SysDeptExample();
            se.createCriteria().andIdEqualTo(id);
            deptdao.deleteByExample(se);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    //    @RequiresPermissions("Deptmgt:update")
    @RequestMapping(value="update",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Boolean update(@Valid  SysDept dept, Errors errors){
        if(errors.hasErrors()){
            return false;
        }
        try{
            SysDeptExample se = new SysDeptExample();
            se.createCriteria().andIdEqualTo(dept.getId());
            deptdao.updateByExampleSelective(dept,se);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @RequestMapping(value = "updateDeptOrder",  produces = MediaType.APPLICATION_JSON_VALUE) // method = RequestMethod.POST,
    public @ResponseBody ResponseEntity<?> updateDeptOrder(String deptorderparam){
        Map<String, Object> result = new HashMap<String, Object>();
        try{
                String[] str = deptorderparam.split(";");
                for(int i=0;i<str.length;i++) {
                    String[] menu = str[i].split(":");
                    deptdao.updateDeptOrder(menu[0], menu[1]);
                }
            result.put("success", true);
        }catch(Exception e){
                e.printStackTrace();
            result.put("success", false);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @RequestMapping(value = "depttree", method = RequestMethod.GET)
    @ResponseBody
    public List<MenuTreeNode> DeptTree() {

        if(Securitys.isAdmin()){




            try {

                SysMenuExample me = new SysMenuExample();
                me.setOrderByClause(" menu_order asc");
                List<SysDept> list = deptdao.selectByExample(null);

                HashMap<String,MenuTreeNode> nodemap = new HashMap<String, MenuTreeNode>();

                for( SysDept menu : list ){

                    MenuTreeNode node = new MenuTreeNode();
                    node.setId(menu.getId());
                    node.setName(menu.getName());
                    node.setParentId(menu.getParentId());
                    node.setUrl(menu.getUrl());
                    node.setType(menu.getType());
                    nodemap.put(menu.getId() , node);

                }

                Set entrySet = nodemap.entrySet();

                MenuTreeNode root = null;

                for(Iterator it = entrySet.iterator(); it.hasNext();){
                    MenuTreeNode node = (MenuTreeNode)((Map.Entry)it.next()).getValue();

                    if( node.getParentId() == null || "".equals(node.getParentId())){
                        root = node;
                    }else{
                        if( nodemap.get(node.getParentId()) != null ){
                            ((MenuTreeNode) nodemap.get(node.getParentId())).addChildNode(node);
                        }
                    }
                }

                return root.getChildren();

            } catch (Exception e) {
                throw new ServiceException("查询部门失败", e);
            }






//            return deptdao.getAllDepts();
        }
//        return DeptDao.getDeptByUserId(Securitys.getUserId());
        return null;
    }
}
