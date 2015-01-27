package com.yunxuetang.util;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

public class DrycargoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String Id;
	private Object group;//群组（数据库存储群组ID）
	private String url; //干货的源地址
	private String fileUrl; //干货的关键图片或路径
	private String message;//干货的内容
	private long ctime;//创建时间
	private long utime;//修改时间
	private String sharePerson;// 分享人
	private int viewCount; //浏览量
	
		public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public Object getGroup() {
		return group;
	}
	public void setGroup(Object group) {
		this.group = group;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public long getCtime() {
		return ctime;
	}
	public void setCtime(long ctime) {
		this.ctime = ctime;
	}
	public long getUtime() {
		return utime;
	}
	public void setUtime(long utime) {
		this.utime = utime;
	}
	public String getSharePerson() {
		return sharePerson;
	}
	public void setSharePerson(String sharePerson) {
		this.sharePerson = sharePerson;
	}
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}


}
