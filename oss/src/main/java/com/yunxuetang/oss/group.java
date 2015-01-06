package com.yunxuetang.oss;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.JSON2ObjUtil;
import com.yunxuetang.util.ReponseData;
import com.yunxuetang.util.ResponseContainer;
import com.yunxuetang.util.ResponseGroup;

@Controller
@RequestMapping("/group")
public class group {

	public static String YXTSERVER = "http://s1.xuewen.yunxuetang.com:8084/";
	public static String YXTSERVER2 = "http://s1.xuewen.yunxuetang.com:8082/";
	public static String YXTSERVER3 = "http://localhost:8080/";

	/**
	 * 
	 * 根据条件查找群组
	 */
	@RequestMapping("/searchGroup")
	private ModelAndView searchGroup(HttpServletRequest request) {
		String courSharResoStr;
		// String courSharResoStr2;
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
			courSharResoStr = restTemplate.getForObject(YXTSERVER3 + "oss/group/search?n=" + pagenumber + "&s=" + pagelines, String.class);
		} else {
			courSharResoStr = restTemplate.getForObject(YXTSERVER3 + "oss/group/search?n=" + pagenumber + "&s=" + pagelines + "&keyword=" + keyword,
					String.class);
		}

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
			// ResponseContainer rdData = (ResponseContainer)
			// JSON2ObjUtil.getDTO(courSharResoStr, ResponseContainer.class);
			// ReponseData rData = (ReponseData)rdData.getData();
			// List<ResponseGroup> rg = (List<ResponseGroup> )rData.getResult();
			// JSONObject objj2=JSONObject.fromObject(courSharResoStr2);
			// JSONObject tt=(JSONObject) objj.get("data");
			// JSONArray ttt=(JSONArray) tt.get("result");
			// JSONArray t = ttt.getJSONArray("content");

			// JSONObject tttt=(JSONObject) t.get(0);
			// List<ResponseGroup> rg
			// =(List<ResponseGroup>)JSON2ObjUtil.getDTOList(ttt.toString(),
			// ResponseGroup.class);

			// System.out.print(t.get(0));
			modelview.addObject("groupList", objj);
			// modelview.addObject("resuserGroup", objj2);
		} catch (Exception e) {
			e.printStackTrace();
		}

		modelview.setViewName("show");
		return modelview;

	}

	/**
	 * 
	 * 创建群展示页 查询机器人
	 */
	@RequestMapping("/createGroupView")
	public ModelAndView createGroupView(HttpServletRequest request) {
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

		courSharResoStr = restTemplate.getForObject(YXTSERVER3 + "oss/user/searchbyinfo?n=" + pagenumber + "&s=" + pagelines + "&robot=1",
				String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
			modelview.addObject("ressearchGroupView", objj);
			String s = objj.getString("msg");
			System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		}

		modelview.setViewName("show");
		return modelview;
	}

	/**
	 * 
	 * 用机器人id创建群
	 */
	@RequestMapping("/createGroup")
	public ModelAndView createGroup(HttpServletRequest request) {
		String courSharResoStr;
		// 当前第几页
		String id = request.getParameter("id");
		id = "54a8f1d9e4b0df8f6b1b550b";
		// 每页条数

		String groupName = request.getParameter("groupName");
		groupName = "test123456";

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(YXTSERVER3 + "oss/group/create?groupName=" + groupName + "&id=" + id, null, String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
			modelview.addObject("ressearchGroupView", objj);
			String s = objj.getString("msg");
			System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		}

		modelview.setViewName("show");
		return modelview;
	}

	/**
	 * 
	 * 更新群组 展示页
	 */
	@RequestMapping("/updateGroupView")
	public ModelAndView updateGroupView(HttpServletRequest request) {
		String id = request.getParameter("id");
		id = "54aa495de4b059141b1b67dd";
		String courSharResoStr;
		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(YXTSERVER3 + "oss/group/one/" + id, null, String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);

			JSONObject tt = (JSONObject) objj.get("data");
			JSONObject ttt = (JSONObject) tt.get("result");
			JSONArray t = ttt.getJSONArray("owner");

			String tttt = t.get(0).toString();

			modelview.addObject("resupdateGroupView", objj);
			modelview.addObject("resOwner", tttt);

		} catch (Exception e) {
			e.printStackTrace();
		}

		modelview.setViewName("show");
		return modelview;
	}

	/**
	 * 
	 * 更新群组
	 */
	@RequestMapping("/updateGroup")
	public ModelAndView updateGroup(HttpServletRequest request) {
		String courSharResoStr;
		// 拥有者id
		String uid = request.getParameter("uid");
		uid = "548fcd49e4b044f4775e063b";
		// 群组id

		String gid = request.getParameter("gid");
		gid = "54a8fadde4b0df8f6b1b569f";

		String intro = request.getParameter("intro");
		String tag = request.getParameter("tag");
		String logoUrl = request.getParameter("logoUrl");
		String groupName = request.getParameter("groupName");
		String bgUrl = request.getParameter("bgUrl");

		RestTemplate restTemplate = new RestTemplate();
		ModelAndView modelview = new ModelAndView();

		courSharResoStr = restTemplate.postForObject(YXTSERVER3 + "oss/group/" + gid + "/update?uid=" + uid + "&intro=" + intro + "&tag=" + tag
				+ "&logoUrl=" + logoUrl + "&groupName=" + groupName + "&bgUrl=" + bgUrl, null, String.class);

		try {
			// courSharReso = new ObjectMapper().readValue(courSharResoStr,
			// CourseShareResponse.class);
			JSONObject objj = JSONObject.fromObject(courSharResoStr);

			modelview.addObject("ressearchGroup", objj);
			 
		} catch (Exception e) {
			e.printStackTrace();
		}

		modelview.setViewName("show");
		return modelview;
	}
	
	
	

}
