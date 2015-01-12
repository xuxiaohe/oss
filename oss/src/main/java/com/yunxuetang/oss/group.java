package com.yunxuetang.oss;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.Config;

@Controller
@RequestMapping("/group")
public class group extends BaseController {

	/**
	 * 
	 * 根据条件查找群组
	 */
	@RequestMapping("/groupList")
	public ModelAndView groupList(HttpServletRequest request) {
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
		ModelAndView modelview = new ModelAndView();

		modelview.addObject("groupList", groupList(keyword, pagenumber, pagelines));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("group/groupList");
		return modelview;

	}

	/**
	 * 
	 * 创建群展示页 查询机器人
	 */
	@RequestMapping("/createGroupForm")
	public ModelAndView createGroupForm(HttpServletRequest request) {
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

		modelview.addObject("ressearchGroupView", createDryByGroupView(pagenumber, pagelines));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("show");
		return modelview;
	}

	/**
	 * 
	 * 用机器人id创建群
	 */
	@RequestMapping("/createGroupAction")
	public ModelAndView createGroupAction(HttpServletRequest request) {
		String courSharResoStr;
		// 当前第几页
		String id = request.getParameter("id");
		id = "54a8f1d9e4b0df8f6b1b550b";
		// 每页条数

		String groupName = request.getParameter("groupName");
		groupName = "test123456";

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3 + "oss/group/create?groupName=" + groupName + "&id=" + id, null, String.class);

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
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
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

		courSharResoStr = restTemplate.getForObject(Config.YXTSERVER3 + "oss/user/one/" + userid, String.class);

