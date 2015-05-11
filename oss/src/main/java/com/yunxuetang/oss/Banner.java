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
public class Banner extends BaseController {

	/**
	 * banner列表
	 * */
	@RequestMapping("/bannerlist")
	public ModelAndView bannerListView(HttpServletRequest request) {
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
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
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
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("banner/bannerDetail");
		return modelview;

	}

	/**
	 * 创建banner视图
	 * */
	@RequestMapping("/createView")
	public String bannerListView(HttpServletRequest request, Model model) {
		return "banner/bannercreate";
	}

	/**
	 * 创建banner视图
	 * */
	@RequestMapping("/saveBanner")
	public String saveBanner(HttpServletRequest request) {
		String adId = request.getParameter("adId");
		String adSid = request.getParameter("adSid");
		// 3 站外
		String adSellerId = request.getParameter("adSellerId");
		// 站外链接
		String adSellerName = request.getParameter("adSellerName");
		// 创建人
		String creater = (String) request.getSession().getAttribute("name");

		String name = request.getParameter("name");

		String picUrl = request.getParameter("picUrl");
		String picWidth = request.getParameter("picWidth");
		String picHeight = request.getParameter("picHeight");
		String linkUrl = "";
		String groupId = "";
		String topicId = "";
		String dryCargoId = "";
		String courseId = "";
		String groupCourseId = "";

		if ("10".equals(adSid)) {
			linkUrl = request.getParameter("linkUrl");
		} else {
			if ("0".equals(adSellerId)) {
				groupId = request.getParameter("groupId");
				topicId = request.getParameter("topicId");

			}
			if ("1".equals(adSellerId)) {
				groupId = request.getParameter("groupId");
				dryCargoId = request.getParameter("dryCargoId");

			}
			if ("2".equals(adSellerId)) {
				groupId = request.getParameter("groupId");
				courseId = request.getParameter("courseId");
				groupCourseId = request.getParameter("groupCourseId");

			}
		}

		savebanner(picUrl, picWidth, picHeight, adSid, linkUrl, name,
				adSellerId, adSellerName, creater, adId, groupId, topicId,
				dryCargoId, courseId, groupCourseId);
		return "redirect:/banner/bannerlist";
	}
	
	
	
	/**
	 * 更新banner视图
	 * */
	@RequestMapping("/updateBanner")
	public String updateBanner(HttpServletRequest request) {
		String adId = request.getParameter("adId");
		String adSid = request.getParameter("adSid");
		// 3 站外
		String adSellerId = request.getParameter("adSellerId");
		//是否显示
		String effective = request.getParameter("effective");
		// 站外链接
		String adSellerName = request.getParameter("adSellerName");
		// 创建人
		String creater = (String) request.getSession().getAttribute("name");

		String name = request.getParameter("name");

		String picUrl = request.getParameter("picUrl");
		String picWidth = request.getParameter("picWidth");
		String picHeight = request.getParameter("picHeight");
		String linkUrl = "";
		String groupId = "";
		String topicId = "";
		String dryCargoId = "";
		String courseId = "";
		String groupCourseId = "";

		
		if ("10".equals(adSid)) {
			linkUrl = request.getParameter("linkUrl");
		} else {
			if ("0".equals(adSellerId)) {
				groupId = request.getParameter("groupId");
				topicId = request.getParameter("topicId");

			}
			if ("1".equals(adSellerId)) {
				groupId = request.getParameter("groupId");
				dryCargoId = request.getParameter("dryCargoId");

			}
			if ("2".equals(adSellerId)) {
				groupId = request.getParameter("groupId");
				courseId = request.getParameter("courseId");
				groupCourseId = request.getParameter("groupCourseId");

			}
		}

		updatebanner(picUrl, picWidth, picHeight, adSid, linkUrl, name,
				adSellerId, adSellerName, creater, adId, groupId, topicId,
				dryCargoId, courseId, groupCourseId,effective);
		return "redirect:/banner/bannerlist";
	}

	/**
	 * 搜索干货
	 * 
	 * @param keywords
	 *            关键字
	 * */
	@RequestMapping("/searchDry")
	public String searchDrycagro(HttpServletRequest request, Model model) {
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
		// ModelAndView modelview = new ModelAndView();

		model.addAttribute("Drys", dryList(keyword, n, s));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		model.addAttribute("cbasePath", cbasePath);
		model.addAttribute("sourcePath", Config.YXTSERVER5);
		return "banner/drylist";
	}

	/**
	 * 
	 * 查找所有话题 包含没有关联群组的
	 */
	@RequestMapping("/searchTopic")
	public ModelAndView topicList(HttpServletRequest request) {
		// 群组id
		String keyword = request.getParameter("keyword");
		// 当前第几页
		String pagenumber = request.getParameter("n");

		if (pagenumber == null) {
			pagenumber = "0";
		}

		// 每页条数

		String pagelines = request.getParameter("s");

		if (pagelines == null) {
			pagelines = "10";
		}

		ModelAndView modelview = new ModelAndView();

		modelview.addObject("topicList",
				topicList(keyword, pagenumber, pagelines));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("banner/topiclist");
		return modelview;
	}
	
