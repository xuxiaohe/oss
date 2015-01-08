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
public class topic {

	public topic() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping("/delete")
	public String userDetail(HttpServletRequest request) {
		String courSharResoStr;
		String topicid = request.getParameter("topicid");
		String userid = request.getParameter("userid");
		RestTemplate restTemplate = new RestTemplate();

		courSharResoStr = restTemplate.getForObject(Config.YXTSERVER3
				+ "oss/topic/delete?topicid=" + topicid, String.class);
		JSONObject objj = null;
		try {
			objj = JSONObject.fromObject(courSharResoStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/user/userTopic?userid="+userid; 
	}
	
	
	/**
	 * 分页获得话题
	 * 
	 */
	@RequestMapping("/searchTopic")
	private ModelAndView userList(HttpServletRequest request) {
		String courSharResoStr;
		 
		String keyword = null;

		keyword = request.getParameter("keyword");

		if (keyword == null) {
			keyword = "";
		}

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
				+ "oss/topic/search?n=" + pagenumber + "&s=" + pagelines
				+ "&keyword=" + keyword, String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
			modelview.addObject("restopicList", objj);
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
		modelview.addObject("keyword", keyword);
		modelview.setViewName("user/userList");
		return modelview;
	}
	
	
	/**
	 * 
	 * 创建话题展示页 查询机器人
	 */
	@RequestMapping("/createTopicView")
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

		courSharResoStr = restTemplate.getForObject(Config.YXTSERVER3 + "oss/user/searchbyinfo?n=" + pagenumber + "&s=" + pagelines + "&robot=1",
				String.class);

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
	@RequestMapping("/createTopic")
	public ModelAndView createTopicByGroup(HttpServletRequest request) {
		String courSharResoStr;

		// 必输
		String uid = request.getParameter("uid");
		String type = request.getParameter("type");
		String title = request.getParameter("title");
		// 可选
		String sourceId = request.getParameter("gid");
		String tagName = request.getParameter("tagName");
		String content = request.getParameter("content");
		String picUrl = request.getParameter("picUrl");
		String lat = request.getParameter("lat");
		String lng = request.getParameter("lng");
		String localName = request.getParameter("localName");
		String barCode = request.getParameter("barCode");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3 + "oss/topic/create?uid=" + uid + "&sourceId=" + sourceId + "&type=" + type
				+ "&title=" + title + "&tagName=" + tagName + "&content=" + content + "&picUrl=" + picUrl + "&lat=" + lat + "&lng=" + lng
				+ "&localName=" + localName + "&barCode=" + barCode, null, String.class);

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
	@RequestMapping("/updateTopicView")
	public ModelAndView updateTopicByGroupView(HttpServletRequest request) {
		String courSharResoStr;

		// 必输
		String topicid = request.getParameter("topicid");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3 + "oss/topic/one?topicid=" + topicid, null, String.class);

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
	 * 更新话题
	 */
	@RequestMapping("/updateTopic")
	public ModelAndView updateTopicByGroup(HttpServletRequest request) {
		String courSharResoStr;

		// 必输
		String topicid = request.getParameter("topicid");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String picUrl = request.getParameter("picUrl");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3 + "oss/topic/updateTopicByGroup?topicid=" + topicid + "&title=" + title + "&content="
				+ content + "&picUrl=" + picUrl, null, String.class);

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
