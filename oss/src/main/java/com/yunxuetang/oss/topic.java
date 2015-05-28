package com.yunxuetang.oss;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
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
@RequestMapping("/topic")
public class topic extends BaseController {
	Logger logger = LoggerFactory.getLogger(topic.class);
	@Autowired
	Saveimage saveimage;
	public topic() {
		// TODO Auto-generated constructor stub
	}

	// 删除话题
	@RequestMapping("/deleteTopic")
	public String deleteTopic(HttpServletRequest request) {
		ModelAndView modelview = new ModelAndView();
		String topicid = request.getParameter("topicid");
		logger.warn("======================================管理员-"+ request.getSession().getAttribute("name") + "删除话题, ID:" + topicid);
		modelview.addObject("deleteTopic", deleteTopicByID(topicid));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("topic/show");
		
		return "redirect:/topic/topicList";
	}

	// 删除话题For User
	@RequestMapping("/deleteTopicForUser")
	public String deleteTopicForUser(HttpServletRequest request) {
		String topicid = request.getParameter("topicid");
		String userid = request.getParameter("userid");
		logger.warn("=====================从用户管理里删除话题的管理员："+request.getSession().getAttribute("name")+"====用户id："+userid+"====话题id："+topicid);
		deleteTopicByID(topicid);
		return "redirect:/user/userTopic?userid=" + userid;
	}

	// 删除话题For Group
	@RequestMapping("/deleteTopicForGroup")
	public String deleteTopicForGroup(HttpServletRequest request) {
		String topicid = request.getParameter("topicid");
		String gid = request.getParameter("gid");
		logger.warn("======================================管理员-"+ request.getSession().getAttribute("name") + "删除话题, ID:" + gid + "=======群组id:" + gid);
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("topic/topicList");
		return modelview;
	}
	
	
	/**
	 * 
	 * 查找所有未审核话题 
	 */
	@RequestMapping("/noCheckTopicList")
	public ModelAndView noCheckTopicList(HttpServletRequest request) {
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

		modelview.addObject("topicList", nochecktopicList(keyword, pagenumber, pagelines));

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("topic/nochecktopicList");
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
			pagelines = "100";
		}

		ModelAndView modelview = new ModelAndView();

