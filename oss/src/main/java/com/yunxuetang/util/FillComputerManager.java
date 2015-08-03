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
            cell2.setCellValue(datasource.get(i-2).getMouth1());  
            cell2.setCellStyle(bodyCellStyle);  
  
            // Retrieve the model value  
            HSSFCell cell3 = row.createCell(startColIndex+2);  
            cell3.setCellValue(datasource.get(i-2).getMouth2());  
            cell3.setCellStyle(bodyCellStyle);  
  
            // Retrieve the maximum power value  
            HSSFCell cell4 = row.createCell(startColIndex+3);  
            cell4.setCellValue(datasource.get(i-2).getMouth3());  
            cell4.setCellStyle(bodyCellStyle);  
  
            // Retrieve the price value  
            HSSFCell cell5 = row.createCell(startColIndex+4);  
            cell5.setCellValue(datasource.get(i-2).getMouth4());  
            cell5.setCellStyle(bodyCellStyle);  
          
            // Retrieve the efficiency value  
            HSSFCell cell6 = row.createCell(startColIndex+5);  
            cell6.setCellValue(datasource.get(i-2).getMouth5());  
            cell6.setCellStyle(bodyCellStyle);  
            
            // Retrieve the efficiency value  
            HSSFCell cell7 = row.createCell(startColIndex+6);  
            cell7.setCellValue(datasource.get(i-2).getMouth6());  
            cell7.setCellStyle(bodyCellStyle);  
            
            // Retrieve the efficiency value  
            HSSFCell cell8 = row.createCell(startColIndex+7);  
            cell8.setCellValue(datasource.get(i-2).getMouth7());  
            cell8.setCellStyle(bodyCellStyle);  
            
            // Retrieve the efficiency value  
            HSSFCell cell9 = row.createCell(startColIndex+8);  
            cell9.setCellValue(datasource.get(i-2).getMouth8());  
            cell9.setCellStyle(bodyCellStyle);  
            
            
            // Retrieve the efficiency value  
            HSSFCell cell10 = row.createCell(startColIndex+9);  
            cell10.setCellValue(datasource.get(i-2).getMouth9());  
            cell10.setCellStyle(bodyCellStyle);  
            
            // Retrieve the efficiency value  
            HSSFCell cell11 = row.createCell(startColIndex+10);  
            cell11.setCellValue(datasource.get(i-2).getMouth10());  
            cell11.setCellStyle(bodyCellStyle);  
            
            // Retrieve the efficiency value  
            HSSFCell cell12 = row.createCell(startColIndex+11);  
            cell12.setCellValue(datasource.get(i-2).getMouth11());  
            cell12.setCellStyle(bodyCellStyle);  
            
            // Retrieve the efficiency value  
            HSSFCell cell13 = row.createCell(startColIndex+12);  
            cell13.setCellValue(datasource.get(i-2).getMouth12());  
            cell13.setCellStyle(bodyCellStyle);  
            
            // Retrieve the efficiency value  
            HSSFCell cell14 = row.createCell(startColIndex+13);  
            cell14.setCellValue(datasource.get(i-2).getSum());  
            cell14.setCellStyle(bodyCellStyle);  
            
            // Retrieve the efficiency value  
            HSSFCell cell15 = row.createCell(startColIndex+14);  
            cell15.setCellValue(datasource.get(i-2).getPrecent());  
            cell15.setCellStyle(bodyCellStyle);  
        }  
    }  
}  