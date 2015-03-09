package com.yunxuetang.oss;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.Config;
import com.yunxuetang.util.HttpUtil;
import com.yunxuetang.util.ResponseContainer;

/**
 * 
 * 
* @ClassName: QinNiuController
* @Description: 七牛配置
* @author tangli
* @date 2015年3月9日 下午12:58:37
*
 */
@Controller
@RequestMapping("mail")
public class MailController {
	@RequestMapping("/")
	public ModelAndView index(HttpServletRequest request){
		ModelAndView mView=new ModelAndView("email/mail");
		HashMap<String, Object> map=new HashMap<String, Object>();
		ResponseContainer responseContainer=HttpUtil.doPost(Config.YXTSERVER3+"oss/email/getAll", map, ResponseContainer.class);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		mView.addObject("cbasePath", cbasePath);
		mView.addObject("temps",responseContainer.getData().getResult());
		return mView;
		
	}
	
	@RequestMapping("get")
	@ResponseBody
	public ResponseContainer gettemp(String name){
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("name", name);
		ResponseContainer responseContainer=HttpUtil.doPost(Config.YXTSERVER3+"oss/email/getByName", map, ResponseContainer.class);
		return responseContainer;
	}
	
	@RequestMapping("getById")
	@ResponseBody
	public Object getById(String id){
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("id", id);
		ResponseContainer responseContainer=HttpUtil.doPost(Config.YXTSERVER3+"oss/email/getById", map, ResponseContainer.class);
		return responseContainer.getData().getResult();
	}
	
	@RequestMapping("modify")
	@ResponseBody
	public Object getById(String id,String content){
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("id", id);
		map.put("content", content);
		ResponseContainer responseContainer=HttpUtil.doPost(Config.YXTSERVER3+"oss/email/modify", map, ResponseContainer.class);
		return responseContainer;
	}
	
}
