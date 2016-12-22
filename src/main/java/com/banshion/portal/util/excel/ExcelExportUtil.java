package com.banshion.portal.util.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class ExcelExportUtil {

    private static List<Map<String,Object>> list;

    private static String[] headerArray;

    private static Object[] dataNameArray;

    private static Workbook workbook;

    private static ExcelExportConfig config;


    /**
     * description:填充 excel
     * @author xu.sh
     * @param list  数据
     * @param headerArray  表头
     * @param dataNameArray 数据对应的name
     * @update 2016年10月9日
     */
    public static void fillExcel(List<Map<String,Object>> list, String[] headerArray,
                                 Object[] dataNameArray,Workbook workbook,ExcelExportConfig config){

        if(list==null)list = new ArrayList<Map<String,Object>>();
        ExcelExportUtil.list = list;
        ExcelExportUtil.headerArray = headerArray;
        ExcelExportUtil.dataNameArray = dataNameArray;
        ExcelExportUtil.workbook = workbook;
        if(config == null)config=new ExcelExportConfig();
        ExcelExportUtil.config = config;

        if(list.size()==0){
            createAndFillSheet(0);
            return;
        }

        int page =0;

        //分sheet页打印
        for(;page<(list.size()+config.getSheetRowCount()-1)/config.getSheetRowCount(); page++){
            createAndFillSheet(page);
        }
    }


    private static void createAndFillSheet(int page){
        String sheetName;
        if(config.getSheetName()==null){
            sheetName = "第"+(page+1)+"页";
        }else{
            sheetName = config.getSheetName();
            if((list.size()+config.getSheetRowCount()-1)/config.getSheetRowCount()>1){
                sheetName += "_"+(page+1);
            }
        }

        Sheet sheet = workbook.createSheet(sheetName);
        createSheetHeader(sheet);

        CellStyle defaultStyle = createDefaultDataStyle();
        for(int j = 0;j<config.getSheetRowCount();j++){
            Row row = sheet.createRow(j+1);
            row.setHeight((short) config.getRowHeight());
            if(list.size()<=page*config.getSheetRowCount()+j)break;
            Map map = list.get(page*config.getSheetRowCount()+j);
            for(int h = 0; h<dataNameArray.length;h++){
                if(map.get(dataNameArray[h])==null || map.get(dataNameArray[h])==""){
                    continue;
                }
                Cell cell = row.createCell(h);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if(config.getDataCellStyle().get(h)==null){
                    cell.setCellStyle(defaultStyle);
                }else{
                    cell.setCellStyle(config.getTitleCellStyle().get(h));
                }
                cell.setCellValue(map.get(dataNameArray[h]).toString());
            }
        }

        //列宽
        for(int k = 0; k<dataNameArray.length;k++){
            if(config.getColWidth().get(k)==null){
                sheet.autoSizeColumn(k);
                int w = sheet.getColumnWidth(k);
                sheet.setColumnWidth(k, (int) Math.round(w*1.6));
            }else{
                sheet.setColumnWidth(k, config.getColWidth().get(k));
            }
        }
    }

    private static void createSheetHeader(Sheet sheet){
        Row row = sheet.createRow(0);
        row.setHeight((short) config.getTitleRowHeight());
        CellStyle defaultStyle = createDefaultTitleStyle();
        for(int i =0;i<headerArray.length; i++){
            Cell cell = row.createCell(i);
            if(config.getTitleCellStyle().get(i)==null){
                cell.setCellStyle(defaultStyle);
            }else{
                cell.setCellStyle(config.getTitleCellStyle().get(i));
            }
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cell.setCellValue(headerArray[i]);
        }
    }




    /**
     * 标题默认样式 : 居中,背景灰,16,加粗
     */
    private static CellStyle createDefaultTitleStyle(){
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);

        // 生成一个字体
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 16);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        // 把字体应用到当前的样式
        style.setFont(font);
        return style;
    }

    /**
     * 数据行默认样式 居左,12
     */
    private static CellStyle createDefaultDataStyle(){
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);

        // 生成一个字体
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);

        // 把字体应用到当前的样式
        style.setFont(font);
        return style;
    }

    /**
     * 根据HSSFCell类型设置数据
     *
     * @param cell
     * @return
     */
    public static String getCellFormatValue(HSSFCell cell) {
        String cellvalue = null;
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                // 如果当前Cell的Type为NUMERIC
                case HSSFCell.CELL_TYPE_NUMERIC:{
                    cellvalue = String.valueOf(cell.getNumericCellValue());
                    break;
                }
                case HSSFCell.CELL_TYPE_FORMULA: {
                    // 判断当前的cell是否为Date
                    if (HSSFDateUtil.isCellDateFormatted(cell)){
                        // 如果是Date类型则，转化为Data格式
                        // 方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
                        // cellvalue = cell.getDateCellValue().toLocaleString();

                        // 方法2：这样子的data格式是不带带时分秒的：2011-10-12
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        cellvalue = sdf.format(date);
                    }
                    // 如果是纯数字
                    else {
                        // 取得当前Cell的数值
                        cellvalue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                // 如果当前Cell的Type为STRIN
                case HSSFCell.CELL_TYPE_STRING:
                    // 取得当前的Cell字符串
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                // 默认的Cell值
                default:
                    cellvalue = null;
            }
        } else {
            cellvalue = null;
        }
        return cellvalue;
    }
}
