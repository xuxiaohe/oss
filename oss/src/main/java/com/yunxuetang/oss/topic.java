package com.yunxuetang.oss;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

import com.yunxuetang.util.Config;
import com.yunxuetang.util.Saveimage;

@Controller
@RequestMapping("/topic")
public class topic extends BaseController {

	@Autowired
	Saveimage saveimage;
	public topic() {
		// TODO Auto-generated constructor stub
	}

	// 删除话题
	@RequestMapping("/deleteTopic")
	public ModelAndView deleteTopic(HttpServletRequest request) {
		ModelAndView modelview = new ModelAndView();
		String topicid = request.getParameter("topicid");

		modelview.addObject("deleteTopic", deleteTopicByID(topicid));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("topic/show");
		return modelview;
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
		modelview.addObject("groupList", groupList(pagenumber, pagelines));
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
		if (tagName == null) {
			tagName = "";
		} else {
			String tagNameArry[] = tagName.split(",");
			List l = new ArrayList();
			for (String a : tagNameArry) {
				l.add("\"" + a + "\"");
			}
			tagName = l.toString();
		}
		String content = request.getParameter("content");
		if (content == null) {
			content = "";
		}
		String picUrl = request.getParameter("picUrl");
		if (picUrl == null) {
			picUrl = "";
		}
		String lat = request.getParameter("lat");
		if (lat == null) {
			lat = "";
		}
		String lng = request.getParameter("lng");
		if (lng == null) {
			lng = "";
		}
		String localName = request.getParameter("localName");
		if (localName == null) {
			localName = "";
		}
		String barCode = request.getParameter("barCode");
		if (barCode == null) {
			barCode = "";
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
	 * 查询话题主楼回复和副楼回复
	 */
	@RequestMapping("/findPost")
	public ModelAndView findPost(HttpServletRequest request) {

		// 必输
		String topicid = request.getParameter("topicid");

		ModelAndView modelview = new ModelAndView();

		modelview.addObject("resuserTopic", findPost(topicid));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("topic/updateTopicForm");
		return modelview;
	}

	/**
	 * 
	 * 查询话题信息
	 */
	@RequestMapping("/topicDetail")
	public ModelAndView topicDetail(HttpServletRequest request) {

		// 必输
		String topicid = request.getParameter("topicid");

		ModelAndView modelview = new ModelAndView();

		modelview.addObject("topicDetail", getOneTopic(topicid));
		modelview.addObject("resTopicPost", findPost(topicid));
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
	public String updateTopicByGroup(HttpServletRequest request,@RequestParam MultipartFile file) {

		// 必输
		String topicid = request.getParameter("topicid");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String picUrl = request.getParameter("picUrl");
		try {
			String t[]=file.getContentType().split("/");
			String tt="."+t[1];
			if (file.getSize()!=0) {

			Long l=System.currentTimeMillis();

			String urlString="/data/ossImgTemp";

			String urlString2=topicid+l+tt;

			InputStream stream=	file.getInputStream();

			picUrl=saveimage.save(urlString, urlString2, stream,"topic");

			}

			} catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

			}

		ModelAndView modelview = new ModelAndView();

		modelview.addObject("rescreateTopicByGroup", updateTopicByGroup(topicid, title, content, picUrl));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);

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
	 * 关联群组 展示页
	 */
	@RequestMapping("/updateTopicForm")
	public ModelAndView updateTopicForm(HttpServletRequest request) {

		// 当前第几页
		String topicid = request.getParameter("topicid");

		ModelAndView modelview = new ModelAndView();

		try {
			JSONObject objj3 = getOneTopic(topicid);
			modelview.addObject("resuserTopic", objj3);
			modelview.addObject("groupList", groupList("0", "100"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("topic/bindTopicByGroupForm");
		return modelview;
	}

	/**
	 * 
	 * 根据话题id删除主楼回复
	 */
	@RequestMapping("/deletePostByTopicId")
	public ModelAndView deletePostByTopicId(HttpServletRequest request) {

		// 当前第几页
		String topicid = request.getParameter("topicid");
		String postid = request.getParameter("postid");
		ModelAndView modelview = new ModelAndView();
			 
		modelview.addObject("resuserTopic", deletePost(topicid, postid));
		modelview.addObject("topicDetail", getOneTopic(topicid));

		modelview.addObject("resTopicPost", findPost(topicid));

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("topic/topicDetail");
		return modelview;
	}

	/**
	 * 
	 * 根据主楼id删除副楼回复
	 */
	@RequestMapping("/deleteSubPostByTopicId")
	public ModelAndView deleteSubPostByTopicId(HttpServletRequest request) {
		ModelAndView modelview = new ModelAndView();
		// 当前第几页
		String postid = request.getParameter("postid");
		String topicid = request.getParameter("topicid");
		String subpostid = request.getParameter("subpostid");
		String index = request.getParameter("index");
		if(subpostid==null){
			subpostid="";
		}
		 
			modelview.addObject("subpostList", deleteSubPost(postid, subpostid, index));
			modelview.addObject("topicDetail", getOneTopic(topicid));

			modelview.addObject("resTopicPost", findPost(topicid));


		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("topic/topicDetail");
		return modelview;
	}

	/**
	 * 
	 * 根据话题id添加主楼回复 展示页
	 */
	@RequestMapping("/addPostByTopicIdForm")
	public ModelAndView addPostByTopicIdForm(HttpServletRequest request) {

		// 当前第几页
		String topicid = request.getParameter("topicid");

		ModelAndView modelview = new ModelAndView();

		modelview.addObject("topicid", topicid);
		modelview.addObject("robots", findRoboit("0", "100"));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("topic/addpost");
		return modelview;
	}

	/**
	 * 
	 * 根据话题id添加主楼回复
	 */
	@RequestMapping("/addPostByTopicIdAction")
	public ModelAndView addPostByTopicIdAction(HttpServletRequest request) {

		// 当前第几页
		String topicid = request.getParameter("topicid");
		String uid = request.getParameter("uid");
		String appKey = request.getParameter("appKey");
		String type = request.getParameter("type");
		String message = request.getParameter("message");
		String fileUrl = request.getParameter("fileUrl");

		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("addpost", addPost(uid, message, topicid, appKey, type, fileUrl));
		modelview.addObject("topicDetail", getOneTopic(topicid));

		modelview.addObject("resTopicPost", findPost(topicid));
		modelview.setViewName("topic/topicDetail");
		return modelview;
	}
	
	
	
	
	/**
	 * 
	 * 添加副楼回复 展示页
	 */
	@RequestMapping("/addSubPostForm")
	public ModelAndView addSubPostForm(HttpServletRequest request) {

		// 当前第几页
		String topicid = request.getParameter("topicid");
		String postid = request.getParameter("postid");
		
		
		ModelAndView modelview = new ModelAndView();
		
		modelview.addObject("topicid", topicid);
		modelview.addObject("postid", postid);
		modelview.addObject("robots", findRoboit("0", "100"));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("topic/addsubpost");
		return modelview;
	}

	/**
	 * 
	 * 添加副楼回复
	 */
	@RequestMapping("/addSubPostAction")
	public ModelAndView addSubPostAction(HttpServletRequest request) {

		// 当前第几页
		String topicid = request.getParameter("topicid");
		String parentid = request.getParameter("postid");
		String uid = request.getParameter("uid");
		String appKey="yxtapp";
		String type = request.getParameter("type");
		String message = request.getParameter("message");
		String fileUrl = request.getParameter("fileUrl");

		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("addpost", addSubPost(uid, message, topicid, appKey, type, fileUrl,parentid));
		modelview.addObject("topicDetail", getOneTopic(topicid));

		modelview.addObject("resTopicPost", findPost(topicid));
		modelview.setViewName("topic/topicDetail");
		return modelview;
	}
	
	/**
	 * 
	 * 添加排行榜  展示页
	 */
	@RequestMapping("/addTopicBoxForm")
	public ModelAndView addDryBoxForm(HttpServletRequest request) {

		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);

		modelview.setViewName("topic/createtopicBoxForm");
		return modelview;
	}
	
	
	
	/**
	 * 
	 * 添加排行榜  
	 */
	@RequestMapping("/addTopicBoxAction")
	public String addTopicBoxAction(HttpServletRequest request) {
		String chinaName = request.getParameter("chinaName");
		String englishName = request.getParameter("englishName");
		String local = request.getParameter("local");
		String size = request.getParameter("size");
		String type = "topic";

		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		
		modelview.addObject("addDryBox", addDryBox(chinaName, englishName, local, type,size));
		return "redirect:/topic/topicList";
	}
	
	
	/**
	 * 
	 * 话题排行榜列表
	 */
	@RequestMapping("/BoxTopicList")
	public ModelAndView BoxDryList(HttpServletRequest request) {
		String type = "topic";
		 
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		
		modelview.addObject("addDryBoxposition", dryboxpost(type));
		
		modelview.setViewName("topic/topicBoxPostList");
		return modelview;
	}
	
	
	
	/**
	 * 
	 * 话题排行榜删除
	 */
	@RequestMapping("/BoxTopicListDelete")
	public String  BoxDryListDelete(HttpServletRequest request) {
		String boxId = request.getParameter("boxId");
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("addDryBoxList", deleteBoxPost(boxId));
		
		return "redirect:/topic/BoxTopicList";
	}
	
	
	
	/**
	 * 
	 * 排行榜未绑定列表详情页 初始页  话题
	 */
	@RequestMapping("/TopicBoxDetail")
	public ModelAndView DryBoxDetail(HttpServletRequest request) {
		 
		String type = "topic";
		
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
		
		modelview.addObject("addDryBoxposition", objj);
		modelview.addObject("name", objj6);
		modelview.addObject("id", objj5);
		modelview.addObject("addDryBoxList", getdryboxlist(objj5,"0","10"));
		modelview.setViewName("topic/topicBoxList");
		return modelview;
	}
	
	
	
	/**
	 * 
	 * 查询未关联排行版的话题列表
	 */
	@RequestMapping("/TopicBoxList")
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
		
		String type = "topic";
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		 
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		
		modelview.addObject("addDryBoxposition", dryboxpost(type));
		modelview.addObject("name", name);
		modelview.addObject("id", id);
		modelview.addObject("addDryBoxList", getdryboxlist( id,pagenumber,pagelines));
		modelview.setViewName("topic/topicBoxList");
		return modelview;
	}
	
	
	/**
	 * 
	 * 话题关联到具体的排行榜
	 */
	@RequestMapping("/bindBoxTopic")
	public ModelAndView bindBoxDry(HttpServletRequest request) {
		String type = "topic";
		String sourceType = "topic";
		String name = request.getParameter("name");
		//位置id
		String boxPostId = request.getParameter("boxPostId");
		//干货id
		String sourceId = request.getParameter("sourceId");
		 
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		
	 
		modelview.addObject("addDryBoxList", bindBoxDry(boxPostId, sourceType, sourceId));
		
		modelview.addObject("addDryBoxposition", dryboxpost(type));
		modelview.addObject("name", name);
		modelview.addObject("id", boxPostId);
		modelview.addObject("addDryBoxList", getdryboxlist( boxPostId,"0","10"));
		
		modelview.setViewName("topic/topicBoxList");
		return modelview;
	}
	
	
	
	
	/**
	 * 
	 * 排行榜已绑定列表详情页 初始页  干货
	 */
	@RequestMapping("/TopicBoxBindedDetail")
	public ModelAndView DryBoxBindedDetail(HttpServletRequest request) {
		 
		String type = "topic";
		
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
		
		modelview.addObject("addDryBoxposition", objj);
		modelview.addObject("name", objj6);
		modelview.addObject("id", objj5);
		modelview.addObject("addDryBoxList", drycargoInBox(objj5,"0","10"));
		modelview.setViewName("topic/topicInBoxList");
		return modelview;
	}
	
	
	/**
	 * 
	 * 查询已关联排行版的干货列表
	 */
	@RequestMapping("/TopicInBoxList")
	public ModelAndView DryInBoxList(HttpServletRequest request) {
		
		String pagenumber = request.getParameter("n");

		if (pagenumber == null) {
			pagenumber = "0";
		}

		// 每页条数

		String pagelines = request.getParameter("s");

		if (pagelines == null) {
			pagelines = "10";
		}
		 
		String type = "topic";
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		 
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		
		modelview.addObject("addDryBoxposition", dryboxpost(type));
		modelview.addObject("name", name);
		modelview.addObject("id", id);
		modelview.addObject("addDryBoxList", drycargoInBox(id,pagenumber,pagelines));
		modelview.setViewName("topic/topicInBoxList");
		return modelview;
	}
	
	
	/**
	 * 
	 * 干货取消关联到具体的排行榜
	 */
	@RequestMapping("/unbindBoxTopic")
	public ModelAndView unbindBoxDry(HttpServletRequest request) {
		String type = "topic";
		 
		String name = request.getParameter("name");
		//位置id
		String boxPostId = request.getParameter("boxPostId");
		//排行榜id
		String boxId = request.getParameter("boxId");
		 
		 
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		
	 
		modelview.addObject("addDryBoxList", deleteBox(boxId));
		
		modelview.addObject("addDryBoxposition", dryboxpost(type));
		modelview.addObject("name", name);
		modelview.addObject("id", boxPostId);
		modelview.addObject("addDryBoxList", drycargoInBox(boxPostId,"0","10"));
		
		modelview.setViewName("topic/topicInBoxList");
		return modelview;
	}
	
	private JSONObject deleteBox(String boxId) {
		String url = Config.YXTSERVER3 + "oss/box/deleteBox?boxId=" + boxId;
		return getRestApiData(url);
	}
	
	
	private JSONObject drycargoInBox(String boxPostId,String n,String s) {
		String url = Config.YXTSERVER3 + "oss/box/topicInBox?boxPostId=" + boxPostId+"&n="+n+"&s="+s;
		return getRestApiData(url);
	}
	
	
	private JSONObject bindBoxDry(String boxPostId,String sourceType,String sourceId) {
		String url = Config.YXTSERVER3 + "oss/box/addBoxInBoxPost?boxPostId=" + boxPostId+"&sourceType="+sourceType+"&sourceId="+sourceId;
		return getRestApiData(url);
	}
	
	
	private JSONObject getdryboxlist(String boxPostId,String n,String s) {
		String url = Config.YXTSERVER3 + "oss/box/topicListNotInBoxPost?boxPostId="+boxPostId+"&n="+n+"&s="+s;
		return getRestApiData(url);
	}
	
	private JSONObject deleteBoxPost(String boxId) {
		String url = Config.YXTSERVER3 + "oss/box/deleteBoxPost?id=" + boxId;
		return getRestApiData(url);
	}
	
	private JSONObject dryboxpost(String type) {
		String url = Config.YXTSERVER3 + "oss/box/getBoxPostByType?type=" + type;
		return getRestApiData(url);
	}

	private JSONObject topicList(String keyword, String n, String s) {
		String url;
		if (keyword == null) {
			url = Config.YXTSERVER3 + "oss/topic/search?n=" + n + "&s=" + s;
		} else {
			url = Config.YXTSERVER3 + "oss/topic/search?keyword=" + keyword + "&n=" + n + "&s=" + s;
		}

		return getRestApiData(url);
	}
	
	private JSONObject addDryBox(String chinaName,String englishName,String local,String type,String size) {
		String url = Config.YXTSERVER3 + "oss/box/addBoxPost?chinaName=" + chinaName+"&englishName="+englishName+"&local="+local+"&type="+type+"&size="+size;
		return getRestApiData(url);
	}
	

	private JSONObject deleteTopic(String topicid) {
		String url = Config.YXTSERVER3 + "oss/topic/delete?topicid=" + topicid;
		return getRestApiData(url);
	}
	
	
	private JSONObject deletePost(String topicid,String postid) {
		String url = Config.YXTSERVER3 + "oss/topic/deletePost?topicid=" + topicid+"&postid="+postid;
		return getRestApiData(url);
	}
	
	
	private JSONObject deleteSubPost(String postid,String subpostid,String index) {
		String url = Config.YXTSERVER3 + "oss/topic/deleteSubPost?postid=" + postid+"&subpostid="+subpostid+"&index="+index;
		return getRestApiData(url);
	}

	private JSONObject findRoboit(String n, String s) {
		String url = Config.YXTSERVER3 + "oss/user/searchbyinfo?n=" + n + "&s=" + s + "&robot=1";
		return getRestApiData(url);
	}

	private JSONObject createTopic(String uid, String sourceId, String type, String title, String tagName, String content, String picUrl, String lat,
			String lng, String localName, String barCode) {
		// String url = Config.YXTSERVER3 + "oss/topic/create?uid=" + uid +
		// "&sourceId=" + sourceId + "&type=" + type + "&title=" + title +
		// "&tagName="
		// + tagName + "&content=" + content + "&picUrl=" + picUrl + "&lat=" +
		// lat + "&lng=" + lng + "&localName=" + localName + "&barCode="
		// + barCode;

		String url = Config.YXTSERVER3 + "oss/topic/create?uid=" + uid + "&sourceId=" + sourceId + "&type=" + type + "&title=" + title + "&content="
				+ content;
		return getRestApiData(url);
	}

	private JSONObject getOneTopic(String topicid) {
		String url = Config.YXTSERVER3 + "oss/topic/one?topicid=" + topicid;
		return getRestApiData(url);
	}

	private JSONObject findPost(String topicid) {
		String url = Config.YXTSERVER3 + "oss/topic/searchAllPostAndSubPost?topicid=" + topicid;
		return getRestApiData(url);
	}

	private JSONObject updateTopicByGroup(String topicid, String title, String content, String picUrl) {
		String url = Config.YXTSERVER3 + "oss/topic/updateTopicByGroup?topicId=" + topicid + "&title=" + title + "&content=" + content + "&picUrl="
				+ picUrl;
		return getRestApiData(url);
	}

	private JSONObject updateTopic(String topicid, String groupid) {
		String url = Config.YXTSERVER3 + "oss/topic/updateTopicByGroup?topicId=" + topicid + "&groupid=" + groupid;
		return getRestApiData(url);
	}

	private JSONObject addPost(String uid, String message,String topicId,String appKey,String type,String fileUrl) {
		String url = Config.YXTSERVER3 + "oss/topic/replyTopic?uid=" + uid + "&topicId=" + topicId + "&appKey=" + appKey + "&type=" + type
				+ "&message=" + message + "&fileUrl=" + fileUrl;
		return getRestApiData(url);
	}
	
	private JSONObject addSubPost(String uid, String message,String topicId,String appKey,String type,String fileUrl,String parentId) {
		String url = Config.YXTSERVER3 + "oss/topic/replyPost?uid=" + uid + "&topicId=" + topicId + "&appKey=" + appKey + "&type=" + type
				+ "&message=" + message + "&fileUrl=" + fileUrl+ "&parentId=" + parentId;
		return getRestApiData(url);
	}

}
