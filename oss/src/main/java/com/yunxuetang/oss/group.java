package com.yunxuetang.oss;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.yunxuetang.util.Config;

@Controller
@RequestMapping("/group")
public class group {

	/**
	 * 
	 * 根据条件查找群组
	 */
	@RequestMapping("/groupList")
	public ModelAndView groupList(HttpServletRequest request) {
		String courSharResoStr;
		// String courSharResoStr2;
		String keyword = request.getParameter("keyword");
		// keyword="test123456";

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

		if (keyword == null) {
			courSharResoStr = restTemplate.getForObject(Config.YXTSERVER3
					+ "oss/group/search?n=" + pagenumber + "&s=" + pagelines,
					String.class);
		} else {
			courSharResoStr = restTemplate.getForObject(Config.YXTSERVER3
					+ "oss/group/search?n=" + pagenumber + "&s=" + pagelines
					+ "&keyword=" + keyword, String.class);
		}

		try {
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
			modelview.addObject("groupList", objj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("group/groupList");
		return modelview;

	}

	/**
	 * 
	 * 创建群展示页 查询机器人
	 */
	@RequestMapping("/createGroupView")
	public ModelAndView createGroupView(HttpServletRequest request) {
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
			modelview.addObject("ressearchGroupView", objj);
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
	 * 用机器人id创建群
	 */
	@RequestMapping("/createGroup")
	public ModelAndView createGroup(HttpServletRequest request) {
		String courSharResoStr;
		// 当前第几页
		String id = request.getParameter("id");
		id = "54a8f1d9e4b0df8f6b1b550b";
		// 每页条数

		String groupName = request.getParameter("groupName");
		groupName = "test123456";

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3
				+ "oss/group/create?groupName=" + groupName + "&id=" + id,
				null, String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
			modelview.addObject("ressearchGroupView", objj);
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
	 * 为某一个用户id创建群
	 */
	@RequestMapping("/createGroupForUserForm")
	public ModelAndView createGroupForUserForm(HttpServletRequest request) {
		String courSharResoStr;
		// 当前第几页
		String userid = request.getParameter("userid");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.getForObject(Config.YXTSERVER3
				+ "oss/user/one/" + userid, String.class);

		try {
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
			modelview.addObject("user", objj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("userid", userid);
		modelview.setViewName("group/groupAddFormForUser");
		return modelview;
	}

	/**
	 * 
	 * 为某一个用户id创建群
	 */
	@RequestMapping("/createGroupForUserAction")
	public String createGroupForUserAction(HttpServletRequest request) {
		String courSharResoStr;
		// 当前第几页
		String userid = request.getParameter("id");
		String groupName = request.getParameter("groupName");
		String groupDesc = request.getParameter("groupDesc");

		RestTemplate restTemplate = new RestTemplate();
		
		Map<String, String> rp=new HashMap<String, String>();

		rp.put("id", userid);
		rp.put("groupName", groupName.trim());
		rp.put("intro", groupDesc.trim());

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3
				+ "oss/group/create", null, String.class, rp);

		try {
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
			if(objj.get("status").toString() != "200"){
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/user/userGroup?userid="+userid;
	}

	/**
	 * 
	 * 更新群组 展示页
	 */
	@RequestMapping("/updateGroupView")
	public ModelAndView updateGroupView(HttpServletRequest request) {
		String id = request.getParameter("id");
		id = "54aa495de4b059141b1b67dd";
		String courSharResoStr;
		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3
				+ "oss/group/one/" + id, null, String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);

			JSONObject tt = (JSONObject) objj.get("data");
			JSONObject ttt = (JSONObject) tt.get("result");
			JSONArray t = ttt.getJSONArray("owner");

			String tttt = t.get(0).toString();

			modelview.addObject("resupdateGroupView", objj);
			modelview.addObject("resOwner", tttt);

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
	 * 更新群组
	 */
	@RequestMapping("/updateGroup")
	public ModelAndView updateGroup(HttpServletRequest request) {
		String courSharResoStr;
		// 拥有者id
		String uid = request.getParameter("uid");
		uid = "548fcd49e4b044f4775e063b";
		// 群组id

		String gid = request.getParameter("gid");
		gid = "54a8fadde4b0df8f6b1b569f";

		String intro = request.getParameter("intro");
		String tag = request.getParameter("tag");
		String logoUrl = request.getParameter("logoUrl");
		String groupName = request.getParameter("groupName");
		String bgUrl = request.getParameter("bgUrl");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3
				+ "oss/group/" + gid + "/update?uid=" + uid + "&intro=" + intro
				+ "&tag=" + tag + "&logoUrl=" + logoUrl + "&groupName="
				+ groupName + "&bgUrl=" + bgUrl, null, String.class);

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
		modelview.setViewName("show");
		return modelview;
	}

	/**
	 * 
	 * 查找群的所有话题
	 */
	@RequestMapping("/findGroupTopic")
	public ModelAndView findGroupTopic(HttpServletRequest request) {
		String courSharResoStr;
		// 群组id
		String gid = request.getParameter("gid");
		gid = "54169d8d30046f78437c472f";

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3
				+ "oss/topic/one/source?sourceId=" + gid + "&appKey=yxtapp",
				null, String.class);

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
		modelview.setViewName("show");
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
	 * 删除话题
	 */
	@RequestMapping("/deleteTopicByGroup")
	public ModelAndView deleteTopicByGroup(HttpServletRequest request) {
		String courSharResoStr;

		// 必输
		String topicid = request.getParameter("topicid");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3
				+ "oss/topic/delete?topicid=" + topicid, null, String.class);

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
	@RequestMapping("/updateTopicByGroupView")
	public ModelAndView updateTopicByGroupView(HttpServletRequest request) {
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
	 * 创建干货展示页 查询机器人
	 */
	@RequestMapping("/createDryByGroupView")
	public ModelAndView createDryByGroupView(HttpServletRequest request) {
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
	 * 用机器人id创建干货
	 */
	@RequestMapping("/createDryByGroup")
	public ModelAndView createDryByGroup(HttpServletRequest request) {
		String courSharResoStr;

		// 必输
		String id = request.getParameter("uid");
		String tagName = request.getParameter("tagName");
		String group = request.getParameter("gid");
		String url = request.getParameter("url");
		String fileUrl = request.getParameter("fileUrl");
		String message = request.getParameter("message");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3
				+ "oss/dry/uploadDrycargo?id=" + id + "&tagName=" + tagName
				+ "&group=" + group + "&url=" + url + "&fileUrl=" + fileUrl
				+ "&message=" + message, null, String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
			modelview.addObject("rescreateDryByGroup", objj);
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
	 * 删除干货
	 */
	@RequestMapping("/deleteDryByGroup")
	public ModelAndView deleteDryByGroup(HttpServletRequest request) {
		String courSharResoStr;

		// 必输
		String dryid = request.getParameter("dryid");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3
				+ "oss/dry/delete?dryCargoId=" + dryid, null, String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
			modelview.addObject("resdeleteDryByGroup", objj);
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
	 * 根据群id查询所有干货
	 */
	@RequestMapping("/findDryByGroup")
	public ModelAndView findDryByGroup(HttpServletRequest request) {
		String courSharResoStr;

		// 必输
		String groupId = request.getParameter("groupId");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3 + "oss/dry/allPc?groupId=" + groupId, null, String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
			modelview.addObject("resfindDryByGroup", objj);
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
	 * 更新干货 展示页 查询干货信息
	 */
	@RequestMapping("/updateDryByGroupView")
	public ModelAndView updateDryByGroupView(HttpServletRequest request) {
		String courSharResoStr;

		// 必输
		String dryid = request.getParameter("dryid");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3
				+ "oss/dry/getOneDry?dryid=" + dryid, null, String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
			modelview.addObject("resupdateDryByGroupView", objj);
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
	 * 更新干货
	 */
	@RequestMapping("/updateDryByGroup")
	public ModelAndView updateDryByGroup(HttpServletRequest request) {
		String courSharResoStr;
		// 拥有者id
		String dryCargoId = request.getParameter("dryid");
		String fileUrl = request.getParameter("fileUrl");
		String message = request.getParameter("message");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3
				+ "oss/dry/updateOne?dryid=" + dryCargoId + "&fileUrl="
				+ fileUrl + "&message=" + message, null, String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);

			modelview.addObject("resupdateDryByGroup", objj);

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
	 * 群成员列表 展示页
	 */
	@RequestMapping("/findGroupMenberView")
	public ModelAndView findGroupMenberView(HttpServletRequest request) {
		String gid = request.getParameter("gid");
		String courSharResoStr;
		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3
				+ "oss/group/one/" + gid + "/memberPc", null, String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);

			modelview.addObject("resupdateGroupView", objj);

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
	 * 踢出群成员
	 */
	@RequestMapping("/kickGroupMenber")
	public ModelAndView kickGroupMenber(HttpServletRequest request) {
		String id = request.getParameter("gid");
		String uid = request.getParameter("userId");
		String ownerid = request.getParameter("ownerid");
		String courSharResoStr;
		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3
				+ "oss/group/" + id + "/" + uid + "/kick?ownerid=" + ownerid,
				null, String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);

			modelview.addObject("reskickGroupMenber", objj);

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
	 * 添加群成员
	 */
	@RequestMapping("/addGroupMenber")
	public ModelAndView addGroupMenber(HttpServletRequest request) {
		// 例如：jack,15050473234,shenbin1225@126.com|test2,test2,test2@126.com
		String users = request.getParameter("users");
		String groupId = request.getParameter("uid");

		String courSharResoStr;
		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3
				+ "oss/group/registGroupUser?users=" + users + "&groupId="
				+ groupId + "&md5=dsfgfdrgsh", null, String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);

			modelview.addObject("resaddGroupMenber", objj);

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
	 * 取群组的所有标签
	 */
	@RequestMapping("/getTagsByGroup")
	public ModelAndView getTagsByGroup(HttpServletRequest request) {
		String id = request.getParameter("gid");

		String courSharResoStr;
		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER4
				+ "tag/getTagsByIdAndType?domain=yxtoss&itemId=" + id
				+ "&itemType=3", null, String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);

			modelview.addObject("resgetTagsByGroup", objj);

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
	 * 更新群组的所有标签
	 */
	@RequestMapping("/insertTagsByGroup")
	public ModelAndView insertTagsByGroup(HttpServletRequest request) {
		String id = request.getParameter("gid");
		String tagNames = request.getParameter("tagNames");
		String courSharResoStr;
		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER4
				+ "tag/editTagsDelAdd?domain=yxtoss&itemId=" + id
				+ "&itemType=3" + "&tagNames=" + tagNames
				+ "&userId=542010dde4b01ccc1ee95d28&userName=donny", null,
				String.class);
		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);

			modelview.addObject("resgetTagsByGroup", objj);

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
	 * 删除群组
	 */
	@RequestMapping("/deleteGroup")
	public ModelAndView deleteGroup(HttpServletRequest request) {
		String gid = request.getParameter("gid");
		 
		String courSharResoStr;
		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3 + "oss/group/"+gid+"/delete", null, String.class);
		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);

			modelview.addObject("resdeleteGroup", objj);

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
	 * 获取某个群组的详细信息
	 */
	@RequestMapping("/getOneGroup")
	public ModelAndView getOneGroup(HttpServletRequest request) {
		String gid = request.getParameter("gid");
		 
		String courSharResoStr;
		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3 + "oss/group/findOneGroups/"+gid, null, String.class);
		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);

			modelview.addObject("resgetOneGroup", objj);

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
