package com.ccoffice.util.page;

import java.util.Map;

public class PageInfo {

	private int next_page = 1;
	private int page_size = 10;
	private int curPage=0;
	private int allPage=0;
	private int allCount=0;
	private String sql;
	private String sqlCount;	
	public String getSqlCount() {
		return sqlCount;
	}
	public void setSqlCount(String sqlCount) {
		this.sqlCount = sqlCount;
	}
	public PageInfo(Map _map){
		if(_map.get("next_page")!=null){
			this.setNext_page(Integer.parseInt(_map.get("next_page").toString()));
		}
		if(_map.get("page_size")!=null){
			this.setPage_size(Integer.parseInt(_map.get("page_size").toString()));
		}
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public int getNext_page() {
		return next_page;
	}
	public void setNext_page(int next_page) {
		this.next_page = next_page;
	}
	public int getPage_size() {
		return page_size;
	}
	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getAllPage() {
		return allPage;
	}
	public void setAllPage(int allPage) {
		this.allPage = allPage;
	}
	public int getAllCount() {
		return allCount;
	}
	public void setAllCount(int allCount) {
		this.allCount = allCount;
	}
}
