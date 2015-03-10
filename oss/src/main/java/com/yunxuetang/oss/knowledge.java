package com.yunxuetang.oss;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.qiniu.api.auth.DigestAuthClient;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.net.CallRet;
import com.qiniu.api.net.EncodeUtils;
import com.yunxuetang.util.Config;
import com.yunxuetang.util.qiniu;

@Controller
@RequestMapping(value="/knowledge")
public class knowledge extends BaseController{
	
	/**
	 * 
	 * 查询所有待审核的知识
	 */
	@RequestMapping("/knowledgeList")
	public ModelAndView courseList(HttpServletRequest request) {
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

		modelview.addObject("knowledgeList", getCourses( n, s));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("knowledge/knowledgeList");
		return modelview;
	}
	/**
	 * 
	 * 审核知识
	 */
	@RequestMapping("/verify")
	private String verify(HttpServletRequest request) {
		String id = request.getParameter("id");
		String status=request.getParameter("status");
		verifyKnowledge(id,status);
		return "redirect:/knowledge/knowledgeList";

	}
	/**
	 * 
	 * @Title: getToken
	 * @Description: 获取七牛token
	 * @param request
	 * @return String
	 * @throws
	 */
	@RequestMapping("/getToken")
	@ResponseBody
	private Map<String, String> getToken(HttpServletRequest request) {
		Map<String, String> map=new HashMap<String, String>();
		map.put("uptoken", qiniu.token());
		return map;
	}
	@RequestMapping("/upload")
	private ModelAndView upload(HttpServletRequest request) {
		ModelAndView modelview = new ModelAndView();
		modelview.setViewName("knowledge/upload");
		return modelview;
	}
	@RequestMapping("/uploadKnowledge")
	private ModelAndView uploadKnowledge(HttpServletRequest request,String ckey) {
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("knowledge/uploadKnowledge");
		Map<String, Object> map=new HashMap<String, Object>();
		JSONObject json=getConfig(ckey);
		JSONObject data=JSONObject.fromObject(json.get("data"));
		JSONArray resultArr=JSONArray.fromObject(data.get("result"));
		JSONObject result=(JSONObject) resultArr.get(0);
		map.put("filters", result.get("filters"));
		map.put("fileParam", result.get("fileParam"));
		map.put("pathrule", result.get("pathrule"));
		map.put("id", result.get("id"));
		modelview.addObject("config", map);
		modelview.addObject("ckey", ckey);
		
		return modelview;
	}
	@RequestMapping("/getFileName")
	@ResponseBody
	public String getFileName(String name,String pathrule){
		SimpleDateFormat format=new SimpleDateFormat("yyyyMM");
		String time=format.format(new Date());
		String ext=name.substring(name.lastIndexOf("."));
		String temp=pathrule.replace("{yyyymm}",time);
		UUID uuid=UUID.randomUUID();
		return temp+uuid.toString()+ext;
	}
	private JSONObject getCourses(String n,String s) {
		String url = Config.YXTSERVER3 + "oss/knowledge/knowledgeList?n="+n+"&s="+s;
		return getRestApiData(url);
	}
	private JSONObject verifyKnowledge(String id,String status) {
		String url = Config.YXTSERVER3 + "oss/knowledge/verifyKnowledge?id="+id+"&status="+status;
		return getRestApiData(url);
	}
	private JSONObject getConfig(String ckey){
		String url = Config.YXTSERVER3 + "cloudConfig/findByCkey?ckey="+ckey;
		return getRestApiData(url);
	}
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> add(HttpServletRequest request,String fid,String name,String cid,String kngType,String key,String userId) {
		Map<String, String> requestParams =new HashMap<String, String>();
		String pid =trans(key);
		JSONObject jsonObject=JSONObject.fromObject(pid);
		
		requestParams.put("fid", fid);
		requestParams.put("name", name);
		requestParams.put("cid", jsonObject.getString("persistentId"));
		requestParams.put("kngType", kngType);
		requestParams.put("userId", userId);
		requestParams.put("status", "1");
		JSONObject json=addKnowledge(requestParams);
		JSONObject data=JSONObject.fromObject(json.get("data"));
		JSONObject result=JSONObject.fromObject(data.get("result"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("knowledgeId", result.get("id"));
		return map;
		
	}
	private JSONObject addKnowledge(Map<String, String> map) {
		String url = Config.YXTSERVER3 + "oss/knowledge/addKnowledge";
		return getRestApiData(url,map);
	}
	
	public String  trans(String key) {
		Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
		DigestAuthClient digestAuthClient = new DigestAuthClient(mac);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("bucket", Config.BUCKETNAME));
		nvps.add(new BasicNameValuePair("key",key ));
		//nvps.add(new BasicNameValuePair("fops","avthumb/m3u8/noDomain/1"));
		String newkey=key.substring(0,key.indexOf("."));
		
		String t="tpublic";
		String m3u8=EncodeUtils.urlsafeEncode(t+":"+newkey+".m3u8");
		String jpg=EncodeUtils.urlsafeEncode(t+":"+newkey+".jpg");
		nvps.add(new BasicNameValuePair("fops","avthumb/m3u8/noDomain/1|saveas/"+m3u8+";vframe/jpg/offset/2/w/480/h/360|saveas/"+jpg+";"));
		CallRet call = digestAuthClient.call("http://api.qiniu.com/pfop", nvps);
		if(call.ok()){
			return call.response;
		}else{
			return null;
		}
	}
 

}
