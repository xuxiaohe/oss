package com.yunxuetang.oss;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
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
	 * 查看专题详情
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping("subjectDetail")
	public String subjectDetail(HttpServletRequest request, Model model) throws UnsupportedEncodingException{
		String chinaName = request.getParameter("chinaName");
		chinaName = new String(chinaName.getBytes("iso-8859-1"), "utf-8");
		model.addAttribute("id", request.getParameter("id"));
		model.addAttribute("logoUrl", request.getParameter("logoUrl"));
		model.addAttribute("chinaName", chinaName);
		model.addAttribute("ctime", request.getParameter("ctime"));
		model.addAttribute("type", request.getParameter("type"));
		
		//查询盒子内数据
		//model.addAttribute("innerDetail", findBoxById(request.getParameter("id"), "", ""));
		
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		model.addAttribute("cbasePath", cbasePath);
		model.addAttribute("sourcePath", Config.YXTSERVER5);
		return "subject/subjectDetail";
	}
	
	/**
	 * 创建专题页面跳转
	 * */
	@RequestMapping("createView")
	public String createView(HttpServletRequest request, Model model){
		JSONObject jo = categoryList();
		model.addAttribute("categoryList", jo);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath;
		model.addAttribute("cbasePath", cbasePath);
		model.addAttribute("sourcePath", Config.YXTSERVER5);
		return "subject/create";
	}
	
	
	/**
	 *往盒子里添加数据
	 * */
	@RequestMapping("addDataInBox")
	public String addDataInBox(HttpServletRequest request, Model model){
		String boxPostId = request.getParameter("boxPostId");
		String sourceType = request.getParameter("sourceType");
		String sourceId = request.getParameter("sourceId");
		String ctime = request.getParameter("ctime");
		addInBox(boxPostId,sourceType,sourceId,ctime);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath;
		model.addAttribute("cbasePath", cbasePath);
		model.addAttribute("sourcePath", Config.YXTSERVER5);
		return "subject/create";
	}
	
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
		JSONObject result = getOrderDetail(type,categoryId,logoUrl,h5Url,order,enabled,chinaName);
		model.addAttribute("addbox", result);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		model.addAttribute("cbasePath", cbasePath);
		model.addAttribute("sourcePath", Config.YXTSERVER5);
		return "redirect:getBoxPostByType";
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
		if(null == type || "".equals(type)) type = "contentspecial";
		model.addAttribute("type", type);
		model.addAttribute("boxlist", getBoxPostByType(type,pagenumber,pagelines));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		model.addAttribute("cbasePath", cbasePath);
		model.addAttribute("sourcePath", Config.YXTSERVER5);
		return "subject/subjectList";
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
	 
		if("activityspecial".equals(dataType)){
			String a="";
			JSONObject j = findBycoupon(boxPostId);
			JSONObject jj = (JSONObject) j.get("data");
			JSONArray jjj=jj.getJSONArray("result");
			
			if(jjj.size()!=0){
				 a=jjj.getString(0)+",";
				for(int i=1;i<jjj.size();i++){
					a+=","+jjj.getString(i);
				}
			}

			model.addAttribute("list", findBycouponList(a));
		}
		else {
			model.addAttribute("list", findByothers(boxPostId,dataType,childCategoryId,pagenumber,pagelines));
		}
		
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
				+ "/box/addBoxPost";
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("type", type);
		param.put("categoryId", categoryId);
		param.put("logoUrl", logoUrl);
		param.put("h5Url", h5Url);
		param.put("order", order);
		param.put("enabled", enabled);
		param.put("chinaName", chinaName);
		return getRestApiData(url, param);
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
				+ "/oss/box/notInBoxPostAndNotInCategory?boxPostId=" + boxPostId+"&n="+n+"&s="+s+"&dataType="+dataType+"&childCategoryId="+childCategoryId;
		return getRestApiData(url);
	}
	
	private JSONObject findBycoupon(String boxPostId) {
		String url = Config.YXTSERVER3
				+ "/box/subjectInBox?boxPostId=" + boxPostId;
		return getRestApiData(url);
	}
	
	private JSONObject findBycouponList(String activitylist) {
		String url = Config.HONGBAO_SERVER
				+ "/oss/coupon/findNotInBycoupon?activitylist=" + activitylist;
		return getRestApiData(url);
	}
	
	private JSONObject addInBox(String boxPostId,String sourceType,String sourceId,String ctime) {
		String url = Config.SUBJECT_SERVER
				+ "/box/addBoxInBoxPost?boxPostId=" + boxPostId+"&sourceType="+sourceType+"&sourceId="+sourceId+"&ctime="+ctime;
		return getRestApiData(url);
	}
	
	/**
	 * 获取分类列表
	 * */
	private JSONObject categoryList() {
		String url = Config.YXTSERVER3 + "category/all";
		return getRestApiData(url);
	}
}
