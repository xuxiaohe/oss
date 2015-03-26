package com.yunxuetang.oss;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.swing.event.ListSelectionEvent;

import net.sf.json.JSONObject;
import net.sf.json.util.NewBeanInstanceStrategy;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

public class test extends BaseController {

	
	
	
	public static void main(String[] args) {
//		long l=System.currentTimeMillis();
//		Date date = new Date(l);
//		System.out.println(l);
//		System.out.print(date);
		
		
		 long ll=System.currentTimeMillis();
		    Date date = new Date(ll);
		//今日区间
		 Calendar c1 = new GregorianCalendar();
		 c1.setTime(date);
//		    c1.set(Calendar.HOUR_OF_DAY, 14);
//		    c1.set(Calendar.MINUTE, 0);
//		    c1.set(Calendar.SECOND, 0);
//		    Date d=c1.getTime();
//		    long l=d.getTime();
		   
		    System.out.println(c1.getTime().toLocaleString());
//		    System.out.println(c1.getTime().toLocaleString());
		    //System.out.println(l);
		    c1.add(Calendar.HOUR, -168);
		    System.out.println(c1.getTime().toLocaleString());
		    
		    
		    
		    
		    
		    
//		    Calendar c2 = new GregorianCalendar();
//		    c2.set(Calendar.HOUR, -24);
//		    c2.set(Calendar.MINUTE, 0);
//		    c2.set(Calendar.SECOND, 0);
//		    Date d2=c2.getTime();
//		    long l2=d2.getTime();
//		    System.out.println(c2.getTime().toLocaleString());		    
		    //System.out.println(l2);
		    
		  //昨日区间
//			 Calendar c1 = new GregorianCalendar();
//			 
//			    c1.set(Calendar.HOUR_OF_DAY, -24);
//			    c1.set(Calendar.MINUTE, 0);
//			    c1.set(Calendar.SECOND, 0);
//			    Date d=c1.getTime();
//			    long l=d.getTime();
//			    System.out.println(c1.getTime().toLocaleString());
//			    
//			    
//			    
//			    Calendar c2 = new GregorianCalendar();
//			    c2.set(Calendar.HOUR_OF_DAY, -1);
//			    c2.set(Calendar.MINUTE, 59);
//			    c2.set(Calendar.SECOND, 59);
//			    Date d2=c2.getTime();
//			    long l2=d2.getTime();
//			    System.out.println(c2.getTime().toLocaleString());
			    
			    
			    
			    
			    
			    //一周区间
		
		//本周周一的开始
//		  int mondayPlus = getMondayPlus();
//	        GregorianCalendar currentDate = new GregorianCalendar();
//	        currentDate.add(GregorianCalendar.DATE, mondayPlus);
//	        Date monday = currentDate.getTime();
//	        Calendar c = Calendar.getInstance();
//	        c.setTime(monday);
//	        c.set(Calendar.HOUR_OF_DAY, 0);
//		    c.set(Calendar.MINUTE, 0);
//		    c.set(Calendar.SECOND, 0);
//	        
//	        
//	        System.out.println(c.getTime().toLocaleString());
//				    
//	        //本周今天的结束
//	        Calendar c2 = new GregorianCalendar();
//		    c2.set(Calendar.HOUR_OF_DAY, 23);
//		    c2.set(Calendar.MINUTE, 59);
//		    c2.set(Calendar.SECOND, 59);
//		    Date d2=c2.getTime();
//		    long l2=d2.getTime();
//		    System.out.println(c2.getTime().toLocaleString());	    
				    
				
		    
		  //月区间
//			 Calendar c1 = new GregorianCalendar();
//			 c1.set(Calendar.DAY_OF_MONTH, 1);
//			    c1.set(Calendar.HOUR_OF_DAY, 0);
//			    c1.set(Calendar.MINUTE, 0);
//			    c1.set(Calendar.SECOND, 0);
//			    Date d=c1.getTime();
//			    long l=d.getTime();
//			    System.out.println(c1.getTime().toLocaleString());
//			    
//			    
//			    
//			    Calendar c2 = new GregorianCalendar();
//			    c1.set(Calendar.DAY_OF_MONTH, -1);
//			    c2.set(Calendar.HOUR_OF_DAY, 23);
//			    c2.set(Calendar.MINUTE, 59);
//			    c2.set(Calendar.SECOND, 59);
//			    Date d2=c2.getTime();
//			    long l2=d2.getTime();
//			    System.out.println(c2.getTime().toLocaleString());
		    
		    
		    
		    
	}
	
	
	
	
	
	
       public static int getMondayPlus() {
	        Calendar cd = Calendar.getInstance();
	        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
	        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
	        if (dayOfWeek == 1) {
	            return -6;
	        } else {
	            return 2 - dayOfWeek;
	        }
	    }
	
	}

