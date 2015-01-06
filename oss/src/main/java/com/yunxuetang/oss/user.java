package com.yunxuetang.oss;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.PoiService;

@Controller
@RequestMapping("/user")
public class user {

	@Resource(name = "poiService")
	public PoiService service;

	public static String YXTSERVER = "http://s1.xuewen.yunxuetang.com:8084/";
	public static String YXTSERVER2 = "http://s1.xuewen.yunxuetang.com:8082/";
	public static String YXTSERVER3 = "http://localhost:8080/";

	public user() {

	}

	/**
	 * 分页获得系统的所有账户 支持搜索功能 字段： 用户名 手机号 邮箱 支持排序 （按时间倒序，正序,其他字段）
	 * 
	 */
	@RequestMapping("/userList")
	private ModelAndView userList(HttpServletRequest request) {
		String courSharResoStr;
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String keyword = null;

		keyword = request.getParameter("keyword");

		if (keyword == null) {
			keyword = "";
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

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.getForObject(YXTSERVER3 + "oss/user/searchbyinfo?n=" + pagenumber + "&s=" + pagelines + "&keyword=" + keyword,
				String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
			modelview.addObject("resuserList", objj);
			String s = objj.getString("msg");
			System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("keyword", keyword);
		modelview.setViewName("user/userList");
		return modelview;
	}

	/**
	 * 一个用户的详细信息 用户加入的群组 用户发的话题在哪个群组里 用户发的干货和课程
	 */
	@RequestMapping("/userDetail")
	private ModelAndView userDetail(HttpServletRequest request) {
		String courSharResoStr;
		String courSharResoStr2;
		String courSharResoStr3;
		String userid = request.getParameter("userid");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.getForObject(YXTSERVER3 + "oss/user/one/" + userid, String.class);
		courSharResoStr2 = restTemplate.getForObject(YXTSERVER3 + "oss/group/myPcGroup/" + userid, String.class);
		courSharResoStr3 = restTemplate.getForObject(YXTSERVER3 + "oss/user/getMyTopic?userid=" + userid, String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
			JSONObject objj2 = JSONObject.fromObject(courSharResoStr2);
			JSONObject objj3 = JSONObject.fromObject(courSharResoStr3);

			modelview.addObject("resuserDetail", objj);
			modelview.addObject("resuserGroup", objj2);
			modelview.addObject("resuserTopic", objj3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("user/userDetail");
		return modelview;

	}

	/**
	 * 生成表单，表单数据提交的处理
	 * 
	 * 
	 */
	@RequestMapping("/createUser")
	private ModelAndView createUser(HttpServletRequest request) {
		// String userName=request.getParameter("userName");
		// String passWord=request.getParameter("passWord");

		String userName = "888777766655";
		String passWord = "123456eeee";
		String courSharResoStr;
		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();
		courSharResoStr = restTemplate.postForObject(YXTSERVER2 + "user/regist?userName=" + userName + "&passWord=" + passWord, null, String.class);
		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
			modelview.addObject("rescreateUser", objj);
		} catch (Exception e) {
			e.printStackTrace();
		}

		modelview.setViewName("show");
		return modelview;
	}

	/**
	 * 
	 * 
	 * 编辑用户信息的表单，处理提交的数据
	 */
	@RequestMapping("/updateUser")
	private ModelAndView updateUser(HttpServletRequest request) {
		String sex = request.getParameter("sex");
		String phoneNumber = request.getParameter("phoneNumber");
		String email = request.getParameter("email");
		String tag = request.getParameter("tag");
		String logoURL = request.getParameter("logoURL");
		String intro = request.getParameter("intro");
		String nickName = request.getParameter("nickName");
		String userid = request.getParameter("userid");

		String courSharResoStr;
		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(YXTSERVER3 + "oss/user/update/" + userid + "?sex=" + sex + "&phoneNumber=" + phoneNumber
				+ "&email=" + email + "&tag=" + tag + "&logoURL=" + logoURL + "&intro=" + intro + "&nickName=" + nickName, null, String.class);
		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);

			modelview.addObject("resupdateUser", objj);
		} catch (Exception e) {
			e.printStackTrace();
		}

		modelview.setViewName("show");
		return modelview;
	}

	/**
	 * 批量导入用户 展示页 1.页面提交多个表单 2.页面excel csv
	 * 
	 * 
	 */
	@RequestMapping("/importUserView")
	private String importUserView(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return "user/report";
	}

	/**
	 * 批量导入用户 1.页面提交多个表单 2.页面excel csv
	 * 
	 * 
	 */
	@RequestMapping(value = "/importUser", method = RequestMethod.POST)
	public String importUser(@RequestParam MultipartFile file, Model model) throws IOException {
		List list = service.readReport(file.getInputStream());
		model.addAttribute("errousers", list);
		return "user/addedReport";

	}

	/**
	 * 创建机器人 1.正常创建一个用户 2.取用户的id存入机器人bean
	 * 
	 * 
	 */
	@RequestMapping("/createRobot")
	private ModelAndView createRobot(HttpServletRequest request) {

		String userName = request.getParameter("userName");
		String passWord = request.getParameter("passWord");

		String courSharResoStr;
		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();
		courSharResoStr = restTemplate.postForObject(YXTSERVER2 + "user/regist?userName=" + userName + "&passWord=" + passWord + "&robot=1", null,
				String.class);
		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
			modelview.addObject("rescreateRobot", objj);
		} catch (Exception e) {
			e.printStackTrace();
		}

		modelview.setViewName("show");
		return modelview;
	}

