package com.yunxuetang.oss;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
	 * 获取二级分类
	 * */
	@RequestMapping("/getChildCategory")
	public @ResponseBody JSONObject getChildCategoryById(HttpServletRequest request) {
		JSONObject currChildCategory = categoryDetail(request.getParameter("categoryId"));
		return currChildCategory;
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
	
	
//推荐
	@RequestMapping("/categoryBoxDetail")
	public ModelAndView categoryBoxDetail(HttpServletRequest request) {
		 
		String type = "category";
		
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
		modelview.addObject("addDryBoxList", getCategoryboxlist(objj5,"0","10"));
		modelview.setViewName("category/categoryBoxList");
		return modelview;
	}
	
	private JSONObject dryboxpost(String type) {
		String url = Config.YXTSERVER3 + "oss/box/getBoxPostByType?type=" + type;
		return getRestApiData(url);
	}
	private JSONObject getCategoryboxlist(String boxPostId,String n,String s) {
		String url = Config.YXTSERVER3 + "oss/box/categoryListNotInBoxPost?boxPostId="+boxPostId+"&n="+n+"&s="+s;
		return getRestApiData(url);
	}
	
	
	/**
	 * 
	 * 群组关联到具体的排行榜
	 */
	@RequestMapping("/bindBoxCategory")
	public ModelAndView bindBoxActivity(HttpServletRequest request) {
		String type = "category";
		String sourceType = "category";
		String name = request.getParameter("name");
		//位置id
		String boxPostId = request.getParameter("boxPostId");
		//干货id
		String sourceId = request.getParameter("sourceId");
		/*String ctime = request.getParameter("ctime"); */
		String ctime=System.currentTimeMillis()+"";
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("addDryBoxList", bindBoxCategory(boxPostId, sourceType, sourceId,ctime));
		modelview.addObject("addDryBoxposition", dryboxpost(type));
		modelview.addObject("name", name);
		modelview.addObject("id", boxPostId);
		modelview.addObject("addDryBoxList", getCategoryboxlist( boxPostId,"0","10"));
		
		modelview.setViewName("category/categoryBoxList");
		return modelview;
	}
	private JSONObject bindBoxCategory(String boxPostId,String sourceType,String sourceId,String ctime) {
		String url = Config.YXTSERVER3 + "oss/box/addBoxInBoxPost?boxPostId=" + boxPostId+"&sourceType="+sourceType+"&sourceId="+sourceId+"&ctime="+ctime;
		return getRestApiData(url);
	}
	
	/**
	 * 
	 * 查询未关联排行版的干货列表
	 */
	@RequestMapping("/categoryBoxList")
	public ModelAndView categoryBoxList(HttpServletRequest request) {
		 
		String pagenumber = request.getParameter("n");

		if (pagenumber == null) {
			pagenumber = "0";
		}

		// 每页条数

		String pagelines = request.getParameter("s");

		if (pagelines == null) {
			pagelines = "10";
		}
		
		String type = "category";
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		 
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		
		modelview.addObject("addDryBoxposition", dryboxpost(type));
		modelview.addObject("name", name);
		modelview.addObject("id", id);
		modelview.addObject("addDryBoxList", getCategoryboxlist(id,pagenumber,pagelines));
		modelview.setViewName("category/categoryBoxList");
		return modelview;
	}
	
	/**
	 * 
	 * 查询未关联排行版的干货列表
	 */
	@RequestMapping("/searchCategoryBoxList")
	public ModelAndView searchActivityBoxList(HttpServletRequest request) {
		 
		String keyword = request.getParameter("keyword");
		String boxPostId = request.getParameter("id");
		
		String type = "category";
		 
		String name = request.getParameter("name");
		 
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		
		modelview.addObject("addDryBoxposition", dryboxpost(type));
		modelview.addObject("name", name);
		modelview.addObject("id", boxPostId);
		modelview.addObject("addDryBoxList", getcategoryboxlistNotIn( boxPostId, keyword));
		modelview.setViewName("category/categoryBoxList");
		return modelview;
	}
	
	private JSONObject getcategoryboxlistNotIn(String boxPostId,String keyword) {
		String url = Config.YXTSERVER3 + "oss/box/searchcategoryListNotInBoxPost?boxPostId="+boxPostId+"&keyword="+keyword;
		return getRestApiData(url);
	}
	
	
}
