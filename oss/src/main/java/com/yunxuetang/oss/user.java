package com.yunxuetang.oss;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.Config;
import com.yunxuetang.util.PoiService;
import com.yunxuetang.util.Saveimage;

@Controller
@RequestMapping("/user")
public class user extends BaseController {

	@Resource(name = "poiService")
	public PoiService service;
	
	@Autowired
	Saveimage saveimage;

	public user() {

	}

	/**
	 * 分页获得系统的所有账户 支持搜索功能 字段： 用户名 手机号 邮箱 支持排序 （按时间倒序，正序,其他字段）
	 * 
	 */
	@RequestMapping("/userList")
	private ModelAndView userList(HttpServletRequest request) {
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

		ModelAndView modelview = new ModelAndView();

		getUserList(keyword, pagenumber, pagelines);

		JSONObject objj = getUserList(keyword, pagenumber, pagelines);
		modelview.addObject("resuserList", objj);
		String s = objj.getString("msg");
		System.out.println(s);

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("keyword", keyword);
		modelview.setViewName("user/userList");
		return modelview;
	}
	
	
	
	/**
	 * 分页获得系统的所有账户 支持搜索功能 字段： 用户名 手机号 邮箱 支持排序 （按时间倒序，正序,其他字段）
	 * 
	 */
	@RequestMapping("/roboitList")
	private ModelAndView roboitList(HttpServletRequest request) {
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

		ModelAndView modelview = new ModelAndView();

		getUserList(keyword, pagenumber, pagelines);

		JSONObject objj = findRoboit(keyword, pagenumber, pagelines);
		modelview.addObject("resuserList", objj);
		String s = objj.getString("msg");
		System.out.println(s);

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("keyword", keyword);
		modelview.setViewName("user/roboitList");
		return modelview;
	}
	
	
	

