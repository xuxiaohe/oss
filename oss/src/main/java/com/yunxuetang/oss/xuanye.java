package com.yunxuetang.oss;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.Config;

@Controller
@RequestMapping("/xuanye")
public class xuanye extends BaseController{

	public xuanye() {
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * 
	 * 查询所有干货 包括没有关联群组的
	 */
	@RequestMapping("/xuanyeList")
	public ModelAndView xuanyeList(HttpServletRequest request) {
		String keyword = request.getParameter("keyword");
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

		modelview.addObject("Drys", xuanyeList(keyword, n, s));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("xuanye/xuanyeList");
		return modelview;
	}
	
	/**
	 * 
	 * 排行榜未绑定列表详情页 初始页  干货
	 */
	@RequestMapping("/DryBoxDetail")
	public ModelAndView DryBoxDetail(HttpServletRequest request) {
		 
		String type = "xuanye";
		
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
		modelview.addObject("addDryBoxList", getdryboxlist("1", objj5,"0","10"));
		modelview.setViewName("xuanye/xuanyeBoxList");
		return modelview;
	}
	
	
	
	/**
	 * 
	 * 查询未关联排行版的干货列表
	 */
	@RequestMapping("/DryBoxList")
	public ModelAndView DryBoxList(HttpServletRequest request) {
		 
		String pagenumber = request.getParameter("n");

		if (pagenumber == null) {
			pagenumber = "0";
		}

		// 每页条数

		String pagelines = request.getParameter("s");

		if (pagelines == null) {
			pagelines = "10";
		}
		
		String type = "xuanye";
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		 
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		
		modelview.addObject("addDryBoxposition", dryboxpost(type));
		modelview.addObject("name", name);
		modelview.addObject("id", id);
		modelview.addObject("addDryBoxList", getdryboxlist("1", id,pagenumber,pagelines));
		modelview.setViewName("xuanye/xuanyeBoxList");
		return modelview;
	}
	
	
	/**
	 * 
	 * 查询未关联排行版的干货列表
	 */
	@RequestMapping("/searchDryBoxList")
	public ModelAndView searchDryBoxList(HttpServletRequest request) {
		 
		String keyword = request.getParameter("keyword");
		String boxPostId = request.getParameter("id");
		String dryFlag="1";
		
		String type = "xuanye";
		 
		String name = request.getParameter("name");
		 
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		
		modelview.addObject("addDryBoxposition", dryboxpost(type));
		modelview.addObject("name", name);
		modelview.addObject("id", boxPostId);
		modelview.addObject("addDryBoxList", getdryboxlistNotIn(dryFlag, boxPostId, keyword));
		modelview.setViewName("xuanye/xuanyeBoxList");
		return modelview;
	}
	
	
	/**
	 * 
	 * 干货关联到具体的排行榜
	 */
	@RequestMapping("/bindBoxDry")
	public ModelAndView bindBoxDry(HttpServletRequest request) {
		String type = "xuanye";
		String sourceType = "xuanye";
		String name = request.getParameter("name");
		//位置id
		String boxPostId = request.getParameter("boxPostId");
		//干货id
		String sourceId = request.getParameter("sourceId");
		 
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		
	 
		modelview.addObject("addDryBoxList", bindBoxDry(boxPostId, sourceType, sourceId));
		
		modelview.addObject("addDryBoxposition", dryboxpost(type));
		modelview.addObject("name", name);
		modelview.addObject("id", boxPostId);
		modelview.addObject("addDryBoxList", getdryboxlist("1", boxPostId,"0","10"));
		
		modelview.setViewName("xuanye/xuanyeBoxList");
		return modelview;
	}
	
	private JSONObject dryboxpost(String type) {
		String url = Config.YXTSERVER3 + "oss/box/getBoxPostByType?type=" + type;
		return getRestApiData(url);
	}
	
	private JSONObject bindBoxDry(String boxPostId,String sourceType,String sourceId) {
		String url = Config.YXTSERVER3 + "oss/box/addBoxInBoxPost?boxPostId=" + boxPostId+"&sourceType="+sourceType+"&sourceId="+sourceId;
		return getRestApiData(url);
	}
	
	private JSONObject getdryboxlist(String dryFlag,String boxPostId,String n,String s) {
		String url = Config.YXTSERVER3 + "oss/box/drycargoListNotInBoxPost?dryFlag=" + dryFlag+"&boxPostId="+boxPostId+"&n="+n+"&s="+s;
		return getRestApiData(url);
	}
	
	private JSONObject getdryboxlistNotIn(String dryFlag,String boxPostId,String keyword) {
		String url = Config.YXTSERVER3 + "oss/box/searchDrycargoNotInBoxPost?dryFlag=" + dryFlag+"&boxPostId="+boxPostId+"&keyword="+keyword;
		return getRestApiData(url);
	}
	
	private JSONObject xuanyeList(String keyword, String n, String s) {
		String url = null;
		if (keyword == null) {
			url = Config.YXTSERVER3 + "oss/xuanye/searchXuanye?n=" + n + "&s=" + s;
		} else {
			url = Config.YXTSERVER3 + "oss/xuanye/searchXuanye?n=" + n + "&s=" + s + "&keywords=" + keyword;
		}
		return getRestApiData(url);
	}
}
