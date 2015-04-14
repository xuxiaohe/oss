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
import com.qiniu.api.rs.Entry;
import com.qiniu.api.rs.RSClient;
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
		String keywords=request.getParameter("keywords");
		ModelAndView modelview = new ModelAndView();

		modelview.addObject("knowledgeList", getCourses( n, s,keywords));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.addObject("keywords",keywords );
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
	private Map<String, String> getToken(HttpServletRequest request,String ckey) {
		Map<String, String> map=new HashMap<String, String>();
		map.put("uptoken", qiniu.token(getMapConfig(ckey).get("bucket").toString()));
		return map;
	}
	@RequestMapping("/upload")
	private ModelAndView upload(HttpServletRequest request) {
		ModelAndView modelview = new ModelAndView();
		modelview.setViewName("knowledge/upload");
		return modelview;
	}
	private Map<String, Object> getMapConfig(String ckey){
		Map<String, Object> map=new HashMap<String, Object>();
		JSONObject json=getConfig(ckey);
		JSONObject data=JSONObject.fromObject(json.get("data"));
		JSONArray resultArr=JSONArray.fromObject(data.get("result"));
		JSONObject result=(JSONObject) resultArr.get(0);
		map.put("filters", result.get("filters"));
		map.put("fileParam", result.get("fileParam"));
		map.put("pathrule", result.get("pathrule"));
		map.put("id", result.get("id"));
		map.put("bucket", result.get("bucket"));
		map.put("baseUrls", result.get("baseUrls"));
		return map;
	}
	/**
	 * 
	 * @Title: uploadKnowledge
	 * @Description: 跳转到上传知识页面  把必要的参数带过去
	 * @param request
	 * @param ckey
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping("/uploadKnowledge")
	private ModelAndView uploadKnowledge(HttpServletRequest request,String ckey) {
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("knowledge/uploadKnowledge");
		modelview.addObject("config", getMapConfig(ckey));
		modelview.addObject("ckey", ckey);
		
		return modelview;
	}
	/**
	 * 
	 * @Title: getFileName
	 * @Description: 根据pathrule回去新的文件名
	 * @param name
	 * @param pathrule
	 * @return String
	 * @throws
	 */
	@RequestMapping("/getFileName")
	@ResponseBody
	public String getFileName(String name,String pathrule){
		SimpleDateFormat format=new SimpleDateFormat("yyyyMM");
		String time=format.format(new Date());
		String ext=name.substring(name.lastIndexOf(".")+1);
		String temp=pathrule.replace("{yyyymm}",time);
		String doc="pdf,doc,docx,xls,xlsx,ppt,pptx";
		String img="jpg,jpeg,gif,png";
		if(doc.indexOf(ext)!=-1){
			temp=temp.replace("video", "doc");
		}else if(img.indexOf(ext)!=-1){
			temp=temp.replace("video", "imgs");
		}
		UUID uuid=UUID.randomUUID();
		return temp+uuid.toString()+"."+ext;
	}
	private JSONObject getCourses(String n,String s,String keywords) {
		String url = Config.YXTSERVER3 + "oss/knowledge/knowledgeList?n="+n+"&s="+s+"&keywords="+keywords;
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
	/**
	 * 
	 * @Title: add
	 * @Description: 添加知识
	 * @param request
	 * @param fid
	 * @param name
	 * @param cid
	 * @param kngType
	 * @param key
	 * @param userId
	 * @param ckey
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> add(HttpServletRequest request,String fid,String name,String cid,String kngType,String key,String userId,String ckey) {
		Map<String, String> requestParams =new HashMap<String, String>();
		String pid =trans(key,ckey);
		JSONObject jsonObject=JSONObject.fromObject(pid);
		
		requestParams.put("fid", fid);
		requestParams.put("name", name);
		requestParams.put("cid", jsonObject.getString("persistentId"));
		requestParams.put("userId", userId);
		requestParams.put("status", "1");
		String ext=name.substring(name.lastIndexOf(".")+1);
		String video="wmv,mp4,flv,avi,mkv";
		if(video.indexOf(ext)!=-1){
			requestParams.put("kngType", "1");
		}else{
			requestParams.put("kngType", "2");
		}
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
	/**
	 * 
	 * @Title: trans
	 * @Description: 七牛提交转码
	 * @param key
	 * @param ckey
	 * @return String
	 * @throws
	 */
	public String  trans(String key,String ckey) {
		Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
		DigestAuthClient digestAuthClient = new DigestAuthClient(mac);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		String bucket=getMapConfig(ckey).get("bucket").toString();
		String baseurls=getMapConfig(ckey).get("baseUrls").toString();
		JSONArray ja=JSONArray.fromObject(baseurls);
		String baseurl=ja.getString(0);
		nvps.add(new BasicNameValuePair("bucket", bucket));
		nvps.add(new BasicNameValuePair("key",key ));
		String newkey=key.substring(0,key.indexOf("."));
		String m3u8=EncodeUtils.urlsafeEncode(bucket+":"+newkey+".m3u8");
		String jpg=EncodeUtils.urlsafeEncode(bucket+":"+newkey+".jpg");
		String url=baseurl+key+"?avinfo";
		JSONObject jb=getRestApiData(url);
		String streams =jb.get("streams").toString();
		JSONArray stream=JSONArray.fromObject(streams);
		JSONObject format=(JSONObject) jb.get("format");
		String bit_rate=format.get("bit_rate").toString();
		String codec_type=JSONObject.fromObject(stream.get(0)).get("codec_type").toString();
		int size=1280;
		if (codec_type.equals("video")) {
			if(Integer.parseInt(bit_rate)/1024<1280){
				size=Integer.parseInt(bit_rate)/1024;
			}
		}
		nvps.add(new BasicNameValuePair("fops","avthumb/m3u8/vb/"+size+"k/noDomain/1|saveas/"+m3u8+";vframe/jpg/offset/2/w/480/h/360|saveas/"+jpg+";"));
		CallRet call = digestAuthClient.call("http://api.qiniu.com/pfop", nvps);
		if(call.ok()){
			return call.response;
		}else{
			return null;
		}
	}
	@ResponseBody
	@RequestMapping("/getFileSize")
	public long test(String name){
		Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
        RSClient client = new RSClient(mac);
        Entry statRet = client.stat("tpublic", name);
        return statRet.getFsize();
	}

}
