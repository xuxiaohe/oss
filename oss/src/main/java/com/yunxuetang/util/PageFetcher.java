package com.yunxuetang.util;

import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

public class PageFetcher {
	private DefaultHttpClient client;

	/**
	 * 创建HttpClient实例，并初始化连接参数
	 */
	public PageFetcher() {
		// 设置超时时间
	
       //HttpParams params =
		client = new DefaultHttpClient();

		// // 代理的设置
		// HttpHost proxy = new HttpHost("127.0.0.1", 8087);
		// client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
		// proxy);

	}

	/**
	 * 根据url爬取网页内容
	 * 
	 * @param url
	 * @return
	 */
	public FetchedPage getContentFromUrl(String url) {
		String content = null;
		int statusCode = 500;
		
		//RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig)
		//		.build();
		// 创建Get请求，并设置Header
		HttpGet getHttp = new HttpGet(url.trim());
		//getHttp.setConfig(requestConfig);
		getHttp.setHeader(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.57 Safari/537.36");
		HttpResponse response;

		try {
			// 获得信息载体
			response = client.execute(getHttp);
			statusCode = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			Header ceheader = entity.getContentEncoding();
			if (ceheader != null) {
				for (HeaderElement element : ceheader.getElements()) {
					if (element.getName().equalsIgnoreCase("gzip")
							|| element.getName().equalsIgnoreCase("deflate")) {
						response.setEntity(new GzipDecompressingEntity(response
								.getEntity()));

					}
				}
			}
			entity = response.getEntity();

			if (entity != null) {
				//先取得网页默认编码方式
				Charset charsets = ContentType.getOrDefault(entity).getCharset();
				String charsetNew="gb2312";		
				if (charsets == null) {//如果取不到 自定义gb2312编码					
                 if(url.contains("ifeng") ||url.contains("jiajuol.com"))//凤凰
                 {
                	 charsetNew="utf-8";
                 }
					content = EntityUtils.toString(entity, charsetNew);
				} else {
					content = EntityUtils.toString(entity, charsets);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			// 因请求超时等问题产生的异常，将URL放回待抓取队列，重新爬取
		}

		return new FetchedPage(url, content, statusCode);
	}

	public String matchCharset(String content) {
		String chs = "gb2312";
		Pattern p = Pattern.compile("(?<=charset=)(.+)(?=\")");
		Matcher m = p.matcher(content);
		if (m.find())
			return m.group();
		return chs;
	}

	/**
	 * 根据url爬取网页内容
	 * 
	 * @param url
	 * @return
	 */
	public FetchedPage postContentFromUrl(String url) {
		String content = null;
		int statusCode = 500;
		System.err.println("post url:" + url);
		// 创建Get请求，并设置Header
		HttpPost getHttp = new HttpPost(url);
		getHttp.setHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; rv:16.0) Gecko/20100101 Firefox/16.0");
		HttpResponse response;

		try {
			// 获得信息载体
			response = client.execute(getHttp);
			statusCode = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				String charset = ContentType.getOrDefault(entity).getCharset().toString();
				content = EntityUtils.toString(entity, charset);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new FetchedPage(url, content, statusCode);
	}
}
