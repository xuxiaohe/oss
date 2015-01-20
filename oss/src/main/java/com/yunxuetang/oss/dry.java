package com.yunxuetang.oss;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.Config;

@Controller
@RequestMapping("/dry")
public class dry extends BaseController {

	public dry() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 干货详情
	 */
	@RequestMapping("/dryDetail")
	public ModelAndView dryDetail(HttpServletRequest request) {
		// 当前第几页
		String dryid = request.getParameter("dryid");

		ModelAndView modelview = new ModelAndView();

		try {
			JSONObject objj3 = dryDetail(dryid);

			String url = objj3.getJSONObject("data").getJSONObject("result").getString("url");
			url = URLDecoder.decode(url, "utf-8");

			modelview.addObject("url", url);

			modelview.addObject("dryDetail", objj3);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("dry/dryDetail");
		return modelview;

	}

	/**
	 * 干货修改展示页
	 */
	@RequestMapping("/editForm")
	public ModelAndView editForm(HttpServletRequest request) {
		// 当前第几页
		String dryid = request.getParameter("dryid");
		String userid = request.getParameter("userid");

		ModelAndView modelview = new ModelAndView();

		try {
			JSONObject objj3 = getOneDry(dryid);

			String url = objj3.getJSONObject("data").getJSONObject("result").getString("url");
			url = URLDecoder.decode(url, "utf-8");

			modelview.addObject("url", url);
			if(userid!=null){
				modelview.addObject("resuserDetail", getUserDetail(userid));
				
			}
			modelview.addObject("resuserTopic", objj3);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("dry/updateDryForm");
		return modelview;

	}

	/**
	 * 干货修改提交
	 */
	@RequestMapping("/edit")
	public String edit(HttpServletRequest request) {
		// 当前第几页
		String dryid = request.getParameter("dryid");
		String userid = request.getParameter("userid");
		String fileUrl = request.getParameter("fileUrl");

		String message = request.getParameter("message");

		/**
		 * TODO:修改成POST
		 */
		try {
			Map<String, String> m = new HashMap<String, String>();
			m.put("fileUrl", URLEncoder.encode(fileUrl, "utf-8"));
			m.put("message", message);
			edit(dryid, m);
		} catch (RestClientException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(userid!=null){
			return "redirect:/user/userDry?userid=" + userid;
		}
		else {
			return "redirect:/dry/dryList";
		}

		

	}

	private List<String> getPicFromUrl(String url) throws IOException {

		String searchImgReg = "(?x)(src|SRC|background|BACKGROUND)=('|\")/?(([\\w-]+/)*([\\w-]+\\.(jpg|JPG|png|PNG|gif|GIF)))('|\")";
		String searchImgReg2 = "(?x)(src|SRC|background|BACKGROUND)=('|\")(http://([\\w-]+\\.)+[\\w-]+(:[0-9]+)*(/[\\w-]+)*(/[\\w-]+\\.(jpg|JPG|png|PNG|gif|GIF)))('|\")";

		String content = this.getHtmlCode(url);
		System.out.println(content);

		Pattern pattern = Pattern.compile(searchImgReg);
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			System.out.println(matcher.group(3));
			this.getHtmlPicture(url + matcher.group(3));

		}

		pattern = Pattern.compile(searchImgReg2);
		matcher = pattern.matcher(content);

		List<String> urls = new ArrayList<String>();

		while (matcher.find()) {
			if (!urls.contains(matcher.group(3)))
				urls.add(matcher.group(3));
		}
		return urls;
	}

	private String getHtmlCode(String httpUrl) throws IOException {
		String content = "";
		URL uu = new URL(httpUrl); // 创建URL类对象
		BufferedReader ii = new BufferedReader(new InputStreamReader(uu.openStream())); // //使用openStream得到一输入流并由此构造一个BufferedReader对象
		String input;
		while ((input = ii.readLine()) != null) { // 建立读取循环，并判断是否有读取值
			content += input;
		}
		ii.close();
		return content;
	}

	private void getHtmlPicture(String httpUrl) {
		URL url;
		BufferedInputStream in;
		FileOutputStream file;
		try {
			System.out.println("取网络图片");
			String fileName = httpUrl.substring(httpUrl.lastIndexOf("/"));
			String filePath = "F:\\FocuSimple\\test\\src\\pic\\";
			url = new URL(httpUrl);

			in = new BufferedInputStream(url.openStream());

			file = new FileOutputStream(new File(filePath + fileName));
			int t;
			while ((t = in.read()) != -1) {
				file.write(t);
			}
			file.close();
			in.close();
			System.out.println("图片获取成功");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 创建干货展示页 查询机器人
	 */
	@RequestMapping("/createDryByGroupView")
	public ModelAndView createDryByGroupView(HttpServletRequest request) {
		// 当前第几页
		String pagenumber = request.getParameter("n");

		if (pagenumber == null) {
			pagenumber = "0";
		}

		// 每页条数

		String pagelines = request.getParameter("s");

		if (pagelines == null) {
			pagelines = "100";
		}

		ModelAndView modelview = new ModelAndView();

		modelview.addObject("robots", findRoboit(pagenumber, pagelines));
		modelview.addObject("groupList", groupList(pagenumber,pagelines));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("dry/createdryForm");
		return modelview;
	}

	/**
	 * 
	 * 用机器人id创建干货
	 */
	@RequestMapping("/createDryByGroup")
	public String createDryByGroup(HttpServletRequest request) {

		// 必输
		String id = request.getParameter("uid");
		String tagName = request.getParameter("tagName");
		String tagNameArry[]=tagName.split(",");
		List l=new ArrayList();
		for (String a:tagNameArry) {
			 l.add("\""+a+"\"");
		}
		String i=l.toString();
		String group = request.getParameter("gid");
		String url = request.getParameter("url");
		String fileUrl = request.getParameter("fileUrl");
		String message = request.getParameter("message");

		ModelAndView modelview = new ModelAndView();

		modelview.addObject("rescreateDryByGroup", createDryByGroup(id, i, group, url, fileUrl, message));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);

		return "redirect:/dry/dryList";
	}

	/**
	 * 
	 * 删除干货
	 */
	@RequestMapping("/deleteDry")
	public String deleteDry(HttpServletRequest request) {
		// 必输
		String dryid = request.getParameter("dryid");
		deleteDryById(dryid);
		return "redirect:/dry/dryList";
	}

	/**
	 * 
	 * 删除干货For Group
	 */
	@RequestMapping("/deleteDryForGroup")
	public String deleteDryForGroup(HttpServletRequest request) {
		// 必输
		String dryid = request.getParameter("dryid");
		String gid = request.getParameter("gid");
		deleteDryById(dryid);

		return "redirect:/group/groupDry?gid=" + gid;
	}

	/**
	 * 
	 * 删除干货For User
	 */
	@RequestMapping("/deleteDryForUser")
	public String deleteDryForUser(HttpServletRequest request) {
		// 必输
		String dryid = request.getParameter("dryid");
		String userid = request.getParameter("userid");
		deleteDryById(dryid);

		return "redirect:/user/userDry?userid=" + userid;
	}

	/**
	 * 
	 * 查询所有干货 包括没有关联群组的
	 */
	@RequestMapping("/dryList")
	public ModelAndView dryList(HttpServletRequest request) {
		String keyword = request.getParameter("keyword");
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

		modelview.addObject("Drys", dryList(keyword, n, s));
		System.out.print(dryList(keyword, n, s));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("dry/dryList");
		return modelview;
	}

	/**
	 * 
	 * 关联群组
	 */
	@RequestMapping("/updateDryAction")
	public String updateDryAction(HttpServletRequest request) {

		// 必输
		String groupid = request.getParameter("gid");
		String dryid = request.getParameter("dryid");

		ModelAndView modelview = new ModelAndView();

		modelview.addObject("rescreateTopicByGroup", UpdateDryById(dryid, groupid));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		return "redirect:/dry/dryList";
	}
	
	
	/**
	 * 
	 * 关联群组  展示页
	 */
	@RequestMapping("/updateDryForm")
	public ModelAndView updateDryForm(HttpServletRequest request) {

		// 当前第几页
				String dryid = request.getParameter("dryid");

				ModelAndView modelview = new ModelAndView();

				try {
					JSONObject objj3 = getOneDry(dryid);
					modelview.addObject("resuserTopic", objj3);
					modelview.addObject("groupList", groupList("0","100"));
				} catch (Exception e) {
					e.printStackTrace();
				}

				String cpath = request.getContextPath();
				String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
				modelview.addObject("cbasePath", cbasePath);
				modelview.setViewName("dry/bindDryByGroupForm");
				return modelview;
	}

	private JSONObject dryDetail(String dryid) {
		String url = Config.YXTSERVER3 + "oss/dry/getOneDry?dryid=" + dryid;
		return getRestApiData(url);
	}

	private JSONObject getUserDetail(String userid) {
		String url = Config.YXTSERVER3 + "oss/user/one/" + userid;
		return getRestApiData(url);
	}

	private JSONObject getOneDry(String dryid) {
		String url = Config.YXTSERVER3 + "oss/dry/getOneDry?dryid=" + dryid;
		return getRestApiData(url);
	}

	private JSONObject edit(String dryid, Map m) {
		String url = Config.YXTSERVER3 + "oss/dry/updateOne?dryid=" + dryid + "&fileUrl=" + m.get("fileUrl") + "&message=" + m.get("message");
		return getRestApiData(url);
	}

	private JSONObject findRoboit(String n, String s) {
		//String url = Config.YXTSERVER3 + "oss/user/searchbyinfo?n=" + n + "&s=" + s + "&robot=1";
		String url = Config.YXTSERVER3 + "oss/user/searchbyinfo?n=" + n + "&s=" + s + "&robot=1";
		return getRestApiData(url);
	}

	private JSONObject createDryByGroup(String id, String tagName, String group, String url, String fileUrl, String message) {
		String url2 = Config.YXTSERVER3 + "oss/dry/uploadDrycargo?id=" + id + "&tagName=" + tagName + "&group=" + group + "&url=" + url + "&fileUrl="
				+ fileUrl + "&message=" + message;
		return getRestApiData(url2);
	}

	private JSONObject deleteDryById(String dryId) {
		String url = Config.YXTSERVER3 + "oss/dry/deleteDry?dryCargoId=" + dryId;
		return getRestApiData(url);
	}

	private JSONObject dryList(String keyword, String n, String s) {
		String url = null;
		if (keyword == null) {
			url = Config.YXTSERVER3 + "oss/dry/searchDrys?n=" + n + "&s=" + s;
		} else {
			url = Config.YXTSERVER3 + "oss/dry/searchDrys?n=" + n + "&s=" + s + "&keywords=" + keyword;
		}
		return getRestApiData(url);
	}

	private JSONObject UpdateDryById(String dryId, String groupid) {
		String url = Config.YXTSERVER3 + "oss/dry/updateOne?dryid=" + dryId + "&groupid=" + groupid;
		return getRestApiData(url);
	}

}
