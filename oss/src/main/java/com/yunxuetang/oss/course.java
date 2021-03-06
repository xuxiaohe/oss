package com.yunxuetang.oss;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.Config;
import com.yunxuetang.util.HttpUtil;

@Controller
@RequestMapping("/course")
public class course extends BaseController {
	Logger logger = LoggerFactory.getLogger(course.class);
	 

	public course() {

	}

	/**
	 * 
	 * 查询所有课程
	 */
	@RequestMapping("/courseList")
	public ModelAndView courseList(HttpServletRequest request) {
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

		modelview.addObject("courses", getCourses( n, s));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("course/courseList");
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
			s = "10";
		}
		ModelAndView modelview = new ModelAndView();

		modelview.addObject("courses", getCoursesNoShare( n, s));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("course/courseListNoShare");
		return modelview;
	}
	
	
	
	/**
	 * 
	 * 关联群组  展示页
	 */
	@RequestMapping("/updateCourseForm")
	public ModelAndView updateCourseForm(HttpServletRequest request) {

		// 当前第几页
				String cid = request.getParameter("cid");

				ModelAndView modelview = new ModelAndView();

				try {
					JSONObject objj3 = courseDetail(cid);
					modelview.addObject("resuserCourse", objj3);
					modelview.addObject("groupList", groupList("0","100"));
				} catch (Exception e) {
					e.printStackTrace();
				}

				String cpath = request.getContextPath();
				String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
				modelview.addObject("cbasePath", cbasePath);
				modelview.addObject("sourcePath", Config.YXTSERVER5);
				modelview.setViewName("course/bindCourseByGroupForm");
				return modelview;
	}
	
	
	/**
	 * 
	 * 搜索课程
	 */
	@RequestMapping("/searchCourse")
	public ModelAndView searchCourse(HttpServletRequest request) {
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
		String keyword = request.getParameter("keyword");
		ModelAndView modelview = new ModelAndView();

		modelview.addObject("courses", searchCourse(keyword,n,s));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("course/courseList");
		return modelview;
	}
	
	
	
	/**
	 * 
	 * 搜索课程 未分配群组
	 */
	@RequestMapping("/searchCourseNoShare")
	public ModelAndView searchCourseNoShare(HttpServletRequest request) {
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
		String keyword = request.getParameter("keyword");
		ModelAndView modelview = new ModelAndView();

		modelview.addObject("courses", searchCourse(keyword,n,s));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("course/courseListNoShare");
		return modelview;
	}
	
	
	/**
	 * 
	 * 课程详情
	 */
	@RequestMapping("/courseDetail")
	public ModelAndView courseDetail(HttpServletRequest request) {
		String cid = request.getParameter("cid");
		ModelAndView modelview = new ModelAndView();

		modelview.addObject("courseDetail", courseDetail(cid));
		
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("course/courseDetail");
		return modelview;
	}
	
	@RequestMapping("/updateView")
	public ModelAndView updateCourseView(HttpServletRequest request){
		String cid = request.getParameter("cid");
		ModelAndView modelview = new ModelAndView();
		
		JSONObject course = courseDetail(cid);
		JSONObject ttt = course.getJSONObject("data").getJSONObject("result");
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
		
		modelview.addObject("courseDetail", course);
		modelview.addObject("currCategory", currCategory);//群的当前分类信息
		modelview.addObject("currChildCategory", currChildCategory);
		modelview.addObject("categoryList", categoryList);//一级分类列表
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("course/updateCourseForm");
		return modelview;
	}
	
	@RequestMapping("/updateChapterView")
	public ModelAndView updateChapterView(HttpServletRequest request){
		String cid = request.getParameter("cid");
		ModelAndView modelview = new ModelAndView();
		JSONObject course = courseDetail(cid);
		modelview.addObject("chapters", course.getJSONObject("data").getJSONObject("result").getJSONArray("chapters"));
		modelview.addObject("cid", cid);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("course/updateChapterForm");
		return modelview;
	}
	/**
	 * 更新章节
	 * @Title: updateChapter
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param request
	 * @return String
	 * @throws
	 */
	@RequestMapping("/updateChapter")
	public @ResponseBody String updateChapter(HttpServletRequest request){
		String id = request.getParameter("id");
		String title = request.getParameter("title");
		String order = request.getParameter("order");
		logger.info(request.getSession().getAttribute("name")+"更新课程章节操作的管理员 "+request.getSession().getAttribute("name")+"===章节id"+id);
		String url = Config.YXTSERVER3 + "oss/course/modifyChapter";
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("id", id);
		params.put("title", title);
		params.put("order", order);
		return HttpUtil.sendPost(url, params);
		
	}
	
	@RequestMapping("/updateLesson")
	public @ResponseBody String updateLesson(HttpServletRequest request){
		String id = request.getParameter("id");
		String title = request.getParameter("title");
		String order = request.getParameter("order");
		String isbuy = request.getParameter("isbuy");
		logger.info(request.getSession().getAttribute("name")+"更新课程课时操作的管理员 "+request.getSession().getAttribute("name")+"===课时id"+id);
		String url = Config.YXTSERVER3 + "oss/course/modifyLesson";
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("id", id);
		params.put("title", title);
		params.put("order", order);
		params.put("isbuy", isbuy);
		return HttpUtil.sendPost(url, params);
		
	}
	
	
	/**
	 * 更新课程信息
	 * @param cid 课程ID
	 * @param categoryId 一级分类
	 * @param childCategoryId 二级分类
	 * @param title 标题
	 * @param intro 详情
	 * */
	@RequestMapping("/updateCourse")
	public String updateCourse(HttpServletRequest request){
		String cid = request.getParameter("cid");
		String title = request.getParameter("title");
		String intro = request.getParameter("intro");
		if(!("".equals(intro))||intro!=null){
			intro=intro.replace("，", ",");
		}
		String categoryId = request.getParameter("categoryId");
		String childCategoryId = request.getParameter("childCategoryId");
		String tagNames = request.getParameter("tagNames");
		String logoUrl = request.getParameter("logoUrl");
		String pricemodel = request.getParameter("pricemodel");
		String price = request.getParameter("price");
		String scale = request.getParameter("scale");
		String height = request.getParameter("height");
		String width = request.getParameter("width");
		
		if(scale==null||"".equals(scale)){
			scale="0";
		}
		if(price==null||"".equals(price)){
			price="0";
		}
		if(logoUrl == null || "".equals(logoUrl)){
			logoUrl = request.getParameter("oldLogoUrl");
		}
		
		
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		
		ModelAndView modelview = new ModelAndView();
		logger.info(request.getSession().getAttribute("name")+"更新课程基本信息操作的管理员 "+request.getSession().getAttribute("name")+"===课程id"+cid);

		modelview.addObject("updateResult", modifyCourse(cid, title, intro, categoryId, childCategoryId, tagNames, logoUrl,pricemodel,price,scale,height,width));
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		return "redirect:/course/courseList";
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
		logger.info(request.getSession().getAttribute("name")+"分享课程到群组操作的管理员 "+request.getSession().getAttribute("name")+"===群组id"+groupId+"===课程id"+courseId);
		modelview.addObject("shareToMyGroup", shareToMyGroup(groupId,courseId,appKey));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		return  "redirect:/course/courseList";
	}
	
	
	
	/**
	 * 
	 * 课程从群组删除
	 */
	@RequestMapping("/deleteToMyGroup")
	public String deleteToMyGroup(HttpServletRequest request) {
		String groupId = request.getParameter("groupId");
		String courseId = request.getParameter("courseId");
		 String course ="[\""+courseId+"\"]";
		 
		ModelAndView modelview = new ModelAndView();
		logger.info(request.getSession().getAttribute("name")+"课程从群组删除操作的管理员 "+request.getSession().getAttribute("name")+"===群组id"+groupId+"===课程id"+course);
		modelview.addObject("deleteToMyGroup", deleteGroupCourses(groupId,course));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		return "redirect:/group/groupCourse?gid="+groupId;
	}
	
	
	/**
	 * 
	 * 课程从课程池删除
	 */
	@RequestMapping("/deleteGroup")
	public String deleteGroup(HttpServletRequest request) {
		 
		String courseId = request.getParameter("courseId");
		 
		 
		ModelAndView modelview = new ModelAndView();
		logger.info(request.getSession().getAttribute("name")+"课程从课程池删除操作的管理员 "+request.getSession().getAttribute("name")+"===课程id"+courseId);
		modelview.addObject("deleteToMyGroup", deleteCourses(courseId));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		return "redirect:/course/courseList";
	}
	
	
	
	/**
	 * 课程未审核列表
	 * 
	 */
	@RequestMapping("/courseListNoCheck")
	public ModelAndView courseListNoCheck(HttpServletRequest request) {
		 
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

		modelview.addObject("courses", getNotCheckCourses( n, s));
		 

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("course/checkcourseList");
		return modelview;
	}
	
	
	/**
	 * 课程审核展示页  选择一级分类
	 * 
	 */
	@RequestMapping("/checkCourseOneForm")
	public ModelAndView checkCourseForm(HttpServletRequest request) {
		 
		String cid = request.getParameter("cid");
		 
		ModelAndView modelview = new ModelAndView();

		modelview.addObject("courseDetail", courseDetail(cid));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("course/checkCourseOneForm");
		return modelview;
	}
	
	
	/**
	 * 课程审核展示页  选择二级分类
	 * 
	 */
	@RequestMapping("/checkCourseSecondForm")
	public ModelAndView checkCourseSecondForm(HttpServletRequest request) {
		 
		String parentId = request.getParameter("parentId");
		
		String cid = request.getParameter("cid");
		
		ModelAndView modelview = new ModelAndView();

		modelview.addObject("categorySecondList", categorySecondList("10",parentId));

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.addObject("cid", cid);
		modelview.addObject("parentId", parentId);
		
		modelview.setViewName("course/checkCourseSecondForm");
		return modelview;
	}
	
	
	
	/**
	 * 提交课程审核
	 * 
	 */
	@RequestMapping("/checkCourseAction")
	public String  checkCourseAction(HttpServletRequest request) {
		 
		String categoryId= request.getParameter("categoryId");
		 
		String courseId = request.getParameter("cid");
		
		String childCategoryId = request.getParameter("childCategoryId");
		
		ModelAndView modelview = new ModelAndView();
		logger.info(request.getSession().getAttribute("name")+"提交课程审核操作的管理员 "+request.getSession().getAttribute("name")+"===课程id"+courseId);

		modelview.addObject("categorySecondList", categoryAction(categoryId, childCategoryId, courseId));

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		
		return "redirect:/course/courseListNoCheck";
	}
	
	
	/**
	 * 课程创建展示页
	 * 
	 */
	@RequestMapping("/createcourseview")
	public ModelAndView  createcourseview(HttpServletRequest request) {
		
		ModelAndView modelview = new ModelAndView();
		
		/**
		 * 获取一级分类列表
		 * */
		JSONObject category = categoryList();
		JSONArray categoryList = category.getJSONObject("data").getJSONArray("result");
		modelview.addObject("categoryList", categoryList);//一级分类列表

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.addObject("serverPath", Config.YXTSERVER3);
		Map<String, String> map=new HashMap<String, String>();
		map.put("title", "运维");
		map.put("intro", "运维");
		JSONObject jsonObject=createCourse(map);
		JSONObject data=JSONObject.fromObject(jsonObject.get("data"));
		JSONObject result=JSONObject.fromObject(data.get("result"));
		modelview.addObject("courseId",result.get("id") );
		modelview.addObject("robots", findRoboit("1", "10"));
		modelview.setViewName("course/createcourse");
		return modelview;
	}
	
	/**
	 * 课程创建展示页
	 * 
	 */
	@RequestMapping("/createcourseaction")
	public ModelAndView  createcourseaction(HttpServletRequest request) {
		
		String cname= request.getParameter("cname");
		 
		String sumchapter = request.getParameter("sumchapter");

		String sumkeshi = request.getParameter("sumkeshi");
		String	zhangjie[]=null;
		if(sumchapter!=null){
			int i=Integer.parseInt(sumchapter);
			if(i!=0&&i>0){
					zhangjie=new String[i];
				for(int p=0;p<i;p++){
					int u=p+1;
					zhangjie[p] = request.getParameter("zhangjie"+u);
				}
			}
		}
		
		if(sumkeshi!=null){
			int j=Integer.parseInt(sumkeshi);
		}
		
		MultiValueMap<String, Object> requestParams=new LinkedMultiValueMap();
			
		List l=new ArrayList();
		l.add(zhangjie);
		 
		requestParams.put("datas", l);
		
		ModelAndView modelview = new ModelAndView();
		createcourse(requestParams);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.addObject("serverPath", Config.YXTSERVER3);
		modelview.setViewName("course/createcourse");
		return modelview;
	}
	
	/**
	 * 
	 * 选择课程模态框
	 */
	@RequestMapping("/selectcourse")
	public ModelAndView selectCourse(HttpServletRequest request) {
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
		String keyword = request.getParameter("keyword");
		ModelAndView modelview = new ModelAndView();

		modelview.addObject("courseList", searchCourse(keyword, n, s));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("course/selectCourse");
		return modelview;
	}
	
	
	//创建课程Api
	private JSONObject createCourse(Map<String, String> map){
		String url = Config.YXTSERVER3 + "oss/course/createCourse";
		return getRestApiData(url,map);
	}
	private JSONObject createcourse(MultiValueMap<String, Object> requestParams) {
		String url = Config.YXTSERVER3 + "oss/course/courses";
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
	
	private JSONObject categoryAction(String categoryId,String childCategoryId,String courseId) {
		String url = Config.YXTSERVER3 + "oss/course/courseChecked?courseId="+courseId;
		return getRestApiData(url);
	}
	
	
	private JSONObject shareToMyGroup(String groupId,String courseId,String appKey) {
		String url = Config.YXTSERVER3 + "oss/course/shareToMyGroup?groupId="+groupId+"&courseId="+courseId+"&appKey="+appKey;
		return getRestApiData(url);
	}
	
	
	private JSONObject deleteGroupCourses(String groupId,String courseId) {
		String url = Config.YXTSERVER3 + "oss/course/deleteGroupCourses?groupId="+groupId+"&groupCourseId="+courseId;
		return getRestApiData(url);
	}
	
	private JSONObject deleteCourses(String courseId) {
		String url = Config.YXTSERVER3 + "oss/course/deleteCourse?cid="+courseId;
		return getRestApiData(url);
	}
	
	private JSONObject modifyCourse(String cid, String title, String intro, String categoryId, String childCategoryId, String tagNames, String logoUrl,String pricemodel,String price,String scale,String height,String width){
		String url = Config.YXTSERVER3 + "oss/course/modifyBaseInfo";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", cid);
		params.put("title", title);
		params.put("intro", intro);
		params.put("categoryId", categoryId);
		params.put("childCategoryId", childCategoryId);
		params.put("tagNames", tagNames);
		params.put("logoUrl", logoUrl);
		params.put("pricemodel", pricemodel);
		params.put("price", price);
		params.put("scale", scale);
		params.put("height", height);
		params.put("width", width);
		return getRestApiData(url, params);
	}
	
	
	private JSONObject searchCourse(String keyword,String n,String s) {
		String url = Config.YXTSERVER3 + "oss/course/search";
		HashMap<String, String> param = new HashMap<String, String>();
		if(keyword != null) param.put("keywords", keyword);
		if(n != null) param.put("n", n);
		if(s != null) param.put("s", s);
		return getRestApiData(url, param);
	}
	
	private JSONObject getCourses(String n,String s) {
		String url = Config.YXTSERVER3 + "oss/course/courses?n="+n+"&s="+s;
		return getRestApiData(url);
	}
	
	private JSONObject getNotCheckCourses(String n,String s) {
		String url = Config.YXTSERVER3 + "oss/course/getNotCheckedCourse?n="+n+"&s="+s;
		return getRestApiData(url);
	}
	
	private JSONObject getCoursesNoShare(String n,String s) {
		String url = Config.YXTSERVER3 + "oss/course/coursesNoShare?n="+n+"&s="+s;
		return getRestApiData(url);
	}
	
	
	private JSONObject courseDetail(String cid) {
		String url = Config.YXTSERVER3 + "oss/course/oneCourse?courseId="+cid;
		return getRestApiData(url);
	}
	
	/**
	 * 保存章节
	 * 
	 */
	@RequestMapping("/saveChapter")
	@ResponseBody
	public Map<String, Object>  saveChapter(HttpServletRequest request,String title,String order) {
		String[] ids=request.getParameterValues("lessonIds[]");
		String lessonIds=""; 
		if(ids.length>0){
			
			for (String string : ids) {
				lessonIds+=string+",";
			}
		}
		Map<String, String> map=new HashMap<String, String>();
		if(StringUtil.isBlank(title)){
			map.put("title", "运维");
		}else{
			map.put("title", title);
		}
		map.put("order", order);
		map.put("lessonIds", lessonIds);
		logger.info(request.getSession().getAttribute("name")+"保存章节操作的管理员 "+request.getSession().getAttribute("name")+"===章节id"+ids);
		JSONObject json=createChapter(map);
		JSONObject data=JSONObject.fromObject(json.get("data"));
		JSONObject result=JSONObject.fromObject(data.get("result"));
		Map<String, Object> reMap=new HashMap<String, Object>();
		reMap.put("chapterId", result.get("id"));
		return reMap;
	}
	private JSONObject createChapter(Map<String, String> map) {
		String url = Config.YXTSERVER3 + "oss/course/createChapter";
		return getRestApiData(url,map);
	}
	/**
	 * 保存章节
	 * 
	 */
	@RequestMapping("/modifyCourse")
	@ResponseBody
	public Map<String, Object> modifyCourse(HttpServletRequest request, String title, String logoUrl, String intro, String tagNames, String courseId,
			String userId, String pricemodel, String price, String childCategoryId, String categoryId, String userLogo, String createUserName, String scale,String height, String width) {
	String[] chapterIds=request.getParameterValues("chapterIds[]");
		Map<String, String> map=new HashMap<String, String>();
		if(StringUtil.isBlank(title)){
			map.put("title", "运维");
		}else{
			map.put("title", title);
		}
		map.put("logoUrl", logoUrl);
		map.put("intro", intro);
		map.put("tagNames", tagNames.replaceAll("，", ","));
		map.put("isPublic", "0");
		map.put("sourceType", "3");
		map.put("createUser", userId);
		map.put("pricemodel", pricemodel);
		map.put("price", price);
		map.put("childCategoryId", childCategoryId);
		map.put("categoryId", categoryId);
		map.put("userLogo", userLogo);
		map.put("createUserName", createUserName);
		map.put("scale", scale);
		map.put("height", height);
		map.put("width", width);
		String  ids="";
		if(chapterIds!=null){
			if (chapterIds.length>0) {
				for (String string : chapterIds) {
					ids+=string+",";
				}
			}
		}

		map.put("chapterIds", ids);
		map.put("id", courseId);
		logger.info(request.getSession().getAttribute("name")+"更新课程操作的管理员 "+request.getSession().getAttribute("name")+"===课程id"+courseId);

		JSONObject json=modifyCourse(map);
		JSONObject data=JSONObject.fromObject(json.get("data"));
		JSONObject result=JSONObject.fromObject(data.get("result"));
		Map<String, Object> reMap=new HashMap<String, Object>();
		reMap.put("courseId", result.get("id"));
		return reMap;
	}
	private JSONObject modifyCourse(Map<String, String> map) {
		String url = Config.YXTSERVER3 + "oss/course/modifyCourse";
		return getRestApiData(url,map);
	}
	
	@RequestMapping("/createLesson")
	@ResponseBody
	public Map<String, Object>  createLesson(HttpServletRequest request,String title,String knowledgeId,String userId,String order) {
		Map<String, String> map=new HashMap<String, String>();
		if(StringUtil.isBlank(title)){
			map.put("title", "运维");
		}else{
			map.put("title", title);
		}
		map.put("knowledgeId", knowledgeId);
		map.put("order", "1");
		map.put("userId", userId);
		map.put("order", order);
		map.put("status", "3");
		logger.info(request.getSession().getAttribute("name")+"创建课时操作的管理员 "+request.getSession().getAttribute("name")+"===课时名称"+title);
		JSONObject json=createLesson(map);
		JSONObject data=JSONObject.fromObject(json.get("data"));
		JSONObject result=JSONObject.fromObject(data.get("result"));
		Map<String, Object> reMap=new HashMap<String, Object>();
		reMap.put("lessonId", result.get("id"));
		return reMap;
	}
	private JSONObject createLesson(Map<String, String> map) {
		String url = Config.YXTSERVER3 + "oss/course/createLesson";
		return getRestApiData(url,map);
	}
	private JSONObject findRoboit(String n, String s) {
		// String url = Config.YXTSERVER3 + "oss/user/searchbyinfo?n=" + n +
		// "&s=" + s + "&robot=1";
		String url = Config.YXTSERVER3 + "oss/user/searchbyinfo?n=" + n + "&s=" + s + "&robot=1";
		return getRestApiData(url);
	}
   
	@RequestMapping("checkLesson")
	@ResponseBody
	public Object checkLesson(HttpServletRequest request,String lessonId,String knowledgeId, String status){
		Map<String, Object>map=new HashMap<String, Object>();
		map.put("lessonId", lessonId);
		map.put("kngId", knowledgeId);
		map.put("status", status);
		try{
			logger.info(request.getSession().getAttribute("name")+"审核课时操作的管理员 "+request.getSession().getAttribute("name")+"===课时id"+lessonId);

		 HttpUtil.doPost(Config.YXTSERVER3+"oss/lesson/checkLesson", map,String.class);
		}catch(Exception e){
			
		}
		return "ok";
	}
	
	
	/**
	 * 
	 * 排行榜未绑定列表详情页 初始页  干货
	 */
	@RequestMapping("/courseBoxDetail")
	public ModelAndView DryBoxDetail(HttpServletRequest request) {
		 
		String type = "course";
		
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
		modelview.setViewName("course/courseBoxList");
		return modelview;
	}
	
	private JSONObject getdryboxlist(String boxPostId,String n,String s) {
		String url = Config.YXTSERVER3 + "oss/box/courseListNotInBoxPost?boxPostId="+boxPostId+"&n="+n+"&s="+s;
		return getRestApiData(url);
	}
	
	/**
	 * 
	 * 查询未关联排行版的干货列表
	 */
	@RequestMapping("/courseBoxList")
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
		
		String type = "course";
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
		modelview.setViewName("course/courseBoxList");
		return modelview;
	}
	
	private JSONObject dryboxpost(String type) {
		String url = Config.YXTSERVER3 + "oss/box/getBoxPostByType?type=" + type;
		return getRestApiData(url);
	}
	
	
	/**
	 * 
	 * 查询未关联排行版的干货列表
	 */
	@RequestMapping("/searchcourseBoxList")
	public ModelAndView searchDryBoxList(HttpServletRequest request) {
		 
		String keyword = request.getParameter("keyword");
		String boxPostId = request.getParameter("id");
		String dryFlag="0";
		
		String type = "course";
		 
		String name = request.getParameter("name");
		 
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		
		modelview.addObject("addDryBoxposition", dryboxpost(type));
		modelview.addObject("name", name);
		modelview.addObject("id", boxPostId);
		modelview.addObject("addDryBoxList", getdryboxlistNotIn(boxPostId, keyword));
		modelview.setViewName("course/courseBoxList");
		return modelview;
	}
	
	
	private JSONObject getdryboxlistNotIn(String boxPostId,String keyword) {
		String url = Config.YXTSERVER3 + "oss/box/searchcourseListNotInBoxPost?boxPostId="+boxPostId+"&keyword="+keyword;
		return getRestApiData(url);
	}
	
	/**
	 * 
	 * 干货关联到具体的排行榜
	 */
	@RequestMapping("/bindBoxCourse")
	public ModelAndView bindBoxDry(HttpServletRequest request) {
		String type = "course";
		String sourceType = "course";
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
		
		logger.info(request.getSession().getAttribute("name")+"课程关联到具体的排行榜操作的管理员 "+request.getSession().getAttribute("name")+"===位置id"+boxPostId+"课程ID"+sourceId);

		modelview.addObject("addDryBoxList", bindBoxDry(boxPostId, sourceType, sourceId,ctime));
		
		modelview.addObject("addDryBoxposition", dryboxpost(type));
		modelview.addObject("name", name);
		modelview.addObject("id", boxPostId);
		modelview.addObject("addDryBoxList", getdryboxlist( boxPostId,"0","10"));
		
		modelview.setViewName("course/courseBoxList");
		return modelview;
	}
	
	private JSONObject bindBoxDry(String boxPostId,String sourceType,String sourceId,String ctime) {
		String url = Config.YXTSERVER3 + "oss/box/addBoxInBoxPost?boxPostId=" + boxPostId+"&sourceType="+sourceType+"&sourceId="+sourceId+"&ctime="+ctime;
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
	/**
	 * 
	 * 无用章节
	 */
	@RequestMapping("/unUsedChapterList")
	public ModelAndView unUsedChapterList(HttpServletRequest request) {
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
		modelview.addObject("chapters", getChapters( n, s));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("course/chapterList");
		return modelview;
	}
	private JSONObject getChapters(String n,String s) {
		String url = Config.YXTSERVER3 + "oss/course/getUnusedChapters?n="+n+"&s="+s;
		return getRestApiData(url);
	}
	/**
	 * 
	 * @Title: deleteChapter
	 * @Description: 删除
	 * @param request
	 * @param response void
	 * @throws
	 */
	@RequestMapping("/deleteChapter")
	public void deleteChapter(HttpServletRequest request,HttpServletResponse response){
		String id=request.getParameter("id");
		logger.info(request.getSession().getAttribute("name")+"删除章节操作的管理员 "+request.getSession().getAttribute("name")+"===章节id"+id);

		deleteChapter(id);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		try {
			response.sendRedirect(cbasePath+"course/unUsedChapterList");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private JSONObject deleteChapter(String id) {
		String url = Config.YXTSERVER3 + "oss/course/deleteChapter?id="+id;
		return getRestApiData(url);
		
	}
	
	/**
	 * 
	 * 无用课时
	 */
	@RequestMapping("/unUsedLessonList")
	public ModelAndView unUsedLessonList(HttpServletRequest request) {
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
		modelview.addObject("lessons", getLessons( n, s));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("course/lessonList");
		return modelview;
	}
	private JSONObject getLessons(String n,String s) {
		String url = Config.YXTSERVER3 + "oss/course/getUnusedLessons?n="+n+"&s="+s;
		return getRestApiData(url);
	}
	/**
	 * 
	 * @Title: deleteLesson
	 * @Description: 删除
	 * @param request
	 * @param response void
	 * @throws
	 */
	@RequestMapping("/deleteLesson")
	public void deleteLesson(HttpServletRequest request,HttpServletResponse response){
		String id=request.getParameter("id");
		logger.info(request.getSession().getAttribute("name")+"删除课时操作的管理员 "+request.getSession().getAttribute("name")+"===课时id"+id);

		deleteLesson(id);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		try {
			response.sendRedirect(cbasePath+"course/unUsedLessonList");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private JSONObject deleteLesson(String id) {
		String url = Config.YXTSERVER3 + "oss/course/deleteLesson?id="+id;
		return getRestApiData(url);
		
	}
	
	@RequestMapping("selectUser")
	public String selectUser(HttpServletRequest request, Model model){
		String n = request.getParameter("n");
		String s = request.getParameter("s");
		String keyword = request.getParameter("keyword");
		model.addAttribute("userList", getUserList(n, s, keyword));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		model.addAttribute("cpath", cpath);
		model.addAttribute("cbasePath", cbasePath);
		return "course/selectUser";
	}
	
	private JSONObject getUserList(String n, String s, String keyword){
		String url = Config.YXTSERVER3 + "oss/user/searchbyinfo";
		HashMap<String, String> param = new HashMap<String, String>();
		if(n != null) param.put("n", n);
		if(s != null) param.put("s", s);
		if(keyword != null) param.put("keyword", keyword);
		return getRestApiData(url, param);
	}
	
	/**
	 * 查询自己的群组
	 * */
	@RequestMapping("selectGroup")
	public String getOneselfGroup(HttpServletRequest request, Model model){
		model.addAttribute("groupList", getOtherGroup(request.getParameter("uid")));
		return "course/selectGroup";
	}
	
	private JSONObject getOtherGroup(String uid){
		String url = Config.YXTSERVER3 + "oss/group/findMyGroups/" + uid;
		return getRestApiData(url);
	}
	
	/**
	 * 查看购买课程的用户列表
	 * */
	@RequestMapping("/buyusers")
	public String getBuyUsers(HttpServletRequest request, Model model){
		String cid = request.getParameter("cid");
		String pagenumber = request.getParameter("n");

		if (pagenumber == null) {
			pagenumber = "0";
		}

		// 每页条数

		String pagelines = request.getParameter("s");

		if (pagelines == null) {
			pagelines = "10";
		}
		model.addAttribute("buyusers", getBuyUserList(cid, pagenumber, pagelines));
		return "course/buyusers";
	}
	
	private JSONObject getBuyUserList(String cid, String n, String s){
		String url = Config.YXTSERVER3 + "oss/course/getWhoBuyCourseList?courseid=" + cid + "&n=" + n + "&s=" + s;
		return getRestApiData(url);
	}
	
	
	
	/**
	 * 查询课程学习列表 人
	 * */
	@RequestMapping("/getstudyList")
	public String getstudyList(HttpServletRequest request, Model model){
		String courseId = request.getParameter("courseId");
		String pagenumber = request.getParameter("n");
		int allLessonNum=0;
		int studyCount=0;
		if (pagenumber == null) {
			pagenumber = "0";
		}

		// 每页条数

		String pagelines = request.getParameter("s");

		if (pagelines == null) {
			pagelines = "10000";
		}
		List l=new ArrayList();
		JSONObject j=getstudyList(courseId, pagenumber, pagelines);
		JSONObject jj=(JSONObject) j.get("data");
		JSONArray jjj=jj.getJSONArray("result");
		
		//学习人
		for(int i=0;i<jjj.size();i++){
			JSONObject userid=(JSONObject) jjj.get(i);
			String id=(String) userid.get("id");
			String nickName=(String) userid.get("nickName");
			String logoURL=(String) userid.get("logoURL");
			JSONObject a=getstudyOne(courseId,id);
			JSONObject aa=(JSONObject) a.get("data");
			JSONObject aaa=(JSONObject) aa.get("result");
			JSONArray aaaa=aaa.getJSONArray("userChapters");
			 allLessonNum=(Integer) aaa.get("allLessonNum");
			 studyCount=(Integer) aaa.get("studyCount");
			for(int k=0;k<aaaa.size();k++){
				Map m=new HashMap();
				JSONObject aaaaa =(JSONObject) aaaa.get(k);
				JSONObject chapter =(JSONObject) aaaaa.get("chapter");
				JSONArray userLessones=aaaaa.getJSONArray("userLessones");
				List ll=new ArrayList();
				for(int f=0;f<userLessones.size();f++){
					Map mm=new HashMap();
					mm.put("lessones", userLessones.get(f));
					mm.put("lessonorder", f);
					mm.put("userid", id);
					mm.put("nickName", nickName);
					mm.put("logoURL", logoURL);
					
					ll.add(mm);
				}
				
				m.put("lessones", ll);
				m.put("chapter", chapter);
				m.put("chapterorder", k);
				m.put("userid", id);
				m.put("nickName", nickName);
				m.put("logoURL", logoURL);
				l.add(m);
				
			}
		}
		System.out.println(l);
		model.addAttribute("userstudyinfo", l);
		model.addAttribute("allLessonNum", allLessonNum);
		model.addAttribute("studyCount", studyCount);
		return "course/buyusers";
	}
	
	
	
	/**
	 * 查询课程学习列表 人
	 * */
	@RequestMapping("/getstudyCount")
	public String getstudyCount(HttpServletRequest request, Model model){
		String courseId = request.getParameter("courseId");
		String pagenumber = request.getParameter("n");
		int allLessonNum=0;
		int studyCount=0;
		if (pagenumber == null) {
			pagenumber = "0";
		}

		// 每页条数

		String pagelines = request.getParameter("s");

		if (pagelines == null) {
			pagelines = "1000";
		}
		List l=new ArrayList();
		JSONObject j=getstudyList(courseId, pagenumber, pagelines);
		JSONObject jj=(JSONObject) j.get("data");
		JSONArray jjj=jj.getJSONArray("result");
		 
		 List l2=new ArrayList();
		 List l3=new ArrayList();
		 List l4=new ArrayList();
		//取一个用户获取章节数目，章节编号 
		JSONObject userid2 = (JSONObject) jjj.get(0);
		String id2 = (String) userid2.get("id");
		String nickName2 = (String) userid2.get("nickName");
		String logoURL2 = (String) userid2.get("logoURL");
		JSONObject a2 = getstudyOne(courseId, id2);
		JSONObject aa2 = (JSONObject) a2.get("data");
		JSONObject aaa2 = (JSONObject) aa2.get("result");
		JSONArray aaaa2 = aaa2.getJSONArray("userChapters");
		int count2=0;
		for(int k=0;k<aaaa2.size();k++){
			JSONObject aaaaa =(JSONObject) aaaa2.get(k);
			JSONObject chapter =(JSONObject) aaaaa.get("chapter");
			int order =(Integer) chapter.get("order");
			String id =(String) chapter.get("id");
			String title =(String) chapter.get("title");
			l2.add(order);
			l3.add(id);
			l4.add(title);
			count2++;
		}
		 
		 int sum[]=new int[count2];
		 for(int i=0;i<sum.length;i++){
			 sum[i]=0;
		 } 
		 
		//学习人
		for(int i=0;i<jjj.size();i++){
			JSONObject userid=(JSONObject) jjj.get(i);
			String id=(String) userid.get("id");
			String nickName=(String) userid.get("nickName");
			String logoURL=(String) userid.get("logoURL");
			JSONObject a=getstudyOne(courseId,id);
			JSONObject aa=(JSONObject) a.get("data");
			JSONObject aaa=(JSONObject) aa.get("result");
			JSONArray aaaa=aaa.getJSONArray("userChapters");
			 allLessonNum=(Integer) aaa.get("allLessonNum");
			 studyCount=(Integer) aaa.get("studyCount");
			 int count=0;
			for(int k=0;k<aaaa.size();k++){
				Map m=new HashMap();
				JSONObject aaaaa =(JSONObject) aaaa.get(k);
				JSONObject chapter =(JSONObject) aaaaa.get("chapter");
				String title =(String) chapter.get("title");
				System.out.println("=================title======================"+title);
				int order =(Integer) chapter.get("order");
				System.out.println("=================order======================"+order);
				JSONArray userLessones=aaaaa.getJSONArray("userLessones");
				List ll=new ArrayList();
				for(int f=0;f<userLessones.size();f++){
					JSONObject u=(JSONObject) userLessones.get(f);
					if(u.getInt("lastProess")>0){
						count++;
					}
					
				}
				sum[order]+=count;  
//				m.put("chapter", chapter);
//				m.put("chapterorder", k);
//				m.put("count", count);
//				l.add(m);
				
			}
		}
		//System.out.println(l);
//		List l2=new ArrayList();
//		for(int i=0;i<allLessonNum;i++){
//			int count2=0;
//			 for(int k=i;k<studyCount;){
//				 Map m1=(Map)l.get(k);
//				 count2+=(Integer)(m1.get("count"));
//				 k=k+allLessonNum;
//			 }
//			 l2.add(count2);
//		}
//		
//		System.out.println(l2);
		System.out.println("=======================================");
		// 返回章节的顺序
		model.addAttribute("order", l2);
		System.out.println(l2);
		// 返回章节的id
		model.addAttribute("chapterid", l3);
		System.out.println(l3);
		// 返回章节的title
		model.addAttribute("chaptertitle", l4);
		System.out.println(l4);
		// 返回章节的顺序中的值
		model.addAttribute("ordervalue", sum);
		for(int i=0;i<sum.length;i++){
			System.out.println(sum[i]);
		 } 
		
		model.addAttribute("allLessonNum", allLessonNum);
		model.addAttribute("studyCount", studyCount);
		
		System.out.println("=======================================");
		return "course/buyusers";
	}
	
	
	
	
	
	
	private JSONObject getstudyList(String courseId,String n,String s){
		String url = Config.YXTSERVER3 + "oss/course/getStudyedList?courseId=" + courseId + "&n=" + n + "&s=" + s;
		return getRestApiData(url);
	}
	
	private JSONObject getstudyOne(String courseId,String userid){
		String url = Config.YXTSERVER3 + "oss/course/getone?courseId=" + courseId + "&userid="+userid;
		return getRestApiData(url);
	}
	
	
	
}
