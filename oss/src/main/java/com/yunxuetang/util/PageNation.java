package com.yunxuetang.util;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class PageNation extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3358230889780918602L;
	private int currPage;
	private int totalPages;
	private int perPageRows;
	private int totalRows;
	private String linkBaseUrl;

	public PageNation() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int doStartTag() throws JspException {
		String html = "";
		/**
		 * 如果当前页是0，则给当前页变成第一页
		 */
//		if ((this.getCurrPage()-1) <= 0) {
//			this.setCurrPage(1);
//		}else{
//			this.setCurrPage(this.getCurrPage()-1);
//		}

		/**
		 * 首页
		 */
		if (this.getTotalPages() > 0) {
			if (this.getCurrPage() == 1) {
				html += "<li class=\"disabled\"><a href=\"#\">首页</a></li>";
			} else {
				html += "<li class=\"\"><a href=\""+this.getLinkBaseUrl()+"&n=0&s="+this.getPerPageRows()+"\">首页</a></li>";
			}
		}
		/**
		 * 上一页
		 */
		if (this.getTotalPages() >= 1) {
			if (this.getCurrPage() == 1) {
				html += "<li class=\"disabled\"><a href=\"#\">上一页</a></li>";
			} else {
				html += "<li class=\"\"><a href=\""+this.getLinkBaseUrl()+"&n="+(this.getCurrPage()-2)+"&s="+this.getPerPageRows()+"\">上一页</a></li>";
			}
		}
		
		/**
		 * 页码显示，当前页+-5
		 */

		int start = 0;
		int end = 0;

		start = (this.getCurrPage() - 3) > 0 ? (this.getCurrPage() - 3) : 1;
		end = (this.getCurrPage() + 3) > this.getTotalPages() ? this
				.getTotalPages() : (this.getCurrPage() + 3);
		for (int i = start; i <= end; i++) {
			if (this.getCurrPage() == i) {
				html += "<li class=\"active\"><a href=\""+this.getLinkBaseUrl()+"&n="+(i-1)+"&s="+this.getPerPageRows()+"\">" + i+ "</a></li>";
			} else {
				html += "<li><a href=\""+this.getLinkBaseUrl()+"&n="+(i-1)+"&s="+this.getPerPageRows()+"\">" + i + "</a></li>";
			}
		}

		/**
		 * 下一页
		 */
		if (this.getCurrPage() <= this.getTotalPages() - 1) {
			html += "<li><a href=\""+this.getLinkBaseUrl()+"&n="+(this.getCurrPage())+"&s="+this.getPerPageRows()+"\">下一页</a></li>";
		}
		/**
		 * 末页
		 */
		if (this.getCurrPage() <= this.getTotalPages() - 1) {
			html += "<li><a href=\""+this.getLinkBaseUrl()+"&n="+(this.getTotalPages()-1)+"&s="+this.getPerPageRows()+"\">末页</a></li>";
		}
		
		/**
		 * 增加总记录数和总分页数的显示
		 */
		html += "<li class=\"disabled\"><a href=\"#\">共计："
				+ this.getTotalPages() + "页</a></li>";
		html += "<li class=\"disabled\"><a href=\"#\">共计："
				+ this.getTotalRows() + "条记录</a></li>";

		try {
			pageContext.getOut().write(html);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getPerPageRows() {
		return perPageRows;
	}

	public void setPerPageRows(int perPageRows) {
		this.perPageRows = perPageRows;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public String getLinkBaseUrl() {
		return linkBaseUrl;
	}

	public void setLinkBaseUrl(String linkBaseUrl) {
		this.linkBaseUrl = linkBaseUrl;
	}

}
