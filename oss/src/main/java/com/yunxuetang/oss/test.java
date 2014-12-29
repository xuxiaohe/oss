package com.yunxuetang.oss;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class test {

	@RequestMapping(value = "/test")
	public String test2(Locale locale, Model model) {
		 
		model.addAttribute("name", "xu");
		
		return "success";
		
	}
}
