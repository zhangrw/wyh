package com.banshion.portal.web.index.controller;

import com.banshion.portal.sys.authentication.ShiroUser;
import com.banshion.portal.util.PasswordUtil;
import com.banshion.portal.util.Securitys;
import com.banshion.portal.util.excel.ExcelExportConfig;
import com.banshion.portal.util.excel.ExcelExportUtil;
import com.banshion.portal.web.index.dao.TTransInfoMapper;
import com.banshion.portal.web.index.domain.TindexUser;
import com.banshion.portal.web.index.filter.TransFilter;
import com.banshion.portal.web.index.filter.UserFilter;
import com.banshion.portal.web.sys.domain.SysUser;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 */


@Controller
@RequestMapping("trans")
public class TransInfoController {

    private static final Logger log = LoggerFactory.getLogger(TransInfoController.class);

    @Autowired
    private TTransInfoMapper transDao;

    @RequestMapping
    public String index(Model model , HttpServletRequest request){
        model.addAttribute("isAdmin", Securitys.isAdmin());
        return "trans/index";
    }

    @RequestMapping("getdata")
    public @ResponseBody
    List<Map<String,Object>> getData(TransFilter filter){
        PageHelper.startPage(filter.getPage(),filter.getRows());
        Page<Map<String,Object>> page = (Page<Map<String,Object>>)transDao.getData(filter);
        return page;
    }

    //流水号生成策略
    public String getSerialNumber(){
        String retValue = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        retValue += sdf.format(new Date());
        int random = new Random().nextInt(9000)+1000; // 随机数 1000-9999范围
        retValue += random;
        return retValue;
    }

    @RequestMapping("openmodaltransinfo")
    public String modify(Model model,@RequestParam(value = "id",defaultValue = "") String id){
        Map<String,Object> m = new HashedMap();
        if(StringUtils.isNotBlank(id)){
            TransFilter filter = new TransFilter();
            filter.setId(id);
            List<Map<String,Object>> list = transDao.getData(filter);
            if( list != null && list.size() > 0 ){
                m = list.get(0);
            }
        }

        model.addAttribute("transinfo",m);
        return "trans/form";
    }

