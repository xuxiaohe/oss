package com.yunxuetang.oss;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.yunxuetang.util.AutoExecuteTaskController;
import com.yunxuetang.util.Config;
import com.yunxuetang.util.FetchedPage;
import com.yunxuetang.util.PageFetcher;
import com.yunxuetang.util.Saveimage;
import com.yunxuetang.util.qiniu;


@Controller
@RequestMapping("/log")
public class log extends BaseController {
	Logger logger = LoggerFactory.getLogger(log.class);
	@Autowired
	Saveimage saveimage;
	public log() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 干货详情
	 */
	@RequestMapping("/getall")
	public ModelAndView dryDetail(HttpServletRequest request) {
		// 当前第几页
		 

		ModelAndView modelview = new ModelAndView();

		try {
			modelview.addObject("logs", categoryDetail());
			 
		} catch (Exception e) {
			e.printStackTrace();
		}

		String cpath = request.getContextPath();
		String cbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + cpath + "/";
		modelview.addObject("cbasePath", cbasePath);
		modelview.addObject("sourcePath", Config.YXTSERVER5);
		modelview.setViewName("dry/dryDetail");
		return modelview;

	}

	

	private JSONObject categoryDetail() {
		String url = Config.YXTSERVER3 + "log/getall";
		return getRestApiData(url);
	}
}
