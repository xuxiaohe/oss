package com.yunxuetang.util;

import org.springframework.data.domain.Pageable;
/**
 * 组装类，主要用来封装返回结果集及分页信息
 * @author nes
 *
 */
public class ReponseData {
	private Object result;
	//private Pageable page;
	private long total_rows;
	private int curr_rows;
	private int curr_page;
	private int page_rows;
	private long runtime;

	public long getTotal_rows() {
		return total_rows;
	}

	public void setTotal_rows(long total_rows) {
		this.total_rows = total_rows;
	}

	public int getCurr_rows() {
		return curr_rows;
	}

	public void setCurr_rows(int curr_rows) {
		this.curr_rows = curr_rows;
	}

	public int getCurr_page() {
		return curr_page;
	}

	public void setCurr_page(int curr_page) {
		this.curr_page = curr_page;
	}

	public int getPage_rows() {
		return page_rows;
	}

	public void setPage_rows(int page_rows) {
		this.page_rows = page_rows;
	}

	public long getRuntime() {
		return runtime;
	}

	public void setRuntime(long runtime) {
		this.runtime = runtime;
	}

	public ReponseData() {
		this.result = null;
	//	this.page = new PageRequest(0, 10);
	}

	public ReponseData(Object result) {
		this.result = result;
	//	this.page = new PageRequest(0, 10);
	}

	public ReponseData(Object result, Pageable page) {
		this.result = result;
	//	this.page = page;
	}
	public ReponseData(Object result, long total_rows, int curr_rows, int curr_page, int page_rows, long runtime) {
		this.result = result;
		this.total_rows = total_rows;
		this.curr_rows = curr_rows;
		this.curr_page = curr_page;
		this.page_rows = page_rows;
		this.runtime = runtime;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	/*public Pageable getPage() {
		return page;
	}

	public void setPage(Pageable page) {
		this.page = page;
	}*/
	
	/**
	 * 清除分页相关信息
	 */
	public void resetPageInfo()
	{
		this.total_rows =0;
		this.curr_rows = 0;
		this.curr_page = 0;
		this.page_rows =0;		
		this.runtime = 0l;
	}

}
