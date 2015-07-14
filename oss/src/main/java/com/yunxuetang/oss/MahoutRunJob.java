package com.yunxuetang.oss;



import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunxuetang.util.Config;

 
public class MahoutRunJob extends BaseController{
	Logger logger = LoggerFactory.getLogger(MahoutRunJob.class);
	
	public void runjob() {
		System.out.println("=================定时任务开始==============");
		logger.info("=================定时任务开始==============");
		sendmessage();
		sendmessage2();
		sendmessage3();
		sendmessage4();
		sendmessage5();
		System.out.println("=================定时任务结束==============");
		logger.info("=================定时任务结束==============");
	}
	
	 
	
	
	private JSONObject sendmessage() {
		String url = Config.MAHOUT_SERVER + "/dry/init";
	
		return getRestApiData(url);
	}
	
	private JSONObject sendmessage2() {
		String url = Config.MAHOUT_SERVER + "/dry/findAllDry";
	
		return getRestApiData(url);
	}
	
	private JSONObject sendmessage3() {
		String url = Config.MAHOUT_SERVER + "/dry/findAllGroup";
	
		return getRestApiData(url);
	}
	
	
	private JSONObject sendmessage4() {
		String url = Config.MAHOUT_SERVER + "/dry/findDry";
	
		return getRestApiData(url);
	}
	
	
	private JSONObject sendmessage5() {
		String url = Config.MAHOUT_SERVER + "/dry/findGroup";
	
		return getRestApiData(url);
	}
	
	
}
