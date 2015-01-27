package com.yunxuetang.util;


/**
 * @author Administrator
 *  抓取回来的页面相关信息 包含状态码等信息
 */
public class FetchedPage {
	private String url;
	private String content;
	private int statusCode;
	
	public FetchedPage(){
		
	}
	
	public FetchedPage(String url, String content, int statusCode){
		this.url = url;
		this.content = content;
		this.statusCode = statusCode;
		
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
	
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
}
