package com.yunxuetang.oss;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.Config;

@Controller
@RequestMapping("/deletecoupon")
public class deletecoupon extends BaseController{

	/**
	 * 
	 * 查询未关联排行版的干货列表
	 */
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request) {
		 
		String userid = request.getParameter("userid");
		String activitycode = request.getParameter("activitycode");
		 
		JSONObject j=check(userid,activitycode);
		JSONObject jj=(JSONObject) j.get("data");
		JSONObject jjj=(JSONObject) jj.get("result");
		boolean b=(Boolean) jjj.get("result");
		//没有已经支付成功的订单
		if(!b){
			delete(userid,activitycode);
		}
		else{
			String orderid=(String) jjj.get("orderid");
			deleteorder(orderid);
			delete(userid,activitycode);
		}
		
		
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		
		return "redirect:/coupon/deleteView";
	}
	
	private JSONObject delete(String userid,String activitycode) {
		String url = Config.HONGBAO_SERVER + "/oss/coupon/discard/user?userid=" + userid+"&activitycode="+activitycode;
		return getRestApiData(url);
	}
	
	private JSONObject check(String userid,String activitycode) {
		String url = Config.ORDER_SERVER + "ossorder/checkorder?userid=" + userid+"&activitycode="+activitycode;
		return getRestApiData(url);
	}
	
	private JSONObject deleteorder(String orderId) {
		String url = Config.ORDER_SERVER + "/ossorder/cancel?orderId=" + orderId;
		return getRestApiData(url);
	}
	
}
