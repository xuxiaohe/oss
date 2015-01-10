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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.Config;

@Controller
@RequestMapping("/dry")
public class dry {

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
		String courSharResoStr3;

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr3 = restTemplate.getForObject(Config.YXTSERVER3
				+ "oss/dry/getOneDry?dryid=" + dryid, String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj3 = JSONObject.fromObject(courSharResoStr3);

			String url = objj3.getJSONObject("data").getJSONObject("result")
					.getString("url");
			url = URLDecoder.decode(url, "utf-8");

			// List<String> imgUrls = this.getPicFromUrl(url);
			// modelview.addObject("imgUrls", imgUrls);

			modelview.addObject("url", url);

			modelview.addObject("dryDetail", objj3);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
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
		String courSharResoStr;
		String courSharResoStr3;

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.getForObject(Config.YXTSERVER3
				+ "oss/user/one/" + userid, String.class);
		courSharResoStr3 = restTemplate.getForObject(Config.YXTSERVER3
				+ "oss/dry/getOneDry?dryid=" + dryid, String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
			JSONObject objj3 = JSONObject.fromObject(courSharResoStr3);

			String url = objj3.getJSONObject("data").getJSONObject("result")
					.getString("url");
			url = URLDecoder.decode(url, "utf-8");

			// List<String> imgUrls = this.getPicFromUrl(url);
			// modelview.addObject("imgUrls", imgUrls);

			modelview.addObject("url", url);

			modelview.addObject("resuserDetail", objj);
			modelview.addObject("resuserTopic", objj3);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("dry/editForm");
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
		String courSharResoStr3 = "";

		RestTemplate restTemplate = new RestTemplate();

		/**
		 * TODO:修改成POST
		 */
		try {
			Map<String, String> m = new HashMap<String, String>();
			m.put("fileUrl", URLEncoder.encode(fileUrl, "utf-8"));
			m.put("message", message);
			courSharResoStr3 = restTemplate
					.postForObject(Config.YXTSERVER3
							+ "oss/dry/updateOne?dryid=" + dryid, null,
							String.class, m);
			JSONObject objj3 = JSONObject.fromObject(courSharResoStr3);
		} catch (RestClientException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return "redirect:/user/userDry?userid=" + userid;

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
		BufferedReader ii = new BufferedReader(new InputStreamReader(
				uu.openStream())); // //使用openStream得到一输入流并由此构造一个BufferedReader对象
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
		String courSharResoStr;
		// 当前第几页
		String pagenumber = request.getParameter("n");

		if (pagenumber == null) {
			pagenumber = "0";
		}

		// 每页条数

		String pagelines = request.getParameter("s");

		if (pagelines == null) {
			pagelines = "10";
		}

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.getForObject(Config.YXTSERVER3 + "oss/user/searchbyinfo?n=" + pagenumber + "&s=" + pagelines + "&robot=1",
				String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
			modelview.addObject("rescreateTopicByGroupView", objj);
			String s = objj.getString("msg");
			System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("show");
		return modelview;
	}

	/**
	 * 
	 * 用机器人id创建干货
	 */
	@RequestMapping("/createDryByGroup")
	public ModelAndView createDryByGroup(HttpServletRequest request) {
		String courSharResoStr;

		// 必输
		String id = request.getParameter("uid");
		String tagName = request.getParameter("tagName");
		String group = request.getParameter("gid");
		String url = request.getParameter("url");
		String fileUrl = request.getParameter("fileUrl");
		String message = request.getParameter("message");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3 + "oss/dry/uploadDrycargo?id=" + id + "&tagName=" + tagName + "&group=" + group
				+ "&url=" + url + "&fileUrl=" + fileUrl + "&message=" + message, null, String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
			modelview.addObject("rescreateDryByGroup", objj);
			String s = objj.getString("msg");
			System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("show");
		return modelview;
	}

	/**
	 * 
	 * 删除干货
	 */
	@RequestMapping("/deleteDry")
	public String deleteDry(HttpServletRequest request) {
		String courSharResoStr;

		// 必输
		String dryid = request.getParameter("dryid");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3 + "oss/dry/deleteDry?dryCargoId=" + dryid, null, String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/dry/dryList";
	}
	
	
	
	/**
	 * 
	 * 查询所有干货  包括没有关联群组的
	 */
	@RequestMapping("/dryList")
	public ModelAndView dryList(HttpServletRequest request) {
		String courSharResoStr;

		String keyword = request.getParameter("keyword");
		// keyword="test123456";

		// 当前第几页
		String pagenumber = request.getParameter("n");

		if (pagenumber == null) {
			pagenumber = "0";
		}

		// 每页条数

		String pagelines = request.getParameter("s");

		if (pagelines == null) {
			pagelines = "10";
		}
		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		if (keyword == null) {
			courSharResoStr = restTemplate.getForObject(Config.YXTSERVER3
					+ "oss/dry/searchDrys?n=" + pagenumber + "&s=" + pagelines,
					String.class);
		} else {
			courSharResoStr = restTemplate.getForObject(Config.YXTSERVER3
					+ "oss/dry/searchDrys?n=" + pagenumber + "&s=" + pagelines
					+ "&keywords=" + keyword, String.class);
		}

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
			modelview.addObject("Drys", objj);
			String s = objj.getString("msg");
			System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("dry/dryList");
		return modelview;
	}

	

}
