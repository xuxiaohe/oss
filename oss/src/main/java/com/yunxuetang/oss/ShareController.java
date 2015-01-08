package com.yunxuetang.oss;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.Config;




@Controller

public class ShareController {

	
	
	//@RequestMapping("/")
//	public String mappings() {
//	
//	}
	
	 @SuppressWarnings("rawtypes")
	 
	 @RequestMapping("/getmethods")
	 private  void getMethodInfo(String pkgName) {
	       try {
	           Class clazz = this.getClass();
	           Method[] methods = clazz.getMethods();
	           for (Method method : methods) {
	               String methodName = method.getName();
	               System.out.println("方法名称:" + methodName);
	               Class<?>[] parameterTypes = method.getParameterTypes();
	               for (Class<?> clas : parameterTypes) {
	                   String parameterName = clas.getName();
	                   System.out.println("参数名称:" + parameterName);
	               }
	               System.out.println("*****************************");
	           }
	       } catch (Exception e) {
	           e.printStackTrace();
	       }
	   }
	 
	 
	 
	/**
	 * 查询分享课程信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/course")
	public  ModelAndView findCourseDetails(HttpServletRequest request,String courseid) {
		RestTemplate restTemplate=new RestTemplate();
		ModelAndView modelview = new ModelAndView();
		String courSharResoStr= restTemplate.getForObject(
				Config.YXTSERVER3+"courseShare/course?courseId="
						+ courseid, String.class); 
		 
		try {
			//courSharReso = new ObjectMapper().readValue(courSharResoStr, CourseShareResponse.class);
			JSONObject objj=JSONObject.fromObject(courSharResoStr);
			modelview.addObject("courSharReso", objj);
		} catch (Exception e) {
			e.printStackTrace();
		} 
				
		modelview.setViewName("share_course");
		return modelview;

	}
	
	
	
	@RequestMapping("/findAllUser")
	public  ModelAndView findAllUser(HttpServletRequest request) {
		RestTemplate restTemplate=new RestTemplate();
		ModelAndView modelview = new ModelAndView();
		String courSharResoStr= restTemplate.getForObject(
				Config.YXTSERVER3+"user/findUserPage?s=200&n=0&udid=E6CF041A-1884-4C3C-B8A1-10CEF5867E9B&token=AED172488C83BD9118DC7AE7774996AB"
						, String.class); 
		 
		try {
			//courSharReso = new ObjectMapper().readValue(courSharResoStr, CourseShareResponse.class);
			JSONObject objj=JSONObject.fromObject(courSharResoStr);
			modelview.addObject("res", objj);
		} catch (Exception e) {
			e.printStackTrace();
		} 
				
		modelview.setViewName("success");
		return modelview;

	}
	
}
