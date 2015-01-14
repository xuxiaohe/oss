package com.yunxuetang.share;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.oss.BaseController;
import com.yunxuetang.util.Config;



@Controller
@RequestMapping("/sharePage")
public class ShareController extends BaseController{

	/**
	 * 查询分享课程信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/course")
	public  ModelAndView findCourseDetails(HttpServletRequest request,String courseid) {
		
		RestTemplate restTemplate=new RestTemplate();
		ModelAndView modelview = new ModelAndView();
		
		String url = Config.YXTSERVER + "sharePage/course?courseId=" + courseid;
		
		
		String courSharResoStr= restTemplate.getForObject(url,String.class); 
		try {
			
			JSONObject objj = JSONObject.fromObject(courSharResoStr);
		
			modelview.addObject("courSharReso", objj);
		}catch (Exception e) {
			e.printStackTrace();
		}  
				
		modelview.setViewName("share/share_course");
		return modelview;

	}
	
	/** 
	* @author yangquanliang
	* @Description: 分享话题
	* @param @param request
	* @param @param dm
	* @param @param topicId
	* @param @param userId
	* @param @param sourceId
	* @param @return
	* @return ModelAndView
	* @throws 
	*/ 
	@RequestMapping("/topic")
	public  ModelAndView findTopicDetails(HttpServletRequest request,
		String topicId,String userId,String sourceId) {
		RestTemplate restTemplate=new RestTemplate();
		ModelAndView modelview = new ModelAndView();
		String url=Config.YXTSERVER+"sharePage/topic?topicId="+ topicId+"&userId="+userId+"&sourceId="+sourceId;
				
		String topicSharResoJsonstr = restTemplate.getForObject(url,String.class);
		
		
		try {

			JSONObject topicSharReso = JSONObject.fromObject(topicSharResoJsonstr);
			modelview.addObject("topicSharReso", topicSharReso);
		}  catch (Exception e) {
			e.printStackTrace();
		}
		modelview.setViewName("share/share_topic");
		return modelview;
		
	}
	
	
	/**
	 * 
	* @author yangquanliang
	* @Description: 分享群组详情
	* @param @param request
	* @param @param GroupId
	* @param @param userId
	* @param @param sourceId
	* @param @return
	* @return ModelAndView
	* @throws
	 */
	@RequestMapping("/group")
	public  ModelAndView findGroupDetails(HttpServletRequest request)
	{
		String gid = request.getParameter("gid");
		// 当前第几页
		String n = request.getParameter("n");
		if (n == null) {
			n = "0";
		}
		// 每页条数
		String s = request.getParameter("s");
		if (s == null) {
			s = "2";
		}
		ModelAndView modelview = new ModelAndView();
		modelview.addObject("Group", getGroupInfo(gid));
		modelview.addObject("DryList", getGroupDry(gid, n, s));
		modelview.addObject("TopicList", getGroupTopic(gid, n, s));
		modelview.setViewName("show");
		return modelview;
	}
	
	
	private JSONObject getGroupInfo(String gid) {
		String url = Config.YXTSERVER3 + "oss/group/findOneGroups/" + gid;
		return getRestApiData(url);
	}
	
	private JSONObject getGroupDry(String gid, String n, String s) {
		String url = Config.YXTSERVER3 + "oss/dry/findDryByGroup?groupId="
				+ gid + "&n=" + n + "&s=" + s;
		return getRestApiData(url);
	}
	
	private JSONObject getGroupTopic(String gid, String n, String s) {
		String url = Config.YXTSERVER3 + "oss/topic/findByGroupId?sourceId="
				+ gid + "&appKey=yxtapp&n=" + n + "&s=" + s;
		return getRestApiData(url);
	}
	
}
