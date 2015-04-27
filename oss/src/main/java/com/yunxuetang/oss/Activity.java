package com.yunxuetang.oss;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	Logger logger = LoggerFactory.getLogger(Activity.class);
	/**
	 * 
	 * @Title: adPage
	 * @Description: 活动分页
	 * @param request
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping("/activityPage")
	public ModelAndView adPage(HttpServletRequest request,String keywords,String qdName,String qdId,String ctime,String etime,Integer s ,Integer n) {
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
			pMap.put("keywords", keywords);
			String resString=HttpUtil.sendPost(Config.YXTSERVER3+"oss/activity/activityPage", pMap);
			JSONObject object=JSONObject.fromObject(resString);
			
			String cpath = request.getContextPath();
			String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
			mv.addObject("cbasePath", cbasePath);
			mv.addObject("keywords", keywords);
			mv.addObject("sourcePath", Config.YXTSERVER5);
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
			String optionEndTime,String province,String city,String address,String options ,String desMainImg,
			String maxCount) throws ParseException{
		Map<String,Object> map=new HashMap<String, Object>();
		if(!StringUtil.isBlank(id)){
			map.put("id", id);
		}
		map.put("name", name);
		map.put("activityStartTime", DateUtil.toLongtimeHM(activityStartTime)+"");
		map.put("activityEndTime", DateUtil.toLongtimeHM(activityEndTime)+"");
		map.put("type", type);
		
		map.put("intro", intro.replaceAll("(\r\n|\r|\n|\n\r)", "\r\n"));
		map.put("des", des.replaceAll("(\r\n|\r|\n|\n\r)", "\r\n"));
		map.put("desImgsStr", desImgs);
		map.put("mainImg", mainImg);
		map.put("price", price);
		map.put("company", company);
		map.put("optionStartTime", DateUtil.toLongtimeHM(optionStartTime)+"");
		map.put("optionEndTime", DateUtil.toLongtimeHM(optionEndTime)+"");
		map.put("province", province);
		map.put("city", city);
		map.put("address", address);
		map.put("options", options);
		map.put("desMainImg", desMainImg);
		if(!StringUtil.isBlank(maxCount)){
			map.put("maxCount", maxCount);
		}
		logger.warn("=======================创建或修改活动管理员："+request.getSession().getAttribute("name")+"===活动名称"+name);
		HttpUtil.sendPost(Config.YXTSERVER3+"oss/activity/create", map);
		String cpath = request.getContextPath();
		//String Config.YXTSERVER5 = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		/*try {
			response.sendRedirect(Config.YXTSERVER5+"activity/activityPage");
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
		mv.addObject("sourcePath", Config.YXTSERVER5);
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
		mv.addObject("sourcePath", Config.YXTSERVER5);
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
		mv.addObject("sourcePath", Config.YXTSERVER5);
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
		mv.addObject("sourcePath", Config.YXTSERVER5);
		mv.addObject("users", users);
		mv.addObject("activityId", id);
		return mv;
	}
	@RequestMapping("/activityBoxDetail")
	public ModelAndView activityBoxDetail(HttpServletRequest request) {
		 
		String type = "activity";
		
		JSONObject objj = dryboxpost(type);
		
		JSONObject objj2 =objj.getJSONObject("data");
		
		JSONArray objj3 =objj2.getJSONArray("result");
		
		JSONObject objj4=(JSONObject) objj3.get(0);
		
		String objj5=objj4.getString("id");
		String objj6=objj4.getString("chinaName");
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		
		modelview.addObject("addDryBoxposition", objj);
		modelview.addObject("name", objj6);
		modelview.addObject("id", objj5);
		modelview.addObject("addDryBoxList", getActivityboxlist(objj5,"0","10"));
		modelview.setViewName("activity/activityBoxList");
		return modelview;
	}
	
	private JSONObject dryboxpost(String type) {
		String url = Config.YXTSERVER3 + "oss/box/getBoxPostByType?type=" + type;
		return getRestApiData(url);
	}
	private JSONObject getActivityboxlist(String boxPostId,String n,String s) {
		String url = Config.YXTSERVER3 + "oss/box/activityListNotInBoxPost?boxPostId="+boxPostId+"&n="+n+"&s="+s;
		return getRestApiData(url);
	}
	
	
	/**
	 * 
	 * 群组关联到具体的排行榜
	 */
	@RequestMapping("/bindBoxActivity")
	public ModelAndView bindBoxActivity(HttpServletRequest request) {
		String type = "activity";
		String sourceType = "activity";
		String name = request.getParameter("name");
		//位置id
		String boxPostId = request.getParameter("boxPostId");
		//干货id
		String sourceId = request.getParameter("sourceId");
		String ctime = request.getParameter("ctime"); 
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		
	 
		modelview.addObject("addDryBoxList", bindBoxActivity(boxPostId, sourceType, sourceId,ctime));
		
		modelview.addObject("addDryBoxposition", dryboxpost(type));
		modelview.addObject("name", name);
		modelview.addObject("id", boxPostId);
		modelview.addObject("addDryBoxList", getActivityboxlist( boxPostId,"0","10"));
		
		modelview.setViewName("activity/activityBoxList");
		return modelview;
	}
	private JSONObject bindBoxActivity(String boxPostId,String sourceType,String sourceId,String ctime) {
		String url = Config.YXTSERVER3 + "oss/box/addBoxInBoxPost?boxPostId=" + boxPostId+"&sourceType="+sourceType+"&sourceId="+sourceId+"&ctime="+ctime;
		return getRestApiData(url);
	}
	
	/**
	 * 
	 * 查询未关联排行版的干货列表
	 */
	@RequestMapping("/activityBoxList")
	public ModelAndView activityBoxList(HttpServletRequest request) {
		 
		String pagenumber = request.getParameter("n");

		if (pagenumber == null) {
			pagenumber = "0";
		}

		// 每页条数

		String pagelines = request.getParameter("s");

		if (pagelines == null) {
			pagelines = "10";
		}
		
		String type = "activity";
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		 
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		
		modelview.addObject("addDryBoxposition", dryboxpost(type));
		modelview.addObject("name", name);
		modelview.addObject("id", id);
		modelview.addObject("addDryBoxList", getActivityboxlist(id,pagenumber,pagelines));
		modelview.setViewName("activity/activityBoxList");
		return modelview;
	}
	
	/**
	 * 
	 * 查询未关联排行版的干货列表
	 */
	@RequestMapping("/searchActivityBoxList")
	public ModelAndView searchActivityBoxList(HttpServletRequest request) {
		 
		String keyword = request.getParameter("keyword");
		String boxPostId = request.getParameter("id");
		
		String type = "activity";
		 
		String name = request.getParameter("name");
		 
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		
		modelview.addObject("addDryBoxposition", dryboxpost(type));
		modelview.addObject("name", name);
		modelview.addObject("id", boxPostId);
		modelview.addObject("addDryBoxList", getactivityboxlistNotIn( boxPostId, keyword));
		modelview.setViewName("activity/activityBoxList");
		return modelview;
	}
	
	private JSONObject getactivityboxlistNotIn(String boxPostId,String keyword) {
		String url = Config.YXTSERVER3 + "oss/box/searchactivityListNotInBoxPost?boxPostId="+boxPostId+"&keyword="+keyword;
		return getRestApiData(url);
	}

}
