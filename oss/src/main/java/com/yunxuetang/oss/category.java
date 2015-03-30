package com.yunxuetang.oss;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

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
public class category extends BaseController {
	@Autowired
	Saveimage saveimage;

	/**
	 * 
	 * 创建一级分类
	 */
	@RequestMapping("/createFirstCategoryAction")
	public String createFirstCategoryAction(HttpServletRequest request, @RequestParam MultipartFile file) {
		// 当前第几页
		String categoryName = request.getParameter("categoryName");
		String logoUrl = null;
		String t[] = file.getContentType().split("/");
		String tt = "." + t[1];
		try {

			if (file.getSize() != 0) {

				Long l = System.currentTimeMillis();

				String urlString = "/data/ossImgTemp";

				String urlString2 = categoryName + l + tt;

				InputStream stream = file.getInputStream();

				logoUrl = saveimage.save(urlString, urlString2, stream, "category");

			}

		} catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		ModelAndView modelview = new ModelAndView();

		modelview.addObject("createFirstCategory", categoryDetail(categoryName, logoUrl));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		return "redirect:/category/categoryList";
	}

	/**
	 * 
	 * 创建一级分类 展示页
	 */
	@RequestMapping("/createFirstCategoryForm")
	public ModelAndView createFirstCategoryForm(HttpServletRequest request) {

		ModelAndView modelview = new ModelAndView();

		modelview.addObject("courses", null);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("category/createFirstCategoryForm");
		return modelview;
	}

	/**
	 * 
	 * 创建二级分类
	 */
	@RequestMapping("/createSecondCategoryAction")
	public String createSecondCategoryAction(HttpServletRequest request, @RequestParam MultipartFile file) {
		// 当前第几页
		String parentId = request.getParameter("parentId");
		String logoUrl = null;
		String t[] = file.getContentType().split("/");
		String tt = "." + t[1];
		try {

			if (file.getSize() != 0) {

				Long l = System.currentTimeMillis();

				String urlString = "/data/ossImgTemp";

				String urlString2 = parentId + l + tt;

				InputStream stream = file.getInputStream();

				logoUrl = saveimage.save(urlString, urlString2, stream, "category");

			}

		} catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}
		String categoryName = request.getParameter("categoryName");

		ModelAndView modelview = new ModelAndView();