	/**
	 * 榜单 1.最新注册的100用户 2.加入群组最多的10 3.
	 * 
	 * 
	 */
	@RequestMapping("/userBoard")
	private void userBoard(HttpServletRequest request) {
		// TODO Auto-generated method stub

	}

	/**
	 * 密码重置
	 * 
	 * 
	 */
	@RequestMapping("/resetPassword")
	private ModelAndView resetPassword(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String uid = request.getParameter("uid");
		String password = request.getParameter("password");

		String courSharResoStr;
		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();
		courSharResoStr = restTemplate.postForObject(YXTSERVER3 + "oss/user/resetPassword?uid=" + uid + "&passWord=" + password, null, String.class);
		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
			modelview.addObject("resresetPassword", objj);
		} catch (Exception e) {
			e.printStackTrace();
		}

		modelview.setViewName("show");
		return modelview;

	}

	/**
	 * 密码重置
	 * 
	 * 
	 */
	@RequestMapping("/deleteUser")
	private ModelAndView deleteUser(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String uid = request.getParameter("uid");

		String courSharResoStr;
		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();
		courSharResoStr = restTemplate.postForObject(YXTSERVER3 + "oss/user/deleuser?uid=" + uid, null, String.class);
		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
			modelview.addObject("resdeleteUser", objj);
		} catch (Exception e) {
			e.printStackTrace();
		}

		modelview.setViewName("show");
		return modelview;

	}

	/**
	 * 根据用户id查找出所有课程
	 */
	@RequestMapping("/getMyTopic")
	private ModelAndView getMyTopic(HttpServletRequest request) {
		String courSharResoStr;

		String userid = request.getParameter("userid");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.getForObject(YXTSERVER3 + "oss/user/getMyTopic?userid=" + userid, String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);

			modelview.addObject("resgetMyTopic", objj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("user/userDetail");
		return modelview;

	}

	/**
	 * 根据用户id查找出所有课程
	 */
	@RequestMapping("/getMyDry")
	private ModelAndView getMyDry(HttpServletRequest request) {
		String courSharResoStr;

		String userid = request.getParameter("userid");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.getForObject(YXTSERVER3 + "oss/user/getMyDry?userid=" + userid, String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);

			modelview.addObject("resMyDry", objj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("user/userDetail");
		return modelview;

	}

}
