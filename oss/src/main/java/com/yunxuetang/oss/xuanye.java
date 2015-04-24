package com.yunxuetang.oss;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.Config;

@Controller
@RequestMapping("/xuanye")
public class xuanye extends BaseController{
	Logger logger = LoggerFactory.getLogger(xuanye.class);
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		
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
		String ctime = request.getParameter("ctime");
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		
		logger.warn("======================炫页关联到具体的排行榜操作的管理员："+request.getSession().getAttribute("name")+"===位置id"+boxPostId+"===炫页id"+sourceId);
		modelview.addObject("addDryBoxList", bindBoxDry(boxPostId, sourceType, sourceId,ctime));
		
		modelview.addObject("addDryBoxposition", dryboxpost(type));
		modelview.addObject("name", name);
		modelview.addObject("id", boxPostId);
		modelview.addObject("addDryBoxList", getdryboxlist("1", boxPostId,"0","10"));
		
		modelview.setViewName("xuanye/xuanyeBoxList");
		return modelview;
	}
	
	
	
	/**
	 * 干货详情
	 */
	@RequestMapping("/xuanyeDetail")
	public ModelAndView dryDetail(HttpServletRequest request) {
		// 当前第几页
		String dryid = request.getParameter("dryid");

		ModelAndView modelview = new ModelAndView();

		try {
			JSONObject objj3 = dryDetail(dryid);

			String url = objj3.getJSONObject("data").getJSONObject("result").getString("url");
			url = URLDecoder.decode(url, "utf-8");

			modelview.addObject("url", url);

			modelview.addObject("dryDetail", objj3);
			modelview.addObject("resTopicPost", findPost(dryid));
		} catch (Exception e) {
			e.printStackTrace();
		}

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("xuanye/xuanyeDetail");
		return modelview;

	}
	
	/**
	 * 
	 * 话题审核
	 */
	@RequestMapping("/checkXuanye")
	public String checkXuanye(HttpServletRequest request) {
		 
		String dryid = request.getParameter("xyid");
		//排行榜id
		 
		 
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
	 
		modelview.addObject("addDryBoxList", checkDry(dryid));
		
		return "redirect:/xuanye/xuanyeList";
	}
	
	
	/**
	 * 
	 * 查询所有未审核干货
	 */
	@RequestMapping("/noCheckDryList")
	public ModelAndView noCheckDryList(HttpServletRequest request) {
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

		modelview.addObject("Drys", nocheckdryList(keyword, n, s));
		//System.out.print(dryList(keyword, n, s));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("dry/nocheckdryList");
		return modelview;
	}
	
	
	/**
	 * 
	 * 删除干货
	 */
	@RequestMapping("/deletexuanye")
	public String deleteDry(HttpServletRequest request) {
		// 必输
		String dryid = request.getParameter("dryid");
		logger.warn("=====================删除炫页操作的管理员："+request.getSession().getAttribute("name")+"炫页id"+dryid);
		deleteDryById(dryid);
		return "redirect:/xuanye/xuanyeList";
	}
	
	
	private JSONObject deleteDryById(String dryId) {
		String url = Config.YXTSERVER3 + "oss/dry/deleteDry?dryCargoId=" + dryId;
		return getRestApiData(url);
	}
	
	
	private JSONObject nocheckdryList(String keyword, String n, String s) {
		String url = null;
		if (keyword == null) {
			url = Config.YXTSERVER3 + "oss/dry/searchNoCheckXuanYe?n=" + n + "&s=" + s;
		} else {
			url = Config.YXTSERVER3 + "oss/dry/searchNoCheckXuanYe?n=" + n + "&s=" + s + "&keywords=" + keyword;
		}
		return getRestApiData(url);
	}
	
	
	private JSONObject dryDetail(String dryid) {
		String url = Config.YXTSERVER3 + "oss/dry/getOneDry?dryid=" + dryid;
		return getRestApiData(url);
	}
	
	private JSONObject findPost(String dryid) {
		String url = Config.YXTSERVER3 + "oss/dry/searchAllPostAndSubPost?dryid=" + dryid;
		return getRestApiData(url);
	}
	
	
	private JSONObject checkDry(String dryid) {
		String url = Config.YXTSERVER3 + "oss/dry/dryChecked?dryid=" + dryid;
		return getRestApiData(url);
	}
	
	private JSONObject dryboxpost(String type) {
		String url = Config.YXTSERVER3 + "oss/box/getBoxPostByType?type=" + type;
		return getRestApiData(url);
	}
	
	private JSONObject bindBoxDry(String boxPostId,String sourceType,String sourceId,String ctime) {
		String url = Config.YXTSERVER3 + "oss/box/addBoxInBoxPost?boxPostId=" + boxPostId+"&sourceType="+sourceType+"&sourceId="+sourceId+"&ctime="+ctime;
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
