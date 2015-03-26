package com.yunxuetang.oss;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.Config;
import com.yunxuetang.util.Saveimage;
import com.yunxuetang.util.qiniu;

@Controller
@RequestMapping("/tools")
public class tools {

	
	@Autowired
	Saveimage saveimage;
	
	
	
	/**
	 * 
	 * 上传七牛 展示页
	 */
	@RequestMapping("/uploadqiniuForm")
	public ModelAndView uploadqiniuForm(HttpServletRequest request) {

		ModelAndView modelview = new ModelAndView();

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("tools/uploadqiniuForm");
		return modelview;
	}
	
	
	
	/**
	 * 图片提交七牛返回url
	 */
	@RequestMapping("/uploadqiniuAction")
	public ModelAndView uploadqiniuAction(HttpServletRequest request,@RequestParam MultipartFile file) {
		// 当前第几页
		 
		String imageurl = null;

		try {
			String t[]=file.getContentType().split("/");
			String tt="."+t[1];
		if (file.getSize()!=0) {

		Long l=System.currentTimeMillis();

		String urlString="/data/ossImgTemp";

		String urlString2=l+tt;

		InputStream stream=	file.getInputStream();

		imageurl=saveimage.save(urlString, urlString2, stream, "dry");

		}

		} catch (Exception e) {

		// TODO Auto-generated catch block

		e.printStackTrace();

		}



		ModelAndView modelview = new ModelAndView();
		modelview.addObject("imageurl", imageurl);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("tools/uploadqiniuAction");
		return modelview;
	
}
	
	
	
	/**
	 * 返回七牛所有文件列表
	 */
	@RequestMapping("/allqiniufiles")
	public ModelAndView allqiniufiles(HttpServletRequest request ) {
		// 当前第几页
		qiniu qn=new qiniu(); 
		
		List imageurls = qn.hehe();


		ModelAndView modelview = new ModelAndView();
		modelview.addObject("imageurls", imageurls);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("tools/allqiniufiles");
		return modelview;
	
}
	
	
	
	
	
	
}
