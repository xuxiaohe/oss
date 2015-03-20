package com.yunxuetang.oss;

import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.csvreader.CsvWriter;
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
				ctime="2011-02-02";
			}
			if(StringUtil.isBlank(etime)){
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
				etime=format.format(new Date(System.currentTimeMillis()+24*60*60*1000));
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
	
	@RequestMapping("export")	
	@ResponseBody
	public Object export(HttpServletRequest request,
			HttpServletResponse response, String userKey, String qdId,
			String ctime, String etime, Integer s, Integer n) {
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		Map<String, String> res = new HashMap<String, String>();
		Map<String, Object> pMap = new HashMap<String, Object>();
		try {
			if (StringUtil.isBlank(ctime)) {
				ctime = "2011-02-02";
			}
			if (StringUtil.isBlank(etime)) {
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
				etime=format.format(new Date(System.currentTimeMillis()+24*60*60*1000));
			}
			long time1 = DateUtil.toLongtime(ctime);
			long time2 = DateUtil.toLongtime(etime);

			if (!StringUtil.isBlank(qdId)) {
				pMap.put("qdId", qdId);
			}
			if (!StringUtil.isBlank(userKey)) {
				pMap.put("userKey", userKey);
			}
			if (n != null) {
				pMap.put("n", n + "");
			}
			if (s != null) {
				pMap.put("s", s + "");
			}
			pMap.put("ctime", time1 + "");
			pMap.put("etime", time2 + "");
			String resString = HttpUtil.sendPost(Config.YXTSERVER3
					+ "oss/user/log/searchUser", pMap);
			JSONObject object = JSONObject.fromObject(resString);
			String fileName = request.getSession().getServletContext()
					.getRealPath("/");
			String nameString = "/resources/files/"
					+ System.currentTimeMillis() + ".csv";
			fileName = fileName + nameString;
			CsvWriter writer = new CsvWriter(fileName, ',',
					Charset.forName("GBK"));

			JSONObject data = (JSONObject) object.get("data");
			JSONArray result = data.getJSONArray("result");
			String[] header = new String[] { "用户昵称", "邮箱", "手机号码", "渠道商Id",
					"注册时间", "最后登录时间" };
			writer.writeRecord(header);
			for (int i = 0; i < result.size(); i++) {
				JSONObject log = result.getJSONObject(i);
				String[] buffer = new String[6];
				buffer[0] = log.getString("userNick");
				buffer[1] = log.getString("email");
				buffer[2] = log.getString("phoneNumber");
				buffer[3] = log.getString("adSellerId");
				Date ct = new Date(log.getLong("ctime"));
				Date et = new Date(log.getLong("logintime"));
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				buffer[4] = format.format(ct);
				;
				buffer[5] = format.format(et);
				writer.writeRecord(buffer);
			}
			writer.close();
			nameString = cbasePath + nameString;
			res.put("url", nameString);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return res;
		}

	}
	
	/**
	 * 
	 * @Title: vlog
	 * @auther Tangli
	 * @Description: 用户邀请统计
	 * @param key
	 * @param ctime
	 * @param etime
	 * @param s
	 * @param n
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping("vlog")	
	public ModelAndView vlog(HttpServletRequest request,String key,String ctime, String etime, Integer s, Integer n){
		Map<String, Object> pMap = new HashMap<String, Object>();
		ModelAndView mv=new ModelAndView("userlog/vUserLog");
		try {
			if (StringUtil.isBlank(ctime)) {
				ctime = "2011-02-02";
			}
			if (StringUtil.isBlank(etime)) {
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
				etime=format.format(new Date(System.currentTimeMillis()+24*60*60*1000));
			}
			long time1 = DateUtil.toLongtime(ctime);
			long time2 = DateUtil.toLongtime(etime);
			if(!StringUtil.isBlank(key)){
				pMap.put("key", key);
			}
			pMap.put("ctime", time1+"");
			pMap.put("etime", time2+"");
			String resString=HttpUtil.sendPost(Config.YXTSERVER3+"oss/user/log/searchRlog", pMap);
			JSONObject object=JSONObject.fromObject(resString);
			String cpath = request.getContextPath();
			String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
			mv.addObject("cbasePath", cbasePath);
			mv.addObject("resuserList",object);
			mv.addObject("key", key);   
			mv.addObject("ctime", ctime); 
			mv.addObject("etime", etime); 
		}catch(Exception e){
			
		}
		return mv;
	}
	
	
	
	@RequestMapping("exportV")	
	@ResponseBody
	public Object exportV(HttpServletRequest request,
			HttpServletResponse response, String key,String ctime, String etime, Integer s, Integer n) {
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		Map<String, String> res = new HashMap<String, String>();
		Map<String, Object> pMap = new HashMap<String, Object>();
		try {
			if (StringUtil.isBlank(ctime)) {
				ctime = "2011-02-02";
			}
			if (StringUtil.isBlank(etime)) {
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
				etime=format.format(new Date(System.currentTimeMillis()+24*60*60*1000));
			}
			long time1 = DateUtil.toLongtime(ctime);
			long time2 = DateUtil.toLongtime(etime);

			if (!StringUtil.isBlank(key)) {
				pMap.put("key", key);
			}
			if (n != null) {
				pMap.put("n", n + "");
			}
			if (s != null) {
				pMap.put("s", s + "");
			}
			pMap.put("ctime", time1 + "");
			pMap.put("etime", time2 + "");
			String resString = HttpUtil.sendPost(Config.YXTSERVER3
					+ "oss/user/log/searchRlog", pMap);
			JSONObject object = JSONObject.fromObject(resString);
			String fileName = request.getSession().getServletContext()
					.getRealPath("/");
			String nameString = "/resources/files/"
					+ System.currentTimeMillis() + ".csv";
			fileName = fileName + nameString;
			CsvWriter writer = new CsvWriter(fileName, ',',
					Charset.forName("GBK"));

			JSONObject data = (JSONObject) object.get("data");
			JSONArray result = data.getJSONArray("result");
			String[] header = new String[] { "邀请人昵称", "邮箱", "手机号码", "渠道商",
					"成功注册人数", "注册时间" };
			writer.writeRecord(header);
			for (int i = 0; i < result.size(); i++) {
				JSONObject log = result.getJSONObject(i);
				String[] buffer = new String[6];
				buffer[0] = log.getString("vUserNick");
				buffer[1] = log.getString("vUserEmail");
				buffer[2] = log.getString("vUserPhone");
				buffer[3] = log.getString("userNick");
				Date ct = new Date(log.getLong("ctime"));
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				buffer[4] = log.getString("total");
				buffer[5] = format.format(ct);
				writer.writeRecord(buffer);
			}
			writer.close();
			nameString = cbasePath + nameString;
			res.put("url", nameString);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return res;
		}

	}
	
	@RequestMapping("searchVuser")	
	public ModelAndView searchVuser(HttpServletRequest request,String ukey,String vkey,String ctime,String etime,Integer s ,Integer n){
		ModelAndView mv=new ModelAndView("userlog/bvUserLog");
		Map<String, Object>pMap=new HashMap<String, Object>();
		try {
			if(StringUtil.isBlank(ctime)){
				ctime="2011-02-02";
			}
			if(StringUtil.isBlank(etime)){
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
				etime=format.format(new Date(System.currentTimeMillis()+24*60*60*1000));
			}
			long time1=DateUtil.toLongtime(ctime);
			long time2=DateUtil.toLongtime(etime);
			
			if(!StringUtil.isBlank(vkey)){
				pMap.put("vkey", vkey);
			}
			if(!StringUtil.isBlank(ukey)){
				pMap.put("ukey", ukey);
			}
			if(n!=null){
				pMap.put("n", n+"");
			}
			if(s!=null){
				pMap.put("s", s+"");
			}
			pMap.put("ctime", time1+"");
			pMap.put("etime", time2+"");
			String resString=HttpUtil.sendPost(Config.YXTSERVER3+"oss/user/log/search", pMap);
			JSONObject object=JSONObject.fromObject(resString);
			String cpath = request.getContextPath();
			String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
			mv.addObject("cbasePath", cbasePath);
			mv.addObject("resuserList",object);
			mv.addObject("ukey", ukey);   
			mv.addObject("vkey", vkey);   
			mv.addObject("ctime", ctime); 
			mv.addObject("etime", etime); 
			return mv;
		} catch (ParseException e) {
			e.printStackTrace();
			return mv;
		}
		
	}
	
	
	@RequestMapping("exportBV")	
	@ResponseBody
	public Object exportBV(HttpServletRequest request,String ukey,String vkey,String ctime,String etime,Integer s ,Integer n){
		Map<String, Object>pMap=new HashMap<String, Object>();
		Map<String, String> res = new HashMap<String, String>();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		try {
			if(StringUtil.isBlank(ctime)){
				ctime="2011-02-02";
			}
			if(StringUtil.isBlank(etime)){
				etime="2016-03-22";
			}
			long time1=DateUtil.toLongtime(ctime);
			long time2=DateUtil.toLongtime(etime);
			
			if(!StringUtil.isBlank(vkey)){
				pMap.put("vkey", vkey);
			}
			if(!StringUtil.isBlank(ukey)){
				pMap.put("ukey", ukey);
			}
			if(n!=null){
				pMap.put("n", n+"");
			}
			if(s!=null){
				pMap.put("s", s+"");
			}
			pMap.put("ctime", time1+"");
			pMap.put("etime", time2+"");
			String resString=HttpUtil.sendPost(Config.YXTSERVER3+"oss/user/log/search", pMap);
			JSONObject object=JSONObject.fromObject(resString);
			String fileName = request.getSession().getServletContext()
					.getRealPath("/");
			String nameString = "/resources/files/"
					+ System.currentTimeMillis() + ".csv";
			fileName = fileName + nameString;
			CsvWriter writer = new CsvWriter(fileName, ',',
					Charset.forName("GBK"));

			JSONObject data = (JSONObject) object.get("data");
			JSONArray result = data.getJSONArray("result");
			String[] header = new String[] { "被邀请人昵称", "邮箱", "手机号码",
					"成功注册人数", "注册时间","邀请人昵称","邀请人邮箱" };
			writer.writeRecord(header);
			for (int i = 0; i < result.size(); i++) {
				JSONObject log = result.getJSONObject(i);
				String[] buffer = new String[7];
				buffer[0] = log.getString("userNick");
				buffer[1] = log.getString("email");
				buffer[2] = log.getString("phoneNumber");
				buffer[3] = log.getString("ctn");
				Date ct = new Date(log.getLong("ctime"));
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				buffer[4] = format.format(ct);
				buffer[5] = log.getString("vUserNick");
				buffer[6] = log.getString("vUserEmail");
				writer.writeRecord(buffer);
			}
			writer.close();
			nameString = cbasePath + nameString;
			res.put("url", nameString);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return res;
		}
		
	}
	
	
}
