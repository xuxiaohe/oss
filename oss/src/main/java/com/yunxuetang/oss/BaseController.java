package com.yunxuetang.oss;

import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.yunxuetang.util.Config;

public class BaseController {

	public BaseController() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 传入一个地址，Get 请求
	 * @param url
	 * @return
	 */
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

	/**
	 * 传入Map，Post到对一个的地址（为了兼容之前传入的Map参数）
	 * @param url
	 * @param requestParams
	 * @return JSONObject
	 */
	public JSONObject getRestApiData(String url,
			Map<String, String> requestParams) {
		String courSharResoStr;
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>(); 
		map.setAll(requestParams);
		
		RestTemplate restTemplate = new RestTemplate();
		courSharResoStr = restTemplate.postForObject(url, map, String.class,"");
		try {
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
			return objj;
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 传入MultiValueMap，Post给对应的地址
	 * @param url
	 * @param requestParams
	 * @return JSONObject
	 */
	public JSONObject getRestApiData(String url,
			MultiValueMap<String, String> requestParams) {
		String courSharResoStr;
		RestTemplate restTemplate = new RestTemplate();
		courSharResoStr = restTemplate.postForObject(url, requestParams, String.class, "");
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
