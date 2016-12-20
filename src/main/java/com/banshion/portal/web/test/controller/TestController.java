package com.banshion.portal.web.test.controller;

import com.banshion.portal.util.CommPropertiesConfiguration;
import com.banshion.portal.util.excel.ExcelExportConfig;
import com.banshion.portal.util.excel.ExcelExportUtil;
import com.banshion.portal.web.sys.dao.SysMenuMapper;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping("test")
public class TestController {



    @RequestMapping
    public String index(){
        return "test/test";
    }


    @RequestMapping(value = "export")
    public ModelAndView export(HttpServletRequest request, HttpServletResponse response)throws Exception {
        request.setCharacterEncoding("utf-8");
        java.io.BufferedOutputStream bos = null;
        try {
            response.setContentType("application/x-msdownload;");
            response.setHeader("Content-disposition", "attachment; filename="
                    + new String("问卷时长列表.xls".getBytes("utf-8"), "ISO8859-1"));

            List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();

            for(int i = 0 ; i < 5 ; i ++ ){
                Map<String,Object> m = new HashedMap();
                m.put("NAME",i+"_NAME");
                m.put("TEST",i+"_TEST");
                list.add(m);
            }
            bos = new BufferedOutputStream(response.getOutputStream());
            HSSFWorkbook workbook = new HSSFWorkbook();
            String[] titleArr = {"测试","标题"};
            String[] keyArr = {"NAME","TEST"};
            ExcelExportConfig config = new ExcelExportConfig();
            config.setSheetName("问卷录入时长列表");
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

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "uploadDocument", method = RequestMethod.POST)
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file,
                                    HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();

        String stockCode = request.getParameter("stockCode") == null ? ""
                : request.getParameter("stockCode");
        String stockName = request.getParameter("stockName") == null ? ""
                : request.getParameter("stockName");
        long fileSize = file.getSize();
        if (fileSize > 10240000) {
            result.put("msg", "文件大小不能超过10M!");
            result.put("success", false);
            return new ResponseEntity(result, HttpStatus.OK);
        }


        String ctxPath = CommPropertiesConfiguration
                .getContextProperty("document_upload_dir");
        File dirPath = new File(ctxPath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
        String fileName = file.getOriginalFilename();// linux 服务器上是gbk的编码
        File filePath = new File(ctxPath + fileName);

        try {
            if (!filePath.exists()) {
                filePath.createNewFile();
            }
            // 文件上传
            file.transferTo(filePath);
            // 文件解析
            List excleList = readExcelContent(filePath, "");
            // 比较 list 里面的数据
            Set setBox = new HashSet();
            String str = "";//compileList(excleList, setBox);// 检查数据库数据
            if (str != null && !"".equals(str)) {
                result.put("msg", str + "  请整理完整excle后重新全部导入");
                result.put("success", false);
                return new ResponseEntity(result, HttpStatus.OK);
            }

            // 保存excle数据
//            excelContentToOracle(setBox, excleList, imorderNo, stockCode,
//                    stockName); 入库

            result.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            if (filePath.isFile() && filePath.exists()) {
                filePath.delete();
            }
            result.put("msg", "上传失败，请联系管理员！");
            result.put("success", false);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }


    public List readExcelContent(File file, String imorderNo)
            throws FileNotFoundException {
//        POIFSFileSystem fs = null;
//        HSSFWorkbook wb = null;
//        HSSFSheet sheet = null;
//        HSSFRow row = null;
//        InputStream is = new FileInputStream(file.toString());
//        Map<Integer, String> content = new HashMap<Integer, String>();
//        String str = "";
//        List list = new ArrayList();
//        try {
//            fs = new POIFSFileSystem(is);
//            wb = new HSSFWorkbook(fs);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        sheet = wb.getSheetAt(0);
//        // 得到总行数
//        int rowNum = sheet.getLastRowNum();
//        row = sheet.getRow(0);
//        // map.put("imorderNo", imorderNo);
//        int colNum = row.getPhysicalNumberOfCells();
//        // 正文内容应该从第二行开始,第一行为表头的标题
////        BooldExcelVo be = null;
//        for (int i = 1; i <= rowNum; i++) {
//            row = sheet.getRow(i);
//            if (row == null)
//                continue;
//            int j = 0;
//            if (row.getCell((short) j) == null) {
//                ++j;
//                break;
//            }
//            // 取所有数据如内存 比较处理
////            be = new BooldExcelVo();
////            be.setRow(String.valueOf(i));
//            while (j < colNum) {
//
//
//                if (j == 0) {// 采血包箱编号
////                    be.setArchivesNo(getCellFormatValue(row.getCell((short) j)) != null
////                            && !"".equals(getCellFormatValue(row
////                            .getCell((short) j))) ? getCellFormatValue(
////                            row.getCell((short) j)).trim() : null);
//                    // map.put("archivesNo",
//                    // getCellFormatValue(row.getCell((short) j))!=null &&
//                    // !"".equals(getCellFormatValue(row.getCell((short) j)) )
//                    // ?getCellFormatValue(row.getCell((short) j)).trim():null
//                    // );
//                }
//                j++;
//            }
//
////            list.add(be);
////            be = null;
//        }
//        return list;

        return null;
    }
}
