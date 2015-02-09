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
	

}
