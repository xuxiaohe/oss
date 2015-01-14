package com.yunxuetang.oss;

import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.web.client.RestTemplate;

import com.yunxuetang.util.Config;

public class BaseController {

	public BaseController() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unused")
	public JSONObject getRestApiData(String url) {
		String courSharResoStr;
		RestTemplate restTemplate = new RestTemplate();
		courSharResoStr = restTemplate.postForObject(url, null, String.class);
		try {
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
			return objj;
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unused")
	public JSONObject getRestApiData(String url,
			Map<String, String> requestParams) {
		String courSharResoStr;
		RestTemplate restTemplate = new RestTemplate();
		courSharResoStr = restTemplate.postForObject(url, null, String.class,
				requestParams);
		try {
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
			return objj;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public JSONObject groupList(String n,String s) {
		String url = Config.YXTSERVER3 + "oss/group/search?s="+s+"&n="+n;
		return getRestApiData(url);
	}

}
