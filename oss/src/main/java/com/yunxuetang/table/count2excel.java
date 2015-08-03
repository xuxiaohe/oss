package com.yunxuetang.table;

import java.security.spec.ECFieldF2m;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yunxuetang.oss.BaseController;
import com.yunxuetang.util.Config;
import com.yunxuetang.util.ExcelOutPOJO;
import com.yunxuetang.util.poiservice2;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Controller
@RequestMapping("/count")
public class count2excel extends BaseController{

	@Autowired
	private  poiservice2 service2;
	/**
	 * 话题图表页面跳转
	 * */
	@RequestMapping("/count2excel")
	public String count2excel(HttpServletRequest request, Model model,HttpServletResponse response) {
		List<ExcelOutPOJO> l=new ArrayList<ExcelOutPOJO>();
		
		
		ExcelOutPOJO e=pojo("user");
		e.setName("用户");
		l.add(e);
		
		
		ExcelOutPOJO e1=pojo("dry");
		e1.setName("干货");
		l.add(e1);
		
		
		ExcelOutPOJO e2=pojo("topic");
		e2.setName("话题");
		l.add(e2);
		
		
		ExcelOutPOJO e3=pojo("course");
		e3.setName("课程");
		l.add(e3);
		
		
		ExcelOutPOJO e4=pojo("group");
		e4.setName("群组");
		l.add(e4);
		
		service2.exportXLS(response,l);
		
		return "table/tableList";
	}
	
	public ExcelOutPOJO pojo(String name) {
		JSONObject j=getGroupCountData("2","2015",name);
		JSONArray jj= j.getJSONArray("result");
		ExcelOutPOJO e=new ExcelOutPOJO();
		int[] a=new int[12];
		int sum=0;
		double d=0.0;
		
		for(int i=0;i<jj.size();i++){
			JSONObject jjj=(JSONObject) jj.get(i);
			a[i]=jjj.getInt("sum");
			if(jj.size()-2==i){
				sum=a[i];
				d=jjj.getInt("numbers")/(double)sum*100;
			}
		}
		
		for(int k=0;k<12;k++){
			if(k==0){
				e.setMouth1(a[k]+"");
			}if(k==1){
				e.setMouth2(a[k]+"");
			}if(k==2){
				e.setMouth3(a[k]+"");
			}if(k==3){
				e.setMouth4(a[k]+"");
			}if(k==4){
				e.setMouth5(a[k]+"");
			}if(k==5){
				e.setMouth6(a[k]+"");
			}if(k==6){
				e.setMouth7(a[k]+"");
			}if(k==7){
				e.setMouth8(a[k]+"");
			}if(k==8){
				e.setMouth9(a[k]+"");
			}if(k==9){
				e.setMouth10(a[k]+"");
			}if(k==10){
				e.setMouth11(a[k]+"");
			}if(k==11){
				e.setMouth12(a[k]+"");
			}
		}
		e.setSum(sum+"");
		e.setPrecent((int)d+"%");
		
		return e;
	}
	
	
	
	/**
	 * 发送POST请求 获取日/月用户统计数据
	 * */
	private JSONObject getGroupCountData(String type, String date,String name) {
		String url = Config.SCHEDULE_SERVER + "/"+name+"/count?type=" + type
				+ "&date=" + date;
		return getRestApiData(url);
	}
	
	
}
