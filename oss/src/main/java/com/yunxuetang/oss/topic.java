package com.yunxuetang.oss;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/topic")
public class topic {
	public static String YXTSERVER = "http://s1.xuewen.yunxuetang.com:8084/";
	public static String YXTSERVER2 = "http://s1.xuewen.yunxuetang.com:8082/";
	public static String YXTSERVER3 = "http://localhost:8080/";
	public topic() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping("/delete")
	public String userDetail(HttpServletRequest request) {
		String courSharResoStr;
		String topicid = request.getParameter("topicid");
		String userid = request.getParameter("userid");
		RestTemplate restTemplate = new RestTemplate();

		courSharResoStr = restTemplate.getForObject(YXTSERVER3
				+ "oss/topic/delete?topicid=" + topicid, String.class);
		JSONObject objj = null;
		try {
			objj = JSONObject.fromObject(courSharResoStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/user/userTopic?userid="+userid; 
	}

}
