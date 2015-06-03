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
	 * 创建卡券页面跳转
	 * */
	@RequestMapping("/createView")
	public String createView(HttpServletRequest request, Model model){
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		model.addAttribute("cbasePath", cbasePath);
		model.addAttribute("sourcePath", Config.YXTSERVER5);
		return "coupon/create";
	}
	
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
		operuserid="55190fdbe4b0c524eabaee66";
		String actname = request.getParameter("actname");
		String cname = request.getParameter("cname");
		String ident = request.getParameter("ident");
		ident="0";
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
		return "redirect:coupon/couponList";
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
	 * 优惠券详情
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/couponDetail")
	public String couponDetail(HttpServletRequest request, Model model){
		String id = request.getParameter("id");
		model.addAttribute("couponDetail", getcouponDetail(id));
		
		//model.addObject("courseDetail", courseDetail(cid));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		model.addAttribute("cbasePath", cbasePath);
		model.addAttribute("sourcePath", Config.YXTSERVER5);
		return "coupon/couponList";
	}
	
	
	
	/**
	 * 分页 所有优惠券列表
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
	
	
	
	/**
	 * 分页 所有活动列表
	 * 
	 */
	@RequestMapping("/activityList")
	private ModelAndView activityList(HttpServletRequest request) {
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

		modelview.addObject("activityList", getallactivity(pagenumber, pagelines));
		// logger.info(request.getSession().getAttribute("name")+"刷新用户列表操作的用户"+request.getSession().getAttribute("name"));

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("coupon/activityList");
		return modelview;
	}
	
	
	/**
	 * 活动详情
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/activityDetail")
	public String activityDetail(HttpServletRequest request, Model model){
		String id = request.getParameter("id");
		model.addAttribute("couponDetail", getacrtivityDetail(id));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		model.addAttribute("cbasePath", cbasePath);
		model.addAttribute("sourcePath", Config.YXTSERVER5);
		return "coupon/activityList";
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
	
	public JSONObject getallactivity(String n,String s){
		String url = Config.HONGBAO_SERVER + "/coupon/allActivityList?n="+n+"&s="+s;
		
		return getRestApiData(url);
	} 
	
	public JSONObject getcouponDetail(String id){
		String url = Config.HONGBAO_SERVER + "/coupon/couponDetail?id="+id;
		
		return getRestApiData(url);
	} 
	
	public JSONObject getacrtivityDetail(String id){
		String url = Config.HONGBAO_SERVER + "/coupon/activityDetail?id="+id;
		
		return getRestApiData(url);
	} 
	private JSONObject courseDetail(String cid) {
		String url = Config.YXTSERVER3 + "oss/course/oneCourse?courseId="+cid;
		return getRestApiData(url);
}
}
