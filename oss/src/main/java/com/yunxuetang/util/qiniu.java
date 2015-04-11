package com.yunxuetang.util;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONException;

import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.net.CallRet;
import com.qiniu.api.rs.PutPolicy;
import com.qiniu.api.rs.RSClient;
import com.qiniu.api.rsf.ListItem;
import com.qiniu.api.rsf.ListPrefixRet;
import com.qiniu.api.rsf.RSFClient;
import com.qiniu.api.rsf.RSFEofException;

public class qiniu {

	public  List hehe() {
		// TODO Auto-generated method stub
		String qnAccessKey = "PN6SV6Fm-Ueu-D8NYN_NfJwpPuedsQALNKmwL_IQ";
		String qnSecretKey = "O6sBs8dw9cJjamEx2d6vgY1sXnPnIlg5Nsn6CwPH";
		String qnBucketName = "yxt-bj";
		String ACCESS_KEY = qnAccessKey;
		String SECRET_KEY = qnSecretKey;
		Mac mac = new Mac(ACCESS_KEY, SECRET_KEY);

		RSFClient rs = new RSFClient(mac);
		String marker = "";
		long currettime = System.currentTimeMillis();
		List<ListItem> all = new ArrayList<ListItem>();
		ListPrefixRet ret = null;
		while (true) {
			ret = rs.listPrifix(qnBucketName, "", marker, 1000);
			marker = ret.marker;
			all.addAll(ret.results);
			if (!ret.ok()) {
				// no more items or error occurs
				break;
			}
		}
		
//		ret = rs.listPrifix(qnBucketName, "", marker, 1002);
//		marker = ret.marker;
//		all.addAll(ret.results);
//		if (ret.exception.getClass() != RSFEofException.class) {
//			// error handler
//		}

		System.err.println(all.size());
		List ls=new ArrayList();
		for (ListItem item : all) {
			System.err.println(item.key);
			ls.add(item.key);
		}
		return ls;
	}

	public static void main(String[] args) {
		//========列表
//		String qnAccessKey = "PN6SV6Fm-Ueu-D8NYN_NfJwpPuedsQALNKmwL_IQ";
//		String qnSecretKey = "O6sBs8dw9cJjamEx2d6vgY1sXnPnIlg5Nsn6CwPH";
//		String qnBucketName = "yxt-bj";
//		String ACCESS_KEY = qnAccessKey;
//		String SECRET_KEY = qnSecretKey;
//		Mac mac = new Mac(ACCESS_KEY, SECRET_KEY);
//
//		RSFClient rs = new RSFClient(mac);
//		String marker = "";
//		long currettime = System.currentTimeMillis();
//		List<ListItem> all = new ArrayList<ListItem>();
//		ListPrefixRet ret = null;
//		while (true) {
//			ret = rs.listPrifix(qnBucketName, "dry", marker, 2);
//			marker = ret.marker;
//			all.addAll(ret.results);
//			
//			if (!ret.ok()) {
//				// no more items or error occurs
//				break;
//			}
//		}
//		if (ret.exception.getClass() != RSFEofException.class) {
//			// error handler
//		}
//
//		System.err.println(all.size());
//
//		for (ListItem item : all) {
//			System.err.println(item.key);
//		}
		
		//删除文件
//		String qnAccessKey="PN6SV6Fm-Ueu-D8NYN_NfJwpPuedsQALNKmwL_IQ";
//		String		qnSecretKey="O6sBs8dw9cJjamEx2d6vgY1sXnPnIlg5Nsn6CwPH";
//		String	qnBucketName="yxt-bj";
//	    String ACCESS_KEY = qnAccessKey;
//	    String SECRET_KEY = qnSecretKey;
//	    Mac mac = new Mac(ACCESS_KEY, SECRET_KEY);
//	    String key=null;
//	    
//	    // 请确保该bucket已经存在 
//	    PutPolicy putPolicy = new PutPolicy(qnBucketName);
//	    String uptoken;
//		try {
//			
//			//bucket, key
//			RSClient client = new RSClient(mac);
//			CallRet c=client.delete(qnBucketName, "dry/1428652117772.png");
//			c.getStatusCode();
//			
//			
////			uptoken = putPolicy.token(mac);  
////			PutExtra extra = new PutExtra();
////			//上传到服务器的文件名称
////	     key = type+"/"+uploadfilename;
////	    //本地存放图片的地址
////	    String localFile = localFileUrl+"/"+uploadfilename;
////	    PutRet ret = IoApi.putFile(uptoken, key, localFile, extra);
////	    
////	   System.err.println(ret.getKey()); 
//	  // http://yxt-bj.qiniudn.com/test.jpg
//		  
//		  
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
		
		
		//上传文件
//		String qnAccessKey="PN6SV6Fm-Ueu-D8NYN_NfJwpPuedsQALNKmwL_IQ";
//		String		qnSecretKey="O6sBs8dw9cJjamEx2d6vgY1sXnPnIlg5Nsn6CwPH";
//		String	qnBucketName="yxt-bj";
//	    String ACCESS_KEY = qnAccessKey;
//	    String SECRET_KEY = qnSecretKey;
//	    Mac mac = new Mac(ACCESS_KEY, SECRET_KEY);
//	    String key=null;
//	    
//	    // 请确保该bucket已经存在 
//	    PutPolicy putPolicy = new PutPolicy(qnBucketName);
//	    String uptoken;
//		try {
//			uptoken = putPolicy.token(mac);  
//			PutExtra extra = new PutExtra();
//			//上传到服务器的文件名称
//	   //  key = type+"/"+uploadfilename;
//	     key = "dry/1428652117772.png";
//	    //本地存放图片的地址
//	    //String localFile = localFileUrl+"/"+uploadfilename;
//	    String localFile = "/data/imagetest/test.png";
//	    PutRet ret = IoApi.putFile(uptoken, key, localFile, extra);
//	    
//	   System.err.println(ret.getKey()); 
//	  // http://yxt-bj.qiniudn.com/test.jpg
//		  
//		  
//		} catch (AuthException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		catch(Exception ex){
//			ex.printStackTrace();
//		}
//		String url="http://yxt-bj.qiniudn.com/dry/1428716211813.png";
//		changandcheck(url);
		
	}
	
