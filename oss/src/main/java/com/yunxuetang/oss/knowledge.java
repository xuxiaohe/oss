package com.yunxuetang.oss;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.Config;

@Controller
@RequestMapping(value="/knowledge")
public class knowledge extends BaseController{
	
	/**
	 * 
	 * 查询所有待审核的分享
	 */
	@RequestMapping("/knowledgeList")
	public ModelAndView courseList(HttpServletRequest request) {
		// 当前第几页
		String n = request.getParameter("n");
		if (n == null) {
			n = "0";
		}
		// 每页条数
		String s = request.getParameter("s");

		if (s == null) {
			s = "10";
		}
		ModelAndView modelview = new ModelAndView();

		modelview.addObject("knowledgeList", getCourses( n, s));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("knowledge/knowledgeList");
		return modelview;
	}
	/**
	 * 
	 * 查询所有待审核的分享
	 */
	@RequestMapping("/verify")
	private String verify(HttpServletRequest request) {
		String id = request.getParameter("id");
		String status=request.getParameter("status");
		verifyKnowledge(id,status);
		return "redirect:/knowledge/knowledgeList";

	}
	private JSONObject getCourses(String n,String s) {
		String url = Config.YXTSERVER3 + "oss/knowledge/knowledgeList?n="+n+"&s="+s;
		return getRestApiData(url);
	}
	private JSONObject verifyKnowledge(String id,String status) {
		String url = Config.YXTSERVER3 + "oss/knowledge/verifyKnowledge?id="+id+"&status="+status;
		return getRestApiData(url);
	}
	
}
