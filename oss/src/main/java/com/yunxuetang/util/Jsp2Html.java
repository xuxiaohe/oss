package com.yunxuetang.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * JSP 转换 HTML 工具类
 * */
public class Jsp2Html {

	/**
	 * @param requestUrl
	 *            需要转换的URL
	 * @param fileName
	 *            最终存储的文件地址
	 * */
	public static String revertFile(String requestUrl, String fileName)
			throws Exception {

		StringBuffer htmlCode = new StringBuffer();

		try {
			InputStream in;
			URL url = new java.net.URL(requestUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/4.0");
			connection.connect();
			in = connection.getInputStream();
			BufferedReader breader = new BufferedReader(new InputStreamReader(
					in, "UTF-8"));
			String currentLine = "";
			while ((currentLine = breader.readLine()) != null) {
				htmlCode.append(currentLine);
			}

			// 输入流写入结束

		} catch (Exception e) {
			throw new Exception("文件转换失败");
		}

		PrintWriter pw = null;
		try {
			File writeFile = new File(fileName);
			boolean isExit = writeFile.exists();
			if (isExit != true) {
				writeFile.createNewFile();
			} else {
				writeFile.delete();
				writeFile.createNewFile();
			}
			pw = new PrintWriter(new FileOutputStream(fileName, true));
			pw.println(htmlCode);
			pw.close();
			
			return writeFile.getAbsolutePath();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			pw.close();
		}

		return "fail";
	}
}
