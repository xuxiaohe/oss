package com.yunxuetang.util;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponseWrapper;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA. User: Ambitor springMVC拦截器 判断session中用户是否过期 Date:
 * 13-6-27 Time: 下午7:31 To change this template use File | Settings | File
 * Templates.
 * 
 * @author xurui
 */

public class SystemFilter implements Filter {

	Logger logger = LoggerFactory.getLogger(SystemFilter.class);

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException,
			ServletException {
		HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper((HttpServletResponse) servletResponse);
		if (!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse)) {
			throw new ServletException("OncePerRequestFilter just supports HTTP requests");
		}
		HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
		HttpSession session = httpRequest.getSession(true);
		String name=(String) session.getAttribute("name");
		StringBuffer url = httpRequest.getRequestURL();
		RestTemplate restTemplate = new RestTemplate();
		//System.out.println("================="+session.getAttribute("name"));
		String courSharResoStr = null;
		try {
			courSharResoStr = restTemplate.getForObject(Config.YXTSERVER3 + "oss/checkip", String.class);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject objj = JSONObject.fromObject(courSharResoStr);
		
		if(name==null){
			wrapper.sendRedirect("/oss");
			return;
		}
		else {
			logger.info("登录用户为"+session.getAttribute("name")+" 访问的路径："+url);
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}
		
	}

	/**
	 * 判断是否为Ajax请求
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 是true, 否false
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		return request.getRequestURI().startsWith("/api");
		// String requestType = request.getHeader("X-Requested-With");
		// return requestType != null && requestType.equals("XMLHttpRequest");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		/* 如果需要注入，请取消注释 */
		//System.out.println("===========");
	}

	@Override
	public void destroy() {
		// To change body of implemented methods use File | Settings | File
		// Templates.
	}

}