	/**
	 * 一个用户的详细信息 用户加入的群组 用户发的话题在哪个群组里 用户发的干货和课程
	 */
	@RequestMapping("/userDetail")
	public ModelAndView userDetail(HttpServletRequest request) {
		String userid = request.getParameter("userid");

		ModelAndView modelview = new ModelAndView();
		JSONObject j=getUserDetail(userid);
		//data.result.openFireUser
		JSONObject j2=(JSONObject) j.get("data");
		JSONObject j3=(JSONObject) j2.get("result");
		System.out.println(j3);
		try {
			JSONObject j4=(JSONObject) j3.get("openFireUser");
			System.out.println(j4.toString());
			if(j4.toString().equals("null")){
				j3.element("openFireUser", "error");
				System.out.println(j3);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			 
			e.printStackTrace();
		}
		
		//modelview.addObject("resuserDetail", getUserDetail(userid));
		modelview.addObject("resuserDetail", j3);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("user/userDetail");
		return modelview;

	}

	/**
	 * 一个用户的详细信息 用户加入的群组 用户发的话题在哪个群组里 用户发的干货和课程
	 */
	@RequestMapping("/userTopic")
	public ModelAndView userTopic(HttpServletRequest request) {
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
		String userid = request.getParameter("userid");

		ModelAndView modelview = new ModelAndView();

		modelview.addObject("resuserDetail", getUserDetail(userid));
		modelview.addObject("resuserTopic", getUserTopic(userid, pagenumber, pagelines));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("user/userTopic");
		return modelview;

	}

	/**
	 * 一个用户的详细信息 用户加入的群组 用户发的话题在哪个群组里 用户发的干货和课程
	 */
	@RequestMapping("/userGroup")
	public ModelAndView userGroup(HttpServletRequest request) {
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
		String userid = request.getParameter("userid");

		ModelAndView modelview = new ModelAndView();

		modelview.addObject("resuserDetail", getUserDetail(userid));
		modelview.addObject("resuserGroup", getUserGroup(userid, pagenumber, pagelines));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("user/userGroup");
		return modelview;

	}

	/**
	 * 一个用户的详细信息 用户加入的群组 用户发的话题在哪个群组里 用户发的干货和课程
	 */
	@RequestMapping("/userDry")
	public ModelAndView userDry(HttpServletRequest request) {
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
		String userid = request.getParameter("userid");

		ModelAndView modelview = new ModelAndView();
		modelview.addObject("resuserDetail", getUserDetail(userid));
		modelview.addObject("resuserTopic", getUserDry(userid, pagenumber, pagelines));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("user/userDry");
		return modelview;

	}

	/**
	 * 一个用户的详细信息 用户加入的群组 用户发的话题在哪个群组里 用户发的干货和课程
	 */
	@RequestMapping("/userCourse")
	public ModelAndView userCourse(HttpServletRequest request) {
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
		String courSharResoStr3;
		String userid = request.getParameter("userid");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr3 = restTemplate.getForObject(Config.YXTSERVER3 + "oss/user/getMyTopic?userid=" + userid + "&n=" + pagenumber + "&s="
				+ pagelines, String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj3 = JSONObject.fromObject(courSharResoStr3);

			modelview.addObject("resuserDetail", getUserDetail(userid));
			modelview.addObject("resuserTopic", objj3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("user/userCourse");
		return modelview;

	}

	/**
	 * 生成表单，表单数据提交的处理
	 * 
	 * 
	 */
	@RequestMapping("/createUser")
	private ModelAndView createUser(HttpServletRequest request) {
		String userName = request.getParameter("userName");
		String passWord = request.getParameter("passWord");

		// String userName = "888777766655";
		// String passWord = "123456eeee";
		String courSharResoStr;
		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();
		modelview.addObject("rescreateUser", getCreateUser(userName, passWord));

		modelview.setViewName("show");
		return modelview;
	}

	/**
	 * 
	 * 
	 * 编辑用户信息的表单，处理提交的数据
	 */
	@RequestMapping("/updateUser")
	private ModelAndView updateUser(HttpServletRequest request,@RequestParam MultipartFile file) {
		String sex = request.getParameter("sex");
		String userid = request.getParameter("userid");
		String phoneNumber = request.getParameter("phoneNumber");
		String email = request.getParameter("email");
		String tag = request.getParameter("tag");
		String robot = request.getParameter("robot");
		String logoURL = request.getParameter("logoURL");
		try {
			if (file.getSize()!=0) {
				String t[]=file.getContentType().split("/");
				String tt="."+t[1];
				Long l=System.currentTimeMillis();
				String urlString="/data/ossImgTemp";
				String urlString2=userid+l+tt;
				InputStream stream=	file.getInputStream();
				logoURL=saveimage.save(urlString, urlString2, stream,"user");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String intro = request.getParameter("intro");
		String nickName = request.getParameter("nickName");
		

		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("resupdateUser", getUpdateUser(userid, sex, phoneNumber, email, tag, logoURL, intro, nickName,robot));

		modelview.setViewName("user/show");
		return modelview;
	}
	
	
	/**
	 * 
	 * 
	 * 编辑用户信息的展示页
	 */
	@RequestMapping("/updateUserForm")
	private ModelAndView updateUserForm(HttpServletRequest request) {
		 
		String userid = request.getParameter("userid");

		ModelAndView modelview = new ModelAndView();

		modelview.addObject("resuserDetail", getUserDetail(userid));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("user/editUser");
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
	@RequestMapping("/createRobotAction")
	private ModelAndView createRobotAction(HttpServletRequest request) {

		String userName = request.getParameter("userName");
		String passWord = request.getParameter("passWord");

		ModelAndView modelview = new ModelAndView();
		try {
			modelview.addObject("rescreateRobot", getCreateRobot(userName, passWord));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("user/show");
		return modelview;
	}
	
	
	/**
	 * 创建机器人  展示页
	 * 
	 * 
	 */
	@RequestMapping("/createRobotForm")
	private ModelAndView createRobotForm(HttpServletRequest request) {


		ModelAndView modelview = new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("user/createUserForm");
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
	 * 密码重置  展示页
	 * 
	 * 
	 */
	@RequestMapping("/resetPasswordForm")
	private ModelAndView resetPasswordForm(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String uid = request.getParameter("uid");

		ModelAndView modelview = new ModelAndView();
		try {
			modelview.addObject("resuserDetail", getUserDetail(uid));
		} catch (Exception e) {
			e.printStackTrace();
		}

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("user/updateUserPasswdForm");
		return modelview;

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

		ModelAndView modelview = new ModelAndView();
		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			modelview.addObject("resresetPassword", getResetPassword(uid, password));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("user/show");
		return modelview;

	}

	/**
	 * 删除用户
	 * 
	 * 
	 */
	@RequestMapping("/deleteUser")
	private String deleteUser(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String uid = request.getParameter("userid");
		getDeleteUser(uid);
		return "redirect:/user/userList";

	}

	/**
	 * 根据用户id查找出所有话题
	 */
	@RequestMapping("/getMyTopic")
	private ModelAndView getMyTopic(HttpServletRequest request) {

		String userid = request.getParameter("userid");

		ModelAndView modelview = new ModelAndView();

		modelview.addObject("resgetMyTopic", getMyTopic(userid));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("user/userDetail");
		return modelview;

	}

	/**
	 * 根据用户id查找出所有干货
	 */
	@RequestMapping("/getMyDry")
	private ModelAndView getMyDry(HttpServletRequest request) {

		String userid = request.getParameter("userid");

		ModelAndView modelview = new ModelAndView();

		modelview.addObject("resMyDry", getMyDry(userid));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("user/userDetail");
		return modelview;

	}

	/**
	 * 根据用户id查找出所有群组
	 */
	@RequestMapping("/getMyGroup")
	private ModelAndView getMyGroup(HttpServletRequest request) {

		String userid = request.getParameter("userid");

		ModelAndView modelview = new ModelAndView();

		modelview.addObject("resMyGroup", getMyGroup(userid));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("user/userDetail");
		return modelview;

	}
	
	
	/**
	 * 根据用户id查找出所有课程
	 */
	@RequestMapping("/getMyCourse")
	private ModelAndView getMyCourse(HttpServletRequest request) {

		String userid = request.getParameter("userid");

		ModelAndView modelview = new ModelAndView();

		modelview.addObject("resMyCourse", getMyCourse(userid));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("user/userDetail");
		return modelview;

	}
	
	/**
	 * 
	 * 为某一个用户id创建群 展示页
	 */
	@RequestMapping("/createGroupForUserForm")
	public ModelAndView createGroupForUserForm(HttpServletRequest request) {
		String courSharResoStr;
		// 当前第几页
		String userid = request.getParameter("userid");
		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("user", userid);

		modelview.setViewName("user/createGroupForm");
		return modelview;
	}
	
	
	/**
	 * 
	 * 为某一个用户id创建群
	 */
	@RequestMapping("/createGroupForUserAction")
	public ModelAndView createGroupForUserAction(HttpServletRequest request) {
		String courSharResoStr;
		String pagenumber = request.getParameter("n");
		if (pagenumber == null) {

			pagenumber = "0";

			}



			// 每页条数



			String pagelines = request.getParameter("s");



			if (pagelines == null) {

			pagelines = "100";

			}
		 
		String userid = request.getParameter("id");
		String groupName = request.getParameter("groupName");
		String groupDesc = request.getParameter("groupDesc");
		ModelAndView modelview = new ModelAndView();
		RestTemplate restTemplate = new RestTemplate();

		// Map<String, String> rp = new HashMap<String, String>();
		//
		// rp.put("id", userid);
		// rp.put("groupName", groupName.trim());
		// rp.put("intro", groupDesc.trim());

		courSharResoStr = restTemplate.postForObject(Config.YXTSERVER3 + "oss/group/create?uid=" + userid + "&groupName=" + groupName + "&intro="
				+ groupDesc, null, String.class);
		JSONObject objj = JSONObject.fromObject(courSharResoStr);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("creategroup", objj);
		modelview.addObject("resuserDetail", getUserDetail(userid));

		modelview.addObject("resuserGroup", getUserGroup(userid, pagenumber, pagelines));
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("user/userGroup");
		return modelview;

	}
	

	private JSONObject getUserList(String keyword, String n, String s) {
		String url = Config.YXTSERVER3 + "oss/user/searchbyinfo?n=" + n + "&s=" + s + "&keyword=" + keyword;
		return getRestApiData(url);
	}

	private JSONObject getUserDetail(String userid) {
		String url = Config.YXTSERVER3 + "oss/user/one/" + userid;
		return getRestApiData(url);
	}

	private JSONObject getUserTopic(String userid, String n, String s) {
		String url = Config.YXTSERVER3 + "oss/user/getMyTopic?userid=" + userid + "&n=" + n + "&s=" + s;
		return getRestApiData(url);
	}

	private JSONObject getUserGroup(String userid, String n, String s) {
		String url = Config.YXTSERVER3 + "oss/group/findMyGroups/" + userid ;
		return getRestApiData(url);
	}

	private JSONObject getUserDry(String userid, String n, String s) {
		String url = Config.YXTSERVER3 + "oss/user/getMyDry?userid=" + userid + "&n=" + n + "&s=" + s;
		return getRestApiData(url);
	}

	private JSONObject getCreateUser(String userName, String passWord) {
		String url = Config.YXTSERVER2 + "user/regist?userName=" + userName + "&passWord=" + passWord;
		return getRestApiData(url);
	}

	private JSONObject getUpdateUser(String userid, String sex, String phoneNumber, String email, String tag, String logoURL, String intro,
			String nickName,String robot) {
		String url = Config.YXTSERVER3 + "oss/user/update/" + userid + "?sex=" + sex + "&phoneNumber=" + phoneNumber + "&email=" + email + "&tag="
				+ tag + "&logoURL=" + logoURL + "&intro=" + intro + "&nickName=" + nickName+ "&robot=" + robot;
		return getRestApiData(url);
	}

	private JSONObject getCreateRobot(String userName, String passWord) {
		String url = Config.YXTSERVER3 + "oss/user/registPc?userName=" + userName + "&passWord=" + passWord + "&robot=1";
		return getRestApiData(url);
	}

	private JSONObject getResetPassword(String uid, String password) {
		String url = Config.YXTSERVER3 + "oss/user/resetPassword?uid=" + uid + "&passWord=" + password;
		return getRestApiData(url);
	}

	private JSONObject getDeleteUser(String uid) {
		String url = Config.YXTSERVER3 + "oss/user/deleuser?uid=" + uid;
		return getRestApiData(url);
	}

	private JSONObject getMyTopic(String userid) {
		String url = Config.YXTSERVER3 + "oss/user/getMyTopic?userid=" + userid;
		return getRestApiData(url);
	}

	private JSONObject getMyDry(String userid) {
		String url = Config.YXTSERVER3 + "oss/user/getMyDry?userid=" + userid;
		return getRestApiData(url);
	}

	private JSONObject getMyGroup(String userid) {
		String url = Config.YXTSERVER3 + "oss/group/findMyGroups/" + userid;
		return getRestApiData(url);
	}
	
	private JSONObject getMyCourse(String userid) {
		String url = Config.YXTSERVER3 + "oss/course/userCourseList/" + userid;
		return getRestApiData(url);
	}
	
	
	private JSONObject findRoboit(String keyword,String n, String s) {
		//String url = Config.YXTSERVER3 + "oss/user/searchbyinfo?n=" + n + "&s=" + s + "&robot=1";
		String url = Config.YXTSERVER3 + "oss/user/searchbyinfo?n=" + n + "&s=" + s + "&robot=1"+ "&keyword=" + keyword;
		return getRestApiData(url);
	}

}
