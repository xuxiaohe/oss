package com.yunxuetang.oss;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
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
	//所有图片
	List imageurls=null;
	
	
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
		//所有图片
		 imageurls = qn.hehe();
//		 List l=new ArrayList();
//		for(int i=0;i<10;i++){
//			l.add(imageurls.get(i));
//		}
//		 imageurls.get(0);
		 //总数
		 int sum=imageurls.size();
		 //共有多少页
		 int pages=sum/10+1;
		 //当前为0页
		 int currpage=1;
		ModelAndView modelview = new ModelAndView();
		modelview.addObject("imageurls", imageurls.subList(0, 10));
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.addObject("sum", sum);
		modelview.addObject("pages", pages);
		modelview.addObject("currpage", currpage);
		modelview.setViewName("tools/allqiniufiles");
		return modelview;
	
}
	

	
	
	/**
	 * 搜索七牛所有文件列表
	 */
	@RequestMapping("/serachpics")
	public ModelAndView serachpics(HttpServletRequest request ) {
		
		String keyword = request.getParameter("keyword");
		List l=new ArrayList();
		for(Object a:imageurls){
			if(("http://yxt-bj.qiniudn.com"+(String)a).equals(keyword)){
				l.add(a);
			}
		}
		//所有图片
//		 List l=new ArrayList();
//		for(int i=0;i<10;i++){
//			l.add(imageurls.get(i));
//		}
//		 imageurls.get(0);
		 //总数
		 int sum=imageurls.size();
		 //共有多少页
		 int pages=sum/10+1;
		 //当前为0页
		 int currpage=1;
		ModelAndView modelview = new ModelAndView();
		modelview.addObject("imageurls",l);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.addObject("sum", sum);
		modelview.addObject("pages", pages);
		modelview.addObject("currpage", currpage);
		modelview.setViewName("tools/allqiniufiles");
		return modelview;
	
}
	
	
	
	
	/**
	 * 七牛所有文件列表分页
	 */
	@RequestMapping("/allqiniufilesforpages")
	public ModelAndView allqiniufilesforpages(HttpServletRequest request ) {
		List list = null;
		int c=0;
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
				
				if(imageurls!=null)
				{
					int a=Integer.parseInt(pagenumber);
					int b=Integer.parseInt(pagelines);
					 c=a*b;
					 list=imageurls.subList(c, c+10);
					
				}
				
				//总数
				 int sum=imageurls.size();
				 //共有多少页
				 int pages=sum/10+1;
				 //当前为0页
				 int currpage=c/10+1;
				
				
		// 当前第几页
//		qiniu qn=new qiniu(); 
//		//所有图片
//		 imageurls = qn.hehe();
//		 List l=new ArrayList();
//		for(int i=0;i<10;i++){
//			l.add(imageurls.get(i));
//		}
//		 imageurls.get(0);

		ModelAndView modelview = new ModelAndView();
		modelview.addObject("imageurls", list);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.addObject("sum", sum);
		modelview.addObject("pages", pages);
		modelview.addObject("currpage", currpage);
		modelview.setViewName("tools/allqiniufiles");
		return modelview;
	
}
	
	
	
	
	
	
	/**
	 * 审核替换图片
	 */
	@RequestMapping("/checkandchange")
	public ModelAndView checkandchange(HttpServletRequest request ) {
		List list = null;
		String url = request.getParameter("url");
		// 当前第几页
		qiniu qn=new qiniu(); 
		//所有图片
		qn.changandcheck(url);
//		
//		 imageurls = qn.hehe();
		 
		 //总数
		 int sum=imageurls.size();
		 //共有多少页
		 int pages=sum/10+1;
		 //当前为0页
		 int currpage=1;
		 
		 if(imageurls!=null)
			{
				
				 list=imageurls.subList(0,10);
				
			}

		ModelAndView modelview = new ModelAndView();
		modelview.addObject("imageurls", list);
		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.addObject("sum", sum);
		modelview.addObject("pages", pages);
		modelview.addObject("currpage", currpage);
		modelview.setViewName("tools/allqiniufiles");
		return modelview;
	
}
	
	
	
	
	
	
}
