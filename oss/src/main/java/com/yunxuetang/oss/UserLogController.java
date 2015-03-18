package com.yunxuetang.oss;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.Config;
import com.yunxuetang.util.DateUtil;
import com.yunxuetang.util.HttpUtil;
import com.yunxuetang.util.StringUtil;
@Controller
@RequestMapping("/userlog")
public class UserLogController {
	
	@RequestMapping("/")	
	public ModelAndView search(HttpServletRequest request,String userKey,String qdId,String ctime,String etime,Integer s ,Integer n){
		ModelAndView mv=new ModelAndView("userlog/userLog");
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
			if(!StringUtil.isBlank(userKey)){
				pMap.put("userKey", userKey);
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
			mv.addObject("resuserList",object);
			mv.addObject("userKey", userKey);   
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
