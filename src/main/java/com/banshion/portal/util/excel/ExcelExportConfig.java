package com.banshion.portal.util.excel;

import org.apache.poi.ss.usermodel.CellStyle;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 16-12-15.
 */

public class ExcelExportConfig {

    //每个sheet页行数
    private int sheetRowCount = 20000;

    //预先开启一定量的row的行数
    private int rowMemory = 3000;

    //sheet页名称
    private String sheetName;

    //标题行高
    private int titleRowHeight = 345;

    //数据行高
    private int rowHeight = 345;

    //列宽
    private Map<Integer,Integer> colWidth = new HashMap<Integer,Integer>();

    //标题行单元格样式
    private Map<Integer,CellStyle> titleCellStyle = new HashMap<Integer,CellStyle>();

    //数据行单元格样式
    private Map<Integer,CellStyle> dataCellStyle = new HashMap<Integer,CellStyle>();

    public int getSheetRowCount() {
        return sheetRowCount;
    }

    public void setSheetRowCount(int sheetRowCount) {
        this.sheetRowCount = sheetRowCount;
    }

    public int getRowMemory() {
        return rowMemory;
    }

    public void setRowMemory(int rowMemory) {
        this.rowMemory = rowMemory;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public int getTitleRowHeight() {
        return titleRowHeight;
    }

    public void setTitleRowHeight(int titleRowHeight) {
        this.titleRowHeight = titleRowHeight;
    }

    public int getRowHeight() {
        return rowHeight;
    }

    public void setRowHeight(int rowHeight) {
        this.rowHeight = rowHeight;
    }

    public Map<Integer, Integer> getColWidth() {
        return colWidth;
    }

    public void setColWidth(int col,int width) {
        if(col>=0 && width > 0){
            colWidth.put(col, width);
        }
    }

    public Map<Integer, CellStyle> getTitleCellStyle() {
        return titleCellStyle;
    }

    public void setTitleCellStyle(int col,CellStyle cellStyle) {
        if(col>=0){
            titleCellStyle.put(col, cellStyle);
        }
    }

    public Map<Integer, CellStyle> getDataCellStyle() {
        return dataCellStyle;
    }

    public void setDataCellStyle(int col,CellStyle cellStyle) {
        if(col>=0){
            dataCellStyle.put(col, cellStyle);
        }
    }

}