    @RequestMapping("save")
    @Transactional
    public @ResponseBody
    ResponseEntity<?> save(ShiroUser shiroUser, String checkbox , String password){
        Map<String,Object> result = new HashMap<String,Object>();
        try{
//            if( StringUtils.isNoneBlank(shiroUser.getId()) ){ //更新
//                SysUser suer = new SysUser();
//                suer.setId(shiroUser.getId());
//                suer.setLoginname(shiroUser.getLoginName());
//                suer.setUsername(shiroUser.getName());
//                if(StringUtils.isNoneBlank(checkbox) && StringUtils.isNotBlank(password)){
//                    byte[] salts = PasswordUtil.getSaltBytes();// 自定义加密串,目前获取8位随机
//                    suer.setSalt(PasswordUtil.getEncodeSalts(salts));
//                    suer.setPassword(PasswordUtil.getEncodePassWord(password,salts));
//                }
//                userDao.updateByPrimaryKeySelective(suer);
//                TindexUser tuser = new TindexUser();
//                tuser.setId(shiroUser.getId());
//                tuser.setName(shiroUser.getName());
//                tuser.setSex(shiroUser.getSex());
//                tuser.setJobNumber(shiroUser.getJobNumber());
//                tuser.setIdNumber(shiroUser.getIdNumber());
//                tuser.setBankNumber(shiroUser.getBankNumber());
//                tuser.setBz(shiroUser.getBz());
//                tuser.setDeptId(shiroUser.getDeptId());
//                tuserDao.updateByPrimaryKeySelective(tuser);
//            }else{ // 新增
//                String newId = UUID.randomUUID().toString();
//                SysUser suer = new SysUser();
//                suer.setId(newId);
//                suer.setLoginname(shiroUser.getLoginName());
//                suer.setUsername(shiroUser.getName());
//                byte[] salts = PasswordUtil.getSaltBytes();// 自定义加密串,目前获取8位随机
//                suer.setSalt(PasswordUtil.getEncodeSalts(salts));
//                suer.setPassword(PasswordUtil.getEncodePassWord(password,salts));
//                userDao.insert(suer);
//                TindexUser tuser = new TindexUser();
//                tuser.setId(newId);
//                tuser.setName(shiroUser.getName());
//                tuser.setSex(shiroUser.getSex());
//                tuser.setJobNumber(shiroUser.getJobNumber());
//                tuser.setIdNumber(shiroUser.getIdNumber());
//                tuser.setBankNumber(shiroUser.getBankNumber());
//                tuser.setBz(shiroUser.getBz());
//                tuser.setDeptId(shiroUser.getDeptId());
//                tuserDao.insert(tuser);
//            }
            result.put("success",true);
            result.put("msg","数据保存成功!");
        }catch (Exception e)
        {
            result.put("success",false);
            result.put("msg","数据保存异常，请刷新页面重试或联系系统管理员!");
            log.error("用户数据新增或更新异常："+e.getMessage());
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @RequestMapping("delete")
    public  @ResponseBody ResponseEntity<?> delete(@RequestParam(value="ids")String ids ){
        Map<String,Object> result = new HashMap<String,Object>();
        try{
            transDao.deleteByIds(ids.split(","));
            result.put("success",true);
        }catch (Exception e){
            log.error("用户删除异常："+e.getMessage());
            result.put("success",false);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }


    @RequestMapping(value = "export")
    public ModelAndView export(HttpServletRequest request, HttpServletResponse response, TransFilter filter)
            throws  Exception{
        request.setCharacterEncoding("utf-8");
        java.io.BufferedOutputStream bos = null;
        try {
            response.setContentType("application/x-msdownload;");
            response.setHeader("Content-disposition", "attachment; filename="
                    + new String("用户基本信息.xls".getBytes("utf-8"), "ISO8859-1"));
            // 对可能乱码字段进行转码处理
//            filter.setName(java.net.URLDecoder.decode(filter.getName(),"UTF-8"));
//            filter.setLoginName(java.net.URLDecoder.decode(filter.getLoginName(),"UTF-8"));
            List<Map<String,Object>> list =  transDao.getData(filter);
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

    @RequestMapping(value="upload", method= RequestMethod.POST)
    public ResponseEntity impExcel(@RequestParam MultipartFile file, HttpServletRequest request)
            throws Exception{
        Map<String, Object> result = new HashMap<String, Object>();
//        long fileSize = file.getSize();
//        if(fileSize > 10240000){
//            result.put("msg", "文件大小不能超过10M!");
//            result.put("success", false);
//            return new ResponseEntity(result, HttpStatus.OK);
//        }
//
//        String proPath = request.getSession().getServletContext().getRealPath("");
//        int index = proPath.lastIndexOf(File.separator) + 1;
//        String ctxPath = proPath.substring(0,index)+"uploadFiles";
//        File dirPath = new File(ctxPath);
//        if (!dirPath.exists()) {
//            dirPath.mkdirs();
//        }
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
//        String fileName = sdf.format(new Date())+"_"+ Securitys.getUserName()+"_"+new Date().getTime()+".xls";
////        String fileName =file.getOriginalFilename();
//        File filePath = new File(ctxPath + File.separator + fileName);
//        try {
//            if(!filePath.exists()){
//                filePath.createNewFile();
//            }
//            file.transferTo(filePath);
//            result.put("success", true);
//        } catch (Exception e) {
//            e.printStackTrace();
//            if(filePath.isFile()&&filePath.exists()){
//                filePath.delete();
//            }
//            result.put("msg", "上传失败，请联系管理员！");
//            result.put("success", false);
//        }
//        try{
//            List<TindexUser> list = readUserExcel(filePath);
//            SysUser suser;
//            for( TindexUser po : list ){
//                suser = new SysUser();
//                tuserDao.insert(po);
//                suser.setId(po.getId());
//                suser.setLoginname(po.getJobNumber());
//                suser.setUsername(po.getName());
//                byte[] salts = PasswordUtil.getSaltBytes();// 自定义加密串,目前获取8位随即
//                suser.setSalt(PasswordUtil.getEncodeSalts(salts));
//                suser.setPassword(PasswordUtil.getEncodePassWord(po.getJobNumber(),salts));
//                userDao.insert(suser);
//            }
//            result.put("success", true);
//        }catch (Exception e){
//            e.printStackTrace();
//            log.info("用户信息Excel上传后处理异常："+e.getMessage());
//            result.put("msg", "Excel解析异常,请检查Excel格式和数据正确性！");
//            result.put("success", false);
//        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    public List<TindexUser> readUserExcel(File file)
            throws FileNotFoundException {
        POIFSFileSystem fs = null;
        HSSFWorkbook wb = null;
        HSSFSheet sheet = null;
        HSSFRow row = null;
        InputStream is = new FileInputStream(file.toString());
        Map<Integer, String> content = new HashMap<Integer, String>();
        String str = "";
        List<TindexUser> list = new ArrayList<TindexUser>();
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();
        // 正文内容应该从第二行开始,第一行为表头的标题
        TindexUser user;
        for (int i = 1; i <= rowNum; i++){
            user = new TindexUser();
            row = sheet.getRow(i);
            if (row == null)
                continue;
            int j = 0;
            if (row.getCell((short) j) == null) {
                ++j;
                break;
            }
            // 取所有数据如内存 比较处理
            user.setId(UUID.randomUUID().toString());
            String cellValue;
            while (j < colNum) {
                cellValue = ExcelExportUtil.getCellFormatValue(row.getCell((short)j));
                cellValue = StringUtils.isNotBlank(cellValue) ? cellValue : null;
                switch(j) //name sex job_number id_number bank_number bz
                {
                    case 0 : user.setName(cellValue); break;
                    case 1 : user.setSex(cellValue.indexOf("男") != -1 ? 1 : 2 ); break;
                    case 2 : user.setJobNumber(cellValue); break;
                    case 3 : user.setIdNumber(cellValue); break;
                    case 4 : user.setBankNumber(cellValue); break;
                    case 5 : user.setBz(cellValue); break;
                    default: break;
                }
                j++;
            }
            list.add(user);
        }
        return list;
    }




}
