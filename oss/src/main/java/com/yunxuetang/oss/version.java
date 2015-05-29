package com.yunxuetang.oss;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.Config;

@Controller
@RequestMapping("/version")
public class version extends BaseController {

	/**
	 * 分页获得版本信息
	 * 
	 */
	@RequestMapping("/versionList")
	private ModelAndView versionList(HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

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

		ModelAndView modelview = new ModelAndView();

		modelview.addObject("versionList", versionList(pagenumber, pagelines));

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("version/versionList");
		return modelview;
	}

	/**
	 * 
	 * 更新版本 展示页
	 */
	@RequestMapping("/updateVersionForm")
	public ModelAndView updateVersionForm(HttpServletRequest request) {
		String courSharResoStr;
		// 当前第几页
		String id = request.getParameter("id");
		String versionId = request.getParameter("versionId");
		String message = request.getParameter("message");
		String url = request.getParameter("url");
		String context = request.getParameter("context");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.addObject("id", id);
		modelview.addObject("versionId", versionId);
		modelview.addObject("message", message);
		modelview.addObject("url", url);
		modelview.addObject("context", context);

		modelview.setViewName("version/updateVersionForm");
		return modelview;
	}

	/**
	 * 
	 * 更新版本
	 */
	@RequestMapping("/updateVersion")
	public String updateVersion(HttpServletRequest request) {
		String courSharResoStr;
		// 当前第几页
		String id = request.getParameter("id");
		String vnumber = request.getParameter("vnumber");
		String message = request.getParameter("message");
		String url = request.getParameter("url");
		String context = request.getParameter("context");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.addObject("updateVersion", updateVersion(id, vnumber, message, url, context));

		return "redirect:/version/versionList";
	}

	/**
	 * 
	 * 创建版本 展示页
	 */
	@RequestMapping("/createVersionForm")
	public ModelAndView createVersionForm(HttpServletRequest request) {

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);

		modelview.setViewName("version/createVersionForm");
		return modelview;
	}

	/**
	 * 
	 * 创建版本
	 */
	@RequestMapping("/createVersionAction")
	public String createVersionAction(HttpServletRequest request) {
		String courSharResoStr;
		// 当前第几页
		String vdomain = request.getParameter("vdomain");
		String vappkey = request.getParameter("vappkey");
		String vcertificate = request.getParameter("vcertificate");
		String vphase = request.getParameter("vphase");
		String vnumber = request.getParameter("vnumber");
		String message = request.getParameter("message");
		String url = request.getParameter("url");
		String context = request.getParameter("context");
		String versionId = request.getParameter("versionId");
		String device = request.getParameter("device");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.addObject("updateVersion", createVersion(vdomain, vappkey, vcertificate, vphase, vnumber, message, url, context,versionId,device));

		return "redirect:/version/versionList";
	}

	private JSONObject versionList(String n, String s) {
		String url = Config.YXTSERVER3 + "version/versionList?n=" + n + "&s=" + s;
		return getRestApiData(url);
	}

	private JSONObject updateVersion(String id, String vnumber, String message, String url, String context) {
		String url1 = Config.YXTSERVER3 + "version/updateVersion?id=" + id + "&vnumber=" + vnumber + "&message=" + message + "&url=" + url
				+ "&context=" + context;
		return getRestApiData(url1);
	}

	private JSONObject createVersion(String vdomain, String vappkey, String vcertificate, String vphase, String vnumber, String message, String url,
			String context,String versionId,String device) {
		String url1 = Config.YXTSERVER3 + "version/updateVersion?vdomain=" + vdomain + "&vappkey=" + vappkey + "&vcertificate=" + vcertificate + "&vphase=" + vphase
				+ "&vnumber=" + vnumber+ "&message=" + message+ "&url=" + url+ "&context=" + context+ "&versionId=" + versionId+ "&device=" + device;
		return getRestApiData(url1);
	}
}
