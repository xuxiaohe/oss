package com.yunxuetang.oss;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunxuetang.util.Config;
import com.yunxuetang.util.StringUtil;

@Controller
@RequestMapping("/message")
public class message extends BaseController{
	
	
	@RequestMapping("sendView")
	public String sendView(HttpServletRequest request, Model model){
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		model.addAttribute("cbasePath", cbasePath);
		model.addAttribute("sourcePath", Config.YXTSERVER5);
		return "message/send";
	}

	/**
	 * 发送消息
	 * */
	@RequestMapping("sendmessage")
	public @ResponseBody JSONObject orderList(HttpServletRequest request, Model model){
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		JSONObject result = sendmessage(title, content);
		//model.addAttribute("orderType", getOrderTypeList());
//		String cpath = request.getContextPath();
//		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
//		model.addAttribute("cbasePath", cbasePath);
//		model.addAttribute("sourcePath", Config.YXTSERVER5);
		return result;
	}
	
	
	
	private JSONObject sendmessage(String title,String content) {
		String url = Config.YXTSERVER3 + "oss/message/sendMessage";
		Map<String,String> m=new HashMap<String,String>();
		m.put("title", title);
		m.put("content", content);
		return getRestApiData(url,m);
	}
	
}
