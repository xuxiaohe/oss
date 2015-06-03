package com.yunxuetang.oss;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.Config;


@Controller
@RequestMapping("/coupon")
public class Coupon extends BaseController{
	
	/**
	 * 某个用户所有优惠券
	 * @param request
	 * @param model
	 * @return
	 */
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
	
	
	
	/**
	 * 创建优惠券
	 * @param request
	 * @param model
	 * @return
	 */
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
	
	/**
	 * 根据课程id 查找有效批次（活动）信息
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/findactbycourseid")
	public String findactbycourseid(HttpServletRequest request, Model model){
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
	
	
	
	/**
	 * 分页获得系统的所有账户 支持搜索功能 字段： 用户名 手机号 邮箱 支持排序 （按时间倒序，正序,其他字段）
	 * 
	 */
	@RequestMapping("/couponList")
	private ModelAndView userList(HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String keyword = null;

		keyword = request.getParameter("keyword");

		if (keyword == null) {
			keyword = "";
		}

		// 当前第几页
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

		modelview.addObject("couponList", getall(pagenumber, pagelines));
		// logger.info(request.getSession().getAttribute("name")+"刷新用户列表操作的用户"+request.getSession().getAttribute("name"));

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("coupon/couponList");
		return modelview;
	}
	
	
	public JSONObject getCouponList(String userId){
		String url = Config.HONGBAO_SERVER + "/coupon/user/coupons?userid=" + userId;
		return getRestApiData(url);
	} 
	
	public JSONObject create(Map<String,String> m){
		String url = Config.HONGBAO_SERVER + "/coupon/quota/add";
		
		return getRestApiData(url,m);
	} 
	
	
	public JSONObject getall(String n,String s){
		String url = Config.HONGBAO_SERVER + "/coupon/allList?n="+n+"&s="+s;
		
		return getRestApiData(url);
	} 
}
