package com.yunxuetang.share;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.Config;

/**
 * 轻单分享专用
 * */
@Controller
@RequestMapping("/qdan")
public class QdanShareController {

	@RequestMapping("/share")
	public  ModelAndView findCourseDetails(HttpServletRequest request,String qid) {
		ModelAndView modelview = new ModelAndView();
		RestTemplate restTemplate = new RestTemplate();
		
		String url = Config.QDANSERVER + "share/" + qid;
		
		
		String qdanSharResoStr= restTemplate.getForObject(url,String.class); 
		System.out.println(qdanSharResoStr.toString());
		try {
			
			JSONObject objj = JSONObject.fromObject(qdanSharResoStr);
			modelview.addObject("o", objj);
		}catch (Exception e) {
			e.printStackTrace();
		}  
				
		modelview.setViewName("share/share_qdan");
		return modelview;
	}
}
