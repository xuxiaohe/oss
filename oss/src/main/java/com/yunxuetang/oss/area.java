package com.yunxuetang.oss;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.Config;

@Controller
@RequestMapping("/area")
public class area extends BaseController{

	public area() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * 排行榜列表
	 */
	@RequestMapping("/BoxDryList")
	public ModelAndView BoxDryList(HttpServletRequest request) {
		String type = request.getParameter("type");
		 
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		
		modelview.addObject("addDryBoxposition", dryboxpost(type));
		modelview.addObject("type", type);
		
		modelview.setViewName("toplist/BoxPostList");
		return modelview;
	}
	
	
	/**
	 * 
	 * 添加排行榜  展示页
	 */
	@RequestMapping("/addDryBoxForm")
	public ModelAndView addDryBoxForm(HttpServletRequest request) {

		ModelAndView modelview = new ModelAndView();
		List<String> l=new ArrayList<String>();
		
		l.add("dry");
		l.add("xuanye");
		l.add("topic");
		l.add("group");
		l.add("course");
		
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("typelist", l);
		modelview.setViewName("toplist/createdryBoxForm");
		return modelview;
	}
	
	
	/**
	 * 
	 * 添加排行榜  
	 */
	@RequestMapping("/addDryBoxAction")
	public String addDryBoxAction(HttpServletRequest request) {
		String chinaName = request.getParameter("chinaName");
		String englishName = request.getParameter("englishName");
		String local = request.getParameter("local");
		String size = request.getParameter("size");
		String type = request.getParameter("type");

		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		
		modelview.addObject("addDryBox", addDryBox(chinaName, englishName, local, type,size));
		return "redirect:/area/BoxDryList?type="+type;
	}
	
	
	/**
	 * 
	 * 查询已关联排行版的列表
	 */
	@RequestMapping("/InBoxList")
	public ModelAndView DryInBoxList(HttpServletRequest request) {
		String path=null;
		String type = request.getParameter("type");
		String pagenumber = request.getParameter("n");

		if (pagenumber == null) {
			pagenumber = "0";
		}

		// 每页条数

		String pagelines = request.getParameter("s");

		if (pagelines == null) {
			pagelines = "10";
		}
		 
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		 
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		
		//modelview.addObject("addDryBoxposition", dryboxpost(type));
		modelview.addObject("type", type);
		modelview.addObject("id", id);
		modelview.addObject("name", name);
		
		if(type.equals("dry")){
			path="dryInBoxList";
			modelview.addObject("addDryBoxList", drycargoInBox(id,pagenumber,pagelines));
		}
		if(type.equals("xuanye")){
			path="dryInBoxList";
			modelview.addObject("addDryBoxList", drycargoInBox(id,pagenumber,pagelines));
		}
		if(type.equals("topic")){
			path="topicInBoxList";
			modelview.addObject("addDryBoxList", topicInBox(id,pagenumber,pagelines));
		}
		if(type.equals("group")){
			path="groupInBoxList";
			modelview.addObject("addDryBoxList", groupInBox(id,pagenumber,pagelines));
		}
		if(type.equals("course")){
			path="courseInBoxList";
			modelview.addObject("addDryBoxList", courseInBox(id,pagenumber,pagelines));
		}
		modelview.setViewName("toplist/"+path);
		return modelview;
	}
	
	
	/**
	 * 
	 * 取消关联到具体的排行榜
	 */
	@RequestMapping("/unbindBox")
	public ModelAndView unbindBoxDry(HttpServletRequest request) {
		String path=null;
		String type = request.getParameter("type");
		 
		String name = request.getParameter("name");
		//位置id
		String boxPostId = request.getParameter("boxPostId");
		//排行榜id
		String boxId = request.getParameter("boxId");
		 
		 
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		
	 
		modelview.addObject("addDryBoxList", deleteBox(boxId));
		
		modelview.addObject("name", name);
		modelview.addObject("id", boxPostId);
		
		if(type.equals("dry")){
			path="dryInBoxList";
			modelview.addObject("addDryBoxList", drycargoInBox(boxPostId,"0","10"));
			modelview.addObject("type", "dry");
		}
		if(type.equals("xuanye")){
			path="dryInBoxList";
			modelview.addObject("addDryBoxList", drycargoInBox(boxPostId,"0","10"));
			modelview.addObject("type", "xuanye");
		}
		if(type.equals("topic")){
			path="topicInBoxList";
			modelview.addObject("addDryBoxList", topicInBox(boxPostId,"0","10"));
			modelview.addObject("type", "topic");
			
		}
		if(type.equals("group")){
			path="groupInBoxList";
			modelview.addObject("addDryBoxList", courseInBox(boxPostId,"0","10"));
			modelview.addObject("type", "group");
		}
		if(type.equals("course")){
			path="courseInBoxList";
			modelview.addObject("addDryBoxList", courseInBox(boxPostId,"0","10"));
			modelview.addObject("type", "course");
		}
		modelview.setViewName("toplist/"+path);
		return modelview;
	}
	
	
	/**
	 * 
	 * 取消关联到具体的排行榜
	 */
	@RequestMapping("/unbindBoxBygroup")
	public ModelAndView unbindBoxBygroup(HttpServletRequest request) {
		String path=null;
		String type = request.getParameter("type");
		 
		String name = request.getParameter("name");
		//位置id
		String boxPostId = request.getParameter("boxPostId");
		//排行榜id
		String sourceid = request.getParameter("sourceid");
		 
		 
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		
	 
		modelview.addObject("addDryBoxList", deleteBoxBypostidAndsourceid(boxPostId, sourceid));
		
		modelview.addObject("name", name);
		modelview.addObject("id", boxPostId);
		
		if(type.equals("dry")){
			path="dryInBoxList";
			modelview.addObject("addDryBoxList", drycargoInBox(boxPostId,"0","10"));
			modelview.addObject("type", "dry");
		}
		if(type.equals("topic")){
			path="topicInBoxList";
			modelview.addObject("addDryBoxList", topicInBox(boxPostId,"0","10"));
			modelview.addObject("type", "topic");
		}
		if(type.equals("group")){
			path="groupInBoxList";
			modelview.addObject("addDryBoxList", groupInBox(boxPostId,"0","10"));
			modelview.addObject("type", "group");
		}
		if(type.equals("course")){
			path="courseInBoxList";
			modelview.addObject("addDryBoxList", courseInBox(boxPostId,"0","10"));
			modelview.addObject("type", "course");
		}
		modelview.setViewName("toplist/"+path);
		return modelview;
	}
	
	
	/**
	 * 
	 * 干货排行榜删除
	 */
	@RequestMapping("/BoxListDelete")
	public String  BoxDryListDelete(HttpServletRequest request) {
		String type = request.getParameter("type");
		String boxId = request.getParameter("boxId");
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("addDryBoxList", deleteBoxPost(boxId));
		
		return "redirect:/area/BoxDryList?type="+type;
	}
	
