package com.yunxuetang.oss;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.Config;
import com.yunxuetang.util.DateUtil;
import com.yunxuetang.util.HttpUtil;
import com.yunxuetang.util.StringUtil;

@Controller
@RequestMapping("/activity")
public class Activity extends BaseController{
	/**
	 * 
	 * @Title: adPage
	 * @Description: 活动分页
	 * @param request
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping("/activityPage")
	public ModelAndView adPage(HttpServletRequest request,String qdName,String qdId,String ctime,String etime,Integer s ,Integer n) {
		ModelAndView mv=new ModelAndView("activity/activityList");
		Map<String, Object>pMap=new HashMap<String, Object>();
		try {
			if(StringUtil.isBlank(ctime)){
				ctime="2015-02-02";
			}
			if(StringUtil.isBlank(etime)){
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
				etime=format.format(new Date(System.currentTimeMillis()+24*60*60*1000));
			}
			long time1=DateUtil.toLongtime(ctime);
			long time2=DateUtil.toLongtime(etime);
			
			if(!StringUtil.isBlank(qdId)){
				pMap.put("qdId", qdId);
			}
			if(!StringUtil.isBlank(qdName)){
				pMap.put("qdName", qdName);
			}
			if(n!=null){
				pMap.put("n", n+"");
			}
			if(s!=null){
				pMap.put("s", s+"");
			}
			pMap.put("ctime", time1+"");
			pMap.put("etime", time2+"");
			String resString=HttpUtil.sendPost(Config.YXTSERVER3+"oss/activity/activityPage", pMap);
			JSONObject object=JSONObject.fromObject(resString);
			
			String cpath = request.getContextPath();
			String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
			mv.addObject("cbasePath", cbasePath);
			mv.addObject("activityList",object);
			return mv;
		} catch (ParseException e) {
			e.printStackTrace();
			return mv;
		}
	}
	@RequestMapping("/createActivity")
	public void createAd(HttpServletRequest request,HttpServletResponse response,String id,
			String name,String activityStartTime,String activityEndTime ,String type,String intro,
			String des,String desImgs,String mainImg,String price,String company,String optionStartTime,
			String optionEndTime,String province,String city,String address,String options ,String desMainImg) throws ParseException{
		Map<String,Object> map=new HashMap<String, Object>();
		if(!StringUtil.isBlank(id)){
			map.put("id", id);
		}
		map.put("name", name);
		map.put("activityStartTime", DateUtil.toLongtime(activityStartTime)+"");
		map.put("activityEndTime", DateUtil.toLongtime(activityEndTime)+"");
		map.put("type", type);
		map.put("intro", intro);
		map.put("des", des);
		map.put("desImgsStr", desImgs);
		map.put("mainImg", mainImg);
		map.put("price", price);
		map.put("company", company);
		map.put("optionStartTime", DateUtil.toLongtime(optionStartTime)+"");
		map.put("optionEndTime", DateUtil.toLongtime(optionEndTime)+"");
		map.put("province", province);
		map.put("city", city);
		map.put("address", address);
		map.put("options", options);
		map.put("desMainImg", desMainImg);
		HttpUtil.sendPost(Config.YXTSERVER3+"oss/activity/create", map);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		/*try {
			response.sendRedirect(cbasePath+"activity/activityPage");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	@RequestMapping("/createView")
	public ModelAndView createView(HttpServletRequest request) {
		ModelAndView mv=new ModelAndView("activity/createView");
		Map<String, String>pMap=new HashMap<String, String>();
		JSONObject json =getRestApiData(Config.YXTSERVER3+"oss/proCity/list",pMap);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		mv.addObject("cbasePath", cbasePath);
		mv.addObject("resultList", json);
		
		return mv;
		
	}
	
	@RequestMapping("/proCityList")
	@ResponseBody
	public Object proCityList(HttpServletRequest request,String pId) {
		Map<String, String>pMap=new HashMap<String, String>();
		pMap.put("pId", pId);
		JSONObject json =getRestApiData(Config.YXTSERVER3+"oss/proCity/list",pMap);
		return json;
	}
	
	@RequestMapping("/editView")
	public ModelAndView editView(HttpServletRequest request,String id) {
		ModelAndView mv=new ModelAndView("activity/editView");
		Map<String, String>pMap=new HashMap<String, String>();
		pMap.put("id", id);
		JSONObject activityjson =getRestApiData(Config.YXTSERVER3+"oss/activity/detail",pMap);
		JSONObject json =getRestApiData(Config.YXTSERVER3+"oss/proCity/list",pMap);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		mv.addObject("cbasePath", cbasePath);
		mv.addObject("resultList", json);
		mv.addObject("activityjson", activityjson);
		return mv;
	}
	
	@RequestMapping("/detail")
	public ModelAndView detail(HttpServletRequest request,String id) {
		ModelAndView mv=new ModelAndView("activity/detail");
		Map<String, String>pMap=new HashMap<String, String>();
		pMap.put("id", id);
		JSONObject activityjson =getRestApiData(Config.YXTSERVER3+"oss/activity/detail",pMap);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		mv.addObject("cbasePath", cbasePath);
		mv.addObject("activity", activityjson);
		return mv;
	}
	
	@RequestMapping("/bmList")
	public ModelAndView bmList(HttpServletRequest request,String id) {
		ModelAndView mv=new ModelAndView("activity/bmList");
		Map<String, String>pMap=new HashMap<String, String>();
		pMap.put("activityId", id);
		JSONObject users =getRestApiData(Config.YXTSERVER3+"oss/activityUser/page",pMap);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		mv.addObject("cbasePath", cbasePath);
		mv.addObject("users", users);
		return mv;
	}
	

}
