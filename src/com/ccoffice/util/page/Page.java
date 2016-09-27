package com.ccoffice.util.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Page {
	private String next_page = "1";
	private String page_size = "20";
	private String curPage="1";
	private String allPage="0";
	private String allCount="0";
	private String sql="";
	private String sqlCount="";
	public String getNext_page() {
		return next_page;
	}
	public void setNext_page(String next_page) {
		this.next_page = next_page;
	}
	public String getPage_size() {
		return page_size;
	}
	public void setPage_size(String page_size) {
		this.page_size = page_size;
	}
	public String getCurPage() {
		return curPage;
	}
	public void setCurPage(String curPage) {
		this.curPage = curPage;
	}
	public String getAllPage() {
		return allPage;
	}
	public void setAllPage(String allPage) {
		this.allPage = allPage;
	}
	public String getAllCount() {
		return allCount;
	}
	public void setAllCount(String allCount) {
		this.allCount = allCount;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getSqlCount() {
		return sqlCount;
	}
	public void setSqlCount(String sqlCount) {
		this.sqlCount = sqlCount;
	}
}
