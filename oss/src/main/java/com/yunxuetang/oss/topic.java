package com.yunxuetang.oss;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.Config;

@Controller
@RequestMapping("/topic")
public class topic {

	public topic() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping("/delete")
	public String userDetail(HttpServletRequest request) {
		String courSharResoStr;
		String topicid = request.getParameter("topicid");
		String userid = request.getParameter("userid");
		RestTemplate restTemplate = new RestTemplate();

		courSharResoStr = restTemplate.getForObject(Config.YXTSERVER3
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
