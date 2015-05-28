package com.yunxuetang.oss;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yunxuetang.util.Config;

@Controller
@RequestMapping(value="/attachFile")
public class attachFile extends BaseController{
	Logger logger = LoggerFactory.getLogger(attachFile.class);
	
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> add(HttpServletRequest request,String ckey,String fkey,String fname,String fsize) {
		long putTime=new Date().getTime();
		Map<String, String> requestParams =new HashMap<String, String>();
		requestParams.put("ckey", ckey);
		requestParams.put("fkey", fkey);
		requestParams.put("fname", fname);
		requestParams.put("putTime",new Long(putTime).toString());
		requestParams.put("fkey", fkey);
		requestParams.put("fsize", fsize);
//		requestParams.put("token", "81FEAE852CB6D554A02CED0F46B7A9F4");
		logger.info("创建附件的管理员 "+request.getSession().getAttribute("name")+"===附件名称"+fname);
		JSONObject json=addAttachFile(requestParams);
		Map<String, Object> map=new HashMap<String, Object>();
		JSONObject data=JSONObject.fromObject(json.get("data"));
		JSONObject result=JSONObject.fromObject(data.get("result"));
		map.put("attid", result.get("id"));
		map.put("name", result.get("fname"));
		map.put("furl", result.get("furl"));
		return map;
	}
	private JSONObject addAttachFile(Map<String, String> map) {
		String url = Config.YXTSERVER3 + "oss/attachFile/add";
		return getRestApiData(url,map);
	}

}