		modelview.addObject("courses", createSecondCategory(parentId, logoUrl, categoryName));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("course/courseListNoShare");
		return "redirect:/category/categoryDetail?id="+parentId;
	}

	/**
	 * 
	 * 创建二级分类 展示页
	 */
	@RequestMapping("/createSecondCategoryForm")
	public ModelAndView createSecondCategoryForm(HttpServletRequest request) {
		// 当前第几页
		String parentId = request.getParameter("parentId");

		ModelAndView modelview = new ModelAndView();

		modelview.addObject("parentId", parentId);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("category/createSecondCategoryForm");
		return modelview;
	}

	/**
	 * 
	 * 修改一级分类
	 */
	@RequestMapping("/updateFirstCategoryAction")
	public String updateFirstCategoryAction(HttpServletRequest request, @RequestParam MultipartFile file) {
		String id = request.getParameter("id");
		String logoUrl = request.getParameter("logoUrl");
		String t[] = file.getContentType().split("/");
		String tt = "." + t[1];
		try {

			if (file.getSize() != 0) {

				Long l = System.currentTimeMillis();

				String urlString = "/data/ossImgTemp";

				String urlString2 = id + l + tt;

				InputStream stream = file.getInputStream();

				logoUrl = saveimage.save(urlString, urlString2, stream, "category");

			}

		} catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}
		String categoryName = request.getParameter("categoryName");
		 
		ModelAndView modelview = new ModelAndView();

		modelview.addObject("courses", updateFirstCategory(id, logoUrl, categoryName));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		return "redirect:/category/categoryDetail?id="+id;
	}

	/**
	 * 
	 * 修改一级分类 展示页
	 */
	@RequestMapping("/updateFirstCategoryForm")
	public ModelAndView updateFirstCategoryForm(HttpServletRequest request) {
		// 当前第几页
		String id = request.getParameter("id");
		 
		ModelAndView modelview = new ModelAndView();

		modelview.addObject("categoryDetail", categoryDetail(id));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("category/updateFirstCategoryForm");
		return modelview;
	}

	/**
	 * 
	 * 修改二级分类
	 */
	@RequestMapping("/updateSecondCategoryAction")
	public String updateSecondCategoryAction(HttpServletRequest request, @RequestParam MultipartFile file) {
		String id = request.getParameter("id");
		String parentId = request.getParameter("parentId");
		
		String logoUrl = request.getParameter("logoUrl");
		String t[] = file.getContentType().split("/");
		String tt = "." + t[1];
		try {

			if (file.getSize() != 0) {

				Long l = System.currentTimeMillis();

				String urlString = "/data/ossImgTemp";

				String urlString2 = id + l + tt;

				InputStream stream = file.getInputStream();

				logoUrl = saveimage.save(urlString, urlString2, stream, "category");

			}

		} catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}
		String categoryName = request.getParameter("categoryName");
		 
		ModelAndView modelview = new ModelAndView();

		modelview.addObject("courses",  updateFirstCategory(id, logoUrl, categoryName));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		return "redirect:/category/categoryDetail?id="+parentId;
	}

	/**
	 * 
	 * 修改二级分类 展示页
	 */
	@RequestMapping("/updateSecondCategoryForm")
	public ModelAndView updateSecondCategoryForm(HttpServletRequest request) {
		String id = request.getParameter("id");
		ModelAndView modelview = new ModelAndView();

		modelview.addObject("categoryDetail", categoryDetail(id));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("category/updateSecondCategoryForm");
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("category/categoryList");
		return modelview;
	}

	/**
	 * 
	 * 分类详情 一级分类下包含所有的二级分类
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
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
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("course/courseListNoShare");
		return modelview;
	}

	/**
	 * 
	 * 删除二级分类
	 */
	@RequestMapping("/deleteSecondCategoryFormDetail")
	public String  deleteSecondCategoryFormDetail(HttpServletRequest request) {
		String cid = request.getParameter("cid");
		String fid = request.getParameter("fid");
		ModelAndView modelview = new ModelAndView();
		deletesecond(cid);
		 
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		return "redirect:/category/categoryDetail?id="+fid;
	}
	
	
	private JSONObject deletesecond(String cid) {
		String url = Config.YXTSERVER3 + "oss/category/deleteSecond";
		Map<String, String> map=new HashMap<String, String>();
		map.put("categoryid", cid);
		return getRestApiData(url,map);
	}

	private JSONObject categoryList() {
		String url = Config.YXTSERVER3 + "category/all";
		return getRestApiData(url);
	}

	private JSONObject categoryDetail(String id) {
		String url = Config.YXTSERVER3 + "category/one?categoryId=" + id;
		return getRestApiData(url);
	}

	private JSONObject categoryDetail(String categoryName, String logoUrl) {
		String url = Config.YXTSERVER3 + "category/createPrimary?categoryName=" + categoryName + "&logoUrl=" + logoUrl;
		return getRestApiData(url);
	}

	private JSONObject createSecondCategory(String parentId, String logoUrl, String categoryName) {
		String url = Config.YXTSERVER3 + "category/createSecond?parentId=" + parentId + "&logoUrl=" + logoUrl + "&categoryName=" + categoryName;
		return getRestApiData(url);
	}
	
	private JSONObject updateFirstCategory(String id, String logoUrl, String categoryName) {
		String url = Config.YXTSERVER3 + "category/update?id=" + id + "&logoUrl=" + logoUrl + "&categoryName=" + categoryName;
		return getRestApiData(url);
	}
}
