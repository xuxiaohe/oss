package com.yunxuetang.oss;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;




@Controller
@RequestMapping("/courseShare")
public class ShareController {

	public static String YXTSERVER="http://s1.xuewen.yunxuetang.com:8084/";
	public static String YXTSERVER2="http://s1.xuewen.yunxuetang.com:8082/";
	//user/findUserPage?udid=E6CF041A-1884-4C3C-B8A1-10CEF5867E9B&token=AED172488C83BD9118DC7AE7774996AB
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
				YXTSERVER+"courseShare/course?courseId="
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
				YXTSERVER2+"user/findUserPage?udid=E6CF041A-1884-4C3C-B8A1-10CEF5867E9B&token=AED172488C83BD9118DC7AE7774996AB"
						, String.class); 
		 
		try {
			//courSharReso = new ObjectMapper().readValue(courSharResoStr, CourseShareResponse.class);
			JSONObject objj=JSONObject.fromObject(courSharResoStr);
			modelview.addObject("courSharReso", objj);
		} catch (Exception e) {
			e.printStackTrace();
		} 
				
		modelview.setViewName("success");
		return modelview;

	}
	
}
