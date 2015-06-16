package com.yunxuetang.oss;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.context.support.StaticApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yunxuetang.util.Config;

@Controller
@RequestMapping("/checkjobs")
public class monitorSystem extends BaseController{
	
	public  JSONObject HOUTAI_API=null;
	public  JSONObject COUPON_API=null;
	public  JSONObject ORDER_SERVER=null;
	public  JSONObject WIKERSERVER=null;
	public  JSONObject QDANSERVER=null;
	public  JSONObject ZHANGDAN_API=null;
	public  JSONObject SCHEDULE_SERVER=null;
	public  JSONObject TAG=null;
	
	public void checkhoutai() {
		System.out.println("=================定时任务开始==============");
		HOUTAI_API=sendmessage();
		System.out.println(HOUTAI_API);
		TAG=sendmessage2();
		System.out.println(TAG);
		WIKERSERVER=sendmessage3();
		System.out.println(WIKERSERVER);
		QDANSERVER=sendmessage4();
		System.out.println(QDANSERVER);
		ZHANGDAN_API=sendmessage5();
		System.out.println(ZHANGDAN_API);
//		COUPON_API=sendmessage6();
//		System.out.println(COUPON_API);
//		SCHEDULE_SERVER=sendmessage7();
//		System.out.println(SCHEDULE_SERVER);
		System.out.println("=================定时任务结束==============");
	}
	
	
	@RequestMapping("sendView")
	public String sendView(HttpServletRequest request, Model model){
		model.addAttribute("status", HOUTAI_API);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		model.addAttribute("cbasePath", cbasePath);
		model.addAttribute("sourcePath", Config.YXTSERVER5);
		return "monitorSystem/status";
	}
	
	
	private JSONObject sendmessage() {
		String url = Config.YXTSERVER3 + "/health";
	
		return getRestApiData(url);
	}
	
	private JSONObject sendmessage2() {
		String url = Config.YXTSERVER4 + "/health";
	
		return getRestApiData(url);
	}
	
	private JSONObject sendmessage3() {
		String url = Config.WIKERSERVER + "/health";
	
		return getRestApiData(url);
	}
	
	
	private JSONObject sendmessage4() {
		String url = Config.QDANSERVER + "/health";
	
		return getRestApiData(url);
	}
	
	
	private JSONObject sendmessage5() {
		String url = Config.ORDER_SERVER + "/health";
	
		return getRestApiData(url);
	}
	
	private JSONObject sendmessage6() {
		String url = Config.HONGBAO_SERVER + "/health";
	
		return getRestApiData(url);
	}
	
	
	
	private JSONObject sendmessage7() {
		String url = Config.SCHEDULE_SERVER + "/health";
	
		return getRestApiData(url);
	}
	
}
