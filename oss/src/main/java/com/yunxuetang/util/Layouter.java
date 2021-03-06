package com.yunxuetang.util;

import org.apache.poi.hssf.usermodel.HSSFCell;  
import org.apache.poi.hssf.usermodel.HSSFCellStyle;  
import org.apache.poi.hssf.usermodel.HSSFRow;  
import org.apache.poi.hssf.usermodel.HSSFSheet;  
import org.apache.poi.hssf.util.CellRangeAddress;  
import org.apache.poi.hssf.util.HSSFColor;  
import org.apache.poi.ss.usermodel.CellStyle;  
import org.apache.poi.ss.usermodel.Font;  
  
/** 
 * 更多单元格请查看http://justdoblogger.com/blog/200911/setfillforegroundcolor.html 
 *  
 * @author liukai 
 *  
 */  
@SuppressWarnings("deprecation")  
public class Layouter {  
  
    /** 
     * 创建报表 
     */  
    public static void buildReport(HSSFSheet worksheet, int startRowIndex,  
            int startColIndex) {  
        // 设置列的宽度  
        worksheet.setColumnWidth(0, 5000);  
        worksheet.setColumnWidth(1, 5000);  
        worksheet.setColumnWidth(2, 5000);  
        worksheet.setColumnWidth(3, 5000);  
        worksheet.setColumnWidth(4, 5000);  
        worksheet.setColumnWidth(5, 5000);  
        worksheet.setColumnWidth(6, 5000);  
        worksheet.setColumnWidth(7, 5000);  
        worksheet.setColumnWidth(8, 5000);  
        worksheet.setColumnWidth(9, 5000);  
        worksheet.setColumnWidth(10, 5000);  
        worksheet.setColumnWidth(11, 5000);
        worksheet.setColumnWidth(12, 5000);  
        worksheet.setColumnWidth(13, 5000);  
        worksheet.setColumnWidth(14, 5000);  
        worksheet.setColumnWidth(15, 5000);
        worksheet.setColumnWidth(16, 5000);  
        worksheet.setColumnWidth(17, 5000);  
        worksheet.setColumnWidth(18, 5000);  
        worksheet.setColumnWidth(19, 5000);  
        worksheet.setColumnWidth(20, 5000);  
        worksheet.setColumnWidth(21, 5000);  
        worksheet.setColumnWidth(22, 5000);  
        worksheet.setColumnWidth(23, 5000);  
        worksheet.setColumnWidth(24, 5000);  
        worksheet.setColumnWidth(25, 5000);  
        worksheet.setColumnWidth(26, 5000);  
        worksheet.setColumnWidth(27, 5000);
        worksheet.setColumnWidth(28, 5000);  
        worksheet.setColumnWidth(29, 5000);  
        worksheet.setColumnWidth(30, 5000);  
        worksheet.setColumnWidth(31, 5000);
        worksheet.setColumnWidth(32, 5000);
        worksheet.setColumnWidth(33, 5000);
        worksheet.setColumnWidth(34, 5000);
        worksheet.setColumnWidth(35, 5000);
        worksheet.setColumnWidth(36, 5000);
  
        buildTitle(worksheet, startRowIndex, startColIndex);  
  
        buildHeaders(worksheet, startRowIndex, startColIndex);  
  
    }  
  
