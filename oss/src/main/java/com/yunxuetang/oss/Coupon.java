package com.yunxuetang.oss;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yunxuetang.util.Config;


@Controller
@RequestMapping("/coupon")
public class Coupon extends BaseController{
	
	
	@RequestMapping("/list")
	public String couponList(HttpServletRequest request, Model model){
		String userId = request.getParameter("userId");
		model.addAttribute("couponlist", getCouponList(userId));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		model.addAttribute("cbasePath", cbasePath);
		model.addAttribute("sourcePath", Config.YXTSERVER5);
		return "coupon/couponList";
	}
	
	@RequestMapping("/create")
	public String create(HttpServletRequest request, Model model){
		String cardhead = request.getParameter("cardhead");
		String quota = request.getParameter("quota");
		String exdatestart = request.getParameter("exdatestart");
		String exdateend = request.getParameter("exdateend");
		String courseid = request.getParameter("courseid");
		String num = request.getParameter("num");
		String remark = request.getParameter("remark");
		String operuserid = request.getParameter("operuserid");
		String actname = request.getParameter("actname");
		String cname = request.getParameter("cname");
		String ident = request.getParameter("ident");
		
		Map<String,String> m=new HashMap<String, String>();
		m.put("cardhead", cardhead);
		m.put("quota", quota);
		m.put("exdatestart", exdatestart);
		m.put("exdateend", exdateend);
		m.put("courseid", courseid);
		m.put("num", num);
		m.put("remark", remark);
		m.put("operuserid", operuserid);
		m.put("actname", actname);
		m.put("cname", cname);
		m.put("ident", ident);
		
		model.addAttribute("couponlist", create(m));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		model.addAttribute("cbasePath", cbasePath);
		model.addAttribute("sourcePath", Config.YXTSERVER5);
		return "coupon/couponList";
	}
	
	
	public JSONObject getCouponList(String userId){
		String url = Config.HONGBAO_SERVER + "/coupon/user/coupons?userid=" + userId;
		return getRestApiData(url);
	} 
	
	public JSONObject create(Map<String,String> m){
		String url = Config.HONGBAO_SERVER + "/coupon/quota/add";
		
		return getRestApiData(url,m);
	} 
	
}
