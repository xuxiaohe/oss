package com.yunxuetang.oss;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.Config;
import com.yunxuetang.util.Saveimage;

@Controller
@RequestMapping("/group")
public class group extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(group.class);

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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.addObject("robots", findRoboit("0", "100"));
		modelview.addObject("categoryOneList", categoryOneList("00"));
		modelview.setViewName("group/createGroupForm");
		return modelview;
	}
	
	
	/**
	 * 
	 * 为某一个用户id创建群 选择二级分类 展示页
	 */
	@RequestMapping("/createGroupSecondForm")
	public ModelAndView createGroupSecondForm(HttpServletRequest request) {
		String courSharResoStr;
		// 当前第几页
		String userid = request.getParameter("id");
		String groupName = request.getParameter("groupName");
		String groupDesc = request.getParameter("groupDesc");
		String parentId = request.getParameter("parentId");
		logger.warn("======================================管理员-"+ request.getSession().getAttribute("name") + "创建群, 用户ID:" + userid + "=======群名字:" + groupName);
		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();
		modelview.addObject("categorySecondList", categorySecondList("10",parentId));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.addObject("robots", findRoboit("0", "100"));
		modelview.addObject("userid", userid);
		modelview.addObject("groupName", groupName);
		modelview.addObject("groupDesc", groupDesc);
		modelview.addObject("parentId", parentId);
		
		modelview.setViewName("group/groupSecondForm");
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
		String userid = request.getParameter("userid");
		String groupName = request.getParameter("groupName");
		String groupDesc = request.getParameter("groupDesc");
		String categoryId = request.getParameter("parentId");
		String childCategoryId = request.getParameter("childCategoryId");
		logger.warn("======================================管理员-"+ request.getSession().getAttribute("name") + "创建群, 用户ID:" + userid + "=============群名字:" + groupName);
		ModelAndView modelview = new ModelAndView();
		RestTemplate restTemplate = new RestTemplate();

		// Map<String, String> rp = new HashMap<String, String>();
		//
		// rp.put("id", userid);
		// rp.put("groupName", groupName.trim());
		// rp.put("intro", groupDesc.trim());

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3 + "oss/group/create?uid=" + userid + "&groupName=" + groupName + "&intro="
				+ groupDesc+ "&categoryId="+ categoryId+ "&childCategoryId="+ childCategoryId, null, String.class);
		JSONObject objj = JSONObject.fromObject(courSharResoStr);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("creategroup", objj);
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		return "redirect:/group/groupList";
		 

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
			/**
			 * 获取一级分类列表
			 * */
			JSONObject category = categoryList();
			JSONArray categoryList = category.getJSONObject("data").getJSONArray("result");
			
			/**
			 * 当前课程的分类
			 * */
			JSONObject currCategory = categoryDetail(ttt.getString("categoryId"));
			JSONObject currChildCategory = categoryDetail(ttt.getString("childCategoryId"));
			
			RestTemplate restTemplate2 = new RestTemplate();

			String tag = restTemplate2.getForObject(Config.YXTSERVER4

			+ "tag/getTagsByIdAndType?domain=" + "yxt" + "&itemId="

			+ id + "&itemType=3", String.class);
			
			JSONObject objj2 = JSONObject.fromObject(tag);

			JSONObject obss = objj2.getJSONObject("data");

			net.sf.json.JSONArray childs = obss.getJSONArray("result");
			String tags2="";
			int i=0;
			if(childs.size()!=0){
				for(Object o:childs){
					if(i==0){
						JSONObject p=(JSONObject) o;
						tags2+=p.getString("value");
					}
					else{
						JSONObject p=(JSONObject) o;
						tags2+=","+p.getString("value");
					}
					i++;
					
				}
				//JSONObject obss2 =(JSONObject) childs.get(0);
				modelview.addObject("tagname", tags2);
			}
			else{
				modelview.addObject("tagname", "");
			}
			
			JSONArray t = ttt.getJSONArray("owner");
			
			String tttt = t.get(0).toString();
			modelview.addObject("currCategory", currCategory);//群的当前分类信息
			modelview.addObject("currChildCategory", currChildCategory);
			modelview.addObject("categoryList", categoryList);//一级分类列表
			modelview.addObject("Group", getGroupInfo(id));
			modelview.addObject("resupdateGroupView", objj);
			modelview.addObject("resOwner", tttt);

		} catch (Exception e) {
			e.printStackTrace();
		}
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("group/updateGroupForm");
		return modelview;
	}
	
	

	/**
	 * 
	 * 更新群组
	 */
	@RequestMapping("/updateGroup")
	public String updateGroup(HttpServletRequest request,@RequestParam MultipartFile file) {
		String courSharResoStr;
		// 拥有者id
		String uid = request.getParameter("uid");
		
		// 群组id

		String gid = request.getParameter("gid");

		String intro = request.getParameter("intro");
		String tag = request.getParameter("tag");
		String logoUrl = request.getParameter("logoUrl");
		
		//群组分类
		String categoryId = request.getParameter("categoryId");
//		String oldCatagoryId = request.getParameter("oldCatagoryId");
//		if(!categoryId.equals(oldCatagoryId)){
//			categoryId = oldCatagoryId;
//		}
		
		String childCategoryId = request.getParameter("childCategoryId");
//		String oldChildCatagoryId = request.getParameter("oldChildCatagoryId");
//		if(!childCategoryId.equals(oldChildCatagoryId)){
//			childCategoryId = oldChildCatagoryId;
//		}
		try {
			logger.warn("======================================管理员-"+ request.getSession().getAttribute("name") + "修改群信息, 用户马甲ID:" + uid + "===============群ID:" + gid);
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

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3 + "oss/group/" + gid + "/update?uid=" + uid + "&intro=" + intro + "&tagName="
				+ tag + "&logoUrl=" + logoUrl + "&groupName=" + groupName + "&bgUrl=" + bgUrl + "&categoryId=" + categoryId + "&childCategoryId=" + childCategoryId, null, String.class);

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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		return "redirect:/group/groupList";
	}

	/**
	 * 
	 * 创建话题展示页 查询机器人
	 */
	@RequestMapping("/createTopicByGroupView")
	public String createTopicByGroupView(HttpServletRequest request) {
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		return "redirect:/group/groupList";
	}

	/**
	 * 
	 * 用机器人id创建话题
	 */
	@RequestMapping("/createTopicByGroup")
	public String createTopicByGroup(HttpServletRequest request) {
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
		logger.warn("======================================管理员-"+ request.getSession().getAttribute("name") + "创建群话题, 用户ID:" + uid + "===============话题:" + title);
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		return "redirect:/group/groupList";
	}

	/**
	 * 
	 * 删除话题
	 */
	@RequestMapping("/deleteTopicByGroup")
	public String deleteTopicByGroup(HttpServletRequest request) {
		String courSharResoStr;

		// 必输
		String topicid = request.getParameter("topicid");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();
		logger.warn("======================================管理员-"+ request.getSession().getAttribute("name") + "删除话题, 话题ID:" + topicid);
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		return "redirect:/group/groupList";
		 
	}

	/**
	 * 
	 * 更新话题 展示页 查询话题信息
	 */
	@RequestMapping("/updateTopicByGroupView")
	public String updateTopicByGroupView(HttpServletRequest request) {
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		return "redirect:/group/groupList";
	}

	/**
	 * 
	 * 更新话题
	 */
	@RequestMapping("/updateTopicByGroup")
	public String updateTopicByGroup(HttpServletRequest request) {
		String courSharResoStr;

		// 必输
		String topicid = request.getParameter("topicid");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String picUrl = request.getParameter("picUrl");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();
		logger.warn("======================================管理员-"+ request.getSession().getAttribute("name") + "更新话题, 话题ID:" + topicid + "=================标题:" + title);
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		return "redirect:/group/groupList";
	}

	/**
	 * 
	 * 创建干货展示页 查询机器人
	 */
	@RequestMapping("/createDryByGroupView")
	public String createDryByGroupView(HttpServletRequest request) {
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		return "redirect:/group/groupList";
	}

	/**
	 * 
	 * 用机器人id创建干货
	 */
	@RequestMapping("/createDryByGroup")
	public String createDryByGroup(HttpServletRequest request,@RequestParam MultipartFile file) {
		String courSharResoStr;

		// 必输
		String id = request.getParameter("uid");
		String tagName = request.getParameter("tagName");
		String group = request.getParameter("gid");
		String url = request.getParameter("url");
		String fileUrl = "";
		try {
			logger.warn("======================================管理员-"+ request.getSession().getAttribute("name") + "创建干货, 用户ID:" + id + "=================群组:" + group);
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		return "redirect:/group/groupList";
	}

	/**
	 * 
	 * 删除干货
	 */
	@RequestMapping("/deleteDryByGroup")
	public String deleteDryByGroup(HttpServletRequest request) {
		String courSharResoStr;

		// 必输
		String dryid = request.getParameter("dryid");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();
		logger.warn("======================================管理员-"+ request.getSession().getAttribute("name") + "删除干货, 干货ID:" + dryid);
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		return "redirect:/group/groupList";
	}

	/**
	 * 
	 * 更新干货 展示页 查询干货信息
	 */
	@RequestMapping("/updateDryByGroupView")
	public String updateDryByGroupView(HttpServletRequest request) {
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		return "redirect:/group/groupList";
	}

	/**
	 * 
	 * 更新干货
	 */
	@RequestMapping("/updateDryByGroup")
	public String updateDryByGroup(HttpServletRequest request) {
		String courSharResoStr;
		// 拥有者id
		String dryCargoId = request.getParameter("dryid");
		String fileUrl = request.getParameter("fileUrl");
		String message = request.getParameter("message");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();
		logger.warn("======================================管理员-"+ request.getSession().getAttribute("name") + "更新, 干货ID:" + dryCargoId);
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		return "redirect:/group/groupList";
	}

	/**
	 * 
	 * 踢出群成员
	 */
	@RequestMapping("/kickGroupMenber")
	public String kickGroupMenber(HttpServletRequest request) {
		String gid = request.getParameter("gid");
		//被删除用户id
		String uid = request.getParameter("userId");
		String ownerid = request.getParameter("ownerid");
		String courSharResoStr;
		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();
		logger.warn("======================================管理员-"+ request.getSession().getAttribute("name") + "剔除群成员, 群ID:" + gid + "===========成员ID:" + uid + "===========拥有者:" + ownerid);
		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3 + "oss/group/" + gid + "/" + uid + "/kick?ownerid=" + ownerid, null,
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		return "redirect:/group/groupMember?gid=" + gid;
	}

	/**
	 * 
	 * 添加群成员
	 */
	@RequestMapping("/addGroupMenber")
	public String addGroupMenber(HttpServletRequest request) {
		// 例如：jack,15050473234,shenbin1225@126.com|test2,test2,test2@126.com
		String users = request.getParameter("users");
		String groupId = request.getParameter("uid");

		String courSharResoStr;
		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();
		logger.warn("======================================管理员-"+ request.getSession().getAttribute("name") + "添加群成员, 用户ID:" + users + "============群ID:" + groupId);
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		return "redirect:/group/groupList";
	}

	/**
	 * 
	 * 取群组的所有标签
	 */
	@RequestMapping("/getTagsByGroup")
	public String getTagsByGroup(HttpServletRequest request) {
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		return "redirect:/group/groupList";
	}

	/**
	 * 
	 * 更新群组的所有标签
	 */
	@RequestMapping("/insertTagsByGroup")
	public String insertTagsByGroup(HttpServletRequest request) {
		String id = request.getParameter("gid");
		String tagNames = request.getParameter("tagNames");
		String courSharResoStr;
		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();
		logger.warn("======================================管理员-"+ request.getSession().getAttribute("name") + "更新群组标签, 群ID:" + id + "=================标签:" + tagNames);
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		return "redirect:/group/groupList";
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
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
		JSONObject j=getGroupMember(gid);
		JSONObject jj=(JSONObject) j.get("data");
		JSONObject jjj=(JSONObject) jj.get("result");
		JSONArray jjjj=jjj.getJSONArray("ownerListusers");
		JSONObject jjjjj=(JSONObject) jjjj.get(0);
		String ownerid=(String) jjjjj.get("id");
		
		
		ModelAndView modelview = new ModelAndView();
		modelview.addObject("Group", getGroupInfo(gid));
		modelview.addObject("Member", getGroupMember(gid));
		modelview.addObject("ownerid", ownerid);
		modelview.addObject("gid", gid);

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("group/groupCourse");
		return modelview;
	}

	/**
	 * 
	 * 删除群组
	 */
	@RequestMapping("/deleteGroup")
	public String deleteGroup(HttpServletRequest request) {
		ModelAndView modelview = new ModelAndView();
		String gid = request.getParameter("gid");
		logger.warn("======================================管理员-"+ request.getSession().getAttribute("name") + "删除群, 群ID:" + gid);
		deleteGroupByID(gid);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.addObject("deletegroup", deleteGroupByID(gid));
		return "redirect:/group/groupList";
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
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
		
		logger.warn("======================================管理员-"+ request.getSession().getAttribute("name") + "分享课程到群组, 群组ID:" + groupId + "==============课程ID:" + courseId);
		
		ModelAndView modelview = new ModelAndView();
		

		modelview.addObject("shareToMyGroup", shareToMyGroup(groupId, courseId, appKey));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
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
		logger.warn("======================================管理员-"+ request.getSession().getAttribute("name") + "关联, 群组ID:" + groupid + "干货ID:" + dryid);
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("group/bindDryByGroupForm");
		return modelview;
	}
	
	
	
	/**
	 * 
	 * 排行榜未绑定列表详情页 初始页  群组
	 */
	@RequestMapping("/GroupBoxDetail")
	public ModelAndView GroupBoxDetail(HttpServletRequest request) {
		 
		String type = "group";
		
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
		modelview.addObject("addDryBoxList", getdryboxlist( objj5,"0","10"));
		modelview.setViewName("group/groupBoxList");
		return modelview;
	}
	
	
	
	/**
	 * 
	 * 查询未关联排行版的干货列表
	 */
	@RequestMapping("/groupBoxList")
	public ModelAndView GroupBoxList(HttpServletRequest request) {
		 
		String pagenumber = request.getParameter("n");

		if (pagenumber == null) {
			pagenumber = "0";
		}

		// 每页条数

		String pagelines = request.getParameter("s");

		if (pagelines == null) {
			pagelines = "10";
		}
		
		String type = "group";
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
		modelview.addObject("addDryBoxList", getdryboxlist(id,pagenumber,pagelines));
		modelview.setViewName("group/groupBoxList");
		return modelview;
	}
	
	
	/**
	 * 
	 * 查询未关联排行版的干货列表
	 */
	@RequestMapping("/searchGroupBoxList")
	public ModelAndView searchGroupBoxList(HttpServletRequest request) {
		 
		String keyword = request.getParameter("keyword");
		String boxPostId = request.getParameter("id");
		
		String type = "group";
		 
		String name = request.getParameter("name");
		 
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		
		modelview.addObject("addDryBoxposition", dryboxpost(type));
		modelview.addObject("name", name);
		modelview.addObject("id", boxPostId);
		modelview.addObject("addDryBoxList", getdryboxlistNotIn( boxPostId, keyword));
		modelview.setViewName("group/groupBoxList");
		return modelview;
	}
	
	
	/**
	 * 
	 * 群组关联到具体的排行榜
	 */
	@RequestMapping("/bindBoxGroup")
	public ModelAndView bindBoxGroup(HttpServletRequest request) {
		String type = "group";
		String sourceType = "group";
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
		
	 
		modelview.addObject("addDryBoxList", bindBoxDry(boxPostId, sourceType, sourceId,ctime));
		
		modelview.addObject("addDryBoxposition", dryboxpost(type));
		modelview.addObject("name", name);
		modelview.addObject("id", boxPostId);
		modelview.addObject("addDryBoxList", getdryboxlist( boxPostId,"0","10"));
		
		modelview.setViewName("group/groupBoxList");
		return modelview;
	}
	
	
	/**
	 * 
	 * 话题审核
	 */
	@RequestMapping("/checkGroup")
	public String checkCourse(HttpServletRequest request) {
		 
		String dryid = request.getParameter("gid");
		logger.warn("======================================管理员-"+ request.getSession().getAttribute("name") + "审核话题, ID:" + dryid);
		//排行榜id
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
	 
		modelview.addObject("addDryBoxList", checkcourse(dryid));
		
		return "redirect:/group/groupList";
	}
	
	private JSONObject checkcourse(String groupid) {
		String url = Config.YXTSERVER3 + "oss/group/groupChecked?gid=" + groupid;
		return getRestApiData(url);
	}
	
	
	private JSONObject bindBoxDry(String boxPostId,String sourceType,String sourceId,String ctime) {
		String url = Config.YXTSERVER3 + "oss/box/addBoxInBoxPost?boxPostId=" + boxPostId+"&sourceType="+sourceType+"&sourceId="+sourceId+"&ctime="+ctime;
		return getRestApiData(url);
	}
	
	
	private JSONObject getdryboxlistNotIn(String boxPostId,String keyword) {
		String url = Config.YXTSERVER3 + "oss/box/searchgroupListNotInBoxPost?boxPostId="+boxPostId+"&keyword="+keyword;
		return getRestApiData(url);
	}
	
	
	private JSONObject getdryboxlist(String boxPostId,String n,String s) {
		String url = Config.YXTSERVER3 + "oss/box/groupListNotInBoxPost?boxPostId=" + boxPostId+"&n="+n+"&s="+s;
		return getRestApiData(url);
	}
	
 
	
	private JSONObject dryboxpost(String type) {
		String url = Config.YXTSERVER3 + "oss/box/getBoxPostByType?type=" + type;
		return getRestApiData(url);
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
	
	private JSONObject categoryOneList(String categoryType) {
		String url = Config.YXTSERVER3 + "category/allPrimary?categoryType="+categoryType;
		return getRestApiData(url);
	}
	
	private JSONObject categorySecondList(String categoryType,String parentId) {
		String url = Config.YXTSERVER3 + "category/allSecond?categoryType="+categoryType+"&parentId="+parentId;
		return getRestApiData(url);
	}
	
	/**
	 * 以下为分类相关方法
	 * */
	private JSONObject categoryList() {
		String url = Config.YXTSERVER3 + "category/all";
		return getRestApiData(url);
	}

	private JSONObject categoryDetail(String id) {
		String url = Config.YXTSERVER3 + "category/one?categoryId=" + id;
		return getRestApiData(url);
	}

}
