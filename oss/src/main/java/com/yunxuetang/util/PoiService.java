package com.yunxuetang.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;


@Component
public class PoiService {
    
	 
    
    public void exportXLS(HttpServletResponse response) {
        
        // 1.创建一个 workbook
        HSSFWorkbook workbook = new HSSFWorkbook();
        
        // 2.创建一个 worksheet
        HSSFSheet worksheet = workbook.createSheet("数据统计报表");
        
        // 3.定义起始行和列
        int startRowIndex = 0;
        int startColIndex = 0;
        
        // 4.创建title,data,headers
        
        // 5.填充数据
        
        // 6.设置reponse参数
        String fileName = "Report.xls";
        response.setHeader("Content-Disposition", "inline; filename=" + fileName);
        // 确保发送的当前文本格式
        response.setContentType("application/vnd.ms-excel");
        
        // 7. 输出流
        
    }
    
    /**
     * 读取报表
     */
    public List<String>  readReport(InputStream inp) {
        
        List<String>  List = new ArrayList<String> ();
        List<String>  Listerrs = new ArrayList<String> ();
        
        try {
            String cellStr = null;
            
            Workbook wb = WorkbookFactory.create(inp);
            
            Sheet sheet = wb.getSheetAt(0);// 取得第一个sheets
            
            // 从第1行开始读取数据
            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                
                
                Row row = sheet.getRow(i); // 获取行(row)对象
                //System.out.println(row);
                if (row == null) {
                    // row为空的话,不处理
                    continue;
                }
                
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    
                    Cell cell = row.getCell(j); // 获得单元格(cell)对象
                    
                    // 转换接收的单元格
                    cellStr = ConvertCellStr(cell, cellStr);
                    List.add(cellStr);
                    //System.out.println(cellStr);
                    
                    
                }
                
                
                String courSharResoStr;
        		RestTemplate restTemplate=new RestTemplate();
        		ModelAndView modelview = new ModelAndView();
        		courSharResoStr= restTemplate.postForObject(Config.YXTSERVER2+"user/regist?userName="+List.get(0)+"&passWord="+List.get(1),null, String.class);
        		JSONObject objj=JSONObject.fromObject(courSharResoStr);
        		if(objj.getInt("status")!=200){
        			Listerrs.add(List.get(0));
        		}
        		
                // 将添加数据后的对象填充至list中
                System.out.println(List);
                System.out.println("");
                System.out.println("");
                for(int s=List.size()-1;s>=0;s--){
                	 
                		List.remove(s);
                }
            }
            
          } catch (Exception e) {
           
            e.printStackTrace();
        } finally {
            if (inp != null) {
                try {
                    inp.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                
            }
        }
        return Listerrs;
        
    }
    
    
    
    
    /**
     * 把单元格内的类型转换至String类型
     */
    private String ConvertCellStr(Cell cell, String cellStr) {
        
        switch (cell.getCellType()) {
        
        case Cell.CELL_TYPE_STRING:
            // 读取String
            cellStr = cell.getStringCellValue().toString();
            break;
        
        case Cell.CELL_TYPE_BOOLEAN:
            // 得到Boolean对象的方法
            cellStr = String.valueOf(cell.getBooleanCellValue());
            break;
        
        case Cell.CELL_TYPE_NUMERIC:
            
            // 先看是否是日期格式
            if (DateUtil.isCellDateFormatted(cell)) {
                
                // 读取日期格式
                cellStr = cell.getDateCellValue().toString();
                
            } else {
                
                // 读取数字
                cellStr = String.valueOf(cell.getNumericCellValue());
            }
            break;
        
        case Cell.CELL_TYPE_FORMULA:
            // 读取公式
            cellStr = cell.getCellFormula().toString();
            break;
        }
        return cellStr;
    }
    
    
    
}
