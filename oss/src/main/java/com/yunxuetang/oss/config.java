package com.yunxuetang.oss;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.Config;


@Controller
@RequestMapping(value="/config")
public class config extends BaseController{
	/**
	 * 
	 * 查询所有待审核的知识
	 */
	@RequestMapping("/configList")
	public ModelAndView configList(HttpServletRequest request) {
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


		JSONObject objj = getConfigs(n,s);
		modelview.addObject("resuserList", objj);
		String msg = objj.getString("msg");
		System.out.println(msg);

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("config/configList");
		return modelview;
	}
	private JSONObject getConfigs(String n,String s) {
		String url = Config.YXTSERVER3 + "oss/cloudConfig/configList?n="+n+"&s="+s;
		return getRestApiData(url);
	}
	/**
	 * 
	 * @Title: createConfig
	 * @Description: 创建配置
	 * @param request
	 * @param response void
	 * @throws
	 */
	@RequestMapping("/createConfig")
	public void createConfig(HttpServletRequest request,HttpServletResponse response){
		String ckey=request.getParameter("ckey");
		String category=request.getParameter("category");
		String desc=request.getParameter("desc");
		String bucket=request.getParameter("bucket");
		String filters=request.getParameter("filters");
		String fileParam=request.getParameter("fileParam");
		String pathrule=request.getParameter("pathrule");
		String baseUrls=request.getParameter("baseUrls");
		Map<String, String> map=new HashMap<String, String>();
		map.put("ckey", ckey);
		map.put("category", category);
		map.put("desc", desc);
		map.put("bucket", bucket);
		map.put("filters", filters);
		map.put("fileParam", fileParam);
		map.put("pathrule", pathrule);
		map.put("baseUrlList", baseUrls);
		createConfigs(map);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		try {
			response.sendRedirect(cbasePath+"config/configList");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private JSONObject createConfigs(Map<String, String> map) {
		String url = Config.YXTSERVER3 + "oss/cloudConfig/createConfig";
		return getRestApiData(url,map);
	}
	
	/**
	 * 创建配置  展示页
	 * 
	 * 
	 */
	@RequestMapping("/createConfigView")
	private ModelAndView createRobotForm(HttpServletRequest request) {
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("config/createConfigView");
		return modelview;
	}
	/**
	 * 
	 * @Title: deleteConfig
	 * @Description: 删除
	 * @param request
	 * @param response void
	 * @throws
	 */
	@RequestMapping("/deleteConfig")
	public void deleteConfig(HttpServletRequest request,HttpServletResponse response){
		String configId=request.getParameter("configId");
		deleteConfig(configId);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		try {
			response.sendRedirect(cbasePath+"config/configList");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private JSONObject deleteConfig(String configId) {
		String url = Config.YXTSERVER3 + "oss/cloudConfig/deleteConfig?configId="+configId;
		return getRestApiData(url);
		
	}
	
	@RequestMapping("/updateConfig")
	private void updateConfig(HttpServletRequest request,HttpServletResponse response){
		String id=request.getParameter("id");
		String ckey=request.getParameter("ckey");
		String category=request.getParameter("category");
		String desc=request.getParameter("desc");
		String bucket=request.getParameter("bucket");
		String filters=request.getParameter("filters");
		String fileParam=request.getParameter("fileParam");
		String pathrule=request.getParameter("pathrule");
		String baseUrls=request.getParameter("baseUrls");
		Map<String, String> map=new HashMap<String, String>();
		map.put("id", id);
		map.put("ckey", ckey);
		map.put("category", category);
		map.put("desc", desc);
		map.put("bucket", bucket);
		map.put("filters", filters);
		map.put("fileParam", fileParam);
		map.put("pathrule", pathrule);
		map.put("baseUrlList", baseUrls);
		updateConfig(map);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		try {
			response.sendRedirect(cbasePath+"config/configList");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private JSONObject updateConfig(Map<String, String> map){
		String url = Config.YXTSERVER3 + "oss/cloudConfig/updateConfig";
		return getRestApiData(url,map);
	}
	/**
	 * 
	 * 
	 * 编辑用户信息的展示页
	 */
	@RequestMapping("/updateConfigView")
	private ModelAndView updateUserForm(HttpServletRequest request) {
		 
		String configId = request.getParameter("configId");

		ModelAndView modelview = new ModelAndView();
		JSONObject json=getConfigDetail(configId);
		modelview.addObject("resuserDetail", json);
		JSONObject data=JSONObject.fromObject(json.get("data"));
		JSONObject result=JSONObject.fromObject(data.get("result"));
		JSONArray array=JSONArray.fromObject(result.get("baseUrls"));
		String baseUrl="[";
		for (Object object : array) {
			baseUrl+="'"+object.toString()+"',";
		}
		
		baseUrl+="]";
		modelview.addObject("baseUrl", baseUrl);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("config/editConfig");
		return modelview;
	}
	private JSONObject getConfigDetail(String configId) {
		String url = Config.YXTSERVER3 + "oss/cloudConfig/one?id=" + configId;
		return getRestApiData(url);
	}
}
