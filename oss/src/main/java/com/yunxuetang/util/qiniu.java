package com.yunxuetang.util;

import java.util.ArrayList;
import java.util.List;

import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.rsf.ListPrefixRet;
import com.qiniu.api.rsf.RSFClient;
import com.qiniu.api.rsf.ListItem;
import com.qiniu.api.rsf.RSFEofException;

public class qiniu {

	private void hehe() {
		// TODO Auto-generated method stub
		String qnAccessKey = "PN6SV6Fm-Ueu-D8NYN_NfJwpPuedsQALNKmwL_IQ";
		String qnSecretKey = "O6sBs8dw9cJjamEx2d6vgY1sXnPnIlg5Nsn6CwPH";
		String qnBucketName = "yxt-bj";
		String ACCESS_KEY = qnAccessKey;
		String SECRET_KEY = qnSecretKey;
		Mac mac = new Mac(ACCESS_KEY, SECRET_KEY);

		RSFClient rs = new RSFClient(mac);
		String marker = "";
		long currettime = System.currentTimeMillis();
		List<ListItem> all = new ArrayList<ListItem>();
		ListPrefixRet ret = null;
		while (true) {
			ret = rs.listPrifix(qnBucketName, "head", marker, 10);
			marker = ret.marker;
			all.addAll(ret.results);
			if (!ret.ok()) {
				// no more items or error occurs
				break;
			}
		}
		if (ret.exception.getClass() != RSFEofException.class) {
			// error handler
		}

		System.err.println(all.size());

		for (ListItem item : all) {
			System.err.println(item.key);
		}
	}

	public static void main(String[] args) {
		String qnAccessKey = "PN6SV6Fm-Ueu-D8NYN_NfJwpPuedsQALNKmwL_IQ";
		String qnSecretKey = "O6sBs8dw9cJjamEx2d6vgY1sXnPnIlg5Nsn6CwPH";
		String qnBucketName = "yxt-bj";
		String ACCESS_KEY = qnAccessKey;
		String SECRET_KEY = qnSecretKey;
		Mac mac = new Mac(ACCESS_KEY, SECRET_KEY);

		RSFClient rs = new RSFClient(mac);
		String marker = "";
		long currettime = System.currentTimeMillis();
		List<ListItem> all = new ArrayList<ListItem>();
		ListPrefixRet ret = null;
		while (true) {
			ret = rs.listPrifix(qnBucketName, "head", marker, 10);
			marker = ret.marker;
			all.addAll(ret.results);
			if (!ret.ok()) {
				// no more items or error occurs
				break;
			}
		}
		if (ret.exception.getClass() != RSFEofException.class) {
			// error handler
		}

		System.err.println(all.size());

		for (ListItem item : all) {
			System.err.println(item.key);
		}
	}

}
