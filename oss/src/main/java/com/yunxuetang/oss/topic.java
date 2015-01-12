package com.yunxuetang.oss;

import java.io.UnsupportedEncodingException;

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
		String userid = request.getParameter("userid");
		deleteTopic(topicid);

		return "redirect:/topic/topicList";
	}

	// 删除话题For User
	@RequestMapping("/deleteTopicForUser")
	public String deleteTopicForUser(HttpServletRequest request) {
		String topicid = request.getParameter("topicid");
		String userid = request.getParameter("userid");
		deleteTopic(topicid);

		return "redirect:/user/userTopic?userid=" + userid;
	}

	// 删除话题For Group
	@RequestMapping("/deleteTopicForGroup")
	public String deleteTopicForGroup(HttpServletRequest request) {
		String topicid = request.getParameter("topicid");
		String gid = request.getParameter("gid");
		deleteTopic(topicid);
		return "redirect:/group/groupTopic?gid=" + gid;
	}

	private JSONObject deleteTopic(String topicid) {
		String url = Config.YXTSERVER3 + "oss/topic/delete?topicid=" + topicid;
		return getRestApiData(url);
	}

	/**
	 * 
	 * 查找所有话题 包含没有关联群组的
	 */
	@RequestMapping("/topicList")
	public ModelAndView topicList(HttpServletRequest request) {
		String courSharResoStr;
		// 群组id
		String keyword = request.getParameter("keyword");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3
				+ "oss/topic/search?keyword=" + keyword, null, String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);

			modelview.addObject("ressearchGroup", objj);

		} catch (Exception e) {
			e.printStackTrace();
		}
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
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
		String courSharResoStr;
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

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.getForObject(Config.YXTSERVER3
				+ "oss/user/searchbyinfo?n=" + pagenumber + "&s=" + pagelines
				+ "&robot=1", String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
			modelview.addObject("rescreateTopicByGroupView", objj);
			String s = objj.getString("msg");
			System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("show");
		return modelview;
	}

	/**
	 * 
	 * 用机器人id创建话题
	 */
	@RequestMapping("/createTopicByGroup")
	public ModelAndView createTopicByGroup(HttpServletRequest request) {
		String courSharResoStr;

		// 必输
		String uid = request.getParameter("uid");
		String sourceId = request.getParameter("gid");
		String type = request.getParameter("type");
		String title = request.getParameter("title");
		// 可选
		String tagName = request.getParameter("tagName");
		String content = request.getParameter("content");
		String picUrl = request.getParameter("picUrl");
		String lat = request.getParameter("lat");
		String lng = request.getParameter("lng");
		String localName = request.getParameter("localName");
		String barCode = request.getParameter("barCode");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3
				+ "oss/topic/create?uid=" + uid + "&sourceId=" + sourceId
				+ "&type=" + type + "&title=" + title + "&tagName=" + tagName
				+ "&content=" + content + "&picUrl=" + picUrl + "&lat=" + lat
				+ "&lng=" + lng + "&localName=" + localName + "&barCode="
				+ barCode, null, String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
			modelview.addObject("rescreateTopicByGroup", objj);
			String s = objj.getString("msg");
			System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("show");
		return modelview;
	}

	/**
	 * 
	 * 更新话题 展示页 查询话题信息
	 */
	@RequestMapping("/createTopicForm")
	public ModelAndView createTopicForm(HttpServletRequest request) {
		String courSharResoStr;

		// 必输
		String topicid = request.getParameter("topicid");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3
				+ "oss/topic/one?topicid=" + topicid, null, String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
			modelview.addObject("resupdateTopicByGroupView", objj);
			String s = objj.getString("msg");
			System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("show");
		return modelview;
	}

	/**
	 * 
	 * 更新话题 展示页 查询话题信息
	 */
	@RequestMapping("/topicDetail")
	public ModelAndView topicDetail(HttpServletRequest request) {
		String courSharResoStr;

		// 必输
		String topicid = request.getParameter("topicid");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3
				+ "oss/topic/one?topicid=" + topicid, null, String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
			modelview.addObject("resupdateTopicByGroupView", objj);
			String s = objj.getString("msg");
			System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("topic/topicDetail");
		return modelview;
	}

	/**
	 * 
	 * 更新话题
	 */
	@RequestMapping("/updateTopicByGroup")
	public ModelAndView updateTopicByGroup(HttpServletRequest request) {
		String courSharResoStr;

		// 必输
		String topicid = request.getParameter("topicid");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String picUrl = request.getParameter("picUrl");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3
				+ "oss/topic/updateTopicByGroup?topicid=" + topicid + "&title="
				+ title + "&content=" + content + "&picUrl=" + picUrl, null,
				String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
			modelview.addObject("rescreateTopicByGroup", objj);
			String s = objj.getString("msg");
			System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("show");
		return modelview;
	}

	/**
	 * 
	 * 关联群组
	 */
	@RequestMapping("/updateTopic")
	public ModelAndView updateTopic(HttpServletRequest request) {
		String courSharResoStr;

		// 必输
		String groupid = request.getParameter("groupid");
		String topicid = request.getParameter("topicid");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3
				+ "oss/topic/updateTopicByGroup?topicId=" + topicid
				+ "&groupid=" + groupid, null, String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
			modelview.addObject("rescreateTopicByGroup", objj);
			String s = objj.getString("msg");
			System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("show");
		return modelview;
	}

}
