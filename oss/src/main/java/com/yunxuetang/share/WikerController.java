package com.yunxuetang.share;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.oss.BaseController;
import com.yunxuetang.util.Config;

/**
 * 微课分享专用
 * */
@Controller
@RequestMapping("/wiker")
public class WikerController extends BaseController{
	
	@RequestMapping("/share")
	public  ModelAndView findCourseDetails(HttpServletRequest request,String courseid) {
		ModelAndView modelview = new ModelAndView();
		RestTemplate restTemplate = new RestTemplate();
		
		String url = Config.WIKERSERVER + "share/" + courseid;
		
		
		String courSharResoStr= restTemplate.getForObject(url,String.class); 
		try {
			
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
			modelview.addObject("course", objj);
		}catch (Exception e) {
			e.printStackTrace();
		}  
				
		modelview.setViewName("share/share_wiker");
		return modelview;
	}
}
