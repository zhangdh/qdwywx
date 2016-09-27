package com.ccoffice.txl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ccoffice.util.json.JsonData;
import com.ccoffice.util.db.Db;
import com.ccoffice.util.log.Log;
import com.ccoffice.util.page.Page;
import com.ccoffice.util.tools.Guid;
public class Txl {
	Map _map = null;
	String yhid = "";
	JsonData json = null;
	public  Txl(Map _map){
		this._map = _map;
		this.yhid = _map.get("yhid").toString();
		json = new JsonData();
	} 
	public Map getTxlTree(){	
		try{
			StringBuffer sqlStr = new StringBuffer();
			String sj_id = _map.get("sj_id")==null?"":String.valueOf(_map.get("sj_id"));
			if(!"".equals(sj_id)){
				sqlStr.append("select guid,name,sj_id,lb_dm from t_cc_txl where yxbz = 1 ").append(" and sj_id='").append(sj_id).append("' order by cjsj  ");
			}else{
				sqlStr.append("select guid,name,sj_id,lb_dm from t_cc_txl where yxbz = 1 order by cjsj  ");
			}
			List _list = Db.getJtN().queryForList(sqlStr.toString());
			json.addData("txlData", _list);
		}catch(Exception e){
			Log.error(yhid,"getTxlTree","","得到通讯录树数据时异常"+e.toString());
			json.addData("result","false");
			json.addData("msg","得到用户树数据时异常");
		}
		return json.getJsonDataMap();
	}
	public Map saveTxl(){
		try{
			String sj_id = _map.get("sj_id")==null?"":String.valueOf(_map.get("sj_id"));
			StringBuffer sqlStr = new StringBuffer();
			sqlStr.append("insert into t_cc_txl(zzid,guid,yhid,name,phone1,phone2,phone3,phone4,phone5,phone6,lb_dm,bz,sj_id,yxbz,cjsj) values('0','").append(Guid.get()).append("','").append(yhid).append("','").append(_map.get("name"))
				  .append("','").append(_map.get("phone1")).append("','").append(_map.get("phone2")).append("','").append(_map.get("phone3")).append("','")
				  .append(_map.get("phone4")).append("','").append(_map.get("phone5")).append("','").append(_map.get("phone6"))
				  .append("','").append(_map.get("lb_dm")).append("','").append(_map.get("bz"))
				  .append("','").append(sj_id).append("',1,'").append(Db.getStr()).append("')");
			Db.getJtN().update(sqlStr.toString());
		}catch(Exception e){
			Log.error(yhid,"saveTxl","","保存通讯录数据时异常"+e.toString());
			json.addResult(false,"保存通讯录数据时异常");
			e.printStackTrace();
		}
		return json.getJsonDataMap();
	}
	
	public Map getTxlList(){
		StringBuffer sqlStr = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlWhere = new StringBuffer();
		String temp = "";
		try{
			temp = _map.get("sjid")==null?"":String.valueOf(_map.get("sjid"));
			if(!"".equals(temp)){
				sqlWhere.append(" and (sj_id= '").append(temp).append("' or guid = '").append(temp).append("')");
			}
			temp = _map.get("cx_name")==null?"":String.valueOf(_map.get("cx_name"));
			if(!"".equals(temp)){
				sqlWhere.append(" and name like '%").append(temp).append("%'");
			}
			sqlStr.append("select * from t_cc_txl where yxbz = 1 ").append(sqlWhere);
			sqlCount.append("select count(*) from ").append("t_cc_txl where yxbz = 1 ").append(sqlWhere);
			Page page = new Page();
			page.setSql(sqlStr.toString());
			page.setSqlCount(sqlCount.toString());

			
			page.setNext_page(_map.get("page")==null?"1":_map.get("page").toString());
			page.setPage_size(_map.get("rows")==null?"10":_map.get("rows").toString());
			
			List _list = Db.getPageData(page);
			json.addListEasyUI("table_list", _list, page);
		}catch(Exception e){
			Log.error(yhid,"getTxlList","","得到通讯录下级数据时异常"+e.toString());
			json.addResult(false,"得到通讯录下级数据时异常");
		}
		return json.getJsonDataMap();
	}
	
	public Map modiTxl(){
		StringBuffer sqlStr = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		try{
			String guid = _map.get("guid")==null?"":String.valueOf(_map.get("guid"));
			if(!"".equals(guid)){
				sqlStr.append("update t_cc_txl set name = '").append(_map.get("name")).append("',phone1='").append(_map.get("phone1"))
					  .append("',phone2='").append(_map.get("phone2")).append("',phone3='").append(_map.get("phone3")).append("',")
					  .append("phone4='").append(_map.get("phone4")).append("' ,")
					  .append("phone5='").append(_map.get("phone5")).append("',phone6='").append(_map.get("phone6")).append("',lb_dm='")
					  .append(_map.get("lb_dm")).append("',bz='").append(_map.get("bz")).append("' where guid = '").append(guid).append("'");
				Db.getJtN().update(sqlStr.toString());
			}
		}catch(Exception e){
			Log.error(yhid,"modiTxl","","修改通讯录数据时异常"+e.toString());
			json.addResult(false,"修改通讯录数据时异常");
		}
		return json.getJsonDataMap();
	}
	
	public Map delTxl(){
		StringBuffer sqlStr = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		try{
			String id = _map.get("id")==null?"":String.valueOf(_map.get("id"));
			if(!"".equals(id)){
				Db.getJtN().update("update t_cc_txl set yxbz = 0 where guid = '"+id+"'");
			}
		}catch(Exception e){
			Log.error(yhid,"delTxl","","删除通讯录数据时异常"+e.toString());
			json.addResult(false,"删除通讯录数据时异常");
		}
		return json.getJsonDataMap();
	}
	
	public Map showTxl(){
		StringBuffer sqlStr = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		try{
			String id = _map.get("id")==null?"":String.valueOf(_map.get("id"));
			if(!"".equals(id)){
				sqlStr.append("select * from t_cc_txl where guid='").append(id).append("'");
				Map map = Db.getJtN().queryForMap(sqlStr.toString());
					  
				json.addForm(map);
			}
		}catch(Exception e){
			Log.error(yhid,"showTxl","","展示通讯录数据时异常"+e.toString());
			json.addResult(false,"展示通讯录数据时异常");
		}
		return json.getFormData();
	}
	
	
	public Map upload(HttpServletRequest request,HttpServletResponse response){
		/*try{
			MultipartFile  uploadFileCtrl =request.getParameter(arg0)
			if(uploadFileCtrl!"".equals(id)){
				Db.getJtN().update("update t_cc_txl set yxbz = 0 where guid = '"+id+"'");
			}
		}catch(Exception e){
			Log.error(yhid,"delTxl","","删除通讯录数据时异常"+e.toString());
			json.addResult(false,"删除通讯录数据时异常");
		}*/
		return json.getJsonDataMap();
	}
}