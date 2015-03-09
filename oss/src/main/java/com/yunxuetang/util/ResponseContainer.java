package com.yunxuetang.util;

/**
 * 组装类，将业务处理结果组装成前端可以使用的格式标准
 * @author nes
 *
 */
public class ResponseContainer {

	private int status;
	private String msg;
	private int mode;
	private ReponseData data;
	private String md5;

	public ReponseData getData() {
		return data;
	}

	public String getMd5() {
		return md5;
	}

	public int getMode() {
		return mode;
	}

	public String getMsg() {
		return msg;
	}

	public int getStatus() {
		return status;
	}

	public void setData(ReponseData data) {
		this.data = data;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
