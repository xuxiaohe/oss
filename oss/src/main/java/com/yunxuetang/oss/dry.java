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

@Controller
@RequestMapping("/dry")
public class dry {
	public static String YXTSERVER = "http://s1.xuewen.yunxuetang.com:8084/";
	public static String YXTSERVER2 = "http://s1.xuewen.yunxuetang.com:8082/";
	public static String YXTSERVER3 = "http://localhost:8080/";

	public dry() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 一个用户的详细信息 用户加入的群组 用户发的话题在哪个群组里 用户发的干货和课程
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

		courSharResoStr = restTemplate.getForObject(YXTSERVER3
				+ "oss/user/one/" + userid, String.class);
		courSharResoStr3 = restTemplate.getForObject(YXTSERVER3
				+ "oss/dry/getOneDry?dryid=" + dryid, String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
			JSONObject objj3 = JSONObject.fromObject(courSharResoStr3);

			String url = objj3.getJSONObject("data").getJSONObject("result")
					.getString("url");
			url = URLDecoder.decode(url, "utf-8");

//			List<String> imgUrls = this.getPicFromUrl(url);
//			modelview.addObject("imgUrls", imgUrls);

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
	 * 一个用户的详细信息 用户加入的群组 用户发的话题在哪个群组里 用户发的干货和课程
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
			Map m=new HashMap();
			m.put("fileUrl", URLEncoder.encode(fileUrl, "utf-8"));
			m.put("message", message);
			courSharResoStr3 = restTemplate.postForObject(YXTSERVER3
					+ "oss/dry/updateOne?dryid=" + 
					dryid,m, String.class);
			JSONObject objj3 = JSONObject.fromObject(courSharResoStr3);
		} catch (RestClientException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return "redirect:/user/userDry?userid="+userid;  

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

}
