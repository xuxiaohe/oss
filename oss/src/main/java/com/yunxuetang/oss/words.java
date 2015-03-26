package com.yunxuetang.oss;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.Config;

@Controller
@RequestMapping("/words")
public class words extends BaseController{
	@RequestMapping("/wordsManage")
	public ModelAndView wordsManage(HttpServletRequest request){
		ModelAndView modelview=new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		String apiurl=Config.YXTSERVER3;
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.addObject("apiurl",apiurl);
		modelview.setViewName("words/wordsManage");
		return modelview;
	}

}
