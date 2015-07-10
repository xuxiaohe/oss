package com.yunxuetang.oss;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.Config;
import com.yunxuetang.util.Jsp2Html;
import com.yunxuetang.util.qiniu;

@Controller
@RequestMapping("/subject")
public class subject extends BaseController{
	Logger logger = LoggerFactory.getLogger(subject.class);
	/**
	 * 预览页面跳转
	 * */
	@RequestMapping("showPreview")
	public String showPreview(HttpServletRequest request, Model model){
		String boxId = request.getParameter("boxId");
		String type = request.getParameter("type");
		String logoUrl = request.getParameter("logoUrl");
		
		model.addAttribute("specialInfo", getSpecialinfo(boxId,type));
		model.addAttribute("logoUrl", logoUrl);
		
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		model.addAttribute("cbasePath", cbasePath);
		model.addAttribute("sourcePath", Config.YXTSERVER5);
		if("activityspecial".equals(type))
			return "subject/activityView";
		else 
			return "subject/contentView" ;
	}
	
	/**
	 * 生成静态页面 
	 * */
	@RequestMapping("createH5File")
	public @ResponseBody String createFile(HttpServletRequest request, Model model){
		String boxId = request.getParameter("boxId");
		String type = request.getParameter("type");
		String logoUrl = request.getParameter("logoUrl");
		String h5Url = request.getParameter("h5Url");
		
		String url = "http://127.0.0.1:8089/oss/subject/showPreview?boxId=" + boxId + "&type=" + type + "&logoUrl=" + logoUrl;
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.substring(uuid.length() - 6);
		String fileName = boxId.concat(uuid).concat(".html");
		try {
			/*将请求的URL返回值转换为HTML页面*/
			fileName = Jsp2Html.revertFile(url, fileName);
			if("fail".equals(fileName)) return "fail";
			fileName = fileName.substring(0, fileName.lastIndexOf("/"));
			//上传到服务器
			qiniu q = new qiniu();
			String fileUrl = q.xixi(fileName, boxId.concat(uuid).concat(".html"), "h5/special/".concat(type));
			if(null != h5Url && !"".equals(h5Url)) q.delete(h5Url);
			//更新接口将文件地址写入
			JSONObject result = updateH5Url(boxId, fileUrl);
			String success = result.getString("status");
			if("200".equals(success)){
				File file = new File(fileName);
				if(file.exists()) file.delete();//删除本地文件
				return "success";
			}
			else 
				return "fail";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return "fail";
		}
	}
	
	/**
	 * 查看专题详情
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping("subjectDetail")
	public String subjectDetail(HttpServletRequest request, Model model) throws UnsupportedEncodingException{
		String chinaName = request.getParameter("chinaName");
		chinaName = new String(chinaName.getBytes("iso-8859-1"), "utf-8");
		String boxPostId = request.getParameter("id");
		String type = request.getParameter("type");
		model.addAttribute("id", request.getParameter("id"));
		model.addAttribute("logoUrl", request.getParameter("logoUrl"));
		model.addAttribute("chinaName", chinaName);
		model.addAttribute("ctime", request.getParameter("ctime"));
		model.addAttribute("type", request.getParameter("type"));
		model.addAttribute("h5Url", request.getParameter("h5Url"));
		
		//查询盒子内数据
		//model.addAttribute("innerDetail", findBoxById(request.getParameter("id"), "", ""));
		model.addAttribute("specialInfo", getSpecialinfo(boxPostId,type));
		//model.addAttribute("boxlist", findBoxById(boxPostId,"0","10"));
		//System.out.println(findBoxById(boxPostId,"0","10"));
		
		
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
	public @ResponseBody String addDataInBox(HttpServletRequest request, Model model){
		String sourceId = request.getParameter("sourceId");
		String[] d=sourceId.split(",");
		
		String boxPostId = request.getParameter("boxPostId");
		String sourceType = request.getParameter("sourceType");
		String ctime = request.getParameter("ctime");
		for(int i=0;i<d.length;i++){
			addInBox(boxPostId,sourceType,d[i],ctime);
		}
		
//		String cpath = request.getContextPath();
//		String cbasePath = request.getScheme() + "://"
//				+ request.getServerName() + ":" + request.getServerPort()
//				+ cpath;
//		model.addAttribute("cbasePath", cbasePath);
//		model.addAttribute("sourcePath", Config.YXTSERVER5);
		return "success";
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
			pagelines = "10";
		}
		/*
		 返回页面的类型: "selectActivity" : "活动", "selectCourse" : "课程", "selectTopic" : "话题", "selectDry" : "干货"
		*/
		String pageType = request.getParameter("pageType");
		
		String boxPostId = request.getParameter("boxPostId");
		String dataType = request.getParameter("dataType");
		String ctime = request.getParameter("ctime");
		String childCategoryId = request.getParameter("childCategoryId");
		model.addAttribute("boxPostId", boxPostId);
		model.addAttribute("dataType", dataType);
		model.addAttribute("ctime", ctime);
		if("activityspecial".equals(dataType)){
			String a="";
			JSONObject j = findBycoupon(boxPostId);
			JSONObject jj = (JSONObject) j.get("data");
			JSONArray jjj=jj.getJSONArray("result");
			
			
			if(jj.size()!=0){
				for(int i=0;i<jjj.size();i++){
					JSONObject job = jjj.getJSONObject(i);
					a = a + job.get("id");
					if(i < jjj.size() - 1) a = a + ",";
				}
			}

			model.addAttribute("list", findBycouponList(a ,pagenumber,pagelines));
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
		return "subject/" + pageType;
	}
	
	
	
	
	/**
	 * 
	 * 显示专题列表中数据项的内容
	 */
	@RequestMapping("/getSpecialInfo")
	public ModelAndView getSpecialInfo(HttpServletRequest request) {
		String boxPostId = request.getParameter("boxPostId");
		 
		String type = request.getParameter("type");
		 
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		
		
		modelview.addObject("specialInfo", getSpecialinfo(boxPostId,type));
		
		modelview.addObject("booxlist", findBoxById(boxPostId,"0","10"));
		
		modelview.setViewName("order/orderdetail");
		return modelview;
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
				+ "/box/findBoxById?boxPostId=" + boxPostId;
		return getRestApiData(url);
	}
	
	private JSONObject updateH5Url(String boxPostId, String h5url){
		String url = Config.SUBJECT_SERVER + "/box/h5Url?boxPostId=" + boxPostId + "&h5Url=" + h5url;
		return getRestApiData(url);
	}
	
	private JSONObject findByothers(String boxPostId,String dataType,String childCategoryId,String n,String s) {
		String url = Config.YXTSERVER3
				+ "/oss/box/notInBoxPostAndNotInCategory?boxPostId=" + boxPostId+"&n="+n+"&s="+s+"&dataType="+dataType+"&childCategoryId="+childCategoryId;
		return getRestApiData(url);
	}
	
	private JSONObject findBycoupon(String boxPostId) {
		String url = Config.YXTSERVER3
				+ "oss/box/subjectInBox?boxPostId=" + boxPostId;
		return getRestApiData(url);
	}
	
	private JSONObject findBycouponList(String activitylist,String n,String s) {
		String url = Config.HONGBAO_SERVER
				+ "/oss/coupon/findNotInBycoupon?activitylist=" + activitylist + "&n=" + n + "&s=" + s;
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
	private JSONObject getSpecialinfo(String boxPostId,String type) {
		String url = Config.YXTSERVER3 + "/oss/exploreoss/findBoxById?boxPostId="+boxPostId+"&type="+type;
		return getRestApiData(url);
	}
	
}
