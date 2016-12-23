package com.banshion.portal.web.index.controller;

import com.banshion.portal.sys.authentication.ShiroUser;
import com.banshion.portal.util.PasswordUtil;
import com.banshion.portal.util.Securitys;
import com.banshion.portal.util.excel.ExcelExportConfig;
import com.banshion.portal.util.excel.ExcelExportUtil;
import com.banshion.portal.web.index.dao.TTransInfoMapper;
import com.banshion.portal.web.index.dao.TindexUserMapper;
import com.banshion.portal.web.index.domain.TTransInfo;
import com.banshion.portal.web.index.domain.TindexUser;
import com.banshion.portal.web.index.domain.TindexUserExample;
import com.banshion.portal.web.index.filter.TransFilter;
import com.banshion.portal.web.index.filter.UserFilter;
import com.banshion.portal.web.sys.domain.SysUser;
import com.banshion.portal.web.sys.domain.SysUserExample;
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

    @Autowired
    private TindexUserMapper indexuserDao;

    @RequestMapping
    public String index(Model model , HttpServletRequest request){
        model.addAttribute("isAdmin", Securitys.isAdmin());
        return "trans/index";
    }

    @RequestMapping("getdata")
    public @ResponseBody
    List<Map<String,Object>> getData(TransFilter filter){

        if( !Securitys.isAdmin() ){
            filter.setUserId(Securitys.getUserId());
        }
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
        model.addAttribute("operate","add");
        if(StringUtils.isNotBlank(id)){
            TransFilter filter = new TransFilter();
            filter.setUserId(id);
            List<Map<String,Object>> list = transDao.getData(filter);
            if( list != null && list.size() > 0 ){
                m = list.get(0);
            }
            model.addAttribute("operate","update");
        }
        model.addAttribute("transinfo",m);
        return "trans/form";
    }

    /**
     *
     * @param filter 拿来当PO使使
     * @return
     */
    @RequestMapping("save")
    @Transactional
    public @ResponseBody
    ResponseEntity<?> save(TransFilter filter){
        Map<String,Object> result = new HashMap<String,Object>();

        TTransInfo po = new TTransInfo();

        po.setUserId(filter.getUserId());
        po.setSrcbankName(filter.getSrcbankName());
        po.setSrcbankNumber(filter.getSrcbankNumber());
        po.setTargetbankName(filter.getTargetbankName());
        po.setTargetbankNumber(filter.getTargetbankNumber());
        po.setBz(filter.getBz());
        try{
            if( StringUtils.isNotBlank(filter.getSerialNumber()) ){ //更新
                po.setSerialNumber(filter.getSerialNumber());
                transDao.updateByPrimaryKeySelective(po);
            }else{
                po.setSerialNumber(getSerialNumber());
                po.setState(1);
                po.setTransValue(filter.getTransValue());
                transDao.insertSelective(po);
            }

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
                    + new String("转账信息.xls".getBytes("utf-8"), "ISO8859-1"));
            // 对可能乱码字段进行转码处理
            filter.setUserName(java.net.URLDecoder.decode(filter.getUserName(),"UTF-8"));
            List<Map<String,Object>> list =  transDao.exportData(filter);
            bos = new BufferedOutputStream(response.getOutputStream());
            HSSFWorkbook workbook = new HSSFWorkbook();
            String[] titleArr = {"流水号","姓名","所属部门","工号","身份证号","转账状态","转账金额",
                    "转出银行","转出银行卡号","转入银行","转入银行卡号","备注信息"};
            String[] keyArr = {"serial_number","user_name","dept_name","job_number","id_number","state","trans_value",
                    "srcbank_name","srcbank_number","targetbank_name","targetbank_number","bz"};
            ExcelExportConfig config = new ExcelExportConfig();
            config.setSheetName("转账信息");
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
        long fileSize = file.getSize();
        if(fileSize > 10240000){
            result.put("msg", "文件大小不能超过10M!");
            result.put("success", false);
            return new ResponseEntity(result, HttpStatus.OK);
        }

        String proPath = request.getSession().getServletContext().getRealPath("");
        int index = proPath.lastIndexOf(File.separator) + 1;
        String ctxPath = proPath.substring(0,index)+"uploadFiles";
        File dirPath = new File(ctxPath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String fileName = sdf.format(new Date())+"_"+ Securitys.getUserName()+"_"+new Date().getTime()+"_转账信息导入.xls";
        File uploadfilePath = new File(ctxPath + File.separator + fileName);
        try {
            if(!uploadfilePath.exists()){
                uploadfilePath.createNewFile();
            }
            file.transferTo(uploadfilePath);
            result.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            if(uploadfilePath.isFile()&&uploadfilePath.exists()){
                uploadfilePath.delete();
            }
            result.put("msg", "上传失败，请联系管理员！");
            result.put("success", false);
        }
        try{
            List<TTransInfo> list = readUserExcel(uploadfilePath);
            for( TTransInfo po : list ){
                transDao.insert(po);
            }
            result.put("success", true);
        }catch (Exception e){
            e.printStackTrace();
            log.info("用户信息Excel上传后处理异常："+e.getMessage());
            result.put("msg", "Excel解析异常,请检查Excel格式和数据正确性！");
            result.put("success", false);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    public List<TTransInfo> readUserExcel(File file)
            throws FileNotFoundException {
        POIFSFileSystem fs = null;
        HSSFWorkbook wb = null;
        HSSFSheet sheet = null;
        HSSFRow row = null;
        InputStream is = new FileInputStream(file.toString());
        Map<Integer, String> content = new HashMap<Integer, String>();
        String str = "";
        List<TTransInfo> list = new ArrayList<TTransInfo>();
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
        TTransInfo transinfo;
        for (int i = 1; i <= rowNum; i++){
            transinfo = new TTransInfo();
            row = sheet.getRow(i);
            if (row == null)
                continue;
            int j = 0;
            if (row.getCell((short) j) == null) {
                ++j;
                break;
            }
            // 取所有数据如内存 比较处理
//            user.setId(UUID.randomUUID().toString());
            String cellValue;
            String user_id;
            while (j < colNum) {
                TindexUserExample se = new TindexUserExample();
                TindexUserExample.Criteria job_tc = se.createCriteria();
                TindexUserExample.Criteria id_tc = se.createCriteria();
                cellValue = ExcelExportUtil.getCellFormatValue(row.getCell((short)j));
                cellValue = StringUtils.isNotBlank(cellValue) ? cellValue : null;
                switch(j)
                {
                    case 0 : job_tc.andJobNumberEqualTo(cellValue); break;
                    case 1 :
                        se.or(job_tc);
                        se.or(id_tc.andIdNumberEqualTo(cellValue));
                        List<TindexUser> list_user = indexuserDao.selectByExample(se);
                        if( list_user != null && list_user.size() == 1 ){
                            user_id = list_user.get(0).getId();
                        }else{
                            user_id = null;
                        }
                        transinfo.setUserId(user_id);
                        break;
                    case 2 :
                        transinfo.setState(cellValue.indexOf("等待") == -1 ?
                                (cellValue.indexOf("成功") == -1 ? ( cellValue.indexOf("失败") == -1 ? null : 2 )
                                        : 3)
                                : 1);
                        break;
                    case 3 : transinfo.setTransValue(cellValue); break;
                    case 4 : transinfo.setSrcbankName(cellValue); break;//"转出银行"
                    case 5 : transinfo.setSrcbankNumber(cellValue); break;//"转出银行卡号"
                    case 6 : transinfo.setTargetbankName(cellValue); break;//"转入银行"
                    case 7 : transinfo.setTargetbankNumber(cellValue); break;
                    case 8 : transinfo.setBz(cellValue); break;
                    default: break;
                }
                j++;
            }
            transinfo.setSerialNumber(getSerialNumber());
            list.add(transinfo);
        }
        return list;
    }

}
