package com.ccoffice.util.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ccoffice.util.page.Page;

public class JsonData {
	private Map listData;
	private Map selectData;
	private Map formData;
	Map _map = null;
	public JsonData(){
		_map = new HashMap();
		listData = new HashMap();
		selectData = new HashMap();
		formData = new HashMap();
		_map.put("success",true);
		_map.put("result",true);
		_map.put("login",true);
		_map.put("msg","处理成功");
	}
	public Map getListData() {
		return listData;
	}
	public void setListData(Map listData) {
		this.listData = listData;
	}
	public Map getSelectData() {
		return selectData;
	}
	public void setSelectData(Map selectData) {
		this.selectData = selectData;
	}
	public Map getFormData() {
		return formData;
	}
	public void setFormData(Map formData) {
		this.formData = formData;
	}
	public void addList(String tableId,List _list,Page page){
		Map pageMap = new HashMap();
		if(page == null){
			pageMap.put("curpage",0);
			pageMap.put("allpage",0);
			pageMap.put("allcount",0);
		}else{
			pageMap.put("curpage",page.getCurPage());
			pageMap.put("allpage",page.getAllPage());
			pageMap.put("allcount",page.getAllCount());
		}
		Map map = new HashMap();
		map.put("page",pageMap);
		map.put("rows",_list);
		listData.put(tableId,map);
	}
	public void addListEasyUI(String tableId,List _list,Page page){
		String  total = "0";
		if(page == null){
			total = "0"; 
		}else{
			total = page.getAllCount();
		}
		_map.put("total",Integer.parseInt(total));
		_map.put("rows",_list);
		_map.put("page",page==null?0:page.getCurPage());
		_map.put("allpage",page==null?0:page.getAllPage());
	}
	
	public void addSelect(String selectName ,List _list,String name){
		if(!"".equals(name)){
			Map map = new HashMap();
			map.put("dm","");
			map.put("mc",name);
			_list.add(0,map);
		}
		this.selectData.put(selectName,_list);
	}
	public void addForm(Map map){
		//this.formData.put("form_show",map);
		_map.put("formMap",map);
	}
	public void addFormExt(String str,Map _map){
		this.formData.put(str,_map);
	}
	
	public void addData(String name,Object value){
		_map.put(name,value);
	}
	public void addResult(boolean result,String msg){
		_map.put("success",result);
		_map.put("result",result);
		_map.put("msg",msg);
	}
	public Map getJsonData(){
		this._map.put("list_data",listData);
		this._map.put("form_show",formData);
		this._map.put("select_data",selectData);
		return this._map;
	}
	public Map getReturnFormData(){
		this._map.put("data",formData);
		return this._map;
	}
	
	public Map getJsonDataEasyUIGrid(){
		return _map;
	}
	public Map getJsonDataSelect(){
		return selectData;
	}
	public Map getJsonDataResult(){
		return _map;
	}
	public Map getJsonDataMap(){
		return _map;
	}
}
