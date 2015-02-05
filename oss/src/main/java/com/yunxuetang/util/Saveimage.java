package com.yunxuetang.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.springframework.stereotype.Service;

@Service
public class Saveimage {
	
	public String save(String urlString,String urlString2,InputStream stream,String type)  {
		// TODO Auto-generated method stub
		String qiniuUrl=null;
		try {
		
		  FileOutputStream outputStream = new FileOutputStream( urlString + "/"+ urlString2);   
	       int byteCount = 0;
	       byte[] bytes = new byte[1024];
	       while ((byteCount = stream.read(bytes)) != -1){
	            outputStream.write(bytes, 0, byteCount);
	       }
	       
	       outputStream.close();   
	       stream.close();  
	       
	       qiniu qn=new qiniu();
	      qiniuUrl=  qn.xixi(urlString, urlString2,type);
	     
	     File file2=new File(urlString + "/"+ urlString2);
	       file2.delete();
	       
	      
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		 return qiniuUrl;   
	}
	
	
	
	

}
