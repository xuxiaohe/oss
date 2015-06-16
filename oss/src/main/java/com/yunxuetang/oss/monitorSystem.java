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
	
	public static JSONObject HOUTAI_API=null;
	public static String COUPON_API="";
	public static String ZHANGDAN_API="";
	
	public void checkhoutai() {
		System.out.println("=================定时任务开始==============");
		HOUTAI_API=sendmessage();
		System.out.println(HOUTAI_API);
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
		String url = Config.YXTSERVER3 + "health";
	
		return getRestApiData(url);
	}
	
}
