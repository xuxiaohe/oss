package com.yunxuetang.oss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.jsoup.helper.StringUtil;
import org.junit.Test;
import org.springframework.stereotype.Controller;
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
		modelview.setViewName("course/courseDetail");
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

		modelview.addObject("shareToMyGroup", shareToMyGroup(groupId,courseId,appKey));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
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

		modelview.addObject("deleteToMyGroup", deleteGroupCourses(groupId,course));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
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

		modelview.addObject("deleteToMyGroup", deleteCourses(courseId));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
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

		modelview.addObject("categoryOneList", categoryOneList("00"));

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("cid", cid);
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

		modelview.addObject("categorySecondList", categoryAction(categoryId, childCategoryId, courseId));

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		
		return "redirect:/course/courseListNoCheck";
	}
	
	
	/**
	 * 课程创建展示页
	 * 
	 */
	@RequestMapping("/createcourseview")
	public ModelAndView  createcourseview(HttpServletRequest request) {
		
		ModelAndView modelview = new ModelAndView();

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
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
		modelview.addObject("serverPath", Config.YXTSERVER3);
		modelview.setViewName("course/createcourse");
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
		String url = Config.YXTSERVER3 + "oss/course/courseChecked?categoryId="+categoryId+"&childCategoryId="+childCategoryId+"&courseId="+courseId;
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
	
	private JSONObject searchCourse(String keyword,String n,String s) {
		String url = Config.YXTSERVER3 + "oss/course/search?keywords="+keyword+"&n="+n+"&s="+s;
		return getRestApiData(url);
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
	public Map<String, Object>  modifyCourse(HttpServletRequest request,String title,String logoUrl,String intro,String tagNames,String courseId) {
		String[] chapterIds=request.getParameterValues("chapterIds[]");
		Map<String, String> map=new HashMap<String, String>();
		if(StringUtil.isBlank(title)){
			map.put("title", "运维");
		}else{
			map.put("title", title);
		}
		map.put("logoUrl", logoUrl);
		map.put("introl", intro);
		map.put("tagNames", tagNames);
		map.put("isPublic", "0");
		map.put("sourceType", "3");
		map.put("status", "3");
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
	public Object checkLesson(String lessonId,String knowledgeId, String status){
		Map<String, Object>map=new HashMap<String, Object>();
		map.put("lessonId", lessonId);
		map.put("kngId", knowledgeId);
		map.put("status", status);
		try{
		 HttpUtil.doPost(Config.YXTSERVER3+"oss/lesson/checkLesson", map,String.class);
		}catch(Exception e){
			
		}
		return "ok";
	}
	
	@Test
	public void test(){
		String url = "http://172.17.123.74:8081/oss/lesson/checkLesson";
		Map<String, Object>map=new HashMap<String, Object>();
		map.put("lessonId", "1");
		map.put("kngId", "1");
		map.put("status", "1");
		HttpUtil.doPost(url, map, String.class);

}
}
