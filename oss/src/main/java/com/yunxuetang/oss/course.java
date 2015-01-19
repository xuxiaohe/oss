package com.yunxuetang.oss;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.Config;
import com.yunxuetang.util.PoiService;

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
		String keyword = request.getParameter("keyword");
		ModelAndView modelview = new ModelAndView();

		modelview.addObject("courses", searchCourse(keyword));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("course/courseList");
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
	public ModelAndView shareToMyGroup(HttpServletRequest request) {
		String groupId = request.getParameter("groupId");
		String courseId = request.getParameter("courseId");
		String appKey = "yxtapp";
		ModelAndView modelview = new ModelAndView();

		modelview.addObject("shareToMyGroup", shareToMyGroup(groupId,courseId,appKey));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("course/courseList");
		return modelview;
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
	
	
	
	private JSONObject shareToMyGroup(String groupId,String courseId,String appKey) {
		String url = Config.YXTSERVER3 + "oss/course/shareToMyGroup?groupId="+groupId+"&courseId="+courseId+"&appKey="+appKey;
		return getRestApiData(url);
	}
	
	
	private JSONObject deleteGroupCourses(String groupId,String courseId) {
		String url = Config.YXTSERVER3 + "oss/course/deleteGroupCourses?groupId="+groupId+"&groupCourseId="+courseId;
		return getRestApiData(url);
	}
	
	
	
	private JSONObject searchCourse(String keyword) {
		String url = Config.YXTSERVER3 + "oss/course/search?keywords="+keyword;
		return getRestApiData(url);
	}
	
	private JSONObject getCourses(String n,String s) {
		String url = Config.YXTSERVER3 + "oss/course/courses?n="+n+"&s="+s;
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
	

}
