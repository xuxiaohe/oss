package com.yunxuetang.oss;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.Config;


@Controller
@RequestMapping("/coupon")
public class Coupon extends BaseController{
	
	Logger logger = LoggerFactory.getLogger(Coupon.class);
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
	 * 删除页面跳转
	 * */
	@RequestMapping("/deleteView")
	public String deleteView(HttpServletRequest request, Model model){
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		model.addAttribute("cbasePath", cbasePath);
		model.addAttribute("sourcePath", Config.YXTSERVER5);
		return "coupon/delete";
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
		String courseName = request.getParameter("courseName");
		 
		String username="";
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
		m.put("userId", username);
		m.put("courseName", courseName);
		logger.info(request.getSession().getAttribute("name")+"创建优惠券操作的管理员 "+request.getSession().getAttribute("name")+"===创建优惠券name"+cname+"课程ID"+courseid);
		
		model.addAttribute("couponlist", create(m));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		model.addAttribute("cbasePath", cbasePath);
		model.addAttribute("sourcePath", Config.YXTSERVER5);
		return "redirect:/coupon/couponList";
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
		String aid = request.getParameter("aid");
		JSONObject j=getcouponDetail(id);
		JSONObject jj=j.getJSONObject("data");
		JSONObject jjj=jj.getJSONObject("result");
		String courseid=(String) jjj.get("courseId");
		model.addAttribute("couponDetail", getcouponDetail(id));
		model.addAttribute("orderList", oederDetail(id));
		model.addAttribute("aid", aid);
		//System.out.print(courseDetail(courseid));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		model.addAttribute("cbasePath", cbasePath);
		model.addAttribute("sourcePath", Config.YXTSERVER5);
		return "coupon/couponDetail";
	}
	
	
	/**
	 *  根据优惠券状态搜索
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/findBycoupon")
	public String findBycoupon(HttpServletRequest request, Model model){
		String status = request.getParameter("status");
		String uid = request.getParameter("uid");
		String cid = request.getParameter("cid");
		String startime = request.getParameter("startime");
		String endtime = request.getParameter("endtime");
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
				
		if(status!=null&&uid==null&&cid==null&&startime==null&&endtime==null){
			model.addAttribute("couponList", getcouponBystatus(status,pagenumber,pagelines));
		}
		if(status==null&&uid!=null&&cid==null&&startime==null&&endtime==null){
			model.addAttribute("couponList", getcouponByuid(uid,pagenumber,pagelines));
		}
		if(status==null&&uid==null&&cid!=null&&startime==null&&endtime==null){
			model.addAttribute("couponList", getcouponBycid(cid,pagenumber,pagelines));
		}	
		if(status==null&&uid==null&&cid==null&&startime!=null&&endtime!=null){
			model.addAttribute("couponList", getcouponBytime(startime,endtime,pagenumber,pagelines));
		}
		
		if(status!=null&&uid!=null&&cid==null&&startime==null&&endtime==null){
			model.addAttribute("couponList", getcouponByStatusAnduserid(status,uid,pagenumber,pagelines));
		}
		if(status!=null&&uid==null&&cid!=null&&startime==null&&endtime==null){
			model.addAttribute("couponList", getcouponByStatusAndcourseid(status,cid,pagenumber,pagelines));
		}
		if(status!=null&&uid==null&&cid==null&&startime!=null&&endtime!=null){
			model.addAttribute("couponList", getcouponByStatusAndtime(status,startime,endtime,pagenumber,pagelines));
		}
		if(status==null&&uid!=null&&cid!=null&&startime==null&&endtime==null){
			model.addAttribute("couponList", getcouponByuidandcid(uid,cid,pagenumber,pagelines));
		}
		if(status==null&&uid!=null&&cid==null&&startime!=null&&endtime!=null){
			model.addAttribute("couponList", getcouponByuidandtime(uid,startime,endtime,pagenumber,pagelines));
		}
		if(status==null&&uid==null&&cid!=null&&startime!=null&&endtime!=null){
			model.addAttribute("couponList", getcouponBycidandtime(cid,startime,endtime,pagenumber,pagelines));
		}
		
		
		if(status!=null&&uid!=null&&cid!=null&&startime==null&&endtime==null){
			model.addAttribute("couponList", getcouponBystatusanduidandcid(cid,uid,cid,pagenumber,pagelines));
		}
		if(status!=null&&uid!=null&&cid==null&&startime!=null&&endtime!=null){
			model.addAttribute("couponList", getcouponBystatusanduidandtime(status,uid,startime,endtime,pagenumber,pagelines));
		}
		if(status!=null&&uid==null&&cid!=null&&startime!=null&&endtime!=null){
			model.addAttribute("couponList", getcouponBystatusandcidandtime(status,cid,startime,endtime,pagenumber,pagelines));
		}
		if(status==null&&uid!=null&&cid!=null&&startime!=null&&endtime!=null){
			model.addAttribute("couponList", getcouponByuidandcidandtime(uid,cid,startime,endtime,pagenumber,pagelines));
		}
		
		
		if(status!=null&&uid!=null&&cid!=null&&startime!=null&&endtime!=null){
			model.addAttribute("couponList", getcouponBystatusanduidandcidandtime(status,uid,cid,startime,endtime,pagenumber,pagelines));
		}
		
		
		
		
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
		modelview.addObject("keyword", keyword);
		// logger.info(request.getSession().getAttribute("name")+"刷新用户列表操作的用户"+request.getSession().getAttribute("name"));

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("coupon/activityList");
		return modelview;
	}
	
	/**
	 * 根据活动ID获取红包列表
	 * */
	@RequestMapping("/couponListByActivity")
	public String couponListByActivity(HttpServletRequest request, Model model){
		String aid = request.getParameter("aid");
		String status = request.getParameter("status");
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
		model.addAttribute("couponList", getCouponListByActivity(aid, status,pagenumber, pagelines));
		model.addAttribute("adetail", getacrtivityDetail(aid));
		model.addAttribute("aid", aid);
		model.addAttribute("status", status);
		return "coupon/couponList";
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
	
	
	/**
	 * 分配红包给个人  展示页
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/givecouponpage")
	public String givecouponpage(HttpServletRequest request, Model model){
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
	
	
	/**
	 * 分配红包给个人
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/givecoupon")
	public String givecoupon(HttpServletRequest request, Model model){
		String uid = request.getParameter("uid");
		String activitycode = request.getParameter("activitycode");
		
		model.addAttribute("couponDetail", givecoupon(uid,activitycode));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		model.addAttribute("cbasePath", cbasePath);
		model.addAttribute("sourcePath", Config.YXTSERVER5);
		return "coupon/activityList";
	}
	
	
	/**
	 * 分配红包给个人  展示页
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/deletecouponpage")
	public String deletecouponpage(HttpServletRequest request, Model model){
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
	
	
	/**
	 * 销毁红包
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/deletecoupon")
	public String deletecoupon(HttpServletRequest request, Model model){
		String uid = request.getParameter("uid");
		String activitycode = request.getParameter("activitycode");
		logger.info(request.getSession().getAttribute("name")+"销毁红包的管理员 "+request.getSession().getAttribute("name")+"===销毁红包批次"+activitycode);
		
		model.addAttribute("couponDetail", deletecoupon(activitycode));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		model.addAttribute("cbasePath", cbasePath);
		model.addAttribute("sourcePath", Config.YXTSERVER5);
		return "redirect:/coupon/activityList";
	}
	
	/**
	 * 根据活动ID获取红包列表
	 * */
	public JSONObject getCouponListByActivity(String aid,String status, String n, String s){
		String url ="";
		if(status==null||"".equals(status)){
			 url = Config.HONGBAO_SERVER + "/oss/coupon/findcouponByactivity?id=" + aid + "&n=" + n + "&s="+s;
		}
		else{
			url = Config.HONGBAO_SERVER + "/oss/coupon/findcouponByactivity?id=" + aid + "&n=" + n + "&s="+s+ "&status="+status;
		}
		 
		return getRestApiData(url);
	}
	
	public JSONObject getCouponList(String userId){
		String url = Config.HONGBAO_SERVER + "/oss/coupon/user/coupons?userid=" + userId;
		return getRestApiData(url);
	} 
	
	public JSONObject create(Map<String,String> m){
		String url = Config.HONGBAO_SERVER + "/oss/coupon/quota/add";
		
		return getRestApiData(url,m);
	} 
	
	
	public JSONObject getall(String n,String s){
		String url = Config.HONGBAO_SERVER + "/oss/coupon/allList?n="+n+"&s="+s;
		
		return getRestApiData(url);
	} 
	
	public JSONObject getallactivity(String n,String s){
		String url = Config.HONGBAO_SERVER + "/oss/coupon/allActivityList?n="+n+"&s="+s;
		
		return getRestApiData(url);
	} 
	
	public JSONObject getcouponDetail(String id){
		String url = Config.HONGBAO_SERVER + "/oss/coupon/couponDetail?id="+id;
		
		return getRestApiData(url);
	} 
	
	public JSONObject getcouponBystatus(String status,String n,String s){
		String url = Config.HONGBAO_SERVER + "/oss/coupon/findListByStatus?status="+status+"&n="+n+"&s="+s;
		
		return getRestApiData(url);
	} 
	
	
	public JSONObject getcouponBytime(String starttime,String endtime,String n,String s){
		String url = Config.HONGBAO_SERVER + "/oss/coupon/findListBytime?starttime="+starttime+"&endtime="+endtime+"&n="+n+"&s="+s;
		
		return getRestApiData(url);
	} 
	
	public JSONObject getcouponByStatusAnduserid(String status,String uid,String n,String s){
		String url = Config.HONGBAO_SERVER + "/oss/coupon/findListByStatusAnduserid?status="+status+"&uid="+uid+"&n="+n+"&s="+s;
		
		return getRestApiData(url);
	} 
	
	public JSONObject getcouponByStatusAndcourseid(String status,String cid,String n,String s){
		String url = Config.HONGBAO_SERVER + "/oss/coupon/findListByStatusAndcourseid?status="+status+"&cid="+cid+"&n="+n+"&s="+s;
		
		return getRestApiData(url);
	} 
	
	public JSONObject getcouponByStatusAndtime(String status,String starttime,String endtime,String n,String s){
		String url = Config.HONGBAO_SERVER + "/oss/coupon/findListByStatusAndcourseid?status="+status+"&starttime="+starttime+"&endtime="+endtime+"&n="+n+"&s="+s;
		
		return getRestApiData(url);
	} 
	
	public JSONObject getcouponByuid(String uid,String n,String s){
		String url = Config.HONGBAO_SERVER + "/oss/coupon/findListByuserid?uid="+uid+"&n="+n+"&s="+s;
		
		return getRestApiData(url);
	} 
	
	public JSONObject getcouponByuidandcid(String uid,String cid,String n,String s){
		String url = Config.HONGBAO_SERVER + "/oss/coupon/findListByuseridAndcourseid?uid="+uid+"&cid="+cid+"&n="+n+"&s="+s;
		
		return getRestApiData(url);
	} 
	
	public JSONObject getcouponBystatusanduidandcid(String status,String uid,String cid,String n,String s){
		String url = Config.HONGBAO_SERVER + "/oss/coupon/findListByStatusAnduseridAndcourseid?status="+status+"uid="+uid+"&cid="+cid+"&n="+n+"&s="+s;
		
		return getRestApiData(url);
	} 
	
	public JSONObject getcouponBystatusanduidandtime(String status,String uid,String starttime,String endtime,String n,String s){
		String url = Config.HONGBAO_SERVER + "/oss/coupon/findListByStatusAndtimeAnduserid?status="+status+"&uid="+uid+"&starttime="+starttime+"&endtime="+endtime+"&n="+n+"&s="+s;
		
		return getRestApiData(url);
	}
	
	public JSONObject getcouponBystatusandcidandtime(String status,String cid,String starttime,String endtime,String n,String s){
		String url = Config.HONGBAO_SERVER + "/oss/coupon/findListByStatusAndtimeAnduserid?status="+status+"&cid="+cid+"&starttime="+starttime+"&endtime="+endtime+"&n="+n+"&s="+s;
		
		return getRestApiData(url);
	}
	
	public JSONObject getcouponByuidandcidandtime(String uid,String cid,String starttime,String endtime,String n,String s){
		String url = Config.HONGBAO_SERVER + "/oss/coupon/findListBytimeAnduseridAndcourseid?uid="+uid+"&cid="+cid+"&starttime="+starttime+"&endtime="+endtime+"&n="+n+"&s="+s;
		
		return getRestApiData(url);
	}
	
	public JSONObject getcouponBystatusanduidandcidandtime(String status,String uid,String cid,String starttime,String endtime,String n,String s){
		String url = Config.HONGBAO_SERVER + "/oss/coupon/findListBytimeAnduseridAndcourseid?uid="+uid+"&status="+status+"&cid="+cid+"&starttime="+starttime+"&endtime="+endtime+"&n="+n+"&s="+s;
		
		return getRestApiData(url);
	}
	
	
	public JSONObject getcouponByuidandtime(String uid,String starttime,String endtime,String n,String s){
		String url = Config.HONGBAO_SERVER + "/oss/coupon/findListBytimeAnduserid?uid="+uid+"&starttime="+starttime+"&endtime="+endtime+"&n="+n+"&s="+s;
		
		return getRestApiData(url);
	} 
	
	public JSONObject getcouponBycidandtime(String cid,String starttime,String endtime,String n,String s){
		String url = Config.HONGBAO_SERVER + "/oss/coupon/findListBytimeAndcourseid?cid="+cid+"&starttime="+starttime+"&endtime="+endtime+"&n="+n+"&s="+s;
		
		return getRestApiData(url);
	} 
	
	public JSONObject getcouponBycid(String cid,String n,String s){
		String url = Config.HONGBAO_SERVER + "/oss/coupon/findListBycourseid?cid="+cid+"&n="+n+"&s="+s;
		
		return getRestApiData(url);
	} 
	
	public JSONObject givecoupon(String uid,String activityid){
		String url = Config.HONGBAO_SERVER + "/oss/coupon/quota/extract?activitycode="+activityid+"&userid="+uid;
		
		return getRestApiData(url);
	} 
	
	
	public JSONObject deletecoupon(String activityid){
		String url = Config.HONGBAO_SERVER + "/coupon/discard?activitycode="+activityid;
		
		return getRestApiData(url);
	} 
	
	
	public JSONObject getacrtivityDetail(String id){
		String url = Config.HONGBAO_SERVER + "/oss/coupon/activityDetail?id="+id;
		
		return getRestApiData(url);
	} 
	private JSONObject oederDetail(String couponid) {
		String url = Config.ORDER_SERVER + "ossorder/allOrdersBycouponid?couponid="+couponid;
		return getRestApiData(url);
}
}
