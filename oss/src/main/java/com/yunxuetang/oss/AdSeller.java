package com.yunxuetang.oss;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.Config;
import com.yunxuetang.util.DateUtil;
import com.yunxuetang.util.HttpUtil;
import com.yunxuetang.util.StringUtil;

@Controller
@RequestMapping("/adSeller")
public class AdSeller extends BaseController{
	/**
	 * 
	 * @Title: adSellerPage
	 * @Description: 渠道商的分页
	 * @param request
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping("/adSellerPage")
	public ModelAndView adSellerPage(HttpServletRequest request){
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

				modelview.addObject("adSellerList", getAdSellerPage( n, s));
				String cpath = request.getContextPath();
				String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
				modelview.addObject("cbasePath", cbasePath);
				modelview.setViewName("ad/adSellerList");
				return modelview;
	}

	private JSONObject getAdSellerPage(String n, String s) {
		String url = Config.YXTSERVER3 + "oss/adSeller/adSellerPage?n="+n+"&s="+s;
		return getRestApiData(url);
	}
	@RequestMapping("/createView")
	public ModelAndView createView(HttpServletRequest request){
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		modelview.addObject("adSellerList", getAdSellerList());
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("ad/createView");
		return modelview;
	}
	/**
	 * 
	 * @Title: createAdSeller
	 * @Description: 创建渠道商
	 * @param request
	 * @param response void
	 * @throws
	 */
	@RequestMapping("/create")
	public void createAdSeller(HttpServletRequest request,HttpServletResponse response){
		String name=request.getParameter("name");
		String adSellerId=request.getParameter("adSellerId");
		Map<String, String> map=new HashMap<String, String>();
		map.put("name", name);
		map.put("adSellerId", adSellerId);
		create(map);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		try {
			response.sendRedirect(cbasePath+"adSeller/createView");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private JSONObject create(Map<String, String> map) {
		String url = Config.YXTSERVER3 + "oss/adSeller/create";
		return getRestApiData(url,map);
	}
	/**
	 * 
	 * @Title: delete
	 * @Description: 删除渠道商
	 * @param request
	 * @param response void
	 * @throws
	 */
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request,HttpServletResponse response){
		String id=request.getParameter("id");
		delete(id);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		try {
			response.sendRedirect(cbasePath+"adSeller/adSellerPage");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private JSONObject delete(String id) {
		String url = Config.YXTSERVER3 + "oss/adSeller/delete?id="+id;
		return getRestApiData(url);
	}
	/**
	 * 
	 * @Title: adSellerList
	 * @Description:渠道商列表
	 * @param request
	 * @return Object
	 * @throws
	 */
	@RequestMapping("/adSellerList")
	@ResponseBody
	public Object adSellerList(HttpServletRequest request){
		
		
		return getAdSellerList();
	}

	private JSONObject getAdSellerList() {
		String url = Config.YXTSERVER3 + "oss/adSeller/adSellerList";
		return getRestApiData(url);
	}
	/******************广告**************************/
	/**
	 * 
	 * @Title: adPage
	 * @Description: 广告分页
	 * @param request
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping("/adPage")
	public ModelAndView adPage(HttpServletRequest request,String qdName,String qdId,String ctime,String etime,Integer s ,Integer n) {
		ModelAndView mv=new ModelAndView("ad/adList");
		Map<String, Object>pMap=new HashMap<String, Object>();
		try {
			if(StringUtil.isBlank(ctime)){
				ctime="2015-02-02";
			}
			if(StringUtil.isBlank(etime)){
				etime="2015-03-22";
			}
			long time1=DateUtil.toLongtime(ctime);
			long time2=DateUtil.toLongtime(etime);
			
			if(!StringUtil.isBlank(qdId)){
				pMap.put("qdId", qdId);
			}
			if(!StringUtil.isBlank(qdName)){
				pMap.put("qdName", qdName);
			}
			if(n!=null){
				pMap.put("n", n+"");
			}
			if(s!=null){
				pMap.put("s", s+"");
			}
			pMap.put("ctime", time1+"");
			pMap.put("etime", time2+"");
			String resString=HttpUtil.sendPost(Config.YXTSERVER3+"oss/ad/adPage", pMap);
			JSONObject object=JSONObject.fromObject(resString);
			String cpath = request.getContextPath();
			String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
			mv.addObject("cbasePath", cbasePath);
			mv.addObject("adList",object);
			mv.addObject("qdName", qdName);   
			mv.addObject("qdId", qdId);   
			mv.addObject("ctime", ctime); 
			mv.addObject("etime", etime); 
			return mv;
		} catch (ParseException e) {
			e.printStackTrace();
			return mv;
		}
	}
	/**
	 * 
	 * @Title: deleteAd
	 * @Description: 删除广告
	 * @param id void
	 * @throws
	 */
	@RequestMapping("/deleteAd")
	public void deleteAd(String id,HttpServletRequest request,HttpServletResponse response){
		deleteById(id);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		try {
			response.sendRedirect(cbasePath+"adSeller/adPage");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private JSONObject deleteById(String id) {
		String url = Config.YXTSERVER3 + "oss/ad/delete?id="+id;
		return getRestApiData(url);
	}
	/**
	 * 
	 * @Title: createAd
	 * @Description: 创建广告
	 * @param request
	 * @param response
	 * @param name
	 * @param remark
	 * @param adSid void
	 * @throws
	 */
	@RequestMapping("/createAd")
	public void createAd(HttpServletRequest request,HttpServletResponse response,String name,String remark,String adSid ,String creater){
		Map<String,String> map=new HashMap<String, String>();
		map.put("adSid", adSid);
		map.put("name", name);
		map.put("remark", remark);
		map.put("creater", creater);
		createAd(map);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		try {
			response.sendRedirect(cbasePath+"adSeller/adPage");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private JSONObject createAd(Map<String, String> map ) {
		String url = Config.YXTSERVER3 + "oss/ad/create";
		return getRestApiData(url,map);
	}
	
	/******************用户**************************/
	@RequestMapping("/userList")
	public ModelAndView adPage(HttpServletRequest request,String qdId,String ctime,String etime,Integer s ,Integer n) {
		ModelAndView mv=new ModelAndView("ad/userList");
		Map<String, Object>pMap=new HashMap<String, Object>();
		try {
			if(StringUtil.isBlank(ctime)){
				ctime="2015-02-02";
			}
			if(StringUtil.isBlank(etime)){
				etime="2020-03-22";
			}
			long time1=DateUtil.toLongtime(ctime);
			long time2=DateUtil.toLongtime(etime);
			
			if(!StringUtil.isBlank(qdId)){
				pMap.put("qdId", qdId);
			}
			if(n!=null){
				pMap.put("n", n+"");
			}
			if(s!=null){
				pMap.put("s", s+"");
			}
			pMap.put("ctime", time1+"");
			pMap.put("etime", time2+"");
			String resString=HttpUtil.sendPost(Config.YXTSERVER3+"oss/user/log/searchUser", pMap);
			JSONObject object=JSONObject.fromObject(resString);
			String cpath = request.getContextPath();
			String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
			mv.addObject("cbasePath", cbasePath);
			mv.addObject("userList",object);
			mv.addObject("qdId", qdId);   
			mv.addObject("ctime", ctime); 
			mv.addObject("etime", etime); 
			return mv;
		} catch (ParseException e) {
			e.printStackTrace();
			return mv;
		}
	}

}
