package com.yunxuetang.oss;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.Config;

@Controller
@RequestMapping("/feedback")
public class feedback extends BaseController{
	
	public feedback() {
		// TODO Auto-generated constructor stub
	} 
	
	/**
	 * 
	 * 排行榜列表
	 */
	@RequestMapping("/FeedBackList")
	public ModelAndView FeedBackList(HttpServletRequest request) {
		String pagenumber = request.getParameter("n");

		if (pagenumber == null) {
			pagenumber = "0";
		}

		// 每页条数

		String pagelines = request.getParameter("s");

		if (pagelines == null) {
			pagelines = "10";
		}
		 
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		
		modelview.addObject("FeedBackList", FeedBackList(pagenumber,pagelines));
		 
		
		modelview.setViewName("feedback/feedbackList");
		return modelview;
	}
	
	
	/**
	 * 
	 * 排行榜列表
	 */
	@RequestMapping("/deleteFeedBack")
	public String deleteFeedBack(HttpServletRequest request) {
		 
		String feedbackid = request.getParameter("id");

		deleteFeedBack(feedbackid);
		
		return "redirect:/feedback/FeedBackList";
	}
	
	private JSONObject FeedBackList(String n,String s) {
		String url = Config.YXTSERVER3 + "oss/feedback/searchFeedBack?n="+n+"&s="+s;
		return getRestApiData(url);
	}
	
	private JSONObject deleteFeedBack(String feedbackid) {
		String url = Config.YXTSERVER3 + "oss/feedback/deleteFeedBack?feedbackid="+feedbackid;
		return getRestApiData(url);
	}
	

}
