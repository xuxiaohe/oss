package com.yunxuetang.oss;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/banner")
public class Banner extends BaseController{
	
	/**
	 * banner列表
	 * */
	@RequestMapping("/list")
	public String bannerListView(HttpServletRequest request){
		return "banner/banner-list";
	}
	
	/**
	 * 创建banner视图
	 * */
	@RequestMapping("/createView")
	public String bannerListView(HttpServletRequest request, Model model){
		return "banner/banner-create";
	}
	
	

}
