package com.yunxuetang.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/startfatche")
public class AutoExecuteTaskController {

	public static String imgBasePath = "/var/www/html/drgcargo/";
	// public static String imgBasePath ="/Users/yangquanliang/Downloads/";
	public static String imghttpUrl = "http://s1.xuewen.yunxuetang.com/drgcargo/";
	 
	 

	private PageFetcher fetcher = new PageFetcher();

	// @RequestMapping(value = { "fetch" })
	// @Scheduled(fixedRate=5000)
	// public void fetchDataFromRedis(SpiderBean sp,HttpServletRequest request)
	// {
	//
	// //被抓取的“干货”队列的key
	// String rediskey ="spider:drycargourl:beans" ;
	//
	// try {
	// String tofetchUrl = stringRedisTemplate.opsForList().rightPop(rediskey,
	// 0, TimeUnit.SECONDS);
	// System.err.println("after Spring start! ");
	// System.err.println(tofetchUrl);
	// //userId ,groupId ,url
	// String[] dryUrlInfo = tofetchUrl.split(",");
	// //抓取返回的结果页
	// if(dryUrlInfo.length >0)
	// {
	// String urlft ="";
	// try {
	// urlft= URLDecoder.decode(dryUrlInfo[2], "UTF-8");
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// FetchedPage fetchedPage = fetcher.getContentFromUrl(urlft);
	//
	// if(isAntiScratch(fetchedPage))
	// {
	// //抓取结果失败
	// return;
	// }
	// else
	// {
	// // 分析页面数据
	// fetchData(dryUrlInfo,fetchedPage);
	//
	// }
	// }
	// }
	// catch(Exception ex)
	// {
	// ex.printStackTrace();
	// }
	//
	// }

