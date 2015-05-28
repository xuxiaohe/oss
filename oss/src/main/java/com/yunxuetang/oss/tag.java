package com.yunxuetang.oss;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.Config;
import com.yunxuetang.util.StringUtil;
@Controller
@RequestMapping("/tag")
public class tag extends BaseController{

	private Logger logger = LoggerFactory.getLogger(tag.class);
	
	
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
		String keyWord = request.getParameter("keyWord");
		JSONObject tags = getBaseTagList(n, s, "score", "DESC", keyWord);
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
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping("/detailView")
	public ModelAndView tagDetails(HttpServletRequest request) throws UnsupportedEncodingException{
		request.setCharacterEncoding("utf-8");
//		String tid = request.getParameter("tid");
//		String type = request.getParameter("type");
//		String domain = Config.YXTDOMAIN;
		HashMap<String, String> tag = new HashMap<String, String>();
		tag.put("tid", request.getParameter("tid"));
		tag.put("tagName", request.getParameter("tagName"));
		tag.put("tagNameLowCase", request.getParameter("tagNameLowCase"));
		tag.put("type", request.getParameter("type"));
		tag.put("status", request.getParameter("status"));
		tag.put("score", request.getParameter("score"));
		
//		JSONObject tag = getTagDetail(tid, type, domain);
		ModelAndView modelview = new ModelAndView();
//		System.out.println(tag.toString());
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
		String tagName = request.getParameter("tagName");
		String score = request.getParameter("score");
//		String type = request.getParameter("type");
//		String domain = Config.YXTDOMAIN;
//		JSONObject tag = getTagDetail(tid, type, domain);
		ModelAndView modelview = new ModelAndView();
		HashMap<String, String> tag = new HashMap<String, String>();
		tag.put("tid", tid);
		tag.put("tagName", tagName);
		tag.put("score", score);
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
		System.out.println(result.toString());
		ModelAndView modelview = new ModelAndView();
		modelview.addObject("tag", result);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		return "redirect:/tag/tagList";
	}
	
	/**
	 * 添加标签页面
	 * */
	@RequestMapping("/addTagView")
	public ModelAndView addTagView(HttpServletRequest request){
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("tag/addTag");
		return modelview;
	}
	
	/**
	 * 添加标签
	 * */
	@RequestMapping("/addNewTag")
	public String addNewTag(HttpServletRequest request){
		String tagName = request.getParameter("tagName");
		String score = request.getParameter("score");
		logger.info("创建标签管理员 "+ request.getSession().getAttribute("name") + "创建标签, 标签名:" + tagName);
		JSONObject result = addNewTag(tagName, score);
		ModelAndView modelview = new ModelAndView();
		modelview.addObject("tag", result);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		return "redirect:/tag/tagList";
	}
	
	/**
	 * 修改标签状态
	 * */
	@RequestMapping("/updateTagStatus")
	public @ResponseBody JSONObject updateTagStatus(HttpServletRequest request){
		String status = request.getParameter("status");
		String tagName = request.getParameter("tagName");
		logger.info("修改标签状态管理员 "+ request.getSession().getAttribute("name") + "修改标签状态, 标签名:" + tagName + "======status:" + status);
		JSONObject result = updateTagStatus(tagName, status);
		return result;
	}
	
	private JSONObject getBaseTagList(String n, String s, String sort, String mode, String keyWord){
		String url = Config.YXTSERVER4 + "tag/searchTagList";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("n", n);
		params.put("s", s);
		params.put("sort", sort);
		params.put("mode", mode);
		if(!StringUtil.isEmpty(keyWord)){
			params.put("keyWord", keyWord);
		}
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
		params.put("oldTagName", oldTagName);
		params.put("newTagName", newTagName);
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
	
	
	private JSONObject updateTagStatus(String tagName, String status){
		String url = Config.YXTSERVER4 + "tag/editBaseTagStatus";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("tagName", tagName);
		params.put("status", status);
		return getRestApiData(url, params);
	}
}
