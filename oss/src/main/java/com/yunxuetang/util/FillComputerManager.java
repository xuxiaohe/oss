package com.yunxuetang.util;

import java.util.List;  

import org.apache.poi.hssf.usermodel.HSSFCell;  
import org.apache.poi.hssf.usermodel.HSSFCellStyle;  
import org.apache.poi.hssf.usermodel.HSSFRow;  
import org.apache.poi.hssf.usermodel.HSSFSheet;  
import org.apache.poi.ss.usermodel.CellStyle;  
  
public class FillComputerManager {  
  
    public static void fillReport(HSSFSheet worksheet, int startRowIndex,  
            int startColIndex, List<ExcelOutPOJO> datasource) {  
  
        // Row offset  
        startRowIndex += 2;  
  
        // Create cell style for the body  
        HSSFCellStyle bodyCellStyle = worksheet.getWorkbook().createCellStyle();  
        bodyCellStyle.setAlignment(CellStyle.ALIGN_CENTER);  
        bodyCellStyle.setWrapText(false); //是否自动换行.  
  
        // Create body  
        for (int i=startRowIndex; i+startRowIndex-2< datasource.size()+2; i++) {  
            // Create a new row  
            HSSFRow row = worksheet.createRow((short) i+1);  
  
            // Retrieve the id value  
            HSSFCell cell1 = row.createCell(startColIndex+0);  
            cell1.setCellValue(datasource.get(i-2).getName());  
            cell1.setCellStyle(bodyCellStyle);  
  
            // Retrieve the brand value  
            HSSFCell cell2 = row.createCell(startColIndex+1);  
            cell2.setCellValue(datasource.get(i-2).getNumber1());  
            cell2.setCellStyle(bodyCellStyle);  
  
            // Retrieve the model value  
            HSSFCell cell3 = row.createCell(startColIndex+2);  
            cell3.setCellValue(datasource.get(i-2).getMouth1());  
            cell3.setCellStyle(bodyCellStyle);  
  
            
         // Retrieve the model value  
            HSSFCell cell4 = row.createCell(startColIndex+3);  
            cell4.setCellValue(datasource.get(i-2).getPrecent1());  
            cell4.setCellStyle(bodyCellStyle); 
            
            
         // Retrieve the brand value  
            HSSFCell cell5 = row.createCell(startColIndex+4);  
            cell5.setCellValue(datasource.get(i-2).getNumber2());  
            cell5.setCellStyle(bodyCellStyle);  
  
            // Retrieve the model value  
            HSSFCell cell6 = row.createCell(startColIndex+5);  
            cell6.setCellValue(datasource.get(i-2).getMouth2());  
            cell6.setCellStyle(bodyCellStyle);  
  
            
         // Retrieve the model value  
            HSSFCell cell7 = row.createCell(startColIndex+6);  
            cell7.setCellValue(datasource.get(i-2).getPrecent2());  
            cell7.setCellStyle(bodyCellStyle); 
            
            
         // Retrieve the brand value  
            HSSFCell cell8 = row.createCell(startColIndex+7);  
            cell8.setCellValue(datasource.get(i-2).getNumber3());  
            cell8.setCellStyle(bodyCellStyle);  
  
            // Retrieve the model value  
            HSSFCell cell9 = row.createCell(startColIndex+8);  
            cell9.setCellValue(datasource.get(i-2).getMouth3());  
            cell9.setCellStyle(bodyCellStyle);  
  
            
         // Retrieve the model value  
            HSSFCell cell10 = row.createCell(startColIndex+9);  
            cell10.setCellValue(datasource.get(i-2).getPrecent3());  
            cell10.setCellStyle(bodyCellStyle);   
  
             
         // Retrieve the brand value  
            HSSFCell cell11 = row.createCell(startColIndex+10);  
            cell11.setCellValue(datasource.get(i-2).getNumber4());  
            cell11.setCellStyle(bodyCellStyle);  
  
            // Retrieve the model value  
            HSSFCell cell12 = row.createCell(startColIndex+11);  
            cell12.setCellValue(datasource.get(i-2).getMouth4());  
            cell12.setCellStyle(bodyCellStyle);  
  
            
         // Retrieve the model value  
            HSSFCell cell13 = row.createCell(startColIndex+12);  
            cell13.setCellValue(datasource.get(i-2).getPrecent4());  
            cell13.setCellStyle(bodyCellStyle); 
            
            
         // Retrieve the brand value  
            HSSFCell cell14 = row.createCell(startColIndex+13);  
            cell14.setCellValue(datasource.get(i-2).getNumber5());  
            cell14.setCellStyle(bodyCellStyle);  
  
            // Retrieve the model value  
            HSSFCell cell15 = row.createCell(startColIndex+14);  
            cell15.setCellValue(datasource.get(i-2).getMouth5());  
            cell15.setCellStyle(bodyCellStyle);  
  
            
         // Retrieve the model value  
            HSSFCell cell16 = row.createCell(startColIndex+15);  
            cell16.setCellValue(datasource.get(i-2).getPrecent5());  
            cell16.setCellStyle(bodyCellStyle); 
            
         // Retrieve the brand value  
            HSSFCell cell17 = row.createCell(startColIndex+16);  
            cell17.setCellValue(datasource.get(i-2).getNumber6());  
            cell17.setCellStyle(bodyCellStyle);  
  
            // Retrieve the model value  
            HSSFCell cell18 = row.createCell(startColIndex+17);  
            cell18.setCellValue(datasource.get(i-2).getMouth6());  
            cell18.setCellStyle(bodyCellStyle);  
  
            
         // Retrieve the model value  
            HSSFCell cell19 = row.createCell(startColIndex+18);  
            cell19.setCellValue(datasource.get(i-2).getPrecent6());  
            cell19.setCellStyle(bodyCellStyle); 
            
         // Retrieve the brand value  
            HSSFCell cell20 = row.createCell(startColIndex+19);  
            cell20.setCellValue(datasource.get(i-2).getNumber7());  
            cell20.setCellStyle(bodyCellStyle);  
  
            // Retrieve the model value  
            HSSFCell cell21 = row.createCell(startColIndex+20);  
            cell21.setCellValue(datasource.get(i-2).getMouth7());  
            cell21.setCellStyle(bodyCellStyle);  
  
            
         // Retrieve the model value  
            HSSFCell cell22 = row.createCell(startColIndex+21);  
            cell22.setCellValue(datasource.get(i-2).getPrecent7());  
            cell22.setCellStyle(bodyCellStyle); 
            
         // Retrieve the brand value  
            HSSFCell cell23 = row.createCell(startColIndex+22);  
            cell23.setCellValue(datasource.get(i-2).getNumber8());  
            cell23.setCellStyle(bodyCellStyle);  
  
            // Retrieve the model value  
            HSSFCell cell24 = row.createCell(startColIndex+23);  
            cell24.setCellValue(datasource.get(i-2).getMouth8());  
            cell24.setCellStyle(bodyCellStyle);  
  
            
         // Retrieve the model value  
            HSSFCell cell25 = row.createCell(startColIndex+24);  
            cell25.setCellValue(datasource.get(i-2).getPrecent8());  
            cell25.setCellStyle(bodyCellStyle); 
            
         // Retrieve the brand value  
            HSSFCell cell26 = row.createCell(startColIndex+25);  
            cell26.setCellValue(datasource.get(i-2).getNumber9());  
            cell26.setCellStyle(bodyCellStyle);  
  
            // Retrieve the model value  
            HSSFCell cell27 = row.createCell(startColIndex+26);  
            cell27.setCellValue(datasource.get(i-2).getMouth9());  
            cell27.setCellStyle(bodyCellStyle);  
  
            
         // Retrieve the model value  
            HSSFCell cell28 = row.createCell(startColIndex+27);  
            cell28.setCellValue(datasource.get(i-2).getPrecent9());  
            cell28.setCellStyle(bodyCellStyle); 
            
         // Retrieve the brand value  
            HSSFCell cell29 = row.createCell(startColIndex+28);  
            cell29.setCellValue(datasource.get(i-2).getNumber10());  
            cell29.setCellStyle(bodyCellStyle);  
  
            // Retrieve the model value  
            HSSFCell cell30 = row.createCell(startColIndex+29);  
            cell30.setCellValue(datasource.get(i-2).getMouth10());  
            cell30.setCellStyle(bodyCellStyle);  
  
            
         // Retrieve the model value  
            HSSFCell cell31 = row.createCell(startColIndex+30);  
            cell31.setCellValue(datasource.get(i-2).getPrecent10());  
            cell31.setCellStyle(bodyCellStyle); 
            
         // Retrieve the brand value  
            HSSFCell cell32 = row.createCell(startColIndex+31);  
            cell32.setCellValue(datasource.get(i-2).getNumber11());  
            cell32.setCellStyle(bodyCellStyle);  
  
            // Retrieve the model value  
            HSSFCell cell33 = row.createCell(startColIndex+32);  
            cell33.setCellValue(datasource.get(i-2).getMouth11());  
            cell33.setCellStyle(bodyCellStyle);  
  
            
         // Retrieve the model value  
            HSSFCell cell34 = row.createCell(startColIndex+33);  
            cell34.setCellValue(datasource.get(i-2).getPrecent11());  
            cell34.setCellStyle(bodyCellStyle); 
            
         // Retrieve the brand value  
            HSSFCell cell35 = row.createCell(startColIndex+34);  
            cell35.setCellValue(datasource.get(i-2).getNumber12());  
            cell35.setCellStyle(bodyCellStyle);  
  
            // Retrieve the model value  
            HSSFCell cell36 = row.createCell(startColIndex+35);  
            cell36.setCellValue(datasource.get(i-2).getMouth12());  
            cell36.setCellStyle(bodyCellStyle);  
  
            
         // Retrieve the model value  
            HSSFCell cell37 = row.createCell(startColIndex+36);  
            cell37.setCellValue(datasource.get(i-2).getPrecent12());  
            cell37.setCellStyle(bodyCellStyle); 
        }  
    }  
}  