package com.yunxuetang.oss;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/tag")
public class tag extends BaseController{

	
	/**
	 * 干货详情
	 */
	@RequestMapping("/tagList")
	public ModelAndView dryDetail(HttpServletRequest request) {
		ModelAndView modelview = new ModelAndView();
		 
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("tag/tagList");
		return modelview;

	}
}
