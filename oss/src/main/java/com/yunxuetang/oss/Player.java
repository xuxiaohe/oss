package com.yunxuetang.oss;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.Config;
import com.yunxuetang.util.HttpUtil;
import com.yunxuetang.util.ResponseContainer;
@Controller
@RequestMapping("/player")
public class Player {
   @RequestMapping("index")
   public ModelAndView play(String id){
	   ModelAndView mv=new ModelAndView("play");
	   Map<String, Object>map=new HashMap<String, Object>();
	   map.put("id", id);
	   mv.addObject("kng",HttpUtil.doGet(Config.YXTSERVER3+"oss/knowledge/getKng", map,ResponseContainer.class).getData().getResult());
	   return mv;
   }
}
