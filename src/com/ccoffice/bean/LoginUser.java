package com.ccoffice.bean;

import java.util.Map;

public class LoginUser {
	private String groupID;
	private String bmId;
	private String jsId;
	private String yhid;	
	private String userName;
	private String name;
	private String dlIp;
	private String dlToken;
	private String dlSj;
	private Map kzMap;
	public String getGroupID() {
		return groupID;
	}
	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
	public String getYhid() {
		return yhid;
	}
	public void setYhid(String yhid) {
		this.yhid = yhid;
	}
	public String getDlIp() {
		return dlIp;
	}
	public void setDlIp(String dlIp) {
		this.dlIp = dlIp;
	}
	public String getDlToken() {
		return dlToken;
	}
	public void setDlToken(String dlToken) {
		this.dlToken = dlToken;
	}
	public String getDlSj() {
		return dlSj;
	}
	public void setDlSj(String dlSj) {
		this.dlSj = dlSj;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Map getKzMap() {
		return kzMap;
	}
	public void setKzMap(Map kzMap) {
		this.kzMap = kzMap;
	}
	public String getBmId() {
		return bmId;
	}
	public void setBmId(String bmId) {
		this.bmId = bmId;
	}
	public String getJsId() {
		return jsId;
	}
	public void setJsId(String jsId) {
		this.jsId = jsId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
