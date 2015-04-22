package com.yunxuetang.table;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunxuetang.oss.BaseController;
import com.yunxuetang.util.Config;

@Controller
@RequestMapping("/table/user")
public class CountUserController extends BaseController{

	/**
	 * 群组图表页面跳转
	 * */
	@RequestMapping("/page")
	public String toGroupView(HttpServletRequest request, Model model){
		return "table/user";
	}
	
	/**
	 * 获取群组图表数据
	 * @param type 数据类型日/月
	 * @param date 日期
	 * */
	@RequestMapping("/loadData")
	public @ResponseBody JSONObject getGroupCountData(HttpServletRequest request){
		return getGroupCountData(request.getParameter("type"), request.getParameter("date"));
	}
	
	/**
	 * 发送POST请求
	 * 获取日/月群组统计数据
	 * */
	private JSONObject getGroupCountData(String type, String date){
		String url = Config.SCHEDULE_SERVER + "/user/count?type=" + type + "&date=" + date;
		return getRestApiData(url);
	}
}
