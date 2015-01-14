package com.yunxuetang.oss;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.Config;

@Controller
@RequestMapping("/topic")
public class topic extends BaseController {

	public topic() {
		// TODO Auto-generated constructor stub
	}

	// 删除话题
	@RequestMapping("/deleteTopic")
	public String deleteTopic(HttpServletRequest request) {
		String topicid = request.getParameter("topicid");
		deleteTopicByID(topicid);

		return "redirect:/topic/topicList";
	}

	// 删除话题For User
	@RequestMapping("/deleteTopicForUser")
	public String deleteTopicForUser(HttpServletRequest request) {
		String topicid = request.getParameter("topicid");
		String userid = request.getParameter("userid");
		deleteTopicByID(topicid);
		return "redirect:/user/userTopic?userid=" + userid;
	}

	// 删除话题For Group
	@RequestMapping("/deleteTopicForGroup")
	public String deleteTopicForGroup(HttpServletRequest request) {
		String topicid = request.getParameter("topicid");
		String gid = request.getParameter("gid");
		deleteTopicByID(topicid);
		return "redirect:/group/groupTopic?gid=" + gid;
	}


	private JSONObject deleteTopicByID(String topicid) {
		String url = Config.YXTSERVER3 + "oss/topic/delete?topicid=" + topicid;
		return getRestApiData(url);
	}

	/**
	 * 
	 * 查找所有话题 包含没有关联群组的
	 */
	@RequestMapping("/topicList")
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

