package com.yunxuetang.oss;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "login";
	}
	
	@RequestMapping(value = "/login")
	public String test(HttpServletRequest request) {
		
	String name=	request.getParameter("name");
		 
	String password=	request.getParameter("password");	 
		
	   
		if (("admin1".equals(name) && "oss1234".equals(password)) || ("admin2".equals(name) && "oss5678".equals(password))
				|| ("admin3".equals(name) && "oss01234".equals(password)) || ("admin4".equals(name) && "oss2345".equals(password))
				|| ("admin5".equals(name) && "oss3456".equals(password)) || ("admin6".equals(name) && "oss4567".equals(password))|| ("admin7".equals(name) && "oss8901".equals(password))) {

			 request.getSession().setAttribute("name", name);
			 return "home";
		 }
		 else {
			 return "login";
		}
		
	}
//	public void login(){
//		
//	}
	 
	
	
	@RequestMapping(value = "/404")
	public String notFound(HttpServletRequest request) {
		
	 	 
		return "404";
		
		
	}
	
}