		modelview.addObject("robots", findRoboit(pagenumber, pagelines));
		modelview.addObject("groupList", groupList(pagenumber, pagelines));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("topic/createtopicForm");
		return modelview;
	}

	/**
	 * 
	 * 用机器人id创建话题
	 */
	@RequestMapping("/createTopicByGroup")
	public String createTopicByGroup(HttpServletRequest request, String[] courseImg, String[] picHeight, String[] picWidth) {
		// 必输
		String uid = request.getParameter("uid");
		String sourceId = request.getParameter("gid");
		String type = request.getParameter("type");
		if (type == null) {
			type = "0";
		}
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
		else{
			Pattern p = Pattern.compile("\\</br\\>");
			Matcher m =p.matcher(content);
			String strnew= m.replaceAll("/r/n");
		}
		String picUrl = "";
		if (courseImg != null && courseImg.length > 0) {
			picUrl = courseImg[0];
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
		//TODO
		//拼装JSON 调用REST接口
		String imgJson = "";
		if (courseImg != null && courseImg.length > 0) {//将图片转换为JSON
			ImgUrl[] imgUrls = new ImgUrl[courseImg.length];
			for(int i = 0;i<courseImg.length;i++){
				imgUrls[i] = new ImgUrl(courseImg[i], picHeight[i], picWidth[i]);
			}
			imgJson = JSONArray.fromObject(imgUrls).toString();
			
		}
		logger.warn("======================================管理员-"+ request.getSession().getAttribute("name") + "用机器人创建话题, 用户ID:" + uid + "=======话题名字:" + title);
		ModelAndView modelview = new ModelAndView();
		JSONObject result = createTopic(uid, sourceId, type, title, tagName, content, picUrl, imgJson, lat, lng, localName, barCode);
		System.out.println("result:==============" + result.toString());
		modelview.addObject("rescreateTopicByGroup", result);
		
		
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);

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
		JSONObject resuserTopic = getOneTopic(topicid);
		JSONObject ttt = resuserTopic.getJSONObject("data").getJSONObject("result");
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
		
		modelview.addObject("resuserTopic", resuserTopic);
		modelview.addObject("currCategory", currCategory);//群的当前分类信息
		modelview.addObject("currChildCategory", currChildCategory);
		modelview.addObject("categoryList", categoryList);//一级分类列表
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
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
		if("null".equals(title)){
			title="";
		}
		String content = request.getParameter("content");
		if("null".equals(content)){
			content="";
		}
		//String picUrl = request.getParameter("picUrl");
		String picUrl = request.getParameter("logoUrl");
		if(picUrl == null || "".equals(picUrl)){
			picUrl = request.getParameter("oldLogoUrl");
		}
		String height = request.getParameter("height");
		String width = request.getParameter("width");
		if("null".equals(height)||"".equals(height)){
			height="0";
		}
		if("null".equals(width)||"".equals(width)){
			width="0";
		}
		String categoryId = request.getParameter("categoryId");
		String childCategoryId = request.getParameter("childCategoryId");
		
		String tagName = request.getParameter("tagName");

		String tagNameArry[] = tagName.split(",");

		List<String> ll = new ArrayList<String>();

		for (String a : tagNameArry) {

		ll.add("\"" + a + "\"");

		}

		String i = ll.toString();
		
		
		if("null".equals(picUrl)){
			picUrl="";
		}
		logger.warn("======================================管理员-"+ request.getSession().getAttribute("name") + "更新话题, 话题ID:" + topicid + "=======标题:" + title);
		ModelAndView modelview = new ModelAndView();

		modelview.addObject("rescreateTopicByGroup", updateTopicByGroup(topicid, title, content, picUrl, categoryId, childCategoryId,tagName,height,width));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);

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
		logger.warn("======================================管理员-"+ request.getSession().getAttribute("name") + "关联话题-群组, 群组ID:" + groupid + "=======话题:" + topicid);
		ModelAndView modelview = new ModelAndView();

		modelview.addObject("rescreateTopicByGroup", updateTopic(topicid, groupid));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
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
		logger.warn("======================================管理员-"+ request.getSession().getAttribute("name") + "删除话题主楼回复, 话题ID:" + topicid + "=======主楼回复:" + postid);
		
		ModelAndView modelview = new ModelAndView();
			 
		modelview.addObject("resuserTopic", deletePost(topicid, postid));
		modelview.addObject("topicDetail", getOneTopic(topicid));

		modelview.addObject("resTopicPost", findPost(topicid));

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
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
		
		logger.warn("======================================管理员-"+ request.getSession().getAttribute("name") + "删除话题副楼回复, 话题ID:" + topicid + "=======主楼ID:" + postid + "======副楼id:" + subpostid);
		if(subpostid==null){
			subpostid="";
		}
		 
			modelview.addObject("subpostList", deleteSubPost(postid, subpostid, index));
			modelview.addObject("topicDetail", getOneTopic(topicid));

			modelview.addObject("resTopicPost", findPost(topicid));


		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("topic/addpost");
		return modelview;
	}

	/**
	 * 
	 * 根据话题id添加主楼回复
	 */
	@RequestMapping("/addPostByTopicIdAction")
	public ModelAndView addPostByTopicIdAction(HttpServletRequest request, String[] courseImg, String[] imgHeight, String[] imgWidth) {

		// 当前第几页
		String topicid = request.getParameter("topicid");
		String uid = request.getParameter("uid");
		String appKey = request.getParameter("appKey");
		String type = request.getParameter("type");
		String message = request.getParameter("message");
		String fileUrl = request.getParameter("fileUrl");
		logger.warn("======================================管理员-"+ request.getSession().getAttribute("name") + "添加话题主楼回复, 话题ID:" + topicid + "=======回复内容:" + message);
		//拼装JSON 调用REST接口
		String imgJson = "";
		if (courseImg != null && courseImg.length > 0) {// 将图片转换为JSON
			ImgUrl[] imgUrls = new ImgUrl[courseImg.length];
			for (int i = 0; i < courseImg.length; i++) {
				imgUrls[i] = new ImgUrl(courseImg[i], imgHeight[i], imgWidth[i]);
			}
			imgJson = JSONArray.fromObject(imgUrls).toString();

		}
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("topicId", topicid);
		params.put("appKey", appKey);
		params.put("type", type);
		params.put("uid", uid);
		params.put("message", message);
		params.put("fileUrl", fileUrl);
		params.put("imgJson", imgJson);
		
//		String url = Config.YXTSERVER3 + "oss/topic/replyTopic?uid=" + uid + "&topicId=" + topicId + "&appKey=" + appKey + "&type=" + type
//				+ "&message=" + message + "&fileUrl=" + fileUrl;
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.addObject("addpost", addPost(params));
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
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

		logger.warn("======================================管理员-"+ request.getSession().getAttribute("name") + "添加话题副楼回复, 话题ID:" + topicid + "=======主楼:" + parentid
				+ "===========回复内容:" + message);
		
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);

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
		logger.warn("======================================管理员-"+ request.getSession().getAttribute("name") + "添加话题排行榜, name:" + chinaName);
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		
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
		logger.warn("======================================管理员-"+ request.getSession().getAttribute("name") + "删除话题排行榜, ID:" + boxId);
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		
		modelview.addObject("addDryBoxposition", dryboxpost(type));
		modelview.addObject("name", name);
		modelview.addObject("id", id);
		modelview.addObject("addDryBoxList", getdryboxlist( id,pagenumber,pagelines));
		modelview.setViewName("topic/topicBoxList");
		return modelview;
	}
	
	
	/**
	 * 
	 * 查询未关联排行版的话题列表
	 */
	@RequestMapping("/TopicBoxListBySearch")
	public ModelAndView TopicBoxListBySearch(HttpServletRequest request) {
		 
		String keyword = request.getParameter("keyword");

		 
		
		String type = "topic";
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
		modelview.addObject("addDryBoxList", getdryboxlistBySearch(id, keyword));
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		
	 
		modelview.addObject("addDryBoxList", deleteBox(boxId));
		
		modelview.addObject("addDryBoxposition", dryboxpost(type));
		modelview.addObject("name", name);
		modelview.addObject("id", boxPostId);
		modelview.addObject("addDryBoxList", drycargoInBox(boxPostId,"0","10"));
		
		modelview.setViewName("topic/topicInBoxList");
		return modelview;
	}
	
	
	
	
	/**
	 * 
	 * 话题审核
	 */
	@RequestMapping("/checkTopic")
	public String checkTopic(HttpServletRequest request) {
		 
		String topicid = request.getParameter("topicid");
		//排行榜id
		String checked = "true";
		logger.warn("======================================管理员-"+ request.getSession().getAttribute("name") + "审核话题, ID:" + topicid); 
		 
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
	 
		modelview.addObject("addDryBoxList", checkTopic(topicid,checked));
		
		return "redirect:/topic/topicList";
	}
	
	private JSONObject checkTopic(String tid,String checked) {
		String url = Config.YXTSERVER3 + "oss/topic/checkTopic?tid=" + tid+"&checked="+checked;
		return getRestApiData(url);
	}
	
	private JSONObject deleteBox(String boxId) {
		String url = Config.YXTSERVER3 + "oss/box/deleteBox?boxId=" + boxId;
		return getRestApiData(url);
	}
	
	
	private JSONObject drycargoInBox(String boxPostId,String n,String s) {
		String url = Config.YXTSERVER3 + "oss/box/topicInBox?boxPostId=" + boxPostId+"&n="+n+"&s="+s;
		return getRestApiData(url);
	}
	
	
	private JSONObject bindBoxDry(String boxPostId,String sourceType,String sourceId,String ctime) {
		String url = Config.YXTSERVER3 + "oss/box/addBoxInBoxPost?boxPostId=" + boxPostId+"&sourceType="+sourceType+"&sourceId="+sourceId+"&ctime="+ctime;
		return getRestApiData(url);
	}
	
	
	private JSONObject getdryboxlist(String boxPostId,String n,String s) {
		String url = Config.YXTSERVER3 + "oss/box/topicListNotInBoxPost?boxPostId="+boxPostId+"&n="+n+"&s="+s;
		return getRestApiData(url);
	}
	
	private JSONObject getdryboxlistBySearch(String boxPostId,String keyword) {
		String url = Config.YXTSERVER3 + "oss/box/searchtopicListNotInBoxPost?boxPostId="+boxPostId+"&keyword="+keyword;
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
	
	
	private JSONObject nochecktopicList(String keyword, String n, String s) {
		String url;
		if (keyword == null) {
			url = Config.YXTSERVER3 + "oss/topic/searchNoCheckTopic?n=" + n + "&s=" + s;
		} else {
			url = Config.YXTSERVER3 + "oss/topic/searchNoCheckTopic?keyword=" + keyword + "&n=" + n + "&s=" + s;
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

	private JSONObject createTopic(String uid, String sourceId, String type, String title, String tagName, String content, String picUrl, String imgJson, String lat,
			String lng, String localName, String barCode) {
		// String url = Config.YXTSERVER3 + "oss/topic/create?uid=" + uid +
		// "&sourceId=" + sourceId + "&type=" + type + "&title=" + title +
		// "&tagName="
		// + tagName + "&content=" + content + "&picUrl=" + picUrl + "&lat=" +
		// lat + "&lng=" + lng + "&localName=" + localName + "&barCode="
		// + barCode;
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("uid", uid);
		params.put("sourceId", sourceId);
		params.put("type", type);
		params.put("title", title);
		params.put("content", content);
		params.put("appKey", "yxtapp");
		if(!StringUtils.isEmpty(imgJson)){
			params.put("image", imgJson);
		}
//		String url = Config.YXTSERVER3 + "oss/topic/create?uid=" + uid + "&sourceId=" + sourceId + "&type=" + type + "&title=" + title + "&content="
//				+ content+ "&appKey=yxtapp";
		return getRestApiData(Config.YXTSERVER3 + "oss/topic/create", params);
	}

	private JSONObject getOneTopic(String topicid) {
		String url = Config.YXTSERVER3 + "oss/topic/one?topicid=" + topicid;
		return getRestApiData(url);
	}

	private JSONObject findPost(String topicid) {
		String url = Config.YXTSERVER3 + "oss/topic/searchAllPostAndSubPost?topicid=" + topicid;
		return getRestApiData(url);
	}

	private JSONObject updateTopicByGroup(String topicid, String title, String content, String picUrl, String categoryId, String childCategoryId,
			String tagName, String height, String width) {
//		String url = Config.YXTSERVER3 + "oss/topic/updateTopicByGroup?topicId=" + topicid + "&title=" + title + "&content=" + content + "&picUrl="
//				+ picUrl + "&categoryId=" + categoryId + "&childCategoryId=" + childCategoryId + "&tagNames=" + tagName + "&picHeight=" + height
//				+ "&picWidth=" + width;
		
		
		String url = Config.YXTSERVER3 + "oss/topic/updateTopic";
		
		Map<String, String> m=new HashMap<String, String>();
		m.put("topicId", topicid);
		m.put("title", title);
		m.put("content",content );
		m.put("picUrl", picUrl);
		m.put("categoryId", categoryId);
		m.put("childCategoryId", childCategoryId);
		m.put("tagNames", tagName);
		m.put("picHeight", height);
		m.put("picWidth", width);
		return getRestApiData(url,m);
	}

	private JSONObject updateTopic(String topicid, String groupid) {
		String url = Config.YXTSERVER3 + "oss/topic/updateTopicByGroup?topicId=" + topicid + "&groupid=" + groupid;
		return getRestApiData(url);
	}

	private JSONObject addPost(HashMap<String, String> params) {
		JSONObject result = getRestApiData(Config.YXTSERVER3 + "oss/topic/replyTopic", params);
		return result;
		
	}
	
	private JSONObject addSubPost(String uid, String message,String topicId,String appKey,String type,String fileUrl,String parentId) {
		String url = Config.YXTSERVER3 + "oss/topic/replyPost?uid=" + uid + "&topicId=" + topicId + "&appKey=" + appKey + "&type=" + type
				+ "&message=" + message + "&fileUrl=" + fileUrl+ "&parentId=" + parentId;
		return getRestApiData(url);
	}

	/**
	 * 
	 * @Title: getFileName
	 * @Description: 根据pathrule回去新的文件名
	 * @param name
	 * @param pathrule
	 * @return String
	 * @throws
	 */
	@RequestMapping("/getFileName")
	@ResponseBody
	public String getFileName(String name,String pathrule){
		SimpleDateFormat format=new SimpleDateFormat("yyyyMM");
		String time=format.format(new Date());
		String ext=name.substring(name.lastIndexOf(".")+1);
		String temp=pathrule.replace("{yyyymm}",time);
		String doc="pdf,doc,docx,xls,xlsx,ppt,pptx";
		String img="jpg,jpeg,gif,png";
		if(doc.indexOf(ext)!=-1){
			temp=temp.replace("video", "doc");
		}else if(img.indexOf(ext)!=-1){
			temp=temp.replace("video", "imgs");
		}
		UUID uuid=UUID.randomUUID();
		return temp+uuid.toString()+"."+ext;
	}
	
	public class ImgUrl{
		private String picUrl;
		
		private String picHeight;
		
		private String picWidth;
		
		public ImgUrl() {
			// TODO Auto-generated constructor stub
		}
		
		public ImgUrl(String picUrl, String picHeight, String picWidth){
			this.picUrl = picUrl;
			this.picHeight = picHeight;
			this.picWidth = picWidth;
		}

		public String getPicUrl() {
			return picUrl;
		}

		public void setPicUrl(String picUrl) {
			this.picUrl = picUrl;
		}

		public String getPicHeight() {
			return picHeight;
		}

		public void setPicHeight(String picHeight) {
			this.picHeight = picHeight;
		}

		public String getPicWidth() {
			return picWidth;
		}

		public void setPicWidth(String picWidth) {
			this.picWidth = picWidth;
		}

		
		
		
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