		try {
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
			modelview.addObject("user", objj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
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
		// 当前第几页
		String userid = request.getParameter("id");
		String groupName = request.getParameter("groupName");
		String groupDesc = request.getParameter("groupDesc");

		Map<String, String> rp = new HashMap<String, String>();

		rp.put("id", userid);
		rp.put("groupName", groupName.trim());
		rp.put("intro", groupDesc.trim());

		try {
			JSONObject objj = createGroup(rp);
			if (objj.get("status").toString() != "200") {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/user/userGroup?userid=" + userid;
	}

	/**
	 * 
	 * 更新群组 展示页
	 */
	@RequestMapping("/updateGroupView")
	public ModelAndView updateGroupView(HttpServletRequest request) {
		String id = request.getParameter("id");
		id = "54aa495de4b059141b1b67dd";
		ModelAndView modelview = new ModelAndView();

		JSONObject objj = getOneTopic(id);

		JSONObject tt = (JSONObject) objj.get("data");
		JSONObject ttt = (JSONObject) tt.get("result");
		JSONArray t = ttt.getJSONArray("owner");

		String tttt = t.get(0).toString();

		modelview.addObject("resupdateGroupView", objj);
		modelview.addObject("resOwner", tttt);

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
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

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3 + "oss/group/" + gid + "/update?uid=" + uid + "&intro=" + intro + "&tag="
				+ tag + "&logoUrl=" + logoUrl + "&groupName=" + groupName + "&bgUrl=" + bgUrl, null, String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);

			modelview.addObject("ressearchGroup", objj);

		} catch (Exception e) {
			e.printStackTrace();
		}
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
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
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
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

		ModelAndView modelview = new ModelAndView();

		modelview.addObject("rescreateTopicByGroup",
				createTopicByGroup(uid, sourceId, type, title, tagName, content, picUrl, lat, lng, localName, barCode));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
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

		// 必输
		String topicid = request.getParameter("topicid");

		ModelAndView modelview = new ModelAndView();

		modelview.addObject("rescreateTopicByGroup", deleteTopicByGroup(topicid));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
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

		// 必输
		String topicid = request.getParameter("topicid");

		ModelAndView modelview = new ModelAndView();

		modelview.addObject("resupdateTopicByGroupView", getOneTopic(topicid));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
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
		return modelview;
	}

	/**
	 * 
	 * 创建干货展示页 查询机器人
	 */
	@RequestMapping("/createDryByGroupView")
	public ModelAndView createDryByGroupView(HttpServletRequest request) {
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

		modelview.addObject("rescreateTopicByGroupView", createDryByGroupView(pagenumber, pagelines));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
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

		// 必输
		String id = request.getParameter("uid");
		String tagName = request.getParameter("tagName");
		String group = request.getParameter("gid");
		String url = request.getParameter("url");
		String fileUrl = request.getParameter("fileUrl");
		String message = request.getParameter("message");

		ModelAndView modelview = new ModelAndView();

		modelview.addObject("rescreateDryByGroup", createDryByGroup(id, tagName, group, url, fileUrl, message));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
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

		// 必输
		String dryid = request.getParameter("dryid");

		ModelAndView modelview = new ModelAndView();

		modelview.addObject("resdeleteDryByGroup", deleteDryByGroup(dryid));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
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

		// 必输
		String dryid = request.getParameter("dryid");

		ModelAndView modelview = new ModelAndView();

		JSONObject objj = getOneDry(dryid);
		modelview.addObject("resupdateDryByGroupView", objj);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
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
		// 拥有者id
		String dryCargoId = request.getParameter("dryid");
		String fileUrl = request.getParameter("fileUrl");
		String message = request.getParameter("message");

		ModelAndView modelview = new ModelAndView();

		modelview.addObject("resupdateDryByGroup", updateDryByGroup(dryCargoId, fileUrl, message));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
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
		ModelAndView modelview = new ModelAndView();

		modelview.addObject("reskickGroupMenber", kickGroupMenber(id, uid, ownerid));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
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

		ModelAndView modelview = new ModelAndView();

		modelview.addObject("resaddGroupMenber", addGroupMenber(users, groupId));

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
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

		ModelAndView modelview = new ModelAndView();

		modelview.addObject("resgetTagsByGroup", getTagsByGroup(id));

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
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
		ModelAndView modelview = new ModelAndView();

		modelview.addObject("resgetTagsByGroup", insertTagsByGroup(id, tagNames));

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
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

		ModelAndView modelview = new ModelAndView();

		modelview.addObject("resdeleteGroup", deleteGroup(gid));

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("show");
		return modelview;
	}

	/**
	 * 
	 * 获取某个群组的详细信息
	 */
	@RequestMapping("/groupDetail")
	public ModelAndView groupDetail(HttpServletRequest request) {
		String gid = request.getParameter("gid");
		ModelAndView modelview = new ModelAndView();
		modelview.addObject("Group", getGroupInfo(gid));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("group/groupDetail");
		return modelview;
	}

	/**
	 * 
	 * 根据群id查询所有干货
	 */
	@RequestMapping("/groupDry")
	public ModelAndView groupDry(HttpServletRequest request) {
		// 必输
		String gid = request.getParameter("gid");
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
		modelview.addObject("Group", getGroupInfo(gid));
		modelview.addObject("DryList", getGroupDry(gid, n, s));

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("group/groupDry");
		return modelview;
	}

	/**
	 * 
	 * 查找群的所有话题
	 */
	@RequestMapping("/groupTopic")
	public ModelAndView groupTopic(HttpServletRequest request) {
		String gid = request.getParameter("gid");
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
		modelview.addObject("Group", getGroupInfo(gid));
		modelview.addObject("TopicList", getGroupTopic(gid, n, s));

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("group/groupTopic");
		return modelview;
	}

	/**
	 * 
	 * 群成员列表 展示页
	 */
	@RequestMapping("/groupMember")
	public ModelAndView groupMember(HttpServletRequest request) {
		String gid = request.getParameter("gid");

		ModelAndView modelview = new ModelAndView();
		modelview.addObject("Group", getGroupInfo(gid));
		modelview.addObject("Member", getGroupMember(gid));

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("group/groupMember");
		return modelview;
	}

	/**
	 * 
	 * 查找群的所有话题
	 */
	@RequestMapping("/groupCourse")
	public ModelAndView groupCourse(HttpServletRequest request) {
		String gid = request.getParameter("gid");
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
		modelview.addObject("Group", getGroupInfo(gid));
		modelview.addObject("CourseList", getGroupCourse(gid, n, s));

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("group/groupTopic");
		return modelview;
	}

	private JSONObject getGroupInfo(String gid) {
		String url = Config.YXTSERVER3 + "oss/group/findOneGroups/" + gid;
		return getRestApiData(url);
	}

	private JSONObject getGroupDry(String gid, String n, String s) {
		String url = Config.YXTSERVER3 + "oss/dry/findDryByGroup?groupId=" + gid + "&n=" + n + "&s=" + s;
		return getRestApiData(url);
	}

	private JSONObject getGroupTopic(String gid, String n, String s) {
		String url = Config.YXTSERVER3 + "oss/topic/findByGroupId?sourceId=" + gid + "&appKey=yxtapp&n=" + n + "s=" + s;
		return getRestApiData(url);
	}

	private JSONObject getGroupMember(String gid) {
		String url = Config.YXTSERVER3
				+ "oss/group/one/"+gid+"/memberPc";
		return getRestApiData(url);
	}

	private JSONObject getGroupCourse(String gid, String n, String s) {
		String url = Config.YXTSERVER3 + "oss/topic/getGroupCourse?gid=" + gid + "&n=" + n + "s=" + s;
		return getRestApiData(url);
	}

	private JSONObject deleteGroup(String gid) {
		String url = Config.YXTSERVER3 + "oss/group/" + gid + "/delete";
		return getRestApiData(url);
	}

	private JSONObject insertTagsByGroup(String id, String tagNames) {
		String url = Config.YXTSERVER4 + "tag/editTagsDelAdd?domain=yxtoss&itemId=" + id + "&itemType=3" + "&tagNames=" + tagNames
				+ "&userId=542010dde4b01ccc1ee95d28&userName=donny";
		return getRestApiData(url);
	}

	private JSONObject getTagsByGroup(String id) {
		String url = Config.YXTSERVER4 + "tag/getTagsByIdAndType?domain=yxtoss&itemId=" + id + "&itemType=3";
		return getRestApiData(url);
	}

	private JSONObject addGroupMenber(String users, String groupId) {
		String url = Config.YXTSERVER3 + "oss/group/registGroupUser?users=" + users + "&groupId=" + groupId + "&md5=dsfgfdrgsh";
		return getRestApiData(url);
	}

	private JSONObject kickGroupMenber(String id, String uid, String ownerid) {
		String url = Config.YXTSERVER3 + "oss/group/" + id + "/" + uid + "/kick?ownerid=" + ownerid;
		return getRestApiData(url);
	}

	private JSONObject updateDryByGroup(String dryCargoId, String fileUrl, String message) {
		String url = Config.YXTSERVER3 + "oss/dry/updateOne?dryid=" + dryCargoId + "&fileUrl=" + fileUrl + "&message=" + message;
		return getRestApiData(url);
	}

	private JSONObject getOneDry(String dryid) {
		String url = Config.YXTSERVER3 + "oss/dry/getOneDry?dryid=" + dryid;
		return getRestApiData(url);
	}

	private JSONObject deleteDryByGroup(String dryid) {
		String url = Config.YXTSERVER3 + "oss/dry/delete?dryCargoId=" + dryid;
		return getRestApiData(url);
	}

	private JSONObject createDryByGroup(String id, String tagName, String group, String url, String fileUrl, String message) {
		String url2 = Config.YXTSERVER3 + "oss/dry/uploadDrycargo?id=" + id + "&tagName=" + tagName + "&group=" + group + "&url=" + url + "&fileUrl="
				+ fileUrl + "&message=" + message;
		return getRestApiData(url2);
	}

	private JSONObject createDryByGroupView(String n, String s) {
		String url = Config.YXTSERVER3 + "oss/user/searchbyinfo?n=" + n + "&s=" + s + "&robot=1";
		return getRestApiData(url);
	}

	private JSONObject updateTopicByGroup(String topicid, String title, String content, String picUrl) {
		String url = Config.YXTSERVER3 + "oss/topic/updateTopicByGroup?topicid=" + topicid + "&title=" + title + "&content=" + content + "&picUrl="
				+ picUrl;
		return getRestApiData(url);
	}

	private JSONObject getOneTopic(String topicid) {
		String url = Config.YXTSERVER3 + "oss/topic/one?topicid=" + topicid;
		return getRestApiData(url);
	}

	private JSONObject createGroup(Map rp) {
		String url = Config.YXTSERVER3 + "oss/group/create";
		return getRestApiData(url, rp);
	}

	private JSONObject deleteTopicByGroup(String topicid) {
		String url = Config.YXTSERVER3 + "oss/topic/delete?topicid=" + topicid;
		return getRestApiData(url);
	}

	private JSONObject createTopicByGroup(String uid, String sourceId, String type, String title, String tagName, String content, String picUrl,
			String lat, String lng, String localName, String barCode) {
		String url = Config.YXTSERVER3 + "oss/topic/create?uid=" + uid + "&sourceId=" + sourceId + "&type=" + type + "&title=" + title + "&tagName="
				+ tagName + "&content=" + content + "&picUrl=" + picUrl + "&lat=" + lat + "&lng=" + lng + "&localName=" + localName + "&barCode="
				+ barCode;
		return getRestApiData(url);
	}

	private JSONObject groupList(String keyword, String n, String s) {
		String url = null;

		if (keyword == null) {
			url = Config.YXTSERVER3 + "oss/group/search?n=" + n + "&s=" + s;
		} else {
			url = Config.YXTSERVER3 + "oss/group/search?n=" + n + "&s=" + s + "&keyword=" + keyword;
		}
		return getRestApiData(url);
	}

}
