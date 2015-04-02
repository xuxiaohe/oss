package com.yunxuetang.oss;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.Config;
import com.yunxuetang.util.StringUtil;
@Controller
@RequestMapping("/tag")
public class tag extends BaseController{

	
	/**
	 * 标签列表
	 */
	@RequestMapping("/tagList")
	public ModelAndView dryDetail(HttpServletRequest request) {
		ModelAndView modelview = new ModelAndView();
		String n = request.getParameter("n");
		String s = request.getParameter("s");
		if(StringUtil.isEmpty(n)) n = "0";
		if(StringUtil.isEmpty(s)) s = "10";
		JSONObject tags = getBaseTagList(n, s, "score", "DESC");
		modelview.addObject("tags", tags);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("tag/tagList");
		return modelview;

	}
	
	/**
	 * 标签详情
	 * */
	@RequestMapping("/detailView")
	public ModelAndView tagDetails(HttpServletRequest request){
		String tid = request.getParameter("tid");
		String type = request.getParameter("type");
		String domain = Config.YXTDOMAIN;
		JSONObject tag = getTagDetail(tid, type, domain);
		ModelAndView modelview = new ModelAndView();
		System.out.println(tag.toString());
		modelview.addObject("tagDetail", tag);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("tag/tagDetail");
		return modelview;
	}
	
	/**
	 * 更新页面跳转
	 * */
	@RequestMapping("/updateView")
	public ModelAndView updateView(HttpServletRequest request){
		String tid = request.getParameter("tid");
		String type = request.getParameter("type");
		String domain = Config.YXTDOMAIN;
		JSONObject tag = getTagDetail(tid, type, domain);
		ModelAndView modelview = new ModelAndView();
		modelview.addObject("tagDetail", tag);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("tag/updateForm");
		return modelview;
	}
	
	/**
	 * 修改标签
	 * */
	@RequestMapping("/updateTag")
	public String updateTag(HttpServletRequest request){
		String oldTagName = request.getParameter("oldTagName");
		String newTagName = request.getParameter("newTagName");
		String domain = Config.YXTDOMAIN;
		JSONObject result = updateTag(oldTagName, newTagName, domain);
		
		ModelAndView modelview = new ModelAndView();
		modelview.addObject("tag", result);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		return "redirect:/tag/tagList";
	}
	
	/**
	 * 添加标签
	 * */
	@RequestMapping("/addNewTag")
	public String addNewTag(HttpServletRequest request){
		String tagName = request.getParameter("tagName");
		String score = request.getParameter("score");
		JSONObject result = addNewTag(tagName, score);
		ModelAndView modelview = new ModelAndView();
		modelview.addObject("tag", result);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		return "redirect:/tag/tagList";
	}
	
	
	private JSONObject getBaseTagList(String n, String s, String sort, String mode){
		String url = Config.YXTSERVER4 + "tag/searchTagList";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("n", n);
		params.put("s", s);
		params.put("sort", sort);
		params.put("mode", mode);
		return getRestApiData(url, params);
	}
	
	private JSONObject getTagDetail(String tid, String type, String domain){
		String url = Config.YXTSERVER4 + "tag/getTagsByIdAndType";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("itemId", tid);
		params.put("itemType", type);
		params.put("domain", domain);
		return getRestApiData(url, params);
	}
	
	private JSONObject updateTag(String oldTagName, String newTagName, String domain){
		String url = Config.YXTSERVER4 + "tag/editBaseTag";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("itemId", oldTagName);
		params.put("itemType", newTagName);
		params.put("domain", domain);
		return getRestApiData(url, params);
	}
	
	private JSONObject addNewTag(String tagName, String score){
		String url = Config.YXTSERVER4 + "tag/baseTag";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("tagName", tagName);
		params.put("score", score);
		return getRestApiData(url, params);
	}
	
}
