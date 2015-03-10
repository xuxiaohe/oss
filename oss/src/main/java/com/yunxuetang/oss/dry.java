package com.yunxuetang.oss;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.AutoExecuteTaskController;
import com.yunxuetang.util.Config;
import com.yunxuetang.util.FetchedPage;
import com.yunxuetang.util.PageFetcher;
import com.yunxuetang.util.Saveimage;
import com.yunxuetang.util.qiniu;


@Controller
@RequestMapping("/dry")
public class dry extends BaseController {

	@Autowired
	Saveimage saveimage;
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
			modelview.addObject("resTopicPost", findPost(dryid));
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
			if (userid != null) {
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
	public String edit(HttpServletRequest request,@RequestParam MultipartFile file) {
		// 当前第几页
		String dryid = request.getParameter("dryid");
		String userid = request.getParameter("userid");
		String fileUrl = request.getParameter("fileUrl");

		try {
			String t[]=file.getContentType().split("/");
			String tt="."+t[1];

		if (file.getSize()!=0) {

		Long l=System.currentTimeMillis();

		String urlString="/data/ossImgTemp";

		String urlString2=userid+l+tt;

		InputStream stream=	file.getInputStream();

		fileUrl=saveimage.save(urlString, urlString2, stream, "dry");

		}

		} catch (Exception e) {

		// TODO Auto-generated catch block

		e.printStackTrace();

		}


		String message = request.getParameter("message");
		String description = request.getParameter("description");
		/**
		 * TODO:修改成POST
		 */
		try {
			Map<String, String> m = new HashMap<String, String>();
			m.put("fileUrl", URLEncoder.encode(fileUrl, "utf-8"));
			m.put("message", message);
			m.put("description", description);
			edit(dryid, m);
		} catch (RestClientException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (userid != null) {
			return "redirect:/user/userDry?userid=" + userid;
		} else {
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
		modelview.addObject("groupList", groupList(pagenumber, pagelines));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("dry/createdryForm");
		return modelview;
	}
	
	
	
	
	/**
	 * 
	 * 创建干货展示页 查询机器人
	 */
	@RequestMapping("/catchDryByGroupView")
	public ModelAndView catchDryByGroupView(HttpServletRequest request) {
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
		modelview.addObject("groupList", groupList(pagenumber, pagelines));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("dry/catchDry");
		return modelview;
	}

	/**
	 * 
	 * 用机器人id创建干货
	 */
	@RequestMapping("/createDryByGroup")
	public String createDryByGroup(HttpServletRequest request,@RequestParam MultipartFile file) {

		// 必输
		String id = request.getParameter("uid");
		String tagName = request.getParameter("tagName");
		String tagNameArry[] = tagName.split(",");
		List<String> l = new ArrayList<String>();
		for (String a : tagNameArry) {
			l.add("\"" + a + "\"");
		}
		String i = l.toString();
		String group = request.getParameter("gid");
		String url = request.getParameter("url");
		String fileUrl = "";
		try {

			String t[]=file.getContentType().split("/");
			String tt="."+t[1];
			if (file.getSize()!=0) {

			Long l1=System.currentTimeMillis();

			String urlString="/data/ossImgTemp";

			String urlString2=id+l1+tt;

			InputStream stream=	file.getInputStream();

			fileUrl=saveimage.save(urlString, urlString2, stream,"dry");

			}

			} catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

			}
		
		
		String message = request.getParameter("message");
		// 干货的抓取的描述
		String description = request.getParameter("description");
		// 干货与炫页标示，0干货1炫页
		String dryFlag = "0";

		ModelAndView modelview = new ModelAndView();

		modelview.addObject("rescreateDryByGroup", createDryByGroup(id, i, group, url, fileUrl, message, description, dryFlag));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);

		return "redirect:/dry/dryList";
	}
	
	
	
	
	
	/**
	 * 
	 * 用机器人id创建干货  处理抓取的干货
	 */
	@RequestMapping("/createDryByCatch")
	public String createDryByCatch(HttpServletRequest request) {

		// 必输
		String id = request.getParameter("uid");
		String tagName = request.getParameter("tagName");
		String tagNameArry[] = tagName.split(",");
		List<String> l = new ArrayList<String>();
		for (String a : tagNameArry) {
			l.add("\"" + a + "\"");
		}
		String i = l.toString();
		String group = request.getParameter("gid");
		String url = request.getParameter("url");
		String fileUrl = request.getParameter("fileUrl");
		AutoExecuteTaskController a=new AutoExecuteTaskController();
		String u=a.handleSmallPic(fileUrl, "/data/ossImgTemp/");
		String uu[]=u.split("/");
		String urlString="/"+uu[1]+"/"+uu[2];
		String urlString2=uu[3];
		try {
			qiniu qn=new qiniu();
			fileUrl=  qn.xixi(urlString, urlString2,"dry");
		     
		     File file=new File(urlString + "/"+ urlString2);
		       file.delete();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String message = request.getParameter("message");
		// 干货的抓取的描述
		String description = request.getParameter("description");
		// 干货与炫页标示，0干货1炫页
		String dryFlag = "0";

		ModelAndView modelview = new ModelAndView();

		modelview.addObject("rescreateDryByGroup", createDryByGroup(id, i, group, url, fileUrl, message, description, dryFlag));
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
		deleteDryByGroup(dryid,gid);

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
	 * 关联群组 展示页
	 */
	@RequestMapping("/updateDryForm")
	public ModelAndView updateDryForm(HttpServletRequest request) {

		// 当前第几页
		String dryid = request.getParameter("dryid");

		ModelAndView modelview = new ModelAndView();

		try {
			JSONObject objj3 = getOneDry(dryid);
			modelview.addObject("resuserTopic", objj3);
			modelview.addObject("groupList", groupList("0", "100"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("dry/bindDryByGroupForm");
		return modelview;
	}
	
	
	/**
	 * 
	 * 根据话题id删除主楼回复
	 */
	@RequestMapping("/deletePostByDryId")
	public ModelAndView deletePostByTopicId(HttpServletRequest request) {

		// 当前第几页
		String dryid = request.getParameter("dryid");
		String postid = request.getParameter("postid");
		ModelAndView modelview = new ModelAndView();
			 
		modelview.addObject("resuserTopic", deletePost(dryid, postid));
		modelview.addObject("dryDetail", dryDetail(dryid));

		modelview.addObject("resTopicPost", findPost(dryid));

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("dry/dryDetail");
		return modelview;
	}

	/**
	 * 
	 * 根据主楼id删除副楼回复
	 */
	@RequestMapping("/deleteSubPostByDryId")
	public ModelAndView deleteSubPostByTopicId(HttpServletRequest request) {
		ModelAndView modelview = new ModelAndView();
		// 当前第几页
		String postid = request.getParameter("postid");
		String dryid = request.getParameter("dryid");
		String subpostid = request.getParameter("subpostid");
		String index = request.getParameter("index");
		if(subpostid==null){
			subpostid="";
		}
		 
			modelview.addObject("subpostList", deleteSubPost(postid, subpostid, index));
			modelview.addObject("dryDetail", dryDetail(dryid));

			modelview.addObject("resTopicPost", findPost(dryid));


		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("dry/dryDetail");
		return modelview;
	}
	
	
	/**
	 * 
	 * 根据话题id添加主楼回复 展示页
	 */
	@RequestMapping("/addPostByDryIdForm")
	public ModelAndView addPostByTopicIdForm(HttpServletRequest request) {

		// 当前第几页
		String dryid = request.getParameter("dryid");

		ModelAndView modelview = new ModelAndView();

		modelview.addObject("dryid", dryid);
		modelview.addObject("robots", findRoboit("0", "100"));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("dry/addpost");
		return modelview;
	}

	/**
	 * 
	 * 根据话题id添加主楼回复
	 */
	@RequestMapping("/addPostByDryIdAction")
	public ModelAndView addPostByTopicIdAction(HttpServletRequest request) {

		// 当前第几页
		String dryid = request.getParameter("dryid");
		String uid = request.getParameter("uid");
		String appKey = request.getParameter("appKey");
		String type = request.getParameter("type");
		String message = request.getParameter("message");
		String fileUrl = request.getParameter("fileUrl");

		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("addpost", addPost(uid, message, dryid, appKey, type, fileUrl,dryid));
		modelview.addObject("dryDetail", dryDetail(dryid));

		modelview.addObject("resTopicPost", findPost(dryid));
		modelview.setViewName("dry/dryDetail");
		return modelview;
	}
	
	
	/**
	 * 
	 * 添加副楼回复 展示页
	 */
	@RequestMapping("/addSubPostForm")
	public ModelAndView addSubPostForm(HttpServletRequest request) {

		// 当前第几页
		String dryid = request.getParameter("dryid");
		String postid = request.getParameter("postid");
		
		
		ModelAndView modelview = new ModelAndView();
		
		modelview.addObject("dryid", dryid);
		modelview.addObject("postid", postid);
		modelview.addObject("robots", findRoboit("0", "100"));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("dry/addsubpost");
		return modelview;
	}

	/**
	 * 
	 * 添加副楼回复
	 */
	@RequestMapping("/addSubPostAction")
	public ModelAndView addSubPostAction(HttpServletRequest request) {

		// 当前第几页
		String dryid = request.getParameter("dryid");
		String parentid = request.getParameter("postid");
		String uid = request.getParameter("uid");
		String appKey="yxtapp";
		String type = request.getParameter("type");
		String message = request.getParameter("message");
		String fileUrl = request.getParameter("fileUrl");

		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("addpost", addSubPost(uid, message, dryid, appKey, type, fileUrl,parentid));
		modelview.addObject("dryDetail", dryDetail(dryid));

		modelview.addObject("resTopicPost", findPost(dryid));
		modelview.setViewName("dry/dryDetail");
		return modelview;
	}
	
	
	
	/**
	 * 
	 * 抓取干货
	 */
	@RequestMapping("/catchDryUrl")
	public ModelAndView catchDryUrl(HttpServletRequest request) {

		String pagenumber = request.getParameter("n");

		if (pagenumber == null) {
			pagenumber = "0";
		}

		// 每页条数

		String pagelines = request.getParameter("s");

		if (pagelines == null) {
			pagelines = "100";
		}
		String url = request.getParameter("url");
		  PageFetcher fetcher = new PageFetcher();
		
		AutoExecuteTaskController a=new AutoExecuteTaskController();
		
		 FetchedPage  fetchedPage = fetcher.getContentFromUrl(url);
		 Map  m=  a.fetchData(url,fetchedPage);
		 
		
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("url", m.get("url"));
		modelview.addObject("message", m.get("message"));
		modelview.addObject("image", m.get("image"));
		modelview.addObject("robots", findRoboit(pagenumber, pagelines));
		modelview.addObject("groupList", groupList(pagenumber, pagelines));
		modelview.setViewName("dry/catchDryAction");
		return modelview;
	}
	
	/**
	 * 
	 * 添加排行榜  展示页
	 */
	@RequestMapping("/addDryBoxForm")
	public ModelAndView addDryBoxForm(HttpServletRequest request) {

		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);

		modelview.setViewName("dry/createdryBoxForm");
		return modelview;
	}
	
	
	
	/**
	 * 
	 * 添加排行榜  
	 */
	@RequestMapping("/addDryBoxAction")
	public String addDryBoxAction(HttpServletRequest request) {
		String chinaName = request.getParameter("chinaName");
		String englishName = request.getParameter("englishName");
		String local = request.getParameter("local");
		String size = request.getParameter("size");
		String type = "dry";

		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		
		modelview.addObject("addDryBox", addDryBox(chinaName, englishName, local, type,size));
		return "redirect:/dry/dryList";
	}
	
	

	/**
	 * 
	 * 排行榜未绑定列表详情页 初始页  干货
	 */
	@RequestMapping("/DryBoxDetail")
	public ModelAndView DryBoxDetail(HttpServletRequest request) {
		 
		String type = "dry";
		
		JSONObject objj = dryboxpost(type);
		
		JSONObject objj2 =objj.getJSONObject("data");
		
		JSONArray objj3 =objj2.getJSONArray("result");
		
		JSONObject objj4=(JSONObject) objj3.get(0);
		
		String objj5=objj4.getString("id");
		String objj6=objj4.getString("chinaName");
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		
		modelview.addObject("addDryBoxposition", objj);
		modelview.addObject("name", objj6);
		modelview.addObject("id", objj5);
		modelview.addObject("addDryBoxList", getdryboxlist("0", objj5,"0","10"));
		modelview.setViewName("dry/dryBoxList");
		return modelview;
	}
	
	
	
	/**
	 * 
	 * 查询未关联排行版的干货列表
	 */
	@RequestMapping("/DryBoxList")
	public ModelAndView DryBoxList(HttpServletRequest request) {
		 
		String pagenumber = request.getParameter("n");

		if (pagenumber == null) {
			pagenumber = "0";
		}

		// 每页条数

		String pagelines = request.getParameter("s");

		if (pagelines == null) {
			pagelines = "10";
		}
		
		String type = "dry";
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		 
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		
		modelview.addObject("addDryBoxposition", dryboxpost(type));
		modelview.addObject("name", name);
		modelview.addObject("id", id);
		modelview.addObject("addDryBoxList", getdryboxlist("0", id,pagenumber,pagelines));
		modelview.setViewName("dry/dryBoxList");
		return modelview;
	}
	
	
	/**
	 * 
	 * 查询未关联排行版的干货列表
	 */
	@RequestMapping("/searchDryBoxList")
	public ModelAndView searchDryBoxList(HttpServletRequest request) {
		 
		String keyword = request.getParameter("keyword");
		String boxPostId = request.getParameter("id");
		String dryFlag="0";
		
		String type = "dry";
		 
		String name = request.getParameter("name");
		 
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		
		modelview.addObject("addDryBoxposition", dryboxpost(type));
		modelview.addObject("name", name);
		modelview.addObject("id", boxPostId);
		modelview.addObject("addDryBoxList", getdryboxlistNotIn(dryFlag, boxPostId, keyword));
		modelview.setViewName("dry/dryBoxList");
		return modelview;
	}
	
	
	/**
	 * 
	 * 干货关联到具体的排行榜
	 */
	@RequestMapping("/bindBoxDry")
	public ModelAndView bindBoxDry(HttpServletRequest request) {
		String type = "dry";
		String sourceType = "dry";
		String name = request.getParameter("name");
		//位置id
		String boxPostId = request.getParameter("boxPostId");
		//干货id
		String sourceId = request.getParameter("sourceId");
		 
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		
	 
		modelview.addObject("addDryBoxList", bindBoxDry(boxPostId, sourceType, sourceId));
		
		modelview.addObject("addDryBoxposition", dryboxpost(type));
		modelview.addObject("name", name);
		modelview.addObject("id", boxPostId);
		modelview.addObject("addDryBoxList", getdryboxlist("0", boxPostId,"0","10"));
		
		modelview.setViewName("dry/dryBoxList");
		return modelview;
	}
	
	
	/**
	 * 
	 * 排行榜已绑定列表详情页 初始页  干货
	 */
	@RequestMapping("/DryBoxBindedDetail")
	public ModelAndView DryBoxBindedDetail(HttpServletRequest request) {
		 
		String type = "dry";
		
		JSONObject objj = dryboxpost(type);
		
		JSONObject objj2 =objj.getJSONObject("data");
		
		JSONArray objj3 =objj2.getJSONArray("result");
		
		JSONObject objj4=(JSONObject) objj3.get(0);
		
		String objj5=objj4.getString("id");
		String objj6=objj4.getString("chinaName");
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		
		modelview.addObject("addDryBoxposition", objj);
		modelview.addObject("name", objj6);
		modelview.addObject("id", objj5);
		modelview.addObject("addDryBoxList", drycargoInBox(objj5,"0","10"));
		modelview.setViewName("dry/dryInBoxList");
		return modelview;
	}
	
	
	/**
	 * 
	 * 查询已关联排行版的干货列表
	 */
	@RequestMapping("/DryInBoxList")
	public ModelAndView DryInBoxList(HttpServletRequest request) {
		
		String pagenumber = request.getParameter("n");

		if (pagenumber == null) {
			pagenumber = "0";
		}

		// 每页条数

		String pagelines = request.getParameter("s");

		if (pagelines == null) {
			pagelines = "10";
		}
		 
		String type = "dry";
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		 
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		
		modelview.addObject("addDryBoxposition", dryboxpost(type));
		modelview.addObject("name", name);
		modelview.addObject("id", id);
		modelview.addObject("addDryBoxList", drycargoInBox(id,pagenumber,pagelines));
		modelview.setViewName("dry/dryInBoxList");
		return modelview;
	}
	
	
	/**
	 * 
	 * 干货取消关联到具体的排行榜
	 */
	@RequestMapping("/unbindBoxDry")
	public ModelAndView unbindBoxDry(HttpServletRequest request) {
		String type = "dry";
		 
		String name = request.getParameter("name");
		//位置id
		String boxPostId = request.getParameter("boxPostId");
		//排行榜id
		String boxId = request.getParameter("boxId");
		 
		 
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		
	 
		modelview.addObject("addDryBoxList", deleteBox(boxId));
		
		modelview.addObject("addDryBoxposition", dryboxpost(type));
		modelview.addObject("name", name);
		modelview.addObject("id", boxPostId);
		modelview.addObject("addDryBoxList", drycargoInBox(boxPostId,"0","10"));
		
		modelview.setViewName("dry/dryInBoxList");
		return modelview;
	}
	
	
	/**
	 * 
	 * 干货排行榜列表
	 */
	@RequestMapping("/BoxDryList")
	public ModelAndView BoxDryList(HttpServletRequest request) {
		String type = "dry";
		 
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		
		modelview.addObject("addDryBoxposition", dryboxpost(type));
		
		modelview.setViewName("toplist/dryBoxPostList");
		return modelview;
	}
	
	
	
	/**
	 * 
	 * 干货排行榜删除
	 */
	@RequestMapping("/BoxDryListDelete")
	public String  BoxDryListDelete(HttpServletRequest request) {
		String boxId = request.getParameter("boxId");
		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("addDryBoxList", deleteBoxPost(boxId));
		
		return "redirect:/dry/BoxDryList";
	}
	
	
//	/**
//	 * 
//	 * 干货排行榜删除
//	 */
//	@RequestMapping("/ajaxTest")
//	public ModelAndView  ajaxTest(HttpServletRequest request) {
//		 
//		ModelAndView modelview = new ModelAndView();
//		String cpath = request.getContextPath();
//		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
//		modelview.addObject("cbasePath", cbasePath);
//		modelview.addObject("serverPath", Config.YXTSERVER3);
//		modelview.setViewName("ajaxTest/ajaxTest");
//		return modelview;
//	}
	
	

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
		String url = Config.YXTSERVER3 + "oss/dry/updateOne?dryid=" + dryid + "&fileUrl=" + m.get("fileUrl") + "&message=" + m.get("message")+ "&description=" + m.get("description");
		return getRestApiData(url);
	}

	private JSONObject findRoboit(String n, String s) {
		// String url = Config.YXTSERVER3 + "oss/user/searchbyinfo?n=" + n +
		// "&s=" + s + "&robot=1";
		String url = Config.YXTSERVER3 + "oss/user/searchbyinfo?n=" + n + "&s=" + s + "&robot=1";
		return getRestApiData(url);
	}

	private JSONObject createDryByGroup(String id, String tagName, String group, String url, String fileUrl, String message, String description,
			String dryFlag) {
		String url2 = Config.YXTSERVER3 + "oss/dry/uploadDrycargo?uid=" + id + "&tagName=" + tagName + "&group=" + group + "&url=" + url + "&fileUrl="
				+ fileUrl + "&message=" + message + "&description=" + description + "&dryFlag=" + dryFlag;
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
	
	private JSONObject findPost(String dryid) {
		String url = Config.YXTSERVER3 + "oss/dry/searchAllPostAndSubPost?dryid=" + dryid;
		return getRestApiData(url);
	}
	
	private JSONObject deletePost(String topicid,String postid) {
		String url = Config.YXTSERVER3 + "oss/topic/deletePost?topicid=" + topicid+"&postid="+postid;
		return getRestApiData(url);
	}
	
	
	private JSONObject deleteSubPost(String postid,String subpostid,String index) {
		String url = Config.YXTSERVER3 + "oss/topic/deleteSubPost?postid=" + postid+"&subpostid="+subpostid+"&index="+index;
		return getRestApiData(url);
	}
	
	private JSONObject addPost(String uid, String message,String dryid,String appKey,String type,String fileUrl,String topicId) {
		String url = Config.YXTSERVER3 + "oss/dry/replyDrycargo?uid=" + uid + "&topicId=" + topicId + "&appKey=" + appKey + "&type=" + type
				+ "&message=" + message + "&fileUrl=" + fileUrl+ "&dryid=" + dryid;
		return getRestApiData(url);
	}
	
	private JSONObject addSubPost(String uid, String message,String topicId,String appKey,String type,String fileUrl,String parentId) {
		String url = Config.YXTSERVER3 + "oss/topic/replyPost?uid=" + uid + "&topicId=" + topicId + "&appKey=" + appKey + "&type=" + type
				+ "&message=" + message + "&fileUrl=" + fileUrl+ "&parentId=" + parentId;
		return getRestApiData(url);
	}
	
	private JSONObject deleteDryByGroup(String dryId,String gid) {
		String url = Config.YXTSERVER3 + "oss/dry/deleteDryByGroup?dryid=" + dryId+"&gid="+gid;
		return getRestApiData(url);
	}
	
	private JSONObject addDryBox(String chinaName,String englishName,String local,String type,String size) {
		String url = Config.YXTSERVER3 + "oss/box/addBoxPost?chinaName=" + chinaName+"&englishName="+englishName+"&local="+local+"&type="+type+"&size="+size;
		return getRestApiData(url);
	}
	
	private JSONObject dryboxpost(String type) {
		String url = Config.YXTSERVER3 + "oss/box/getBoxPostByType?type=" + type;
		return getRestApiData(url);
	}
	 
	
	private JSONObject getdryboxlist(String dryFlag,String boxPostId,String n,String s) {
		String url = Config.YXTSERVER3 + "oss/box/drycargoListNotInBoxPost?dryFlag=" + dryFlag+"&boxPostId="+boxPostId+"&n="+n+"&s="+s;
		return getRestApiData(url);
	}
	
	private JSONObject getdryboxlistNotIn(String dryFlag,String boxPostId,String keyword) {
		String url = Config.YXTSERVER3 + "oss/box/searchDrycargoNotInBoxPost?dryFlag=" + dryFlag+"&boxPostId="+boxPostId+"&keyword="+keyword;
		return getRestApiData(url);
	}
	
	private JSONObject bindBoxDry(String boxPostId,String sourceType,String sourceId) {
		String url = Config.YXTSERVER3 + "oss/box/addBoxInBoxPost?boxPostId=" + boxPostId+"&sourceType="+sourceType+"&sourceId="+sourceId;
		return getRestApiData(url);
	}
	
	private JSONObject drycargoInBox(String boxPostId,String n,String s) {
		String url = Config.YXTSERVER3 + "oss/box/drycargoInBox?boxPostId=" + boxPostId+"&n="+n+"&s="+s;
		return getRestApiData(url);
	}
	
	private JSONObject deleteBox(String boxId) {
		String url = Config.YXTSERVER3 + "oss/box/deleteBox?boxId=" + boxId;
		return getRestApiData(url);
	}
	
	private JSONObject deleteBoxPost(String boxId) {
		String url = Config.YXTSERVER3 + "oss/box/deleteBoxPost?id=" + boxId;
		return getRestApiData(url);
	}

}
