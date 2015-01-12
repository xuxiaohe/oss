package com.yunxuetang.oss;

import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.web.client.RestTemplate;

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

}