	public  void changandcheck(String url) {
		 url=change(url);
			//String url="dry/Img3398324581423796701587.jpg";
			delete(url);
			//check(url);
	}
	
	
	
	public  String change(String ss) {
		//String s="http://yxt-bj.qiniudn.com/dry/1428652117772.png";
		String s=ss;
		String[] a=s.split("/");
		String tString="";
		for(int i=3;i<a.length;i++){
			if(i==3){
				tString+=a[i];
			}
			else{
				tString+="/"+a[i];	
			}
			
		}
		System.out.print(tString);
		return tString;
		
	}
	
	
	public String xixi(String localFileUrl,String uploadfilename,String type) {
		// TODO Auto-generated method stub
		String qnAccessKey="PN6SV6Fm-Ueu-D8NYN_NfJwpPuedsQALNKmwL_IQ";
		String		qnSecretKey="O6sBs8dw9cJjamEx2d6vgY1sXnPnIlg5Nsn6CwPH";
		String	qnBucketName="yxt-bj";
		//String	qnBucketName="yxt-bj";
	    String ACCESS_KEY = qnAccessKey;
	    String SECRET_KEY = qnSecretKey;
	    Mac mac = new Mac(ACCESS_KEY, SECRET_KEY);
	    String key=null;
	    
	    // 请确保该bucket已经存在 
	    PutPolicy putPolicy = new PutPolicy(qnBucketName);
	    String uptoken;
		try {
			uptoken = putPolicy.token(mac);  
			PutExtra extra = new PutExtra();
			//上传到服务器的文件名称
	     key = type+"/"+uploadfilename;
	    //本地存放图片的地址
	    String localFile = localFileUrl+"/"+uploadfilename;
	    PutRet ret = IoApi.putFile(uptoken, key, localFile, extra);
	    
	   System.err.println(ret.getKey()); 
	  // http://yxt-bj.qiniudn.com/test.jpg
		  
		  
		} catch (AuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		 String urlback="http://"+qnBucketName+".qiniudn.com/"+key;
		return urlback;
	}
	
	
	
	/**
	 * 自动替换文件
	 * @param localFileUrl
	 * @param uploadfilename
	 * @param type
	 * @return
	 */
	
	public  String check(String url) {
		// TODO Auto-generated method stub
		String qnAccessKey="PN6SV6Fm-Ueu-D8NYN_NfJwpPuedsQALNKmwL_IQ";
		String		qnSecretKey="O6sBs8dw9cJjamEx2d6vgY1sXnPnIlg5Nsn6CwPH";
		String	qnBucketName="yxt-bj";
	    String ACCESS_KEY = qnAccessKey;
	    String SECRET_KEY = qnSecretKey;
	    Mac mac = new Mac(ACCESS_KEY, SECRET_KEY);
	    String key=null;
	    
	    // 请确保该bucket已经存在 
	    PutPolicy putPolicy = new PutPolicy(qnBucketName);
	    String uptoken;
		try {
			uptoken = putPolicy.token(mac);  
			PutExtra extra = new PutExtra();
			//上传到服务器的文件名称
	   //  key = type+"/"+uploadfilename;
	     key = url;
	    //本地存放图片的地址
	    //String localFile = localFileUrl+"/"+uploadfilename;
	    String localFile = "/data/imagetest/test.png";
	    PutRet ret = IoApi.putFile(uptoken, key, localFile, extra);
	    
	   System.err.println(ret.getKey()); 
	  // http://yxt-bj.qiniudn.com/test.jpg
		  
		  
		} catch (AuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		 String urlback="http://"+qnBucketName+".qiniudn.com/"+key;
		return urlback;
	}
	
	
	
	public  void delete(String url) {
		// TODO Auto-generated method stub
		String qnAccessKey="PN6SV6Fm-Ueu-D8NYN_NfJwpPuedsQALNKmwL_IQ";
		String		qnSecretKey="O6sBs8dw9cJjamEx2d6vgY1sXnPnIlg5Nsn6CwPH";
		String	qnBucketName="yxt-bj";
	    String ACCESS_KEY = qnAccessKey;
	    String SECRET_KEY = qnSecretKey;
	    Mac mac = new Mac(ACCESS_KEY, SECRET_KEY);
	    String key=null;
	    
	    // 请确保该bucket已经存在 
	    PutPolicy putPolicy = new PutPolicy(qnBucketName);
	    String uptoken;
		try {
			
			//bucket, key
			RSClient client = new RSClient(mac);
			CallRet c=client.delete(qnBucketName, url);
			c.getStatusCode();
			
			// 请确保该bucket已经存在 
				uptoken = putPolicy.token(mac);  
				PutExtra extra = new PutExtra();
				//上传到服务器的文件名称
		   //  key = type+"/"+uploadfilename;
		     key = url;
		    //本地存放图片的地址
		    //String localFile = localFileUrl+"/"+uploadfilename;
		    String localFile = "/data/imagetest/test.png";
		    PutRet ret = IoApi.putFile(uptoken, key, localFile, extra);
		    
		   System.err.println(ret.getKey()); 
		  // http://yxt-bj.qiniudn.com/test.jpg
//			uptoken = putPolicy.token(mac);  
//			PutExtra extra = new PutExtra();
//			//上传到服务器的文件名称
//	     key = type+"/"+uploadfilename;
//	    //本地存放图片的地址
//	    String localFile = localFileUrl+"/"+uploadfilename;
//	    PutRet ret = IoApi.putFile(uptoken, key, localFile, extra);
//	    
//	   System.err.println(ret.getKey()); 
	  // http://yxt-bj.qiniudn.com/test.jpg
		  
		  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		//catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		catch(Exception ex){
//			ex.printStackTrace();
//		}
//		 String urlback="http://"+qnBucketName+".qiniudn.com/"+key;
//		return urlback;
	}
	
	
	
	
	public static String token(String bucket){
        Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
        PutPolicy putPolicy = new PutPolicy(bucket);
        String uptoken;
		try {
			uptoken = putPolicy.token(mac);
			 return uptoken;
		} catch (AuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (org.json.JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       return null;
	}
	
	
	
	
	
	
	
	

}