	private JSONObject deleteBoxPost(String boxId) {
		String url = Config.YXTSERVER3 + "oss/box/deleteBoxPost?id=" + boxId;
		return getRestApiData(url);
	}
	
	
	private JSONObject deleteBox(String boxId) {
		String url = Config.YXTSERVER3 + "oss/box/deleteBox?boxId=" + boxId;
		return getRestApiData(url);
	}
	private JSONObject deleteBoxBypostidAndsourceid(String postid,String sourceid) {
		String url = Config.YXTSERVER3 + "oss/box/deleteBoxBypostAndsource?postId=" + postid+"&sourceId="+sourceid;
		return getRestApiData(url);
	}
	
	private JSONObject topicInBox(String boxPostId,String n,String s) {
		String url = Config.YXTSERVER3 + "oss/box/topicInBox?boxPostId=" + boxPostId+"&n="+n+"&s="+s;
		return getRestApiData(url);
	}
	
	private JSONObject groupInBox(String boxPostId,String n,String s) {
		String url = Config.YXTSERVER3 + "oss/box/groupInBox?boxPostId=" + boxPostId+"&n="+n+"&s="+s;
		return getRestApiData(url);
	}
	
	private JSONObject courseInBox(String boxPostId,String n,String s) {
		String url = Config.YXTSERVER3 + "oss/box/courseInBox?boxPostId=" + boxPostId+"&n="+n+"&s="+s;
		return getRestApiData(url);
	}
	
	private JSONObject drycargoInBox(String boxPostId,String n,String s) {
		String url = Config.YXTSERVER3 + "oss/box/drycargoInBox?boxPostId=" + boxPostId+"&n="+n+"&s="+s;
		return getRestApiData(url);
	}
	
	
	private JSONObject dryboxpost(String type) {
		String url = Config.YXTSERVER3 + "oss/box/getBoxPostByType?type=" + type;
		return getRestApiData(url);
	}
	
	private JSONObject addDryBox(String chinaName,String englishName,String local,String type,String size) {
		String url = Config.YXTSERVER3 + "oss/box/addBoxPost?chinaName=" + chinaName+"&englishName="+englishName+"&local="+local+"&type="+type+"&size="+size;
		return getRestApiData(url);
	}
	
}
