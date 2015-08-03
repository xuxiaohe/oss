package com.yunxuetang.table;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunxuetang.oss.BaseController;
import com.yunxuetang.util.Config;
import com.yunxuetang.util.poiservice2;

@Controller
@RequestMapping("/table/topic")
public class CountTopicController extends BaseController {

	@Autowired
	 private poiservice2 service;
	/**
	 * 话题图表页面跳转
	 * */
	@RequestMapping("/page")
	public String toGroupView(HttpServletRequest request, Model model) {
		return "table/topic";
	}
	
	
	

	/**
	 * 获取话题图表数据
	 * 
	 * @param type
	 *            数据类型日/月
	 * @param date
	 *            日期
	 * */
	@RequestMapping("/loadData")
	public @ResponseBody JSONObject getGroupCountData(HttpServletRequest request) {
		return getGroupCountData(request.getParameter("type"),
				request.getParameter("date"));
	}

	/**
	 * 发送POST请求 获取日/月话题统计数据
	 * */
	private JSONObject getGroupCountData(String type, String date) {
		String url = Config.SCHEDULE_SERVER + "/topic/count?type=" + type
				+ "&date=" + date;
		return getRestApiData(url);
	}
}
