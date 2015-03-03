package com.yunxuetang.oss;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/words")
public class words extends BaseController{
	@RequestMapping("/wordsManage")
	public ModelAndView wordsManage(HttpServletRequest request){
		ModelAndView modelview=new ModelAndView();
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.setViewName("words/wordsManage");
		return modelview;
	}

}
