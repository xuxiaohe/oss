package com.yunxuetang.oss;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.yunxuetang.util.Config;
import com.yunxuetang.util.Saveimage;

@Controller
@RequestMapping("/group")
public class group extends BaseController {

	@Autowired
	Saveimage saveimage;
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
			courSharResoStr = restTemplate.getForObject(Config.YXTSERVER3 + "oss/group/search?n=" + pagenumber + "&s=" + pagelines, String.class);
		} else {
			courSharResoStr = restTemplate.getForObject(Config.YXTSERVER3 + "oss/group/search?n=" + pagenumber + "&s=" + pagelines + "&keyword="
					+ keyword, String.class);
		}

		try {
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
			modelview.addObject("groupList", objj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("group/groupList");
		return modelview;

	}

	/**
	 * 
	 * 为某一个用户id创建群 展示页
	 */
	@RequestMapping("/createGroupForUserForm")
	public ModelAndView createGroupForUserForm(HttpServletRequest request) {
		String courSharResoStr;
		// 当前第几页

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("robots", findRoboit("0", "100"));

		modelview.setViewName("group/createGroupForm");
		return modelview;
	}

	/**
	 * 
	 * 为某一个用户id创建群
	 */
	@RequestMapping("/createGroupForUserAction")
	public ModelAndView createGroupForUserAction(HttpServletRequest request) {
		String courSharResoStr;
		// 当前第几页
		String userid = request.getParameter("id");
		String groupName = request.getParameter("groupName");
		String groupDesc = request.getParameter("groupDesc");
		ModelAndView modelview = new ModelAndView();
		RestTemplate restTemplate = new RestTemplate();

		// Map<String, String> rp = new HashMap<String, String>();
		//
		// rp.put("id", userid);
		// rp.put("groupName", groupName.trim());
		// rp.put("intro", groupDesc.trim());

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3 + "oss/group/create?uid=" + userid + "&groupName=" + groupName + "&intro="
				+ groupDesc, null, String.class);
		JSONObject objj = JSONObject.fromObject(courSharResoStr);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("creategroup", objj);
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("group/show");
		return modelview;

	}

	/**
	 * 
	 * 更新群组 展示页
	 */
	@RequestMapping("/updateGroupView")
	public ModelAndView updateGroupView(HttpServletRequest request) {
		String id = request.getParameter("gid");
		String tags;
		String courSharResoStr;
		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3 + "oss/group/one/" + id, null, String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);

			JSONObject tt = (JSONObject) objj.get("data");
			JSONObject ttt = (JSONObject) tt.get("result");

			JSONArray t = ttt.getJSONArray("owner");

			String tttt = t.get(0).toString();
			modelview.addObject("Group", getGroupInfo(id));
			modelview.addObject("resupdateGroupView", objj);
			modelview.addObject("resOwner", tttt);

		} catch (Exception e) {
			e.printStackTrace();
		}
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("group/updateGroupForm");
		return modelview;
	}

	/**
	 * 
	 * 更新群组
	 */
	@RequestMapping("/updateGroup")
	public ModelAndView updateGroup(HttpServletRequest request,@RequestParam MultipartFile file) {
		String courSharResoStr;
		// 拥有者id
		String uid = request.getParameter("uid");

		// 群组id

		String gid = request.getParameter("gid");

		String intro = request.getParameter("intro");
		String tag = request.getParameter("tag");
		String logoUrl = request.getParameter("logoUrl");
		try {

			if (file.getSize()!=0) {

			Long l=System.currentTimeMillis();

			String urlString="/data/ossImgTemp";

			String urlString2=uid+l+".jpg";

			InputStream stream=	file.getInputStream();

			logoUrl=saveimage.save(urlString, urlString2, stream,"group");

			}

			} catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

			}
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
		modelview.setViewName("group/show");
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
		String courSharResoStr;

		// 必输
		String topicid = request.getParameter("topicid");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3 + "oss/topic/delete?topicid=" + topicid, null, String.class);

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
		String courSharResoStr;

		// 必输
		String topicid = request.getParameter("topicid");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String picUrl = request.getParameter("picUrl");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3 + "oss/topic/updateTopicByGroup?topicid=" + topicid + "&title=" + title
				+ "&content=" + content + "&picUrl=" + picUrl, null, String.class);

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
	 * 用机器人id创建干货
	 */
	@RequestMapping("/createDryByGroup")
	public ModelAndView createDryByGroup(HttpServletRequest request,@RequestParam MultipartFile file) {
		String courSharResoStr;

		// 必输
		String id = request.getParameter("uid");
		String tagName = request.getParameter("tagName");
		String group = request.getParameter("gid");
		String url = request.getParameter("url");
		String fileUrl = "";
		try {

			String t[]=file.getContentType().split("/");
			String tt="."+t[1];
			if (file.getSize()!=0) {

			Long l=System.currentTimeMillis();

			String urlString="/data/ossImgTemp";

			String urlString2=id+l+tt;

			InputStream stream=	file.getInputStream();

			fileUrl=saveimage.save(urlString, urlString2, stream,"dry");

			}

			} catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

			}
		
		
		String message = request.getParameter("message");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3 + "oss/dry/uploadDrycargo?id=" + id + "&tagName=" + tagName + "&group="
				+ group + "&url=" + url + "&fileUrl=" + fileUrl + "&message=" + message, null, String.class);

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
		String courSharResoStr;

		// 必输
		String dryid = request.getParameter("dryid");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3 + "oss/dry/delete?dryCargoId=" + dryid, null, String.class);

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
		String courSharResoStr;

		// 必输
		String dryid = request.getParameter("dryid");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3 + "oss/dry/getOneDry?dryid=" + dryid, null, String.class);

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
		String courSharResoStr;
		// 拥有者id
		String dryCargoId = request.getParameter("dryid");
		String fileUrl = request.getParameter("fileUrl");
		String message = request.getParameter("message");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3 + "oss/dry/updateOne?dryid=" + dryCargoId + "&fileUrl=" + fileUrl
				+ "&message=" + message, null, String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);

			modelview.addObject("resupdateDryByGroup", objj);

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

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3 + "oss/group/" + id + "/" + uid + "/kick?ownerid=" + ownerid, null,
				String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);

			modelview.addObject("reskickGroupMenber", objj);

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

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3 + "oss/group/registGroupUser?users=" + users + "&groupId=" + groupId
				+ "&md5=dsfgfdrgsh", null, String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);

			modelview.addObject("resaddGroupMenber", objj);

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
	 * 取群组的所有标签
	 */
	@RequestMapping("/getTagsByGroup")
	public ModelAndView getTagsByGroup(HttpServletRequest request) {
		String id = request.getParameter("gid");

		String courSharResoStr;
		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER4 + "tag/getTagsByIdAndType?domain=yxtoss&itemId=" + id + "&itemType=3", null,
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
		String courSharResoStr;
		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER4 + "tag/editTagsDelAdd?domain=yxtoss&itemId=" + id + "&itemType=3"
				+ "&tagNames=" + tagNames + "&userId=542010dde4b01ccc1ee95d28&userName=donny", null, String.class);
		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);

			modelview.addObject("resgetTagsByGroup", objj);

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
			s = "100";
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
	 * 查找群的所有课程
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
		modelview.setViewName("group/groupCourse");
		return modelview;
	}

	/**
	 * 
	 * 删除群组
	 */
	@RequestMapping("/deleteGroup")
	public ModelAndView deleteGroup(HttpServletRequest request) {
		ModelAndView modelview = new ModelAndView();
		String gid = request.getParameter("gid");
		deleteGroupByID(gid);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("deletegroup", deleteGroupByID(gid));
		modelview.setViewName("group/show");
		return modelview;
	}

	/**
	 * 
	 * 查询所有未分享群组的课程
	 */
	@RequestMapping("/courseListNoShare")
	public ModelAndView courseListNoShare(HttpServletRequest request) {
		// 当前第几页
		String n = request.getParameter("n");
		if (n == null) {
			n = "0";
		}
		// 每页条数
		String s = request.getParameter("s");

		if (s == null) {
			s = "100";
		}
		String gid = request.getParameter("gid");
		ModelAndView modelview = new ModelAndView();

		modelview.addObject("courses", getCoursesNoShare(n, s));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("Groupid", gid);
		modelview.setViewName("group/bindCourseByGroupForm");
		return modelview;
	}

	/**
	 * 
	 * 课程分享到群组
	 */
	@RequestMapping("/shareToMyGroup")
	public String shareToMyGroup(HttpServletRequest request) {
		String groupId = request.getParameter("groupId");
		String courseId = request.getParameter("courseId");
		String appKey = "yxtapp";
		ModelAndView modelview = new ModelAndView();

		modelview.addObject("shareToMyGroup", shareToMyGroup(groupId, courseId, appKey));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		return "redirect:/group/groupCourse?gid=" + groupId;
	}

	/**
	 * 
	 * 关联群组
	 */
	@RequestMapping("/updateDryAction")
	public ModelAndView updateDryAction(HttpServletRequest request) {

		// 必输
		String groupid = request.getParameter("gid");
		String dryid = request.getParameter("dryid");
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

		modelview.addObject("rescreateTopicByGroup", UpdateDryById(dryid, groupid));
		modelview.addObject("Group", getGroupInfo(groupid));

		modelview.addObject("DryList", getGroupDry(groupid, n, s));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("group/groupDry");

		return modelview;
	}

	/**
	 * 
	 * 关联群组 展示页
	 */
	@RequestMapping("/updateDryForm")
	public ModelAndView updateDryForm(HttpServletRequest request) {

		// 当前第几页
		String group = request.getParameter("groupid");

		ModelAndView modelview = new ModelAndView();

		modelview.addObject("group", group);
		modelview.addObject("dryList", dryList(null, "0", "100"));

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("group/bindDryByGroupForm");
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
		String url = Config.YXTSERVER3 + "oss/topic/findByGroupId?sourceId=" + gid + "&appKey=yxtapp&n=" + n + "&s=" + s;
		return getRestApiData(url);
	}

	private JSONObject getGroupMember(String gid) {
		String url = Config.YXTSERVER3 + "oss/group/one/" + gid + "/memberPc";
		return getRestApiData(url);
	}

	// private JSONObject getGroupCourse(String gid, String n, String s) {
	// String url = Config.YXTSERVER3 + "oss/topic/getGroupCourse?gid=" + gid
	// + "&n=" + n + "&s=" + s;
	// return getRestApiData(url);
	// }

	private JSONObject deleteGroupByID(String gid) {
		String url = Config.YXTSERVER3 + "oss/group/" + gid + "/delete";
		return getRestApiData(url);
	}

	private JSONObject getGroupCourse(String gid, String n, String s) {
		String url = Config.YXTSERVER3 + "oss/course/groupCourses?groupId=" + gid + "&n=" + n + "&s=" + s;
		return getRestApiData(url);
	}

	private JSONObject findRoboit(String n, String s) {
		// String url = Config.YXTSERVER3 + "oss/user/searchbyinfo?n=" + n +
		// "&s=" + s + "&robot=1";
		String url = Config.YXTSERVER3 + "oss/user/searchbyinfo?n=" + n + "&s=" + s + "&robot=1";
		return getRestApiData(url);
	}

	private JSONObject getCoursesNoShare(String n, String s) {
		String url = Config.YXTSERVER3 + "oss/course/coursesNoShare?n=" + n + "&s=" + s;
		return getRestApiData(url);
	}

	private JSONObject shareToMyGroup(String groupId, String courseId, String appKey) {
		String url = Config.YXTSERVER3 + "oss/course/shareToMyGroup?groupId=" + groupId + "&courseId=" + courseId + "&appKey=" + appKey;
		return getRestApiData(url);
	}

	private JSONObject getOneDry(String dryid) {
		String url = Config.YXTSERVER3 + "oss/dry/getOneDry?dryid=" + dryid;
		return getRestApiData(url);
	}

	private JSONObject UpdateDryById(String dryId, String groupid) {
		String url = Config.YXTSERVER3 + "oss/dry/updateOne?dryid=" + dryId + "&groupid=" + groupid;
		return getRestApiData(url);
	}

	private JSONObject dryList(String keyword, String n, String s) {
		String url = null;
		if (keyword == null) {
			url = Config.YXTSERVER3 + "oss/dry/searchDrys?n=" + n + "&s=" + s;
		} else {
			url = Config.YXTSERVER3 + "oss/dry/searchDrys?n=" + n + "&s=" + s + "&keywords=" + keyword;
		}
		return getRestApiData(url);
	}

}
