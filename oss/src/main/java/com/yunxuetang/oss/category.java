package com.yunxuetang.oss;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.Config;
import com.yunxuetang.util.Saveimage;

@Controller
@RequestMapping("/category")
public class category extends BaseController{
	@Autowired
	Saveimage saveimage;

	/**
	 * 
	 * 创建一级分类  
	 */
	@RequestMapping("/createFirstCategoryAction")
	public ModelAndView createFirstCategoryAction(HttpServletRequest request,@RequestParam MultipartFile file) {
		// 当前第几页
		String categoryName = request.getParameter("categoryName");
		String logoURL = "";

		try {

		if (file.getSize()!=0) {

		Long l=System.currentTimeMillis();

		String urlString="/data/ossImgTemp";

		String urlString2=categoryName+l+".jpg";

		InputStream stream=	file.getInputStream();

		logoURL=saveimage.save(urlString, urlString2, stream,"category");

		}

		} catch (Exception e) {

		// TODO Auto-generated catch block

		e.printStackTrace();

		}
		 
		ModelAndView modelview = new ModelAndView();

		modelview.addObject("courses", null);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("course/courseListNoShare");
		return modelview;
	}
	
	
	/**
	 * 
	 * 创建一级分类  展示页
	 */
	@RequestMapping("/createFirstCategoryForm")
	public ModelAndView createFirstCategoryForm(HttpServletRequest request) {
		 
		ModelAndView modelview = new ModelAndView();

		modelview.addObject("courses", null);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("category/createFirstCategoryForm");
		return modelview;
	}
	
	/**
	 * 
	 * 创建二级分类
	 */
	@RequestMapping("/createSecondCategoryAction")
	public ModelAndView createSecondCategoryAction(HttpServletRequest request) {
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

		modelview.addObject("courses", null);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("course/courseListNoShare");
		return modelview;
	}
	
	
	/**
	 * 
	 * 创建二级分类  展示页
	 */
	@RequestMapping("/createSecondCategoryForm")
	public ModelAndView createSecondCategoryForm(HttpServletRequest request) {
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

		modelview.addObject("courses", null);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("course/courseListNoShare");
		return modelview;
	}
	
	
	
	/**
	 * 
	 * 修改一级分类  
	 */
	@RequestMapping("/updateFirstCategoryAction")
	public ModelAndView updateFirstCategoryAction(HttpServletRequest request) {
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

		modelview.addObject("courses", null);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("course/courseListNoShare");
		return modelview;
	}
	
	
	/**
	 * 
	 * 修改一级分类  展示页
	 */
	@RequestMapping("/updateFirstCategoryForm")
	public ModelAndView updateFirstCategoryForm(HttpServletRequest request) {
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

		modelview.addObject("courses", null);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("course/courseListNoShare");
		return modelview;
	}
	
	
	
	/**
	 * 
	 * 修改二级分类  
	 */
	@RequestMapping("/updateSecondCategoryAction")
	public ModelAndView updateSecondCategoryAction(HttpServletRequest request) {
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

		modelview.addObject("courses", null);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("course/courseListNoShare");
		return modelview;
	}
	
	
	/**
	 * 
	 * 修改二级分类  展示页
	 */
	@RequestMapping("/updateSecondCategoryForm")
	public ModelAndView updateSecondCategoryForm(HttpServletRequest request) {
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

		modelview.addObject("courses", null);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("course/courseListNoShare");
		return modelview;
	}
	
	/**
	 * 
	 * 一级分类列表
	 */
	@RequestMapping("/categoryList")
	public ModelAndView categoryList(HttpServletRequest request) {
		 
		ModelAndView modelview = new ModelAndView();

		modelview.addObject("categoryList", categoryList());
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("category/categoryList");
		return modelview;
	}
	
	
	/**
	 * 
	 * 分类详情  一级分类下包含所有的二级分类
	 */
	@RequestMapping("/categoryDetail")
	public ModelAndView categoryDetail(HttpServletRequest request) {
		// 当前第几页
		String id = request.getParameter("id");
		 
		ModelAndView modelview = new ModelAndView();

		modelview.addObject("categoryDetail", categoryDetail(id));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("category/categoryDetail");
		return modelview;
	}
	
	
	/**
	 * 
	 * 删除一级分类
	 */
	@RequestMapping("/deleteFirstCategoryFormDetail")
	public ModelAndView deleteFirstCategoryFormDetail(HttpServletRequest request) {
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

		modelview.addObject("courses", null);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("course/courseListNoShare");
		return modelview;
	}
	
	/**
	 * 
	 * 删除二级分类
	 */
	@RequestMapping("/deleteSecondCategoryFormDetail")
	public ModelAndView deleteSecondCategoryFormDetail(HttpServletRequest request) {
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

		modelview.addObject("courses", null);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("course/courseListNoShare");
		return modelview;
	}
	
	
	
	private JSONObject categoryList() {
		String url = Config.YXTSERVER3 + "category/all";
		return getRestApiData(url);
	}
	
	private JSONObject categoryDetail(String id) {
		String url = Config.YXTSERVER3 + "category/one?categoryId="+id;
		return getRestApiData(url);
	}
}