		modelview.addObject("topicList", topicList(keyword, pagenumber, pagelines));

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("topic/topicList");
		return modelview;
	}

	/**
	 * 
	 * 创建话题展示页 查询机器人
	 */
	@RequestMapping("/createTopicByGroupView")
	public ModelAndView createTopicByGroupView(HttpServletRequest request) {
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

		modelview.addObject("robots", findRoboit(pagenumber, pagelines));
		modelview.addObject("groupList", groupList(pagenumber,pagelines));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("topic/createtopicForm");
		return modelview;
	}

	/**
	 * 
	 * 用机器人id创建话题
	 */
	@RequestMapping("/createTopicByGroup")
	public String createTopicByGroup(HttpServletRequest request) {

		// 必输
		String uid = request.getParameter("uid");
		String sourceId = request.getParameter("gid");
		String type = request.getParameter("type");
		String title = request.getParameter("title");
		// 可选
		String tagName = request.getParameter("tagName");
		if(tagName==null){
			tagName="";
		}
		String content = request.getParameter("content");
		if(content==null){
			content="";
		}
		String picUrl = request.getParameter("picUrl");
		if(picUrl==null){
			picUrl="";
		}
		String lat = request.getParameter("lat");
		if(lat==null){
			lat="";
		}
		String lng = request.getParameter("lng");
		if(lng==null){
			lng="";
		}
		String localName = request.getParameter("localName");
		if(localName==null){
			localName="";
		}
		String barCode = request.getParameter("barCode");
		if(barCode==null){
			barCode="";
		}

		ModelAndView modelview = new ModelAndView();

		modelview.addObject("rescreateTopicByGroup", createTopic(uid, sourceId, type, title, tagName, content, picUrl, lat, lng, localName, barCode));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		
		return "redirect:/topic/topicList";
	}

	/**
	 * 
	 * 更新话题 展示页 查询话题信息
	 */
	@RequestMapping("/updateTopicItemsForm")
	public ModelAndView updateTopicItemsForm(HttpServletRequest request) {

		// 必输
		String topicid = request.getParameter("topicid");

		ModelAndView modelview = new ModelAndView();

			modelview.addObject("resuserTopic", getOneTopic(topicid));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("topic/updateTopicForm");
		return modelview;
	}

	/**
	 * 
	 *  查询话题信息
	 */
	@RequestMapping("/topicDetail")
	public ModelAndView topicDetail(HttpServletRequest request) {

		// 必输
		String topicid = request.getParameter("topicid");

		ModelAndView modelview = new ModelAndView();

			modelview.addObject("topicDetail", getOneTopic(topicid));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("topic/topicDetail");
		return modelview;
	}

	/**
	 * 
	 * 更新话题
	 */
	@RequestMapping("/updateTopicByGroup")
	public String updateTopicByGroup(HttpServletRequest request) {

		// 必输
		String topicid = request.getParameter("topicid");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String picUrl = request.getParameter("picUrl");

		ModelAndView modelview = new ModelAndView();

			modelview.addObject("rescreateTopicByGroup", updateTopicByGroup(topicid, title, content, picUrl));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("show");
		return "redirect:/topic/topicList";
	}

	/**
	 * 
	 * 关联群组
	 */
	@RequestMapping("/updateTopic")
	public String updateTopic(HttpServletRequest request) {

		// 必输
		String groupid = request.getParameter("groupid");
		String topicid = request.getParameter("topicid");

		ModelAndView modelview = new ModelAndView();

			modelview.addObject("rescreateTopicByGroup", updateTopic(topicid, groupid));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		return "redirect:/topic/topicList";
	}
	
	/**
	 * 
	 * 关联群组  展示页
	 */
	@RequestMapping("/updateTopicForm")
	public ModelAndView updateTopicForm(HttpServletRequest request) {

		// 当前第几页
				String topicid = request.getParameter("topicid");

				ModelAndView modelview = new ModelAndView();

				try {
					JSONObject objj3 = getOneTopic(topicid);
					modelview.addObject("resuserTopic", objj3);
				} catch (Exception e) {
					e.printStackTrace();
				}

				String cpath = request.getContextPath();
				String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
				modelview.addObject("cbasePath", cbasePath);
				modelview.setViewName("topic/bindTopicByGroupForm");
				return modelview;
	}
	
	
	

	private JSONObject topicList(String keyword, String n, String s) {
		String url;
		if(keyword==null){
			 url = Config.YXTSERVER3 + "oss/topic/search";
		}
		else {
			 url = Config.YXTSERVER3 + "oss/topic/search?keyword=" + keyword + "&n=" + n + "&s=" + s;
		}
		
		return getRestApiData(url);
	}

	private JSONObject deleteTopic(String topicid) {
		String url = Config.YXTSERVER3 + "oss/topic/delete?topicid=" + topicid;
		return getRestApiData(url);
	}

	private JSONObject findRoboit(String n, String s) {
		String url = Config.YXTSERVER3 + "oss/user/searchbyinfo?n=" + n + "&s=" + s + "&robot=1";
		return getRestApiData(url);
	}

	private JSONObject createTopic(String uid, String sourceId, String type, String title, String tagName, String content, String picUrl, String lat,
			String lng, String localName, String barCode) {
//		String url = Config.YXTSERVER3 + "oss/topic/create?uid=" + uid + "&sourceId=" + sourceId + "&type=" + type + "&title=" + title + "&tagName="
//				+ tagName + "&content=" + content + "&picUrl=" + picUrl + "&lat=" + lat + "&lng=" + lng + "&localName=" + localName + "&barCode="
//				+ barCode;
		
		String url = Config.YXTSERVER3 + "oss/topic/create?uid=" + uid + "&sourceId=" + sourceId + "&type=" + type + "&title=" + title+ "&content=" + content;
		return getRestApiData(url);
	}
	
	private JSONObject getOneTopic(String topicid) {
		String url = Config.YXTSERVER3 + "oss/topic/one?topicid=" + topicid;
		return getRestApiData(url);
	}
	
	
	private JSONObject updateTopicByGroup(String topicid, String title, String content, String picUrl) {
		String url = Config.YXTSERVER3 + "oss/topic/updateTopicByGroup?topicId=" + topicid + "&title=" + title + "&content=" + content + "&picUrl="
				+ picUrl;
		return getRestApiData(url);
	}
	
	private JSONObject updateTopic(String topicid,String groupid) {
		String url = Config.YXTSERVER3 + "oss/topic/updateTopicByGroup?topicId=" + topicid + "&groupid=" + groupid;
		return getRestApiData(url);
	}
	
	

}
