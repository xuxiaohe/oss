package com.yunxuetang.util;

import java.io.Serializable;

public class SpiderBean implements Serializable {

	/**
	 * 接收手机端发过来的干货分享信息 存入redis队列
	 */
	private static final long serialVersionUID = 1L;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	//分享的干货地址
	public String url;
	//分享干货的人所在群id
	public String groupId;
	//分享干货的用户id
	public String userId;

}
