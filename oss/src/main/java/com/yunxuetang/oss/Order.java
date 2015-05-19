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
	
	
	
	private JSONObject getOrderList(String n, String s){
		String url = Config.ORDER_SERVER + "/order/allOrders?n=" + n + "&s=" + s;
		return getRestApiData(url);
	}
	private JSONObject getOrderListByStatus(String status, String n, String s){
		String url = Config.ORDER_SERVER + "/order/findOrdersBystate?n=" + n + "&s=" + s + "&orderStatus=" + status;
		return getRestApiData(url);
	}
}
