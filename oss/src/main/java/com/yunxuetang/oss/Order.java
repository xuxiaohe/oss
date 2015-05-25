package com.yunxuetang.oss;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yunxuetang.util.Config;
import com.yunxuetang.util.StringUtil;

@Controller
@RequestMapping("/order")
public class Order extends BaseController{

	/**
	 * 获取订单列表
	 * */
	@RequestMapping("list")
	public String orderList(HttpServletRequest request, Model model){
		String status = request.getParameter("orderStatus");
		String n = request.getParameter("n");
		String s = request.getParameter("s");
		if(StringUtil.isEmpty(n)){
			n = "0";
		}
		if(StringUtil.isEmpty(s)){
			s = "10";
		}
		if(StringUtil.isEmpty(status)){
			model.addAttribute("orderlist", getOrderList(n, s));
		}else{
			model.addAttribute("orderlist", getOrderListByStatus(status, n, s));
			model.addAttribute("orderStatus", status);
		}
		//model.addAttribute("orderType", getOrderTypeList());
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		model.addAttribute("cbasePath", cbasePath);
		model.addAttribute("sourcePath", Config.YXTSERVER5);
		return "order/orderlist";
	}
	
	/**
	 * 根据时间间隔获取订单列表
	 * */
	@RequestMapping("findOrder")
	public String orderBytime(HttpServletRequest request, Model model){
		String n = request.getParameter("n");
		String s = request.getParameter("s");
		String status = request.getParameter("orderStatus");
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		if(StringUtil.isEmpty(n)){
			n = "0";
		}
		if(StringUtil.isEmpty(s)){
			s = "10";
		}
		if(!("0".equals(starttime))&&!("0".equals(endtime))&&!("".equals(status))){
			model.addAttribute("orderlist", findOrdersBytimeAndstate(status,n, s,starttime,endtime));
			model.addAttribute("orderStatus",status);
			model.addAttribute("starttime",starttime);
			model.addAttribute("endtime",endtime);
		}
		 if(("0".equals(starttime))&&("0".equals(endtime))&&!("".equals(status))){
			model.addAttribute("orderlist", findOrdersBystate(status, n, s));
			model.addAttribute("orderStatus",status);
		}
		 if(starttime!=null&&endtime!=null&&!("0".equals(starttime))&&!("0".equals(endtime))&&("".equals(status))){
			model.addAttribute("orderlist", findOrdersBytime(n, s, starttime, endtime));
			model.addAttribute("starttime",starttime);
			model.addAttribute("endtime",endtime);
		}
		if (starttime==null&&endtime==null&&("".equals(status))) {
			model.addAttribute("orderlist", getOrderList(n, s));
			
		}
		
		//model.addAttribute("orderType", getOrderTypeList());
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		model.addAttribute("cbasePath", cbasePath);
		model.addAttribute("sourcePath", Config.YXTSERVER5);
		return "order/orderlist";
	}
	
	
	
	private JSONObject getOrderList(String n, String s){
		String url = Config.ORDER_SERVER + "/ossorder/allOrders?n=" + n + "&s=" + s;
		return getRestApiData(url);
	}
	
	private JSONObject findOrdersBytimeAndstate(String state,String n, String s,String starttime, String endtime){
		String url = Config.ORDER_SERVER + "/ossorder/findOrdersBytimeAndstate?n=" + n + "&s=" + s+ "&starttime=" + starttime+ "&endtime=" + endtime+ "&orderStatus=" + state;
		return getRestApiData(url);
	} 
	
	private JSONObject findOrdersBystate(String state,String n, String s){
		String url = Config.ORDER_SERVER + "/ossorder/findOrdersBystate?n=" + n + "&s=" + s+ "&orderStatus=" + state;
		return getRestApiData(url);
	} 
	
	private JSONObject findOrdersBytime(String n, String s,String starttime, String endtime){
		String url = Config.ORDER_SERVER + "/ossorder/findOrdersBytime?n=" + n + "&s=" + s+ "&starttime=" + starttime+ "&endtime=" + endtime;
		return getRestApiData(url);
	}
	
	private JSONObject getOrderListByStatus(String status, String n, String s){
		String url = Config.ORDER_SERVER + "/ossorder/findOrdersBystate?n=" + n + "&s=" + s + "&orderStatus=" + status;
		return getRestApiData(url);
	}
}