    /** 
     * 创建报表标题和日期 
     */  
    private static void buildTitle(HSSFSheet worksheet, int startRowIndex,  
            int startColIndex) {  
        // 设置报表标题字体  
        Font fontTitle = worksheet.getWorkbook().createFont();  
        fontTitle.setBoldweight(Font.BOLDWEIGHT_BOLD);  
        fontTitle.setFontHeight((short) 280);  
  
        // 标题单元格样式  
        HSSFCellStyle cellStyleTitle = worksheet.getWorkbook()  
                .createCellStyle();  
        cellStyleTitle.setAlignment(CellStyle.ALIGN_CENTER);  
        cellStyleTitle.setWrapText(true);  
        cellStyleTitle.setFont(fontTitle);  
  
        // 报表标题  
        HSSFRow rowTitle = worksheet.createRow((short) startRowIndex);  
        rowTitle.setHeight((short) 500);  
        HSSFCell cellTitle = rowTitle.createCell(startColIndex);  
        cellTitle.setCellValue("每月数据统计报表");  
        cellTitle.setCellStyle(cellStyleTitle);  
  
        // 合并区域内的报告标题  
        worksheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 36));  
  
        // date header  
        HSSFRow dateTitle = worksheet.createRow((short) startRowIndex + 1);  
        HSSFCell cellDate = dateTitle.createCell(startColIndex);  
        cellDate.setCellValue("这个报表创建于: " + DateUtils.getNowTime());  
    }  
  
    /** 
     * 创建表头 
     */  
    private static void buildHeaders(HSSFSheet worksheet, int startRowIndex,  
            int startColIndex) {  
        // Header字体  
        Font font = worksheet.getWorkbook().createFont();  
        font.setBoldweight(Font.COLOR_RED);  
        // 单元格样式  
        HSSFCellStyle headerCellStyle = worksheet.getWorkbook()  
                .createCellStyle();  
        headerCellStyle.setFillBackgroundColor(HSSFColor.DARK_RED.index);  
        headerCellStyle.setFillPattern(CellStyle.FINE_DOTS);  
        headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);  
        headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);  
        headerCellStyle.setWrapText(true);  
        headerCellStyle.setFont(font);  
        headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);  
  
        // 创建字段标题  
        HSSFRow rowHeader = worksheet.createRow((short) startRowIndex + 2);  
        rowHeader.setHeight((short) 500);  
  
        HSSFCell cell1 = rowHeader.createCell(startColIndex + 0);  
        cell1.setCellValue("名称/月份");  
        cell1.setCellStyle(headerCellStyle);  
  
        HSSFCell cell2 = rowHeader.createCell(startColIndex + 1);  
        cell2.setCellValue("一月新增");  
        cell2.setCellStyle(headerCellStyle);
        
        HSSFCell cell3 = rowHeader.createCell(startColIndex + 2);  
        cell3.setCellValue("一月总数");  
        cell3.setCellStyle(headerCellStyle);
        
        HSSFCell cell4 = rowHeader.createCell(startColIndex + 3);  
        cell4.setCellValue("一月增长率");  
        cell4.setCellStyle(headerCellStyle);
  
        HSSFCell cell5 = rowHeader.createCell(startColIndex + 4);  
        cell5.setCellValue("二月新增");  
        cell5.setCellStyle(headerCellStyle); 
        
        HSSFCell cell6 = rowHeader.createCell(startColIndex + 5);  
        cell6.setCellValue("二月总数");  
        cell6.setCellStyle(headerCellStyle); 
        
        HSSFCell cell7 = rowHeader.createCell(startColIndex + 6);  
        cell7.setCellValue("二月增长率");  
        cell7.setCellStyle(headerCellStyle); 
        
        HSSFCell cell8 = rowHeader.createCell(startColIndex + 7);  
        cell8.setCellValue("三月新增");  
        cell8.setCellStyle(headerCellStyle); 
        
        HSSFCell cell9 = rowHeader.createCell(startColIndex + 8);  
        cell9.setCellValue("三月总数");  
        cell9.setCellStyle(headerCellStyle); 
        
        HSSFCell cell10 = rowHeader.createCell(startColIndex + 9);  
        cell10.setCellValue("三月增长率");  
        cell10.setCellStyle(headerCellStyle);
        
        HSSFCell cell11 = rowHeader.createCell(startColIndex + 10);  
        cell11.setCellValue("四月新增");  
        cell11.setCellStyle(headerCellStyle); 
        
        HSSFCell cell12 = rowHeader.createCell(startColIndex + 11);  
        cell12.setCellValue("四月总数");  
        cell12.setCellStyle(headerCellStyle); 
        
        HSSFCell cell13 = rowHeader.createCell(startColIndex + 12);  
        cell13.setCellValue("四月增长率");  
        cell13.setCellStyle(headerCellStyle);
  
        
        HSSFCell cell14 = rowHeader.createCell(startColIndex + 13);  
        cell14.setCellValue("五月新增");  
        cell14.setCellStyle(headerCellStyle); 
        
        HSSFCell cell15 = rowHeader.createCell(startColIndex + 14);  
        cell15.setCellValue("五月总数");  
        cell15.setCellStyle(headerCellStyle); 
        
        HSSFCell cell16 = rowHeader.createCell(startColIndex + 15);  
        cell16.setCellValue("五月增长率");  
        cell16.setCellStyle(headerCellStyle);
        
        HSSFCell cell17 = rowHeader.createCell(startColIndex + 16);  
        cell17.setCellValue("六月新增");  
        cell17.setCellStyle(headerCellStyle); 
        
        HSSFCell cell18 = rowHeader.createCell(startColIndex + 17);  
        cell18.setCellValue("六月总数");  
        cell18.setCellStyle(headerCellStyle); 
        
        HSSFCell cell19 = rowHeader.createCell(startColIndex + 18);  
        cell19.setCellValue("六月增长率");  
        cell19.setCellStyle(headerCellStyle);
  
        HSSFCell cell20 = rowHeader.createCell(startColIndex + 19);  
        cell20.setCellValue("七月新增");  
        cell20.setCellStyle(headerCellStyle); 
        
        HSSFCell cell21 = rowHeader.createCell(startColIndex + 20);  
        cell21.setCellValue("七月总数");  
        cell21.setCellStyle(headerCellStyle); 
        
        HSSFCell cell22 = rowHeader.createCell(startColIndex + 21);  
        cell22.setCellValue("七月增长率");  
        cell22.setCellStyle(headerCellStyle);
        
        
        HSSFCell cell23 = rowHeader.createCell(startColIndex + 22);  
        cell23.setCellValue("八月新增");  
        cell23.setCellStyle(headerCellStyle); 
        
        HSSFCell cell24 = rowHeader.createCell(startColIndex + 23);  
        cell24.setCellValue("八月总数");  
        cell24.setCellStyle(headerCellStyle); 
        
        HSSFCell cell25 = rowHeader.createCell(startColIndex + 24);  
        cell25.setCellValue("八月增长率");  
        cell25.setCellStyle(headerCellStyle);
        
        HSSFCell cell26 = rowHeader.createCell(startColIndex + 25);  
        cell26.setCellValue("九月新增");  
        cell26.setCellStyle(headerCellStyle); 
        
        HSSFCell cell27 = rowHeader.createCell(startColIndex + 26);  
        cell27.setCellValue("九月总数");  
        cell27.setCellStyle(headerCellStyle); 
        
        HSSFCell cell28 = rowHeader.createCell(startColIndex + 27);  
        cell28.setCellValue("九月增长率");  
        cell28.setCellStyle(headerCellStyle);
        
        
        HSSFCell cell29 = rowHeader.createCell(startColIndex + 28);  
        cell29.setCellValue("十月新增");  
        cell29.setCellStyle(headerCellStyle); 
        
        HSSFCell cell30 = rowHeader.createCell(startColIndex + 29);  
        cell30.setCellValue("十月总数");  
        cell30.setCellStyle(headerCellStyle); 
        
        HSSFCell cell31 = rowHeader.createCell(startColIndex + 30);  
        cell31.setCellValue("十月增长率");  
        cell31.setCellStyle(headerCellStyle);
        
        
        HSSFCell cell32 = rowHeader.createCell(startColIndex + 31);  
        cell32.setCellValue("十一月新增");  
        cell32.setCellStyle(headerCellStyle); 
        
        HSSFCell cell33 = rowHeader.createCell(startColIndex + 32);  
        cell33.setCellValue("十一月总数");  
        cell33.setCellStyle(headerCellStyle); 
        
        HSSFCell cell34 = rowHeader.createCell(startColIndex + 33);  
        cell34.setCellValue("十一月增长率");  
        cell34.setCellStyle(headerCellStyle);
        
        HSSFCell cell35 = rowHeader.createCell(startColIndex + 34);  
        cell35.setCellValue("十二月新增");  
        cell35.setCellStyle(headerCellStyle); 
        
        HSSFCell cell36 = rowHeader.createCell(startColIndex + 35);  
        cell36.setCellValue("十二月总数");  
        cell36.setCellStyle(headerCellStyle); 
        
        HSSFCell cell37 = rowHeader.createCell(startColIndex + 36);  
        cell37.setCellValue("十二月增长率");  
        cell37.setCellStyle(headerCellStyle);
        
        
  
    }  
  
}  