	/**
	 * 
	 * 查找所有课程 包含没有关联群组的
	 */
	@RequestMapping("/searchCourse")
	public ModelAndView courseList(HttpServletRequest request) {
		// 群组id
		String keyword = request.getParameter("keyword");
		// 当前第几页
		String pagenumber = request.getParameter("n");

		if (pagenumber == null) {
			pagenumber = "0";
		}

		// 每页条数

		String pagelines = request.getParameter("s");

		if (pagelines == null) {
			pagelines = "10";
		}

		ModelAndView modelview = new ModelAndView();

		modelview.addObject("courseList",
				courseList(keyword, pagenumber, pagelines));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("banner/courselist");
		return modelview;
	}
	
	@RequestMapping("/modify")
	public String toUpdateVeiw(HttpServletRequest request, Model model){
		String id = request.getParameter("id");
		model.addAttribute("ad", bannerDetail(id));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		model.addAttribute("cbasePath", cbasePath);
		model.addAttribute("sourcePath", Config.YXTSERVER5);
		model.addAttribute("banner/courselist");
		return "banner/bannermodify";
	}

	private JSONObject BannerList(String keyword, String n, String s) {
		String url = null;
		if (keyword == null) {
			url = Config.YXTSERVER3 + "oss/ztiaoad/searchAd?n=" + n + "&s=" + s;
		} else {
			url = Config.YXTSERVER3 + "oss/ztiaoad/searchAd?n=" + n + "&s=" + s
					+ "&keywords=" + keyword;
		}
		return getRestApiData(url);
	}

	private JSONObject bannerDetail(String id) {
		String url = Config.YXTSERVER3 + "oss/ztiaoad/searchAdInfo?id=" + id;
		return getRestApiData(url);
	}

	private JSONObject savebanner(String picUrl, String picWidth, String picHeight, String adSid, String linkUrl, String name, String adSellerId,
			String adSellerName, String creater, String adId, String groupId, String topicId, String dryCargoId, String courseId, String groupCourseId) {
		String url = Config.YXTSERVER3 + "oss/ztiaoad/addNew?picUrl=" + picUrl + "&picWidth=" + picWidth + "&picHeight=" + picHeight + "&adSid="
				+ adSid + "&linkUrl=" + linkUrl + "&name=" + name + "&adSellerId=" + adSellerId + "&adSellerName=" + adSellerName + "&creater="
				+ creater + "&adId=" + adId + "&groupId=" + groupId + "&topicId=" + topicId + "&dryCargoId=" + dryCargoId + "&courseId=" + courseId
				+ "&groupCourseId=" + groupCourseId;
		return getRestApiData(url);
	}
	
	
	private JSONObject updatebanner(String picUrl, String picWidth, String picHeight, String adSid, String linkUrl, String name, String adSellerId,
			String adSellerName, String creater, String adId, String groupId, String topicId, String dryCargoId, String courseId,
			String groupCourseId, String effective) {
		String url = Config.YXTSERVER3 + "oss/ztiaoad/addNew?picUrl=" + picUrl + "&picWidth=" + picWidth + "&picHeight=" + picHeight + "&adSid="
				+ adSid + "&linkUrl=" + linkUrl + "&name=" + name + "&adSellerId=" + adSellerId + "&adSellerName=" + adSellerName + "&creater="
				+ creater + "&adId=" + adId + "&groupId=" + groupId + "&topicId=" + topicId + "&dryCargoId=" + dryCargoId + "&courseId=" + courseId
				+ "&groupCourseId=" + groupCourseId + "&effective=" + effective;
		return getRestApiData(url);
	}

	/**
	 * 搜索干货方法
	 * */
	private JSONObject dryList(String keyword, String n, String s) {
		String url = null;
		if (keyword == null) {
			url = Config.YXTSERVER3 + "oss/dry/searchDrys?n=" + n + "&s=" + s;
		} else {
			url = Config.YXTSERVER3 + "oss/dry/searchDrys?n=" + n + "&s=" + s
					+ "&keywords=" + keyword;
		}
		return getRestApiData(url);
	}

	private JSONObject topicList(String keyword, String n, String s) {
		String url;
		if (keyword == null) {
			url = Config.YXTSERVER3 + "oss/topic/search?n=" + n + "&s=" + s;
		} else {
			url = Config.YXTSERVER3 + "oss/topic/search?keyword=" + keyword
					+ "&n=" + n + "&s=" + s;
		}

		return getRestApiData(url);
	}
	
	private JSONObject courseList(String keyword, String n, String s) {
		String url;
		if (keyword == null) {
			url = Config.YXTSERVER3 + "oss/course/getGroupCourseList?n=" + n + "&s=" + s;
		} else {
			url = Config.YXTSERVER3 + "oss/course/getGroupCourseList?keywords=" + keyword
					+ "&n=" + n + "&s=" + s;
		}

		return getRestApiData(url);
	}
}
