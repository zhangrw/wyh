package com.banshion.portal.web.index.controller;

import com.banshion.portal.sys.authentication.ShiroUser;
import com.banshion.portal.util.Securitys;
import com.banshion.portal.util.excel.ExcelExportConfig;
import com.banshion.portal.util.excel.ExcelExportUtil;
import com.banshion.portal.web.index.dao.TindexUserMapper;
import com.banshion.portal.web.sys.dao.SysUserMapper;
import com.banshion.portal.web.sys.domain.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
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
 private Logger log = LoggerFactory.getLogger(IndexUserController.class);
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
        try{
            userDao.deleteByIds(ids.split(","));
            tuserDao.deleteByIds(ids.split(","));
            result.put("success",true);
        }catch (Exception e){
            log.error("用户删除异常："+e.getMessage());
            result.put("success",false);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }


    @RequestMapping(value = "export")
    public ModelAndView export(HttpServletRequest request, HttpServletResponse response)
    throws  Exception{
        request.setCharacterEncoding("utf-8");
        java.io.BufferedOutputStream bos = null;
        try {
            response.setContentType("application/x-msdownload;");
            response.setHeader("Content-disposition", "attachment; filename="
                    + new String("用户基本信息.xls".getBytes("utf-8"), "ISO8859-1"));
            List<Map<String,Object>> list =  userDao.exportUser();
            bos = new BufferedOutputStream(response.getOutputStream());
            HSSFWorkbook workbook = new HSSFWorkbook();
            String[] titleArr = {"部门名称","姓名","性别","工号","身份证号","银行卡号","备注信息"};
            String[] keyArr = {"DEPTNAME","NAME","SEX","JOB_NUMBER","ID_NUMBER","BANK_NUMBER","BZ"};
            ExcelExportConfig config = new ExcelExportConfig();
            config.setSheetName("用户基本信息");
            ExcelExportUtil.fillExcel(list,titleArr,keyArr, workbook,config);
            workbook.write(bos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null)
                bos.close();
        }
        return null;
    }
}
