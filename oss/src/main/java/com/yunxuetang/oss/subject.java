package com.yunxuetang.oss;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.Config;

@Controller
@RequestMapping("/subject")
public class subject extends BaseController{
	Logger logger = LoggerFactory.getLogger(subject.class);
	/**
	 * 添加专题盒子
	 * */
	@RequestMapping("createsubject")
	public String createsubject(HttpServletRequest request, Model model){
		
		String type = request.getParameter("type");
		String categoryId = request.getParameter("categoryId");
		String logoUrl = request.getParameter("logoUrl");
		String h5Url = request.getParameter("h5Url");
		String order = request.getParameter("order");
		String enabled = request.getParameter("enabled");
		String chinaName=request.getParameter("chinaName");
		
		model.addAttribute("addbox", getOrderDetail(type,categoryId,logoUrl,h5Url,order,enabled,chinaName));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		model.addAttribute("cbasePath", cbasePath);
		model.addAttribute("sourcePath", Config.YXTSERVER5);
		return "order/orderdetail";
	}
	
	
	/**
	 * 获取盒子列表
	 * */
	@RequestMapping("getBoxPostByType")
	public String getBoxPostByType(HttpServletRequest request, Model model){
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
		String type = request.getParameter("type");
	 
		model.addAttribute("booxlist", getBoxPostByType(type,pagenumber,pagelines));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		model.addAttribute("cbasePath", cbasePath);
		model.addAttribute("sourcePath", Config.YXTSERVER5);
		return "order/orderdetail";
	}
	
	
	/**
	 * 获取盒子数据列表
	 * */
	@RequestMapping("findBoxById")
	public String findBoxById(HttpServletRequest request, Model model){
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
		String boxPostId = request.getParameter("boxPostId");
	 
		model.addAttribute("booxlist", findBoxById(boxPostId,pagenumber,pagelines));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		model.addAttribute("cbasePath", cbasePath);
		model.addAttribute("sourcePath", Config.YXTSERVER5);
		return "order/orderdetail";
	}
	
	
	/**
	 * 筛选各个数据列表
	 * */
	@RequestMapping("findByothers")
	public String findByothers(HttpServletRequest request, Model model){
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
		String boxPostId = request.getParameter("boxPostId");
		String dataType = request.getParameter("dataType");
		String childCategoryId = request.getParameter("childCategoryId");
	 
		model.addAttribute("list", findByothers(boxPostId,dataType,childCategoryId,pagenumber,pagelines));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		model.addAttribute("cbasePath", cbasePath);
		model.addAttribute("sourcePath", Config.YXTSERVER5);
		return "order/orderdetail";
	}
	
	
	
	
	/**
	 * 
	 * 取消关联到具体的排行榜
	 */
	@RequestMapping("/unbindBox")
	public ModelAndView unbindBoxDry(HttpServletRequest request) {
		String type = request.getParameter("type");
		 
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
		
		modelview.addObject("booxlist", findBoxById(boxPostId,"0","10"));
		
		modelview.setViewName("order/orderdetail");
		return modelview;
	}
	
	private JSONObject deleteBox(String boxId) {
		String url = Config.YXTSERVER3 + "oss/box/deleteBox?boxId=" + boxId;
		return getRestApiData(url);
	}
	
	
	private JSONObject getOrderDetail(String type,String categoryId,String logoUrl,String h5Url,String order,String enabled,String chinaName) {
		String url = Config.SUBJECT_SERVER
				+ "/box/addBoxPost?type=" + type+"&categoryId="+categoryId+"&logoUrl="+logoUrl+"&h5Url="+h5Url+"&order="+order+"&enabled="+enabled+"&chinaName="+chinaName;
		return getRestApiData(url);
	}
	
	private JSONObject getBoxPostByType(String type,String n,String s) {
		String url = Config.SUBJECT_SERVER
				+ "/box/getBoxPostByType?type=" + type+"&n="+n+"&s="+s;
		return getRestApiData(url);
	}
	
	private JSONObject findBoxById(String boxPostId,String n,String s) {
		String url = Config.SUBJECT_SERVER
				+ "/box/findBoxById?boxPostId=" + boxPostId+"&n="+n+"&s="+s;
		return getRestApiData(url);
	}
	
	private JSONObject findByothers(String boxPostId,String dataType,String childCategoryId,String n,String s) {
		String url = Config.YXTSERVER3
				+ "/box/notInBoxPostAndNotInCategory?boxPostId=" + boxPostId+"&n="+n+"&s="+s+"&dataType="+dataType+"&childCategoryId="+childCategoryId;
		return getRestApiData(url);
	}
}
