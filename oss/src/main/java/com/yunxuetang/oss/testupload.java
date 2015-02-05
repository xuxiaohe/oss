package com.yunxuetang.oss;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.yunxuetang.util.qiniu;

@Controller
public class testupload {

	/**
	 * 批量导入用户 展示页 1.页面提交多个表单 2.页面excel csv
	 * 
	 * 
	 */
	@RequestMapping("/importUserView")
	private String importUserView(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return "uploadtest/report";
	}

	/**
	 * 批量导入用户 1.页面提交多个表单 2.页面excel csv
	 * 
	 * 
	 */
	@RequestMapping(value = "/importUser", method = RequestMethod.POST)
	public String importUser(@RequestParam MultipartFile file, Model model,HttpServletRequest request) throws IOException {
		InputStream stream=	file.getInputStream();
		String email = request.getParameter("email");
		String urlString="/Users/yunxuetang/Public";

				String urlString2="test.jpg";
	               FileOutputStream outputStream = new FileOutputStream( urlString + "/"+ urlString2);   
		       int byteCount = 0;
		       byte[] bytes = new byte[1024];
		       while ((byteCount = stream.read(bytes)) != -1){
		            outputStream.write(bytes, 0, byteCount);
		       }
		       
		       outputStream.close();   
		       stream.close();  
		       
		       qiniu qn=new qiniu();
		     String qiniuUrl=  qn.xixi(urlString, urlString2,"user");
		     
		     File file2=new File(urlString + "/"+ urlString2);
		       file2.delete();
		//model.addAttribute("errousers", list);
		return "uploadtest/addedReport";

	}
}
