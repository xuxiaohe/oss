package com.yunxuetang.util;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
/**
 * 
* @ClassName: HttpUtil
* @Description: Http 工具类  用于发送Post Get 请求
* @author Jack Tang
* @date 2015年2月5日 上午10:04:25
*
 */
public class HttpUtil {	
	/**
	 * 
	 * @Title: sendPost
	 * @Description: restPost 用于发送httpPost请求
	 * @param url  请求路径
	 * @param params 请求参数 k vale
	 * @param t   返回类型
	 * @return T
	 * @throws
	 */
	public static <T>  T doPost(String url,Map<String, Object> params,Class<T> t){
		MultiValueMap<String, Object> map=new LinkedMultiValueMap<String, Object>();
		map.setAll(params);
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForObject(url, map, t, "");
	}
    
	/**
	 * 
	 * @Title: doGet
	 * @Description: restPost 用于发送httpPost请求
	 * @param url  请求路径
	 * @param params  请求参数 k vale
	 * @param t 	返回类型
	 * @return T
	 * @throws
	 */
	public static <T> T doGet(String url,Map<String, Object> params,Class<T> t){
		RestTemplate restTemplate = new RestTemplate();
		StringBuffer buffer = new StringBuffer();
		for (Entry<String, Object> entry : params.entrySet()) {
			buffer.append("&" + entry.getKey() + "=" + entry.getValue());
		}
		url+="?"+buffer.toString();
		return  restTemplate.getForObject(url, t, "");
	}
	/**
	 * 
	 * @Title: sendPost
	 * @Description: restPost 用于发送httpPost请求
	 * @param url  请求路径
	 * @param params 请求参数 k vale
	 * @return String 
	 * @throws
	 */
	public static String sendPost(String url, Map<String, Object> params){
		 return doPost(url, params, String.class);
	}
	
	/**
	 * 
	 * @Title: sendGet
	 * @Description: restGet 用于发送httpGet请求
	 * @param url  请求路径
	 * @param params 请求参数 k vale
	 * @return String 
	 * @throws
	 */
	public static String sendGet(String url, Map<String, Object> params){
		 return doGet(url, params, String.class);
	}

}
	
	