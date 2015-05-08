package com.yunxuetang.oss;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.Config;

@Controller
@RequestMapping("/banner")
public class Banner extends BaseController{
	
	/**
	 * banner列表
	 * */
	@RequestMapping("/bannerlist")
	public ModelAndView bannerListView(HttpServletRequest request){
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

		modelview.addObject("BannerList", BannerList(keyword, n, s));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("banner/bannerlist");
		return modelview;
		
	}
	
	
	/**
	 * 广告详情
	 */
	@RequestMapping("/bannerDetail")
	public ModelAndView bannerDetail(HttpServletRequest request) {
		// 当前第几页
		String id = request.getParameter("id");

		ModelAndView modelview = new ModelAndView();

		try {
			JSONObject objj3 = bannerDetail(id);

			modelview.addObject("bannerDetail", objj3);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("banner/bannerDetail");
		return modelview;

	}
	
	/**
	 * 创建banner视图
	 * */
	@RequestMapping("/createView")
	public String bannerListView(HttpServletRequest request, Model model){
		return "banner/bannercreate";
	}
	
	
	/**
	 * 创建banner视图
	 * */
	@RequestMapping("/saveBanner")
	public String saveBanner(HttpServletRequest request){
		String adSid=request.getParameter("adSid");
		//3  站外
		String adSellerId=request.getParameter("adSellerId");
		//站外链接
		String adSellerName=request.getParameter("adSellerName");
		//创建人
		String creater=(String) request.getSession().getAttribute("name");
		
		String name=request.getParameter("name");
		
		String picUrl=request.getParameter("picUrl");
		String picWidth=request.getParameter("picWidth");
		String picHeight= request.getParameter("picHeight");
		if("10".equals(adSid)){
			
			String linkUrl=request.getParameter("linkUrl");
			
			//savebanner(picUrl,picWidth,picHeight,adSid,linkUrl,name,adSellerId,adSellerName,creater);
		}
		else {
			if("0".equals(adSellerId)){
				String groupId=request.getParameter("groupId");
				String topicId=request.getParameter("topicId");
				
			}
			if("1".equals(adSellerId)){
				String groupId=request.getParameter("groupId");
				String dryCargoId=request.getParameter("dryCargoId");
				
			}
			if("2".equals(adSellerId)){
				String groupId=request.getParameter("groupId");
				String topicId=request.getParameter("topicId");
				
			}
		}
		return "redirect:/banner/bannerlist";
	}
	
	
	private JSONObject BannerList(String keyword, String n, String s) {
		String url = null;
		if (keyword == null) {
			url = Config.YXTSERVER3 + "oss/ztiaoad/searchAd?n=" + n + "&s=" + s;
		} else {
			url = Config.YXTSERVER3 + "oss/ztiaoad/searchAd?n=" + n + "&s=" + s + "&keywords=" + keyword;
		}
		return getRestApiData(url);
	}
	
	private JSONObject bannerDetail(String id) {
		String url = Config.YXTSERVER3 + "oss/ztiaoad/searchAdInfo?id=" + id;
		return getRestApiData(url);
	}
	
	private JSONObject savebanner(String picUrl,String picWidth,String picHeight,String adSid,String linkUrl,String name) {
		String url = Config.YXTSERVER3 + "oss/ztiaoad/addNew?picUrl=" + picUrl+"&picWidth="+picWidth+"&picHeight="+picHeight+"&adSid="+adSid+"&linkUrl="+linkUrl+"&name="+name;
		return getRestApiData(url);
	}

}