	/**
	 * 获取分析页面的数据抓取图片和title
	 * 
	 * @param dryUrlInfo
	 * @param fetchedPage
	 */
	public Map fetchData(String dryUrlInfo, FetchedPage fetchedPage) {
		// 通过URL判断
		DrycargoBean drybean = new DrycargoBean();
		
		Map m=new HashMap();
		try {

			Document contentDoc = Jsoup.parse(fetchedPage.getContent());

			// 首先取title 如果title内容为空找h1标签
			String title = contentDoc.getElementsByTag("title").text();

			if (StringUtil.isBlank(title)) {
				title = contentDoc.getElementsByTag("h1").text();
				// 如果title与H1标签内的内容都为空 不再处理直接返回
				if (StringUtil.isBlank(title)) {
					return m;
				}
			}

			// 一般内容中图片多为jpg格式或者jpeg或者png
			Elements imgjpgElements = contentDoc.select("img[src$=.jpg]");
			Elements imgjpegElements = contentDoc.select("img[src$=.jpeg]");
			Elements imgpngElements = contentDoc.select("img[src$=.png]");

			Element elemtToFetch = null;

			List l = new ArrayList();

			for (int i = 0; i < imgjpgElements.size(); i++) {
				elemtToFetch = imgjpgElements.get(i);
				if (elemtToFetch != null)// userId ,groupId ,url
				{
					String imgUrl = elemtToFetch.attr("src");
					if (StringUtil.isBlank(getDomain(imgUrl))) {
						String urlft = URLDecoder.decode(dryUrlInfo, "UTF-8");
						imgUrl = getDomain(urlft) + imgUrl;
					}
					l.add(imgUrl);
					System.out.println(imgUrl);
				}
				
			}

			Element elemtToFetch2 = null;


			for (int i = 0; i < imgjpegElements.size(); i++) {
				elemtToFetch2 = imgjpgElements.get(i);
				if (elemtToFetch2 != null)// userId ,groupId ,url
				{
					String imgUrl = elemtToFetch2.attr("src");
					if (StringUtil.isBlank(getDomain(imgUrl))) {
						String urlft = URLDecoder.decode(dryUrlInfo, "UTF-8");
						imgUrl = getDomain(urlft) + imgUrl;
					}
					l.add(imgUrl);
					System.out.println(imgUrl);
				}
			}

			Element elemtToFetch3 = null;


			for (int i = 0; i < imgpngElements.size(); i++) {
				elemtToFetch3 = imgjpgElements.get(i);
				if (elemtToFetch3 != null)// userId ,groupId ,url
				{
					String imgUrl = elemtToFetch3.attr("src");
					if (StringUtil.isBlank(getDomain(imgUrl))) {
						String urlft = URLDecoder.decode(dryUrlInfo, "UTF-8");
						imgUrl = getDomain(urlft) + imgUrl;
					}
					l.add(imgUrl);
					System.out.println(imgUrl);
				}
			}

			 
			drybean.setCtime(System.currentTimeMillis());

			drybean.setUrl(dryUrlInfo);// 原生url
			drybean.setMessage(title);// 标题
 
			m.put("url", dryUrlInfo);
			m.put("message", title);
			m.put("image", l);
			
			System.out.print("=========" + drybean.getUrl() + "===========" + drybean.getMessage() + "=========" + l.size());

			 
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return m;

	}

	/**
	 * 
	 * @author yangquanliang
	 * @Description: 根据url获取主域名
	 * @param @param curl
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getDomain(String curl) {
		URL url = null;
		String q = "";
		try {
			url = new URL(curl);
			q = url.getHost();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		url = null;
		return q;
	}

	/**
	 * 检查抓取回来的结果是否成功
	 * 
	 * @param fetchedPage
	 * @return
	 */
	private boolean isAntiScratch(FetchedPage fetchedPage) {
		// 403 forbidden
		if ((!isStatusValid(fetchedPage.getStatusCode())) && fetchedPage.getStatusCode() == 403) {
			return true;
		}

		// 页面内容包含的反爬取内容
		if (fetchedPage.getContent().contains("<div>禁止访问</div>")) {
			return true;
		}

		return false;
	}

	private boolean isStatusValid(int statusCode) {
		if (statusCode >= 200 && statusCode < 400) {
			return true;
		}
		return false;
	}

	 

	/**
	 * 图片处理公共方法
	 * 
	 * @param listImgUrl
	 *            要处理的图片的url集合
	 * @param imgBasePath
	 *            要存放的服务器绝对路径
	 * @return
	 */
	public String handleSmallPic(String ImgUrl, String imgBasePath) {
		// 处理预览图片 替换成本地路径
		// 原始图片名称

		try {
			String imgName = ImgUrl.split("[/]")[ImgUrl.split("[/]").length - 1];
			// 小图名称在大图名称后面加s
			String simgName = ImgUrl.split("[/]")[ImgUrl.split("[/]").length - 1].replace(".", "s.");

			FileOutputStream out;
			// 抓取下载原始图
			URL urlt = new URL(ImgUrl);
			HttpURLConnection uc = (HttpURLConnection) urlt.openConnection();
			uc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:16.0) Gecko/20100101 Firefox/16.0");
			InputStream is = uc.getInputStream();
			// 保存原始大图
			File file = new File(imgBasePath + "/" + imgName);
			String newfileUrl = imgBasePath + simgName;
			out = new FileOutputStream(file);
			int i = 0;
			while ((i = is.read()) != -1) {
				out.write(i);
			}
			is.close();
			out.close();

			BufferedImage srcImage = ImageIO.read(file);
			String imgType = "JPEG";
			if (imgName.contains(".png") || imgName.contains(".PNG")) {
				imgType = "PNG";
			}

			if (srcImage.getWidth() > 300) {
				BufferedImage target = resize(srcImage, 592, 320, true);
				File savefile = new File(newfileUrl);
				ImageIO.write(target, imgType, savefile);
				// 小图完整路径加名称
				return imghttpUrl + simgName;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * 将原图片的BufferedImage对象生成缩略图 source：原图片的BufferedImage对象 targetW:缩略图的宽
	 * targetH:缩略图的高
	 */
	public BufferedImage resize(BufferedImage source, int targetW, int targetH, boolean equalProportion) {
		int type = source.getType();
		BufferedImage target = null;
		double sx = (double) targetW / source.getWidth();
		double sy = (double) targetH / source.getHeight();
		// 这里想实现在targetW，targetH范围内实现等比例的缩放
		// 如果不需要等比例的缩放则下面的if else语句注释调即可
		if (equalProportion) {
			if (sx > sy) {
				sx = sy;
				targetW = (int) (sx * source.getWidth());
			} else {
				sy = sx;
				targetH = (int) (sx * source.getHeight());
			}
		}
		if (type == BufferedImage.TYPE_CUSTOM) {
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else {
			target = new BufferedImage(targetW, targetH, type);
			Graphics2D g = target.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
			g.dispose();
		}
		return target;
	}

}
