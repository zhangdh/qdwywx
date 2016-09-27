package com.ccoffice.cc.tjcx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ccoffice.base.excel.ExcelTool;
import com.ccoffice.util.db.Db;
import com.ccoffice.util.json.JsonData;
import com.ccoffice.util.log.Log;
import com.ccoffice.util.page.Page;
import com.ccoffice.util.push.PushSync;
import com.ccoffice.util.tools.Guid;

public class Ywtj {
	Map _map = null;
	String yhid = "";
	String groupId = "";
	JsonData json = null;
	public  Ywtj(Map map){
		this._map = map;
		this.yhid = map.get("yhid").toString();
		this.groupId = map.get("group_id").toString();
		json = new JsonData();
	}
	
	//手机客户端统计数据
	public Map getGrid(){
		try{
			StringBuffer sqlStr = new StringBuffer();
			List _list = Db.getJtN().queryForList("select guid dm,name mc from t_cc_wywx_grid where type = '1010801'");
			
			json.addSelect("cx_repairer_station", _list,"");
		}catch(Exception e){
			Log.error(yhid,"queryTj","","客户端统计"+e.toString());
		}
		return json.getSelectData();
	}
		
		
	//手机客户端统计数据
	public Map queryTj(){
		try{
			StringBuffer sqlStr = new StringBuffer();
			sqlStr.append(" select count(*) count, SUM(case when state = '1010701' then 1 else 0 end) dcl,")
					.append(" SUM(case when state = '1010702' then 1 else 0 end) yht,")
					.append(" SUM(case when state = '1010704' then 1 else 0 end) yyy,")
					.append(" SUM(case when state = '1010703' then 1 else 0 end) yhf  from t_cc_wywx_service_repair where repairer_id = '").append(yhid).append("'");
			
			Map _map = Db.getJtN().queryForMap(sqlStr.toString());
			json.addForm(_map);
		}catch(Exception e){
			Log.error(yhid,"queryTj","","客户端统计"+e.toString());
		}
		return json.getJsonDataMap();
	}
		
	//按照点部统计每个点部的统计数量.当前状态统计
	public Map querySlgd(){
		try{
			String cx_time_start = _map.get("cx_time_start")==null?"":String.valueOf(_map.get("cx_time_start"));
			String cx_time_end = _map.get("cx_time_end")==null?"":String.valueOf(_map.get("cx_time_end"));
			StringBuffer sqlStr = new StringBuffer();
			StringBuffer sqlWhere = new StringBuffer();
			if(!"".equals(cx_time_start)){
				sqlWhere.append(" and create_time > to_timestamp('").append(cx_time_start).append("','YYYY-MM-DD HH24:MI:SS')");
			}
			if(!"".equals(cx_time_end)){
				sqlWhere.append(" and create_time < to_timestamp('").append(cx_time_end).append("','YYYY-MM-DD HH24:MI:SS')");
			}
			
			sqlStr.append("select (select name from t_cc_wywx_grid where guid = a.repairer_station) station_name,")
				  .append("count(*) count,SUM(case when state = '1010701' then 1 else 0 end ) dcl,SUM(case when state = '1010702' then 1 else 0 end ) yht,")
				  .append("SUM(case when state = '1010703' then 1 else 0 end ) yhf ")
				  .append(" from t_cc_wywx_service_repair a where 1= 1 ").append(sqlWhere).append(" group by repairer_station order by repairer_station ");
			List _list = Db.getJtN().queryForList(sqlStr.toString());
			json.addListEasyUI("table_list", _list,null);
		}catch(Exception e){
			Log.error(yhid,"querySlgd","","按照点部统计每个点部的统计数量.当前状态统计"+e.toString());
		}
		return json.getJsonDataEasyUIGrid();
	}
	
		
	//统计各点部下每个维修工的派单数量
	public Map queryRepairerTj(){
		try{
			String cx_time_start = _map.get("cx_time_start")==null?"":String.valueOf(_map.get("cx_time_start"));
			String cx_time_end = _map.get("cx_time_end")==null?"":String.valueOf(_map.get("cx_time_end"));
			String repairer_station = _map.get("cx_repairer_station")==null?"":String.valueOf(_map.get("cx_repairer_station"));
			StringBuffer sqlStr = new StringBuffer();
			StringBuffer sqlWhere = new StringBuffer();
			if(!"".equals(cx_time_start)){
				sqlWhere.append(" and create_time > to_timestamp('").append(cx_time_start).append("','YYYY-MM-DD HH24:MI:SS')");
			}
			if(!"".equals(cx_time_end)){
				sqlWhere.append(" and create_time < to_timestamp('").append(cx_time_end).append("','YYYY-MM-DD HH24:MI:SS')");
			}
			if(!"".equals(repairer_station)){
				sqlWhere.append(" and repairer_station= '").append(repairer_station).append("'");
			}
			sqlStr.append("select (select name from t_sys_yh where yhid = a.repairer_id) repairer_name,")
				  .append("count(*) count,SUM(case when state = '1010701' then 1 else 0 end ) dcl,SUM(case when state = '1010702' then 1 else 0 end ) yht,")
				  .append("SUM(case when state = '1010703' then 1 else 0 end ) yhf ")
				  .append(" from t_cc_wywx_service_repair a where 1= 1 ").append(sqlWhere).append(" group by repairer_id  order by repairer_id ");
			List _list = Db.getJtN().queryForList(sqlStr.toString());
			json.addListEasyUI("table_list", _list,null);
		}catch(Exception e){
			Log.error(yhid,"queryRepairerTj","","统计每个维修工数据时异常"+e.toString());
		}
		return json.getJsonDataEasyUIGrid();
	}
	public Map queryRepairerMyd(){
		try{
			String cx_time_start = _map.get("cx_time_start")==null?"":String.valueOf(_map.get("cx_time_start"));
			String cx_time_end = _map.get("cx_time_end")==null?"":String.valueOf(_map.get("cx_time_end"));
			String repairer_station = _map.get("cx_repairer_station")==null?"":String.valueOf(_map.get("cx_repairer_station"));
			StringBuffer sqlStr = new StringBuffer();
			StringBuffer sqlWhere = new StringBuffer();
			if(!"".equals(cx_time_start)){
				sqlWhere.append(" and create_time > to_timestamp('").append(cx_time_start).append("','YYYY-MM-DD HH24:MI:SS')");
			}
			if(!"".equals(cx_time_end)){
				sqlWhere.append(" and create_time < to_timestamp('").append(cx_time_end).append("','YYYY-MM-DD HH24:MI:SS')");
			}
			if(!"".equals(repairer_station)){
				sqlWhere.append(" and repairer_station= '").append(repairer_station).append("'");
			}
			sqlStr.append("select (select name from t_sys_yh where yhid = a.repairer_id) repairer_name,")
				  .append("count(*) count,SUM(case when state = '1010703' then 1 else 0 end ) yhf,SUM(case when return_attitude = '1010901' then 1 else 0 end ) attitude_hmy,SUM(case when return_attitude = '1010902' then 1 else 0 end ) attitude_my,")
				  .append("SUM(case when return_attitude = '1010903' then 1 else 0 end ) attitude_bmy,SUM(case when return_quality = '1011001' then 1 else 0 end ) quality_hmy, ")
				  .append("SUM(case when return_quality = '1011002' then 1 else 0 end ) quality_my,SUM(case when return_quality = '1011003' then 1 else 0 end ) quality_bmy, ")
				  .append("SUM(case when return_clean = '1011101' then 1 else 0 end ) clean_hmy,SUM(case when return_clean = '1011102' then 1 else 0 end ) clean_my, ")
				  .append("SUM(case when return_clean = '1011103' then 1 else 0 end ) clean_bmy ")
				  .append(" from t_cc_wywx_service_repair a where 1= 1 ").append(sqlWhere).append(" group by repairer_id  order by repairer_id ");
			List _list = Db.getJtN().queryForList(sqlStr.toString());
			json.addListEasyUI("table_list", _list,null);
		}catch(Exception e){
			Log.error(yhid,"queryRepairerMyd","","数据统计-统计维修工满意度数据时异常"+e.toString());
		}
		return json.getJsonDataEasyUIGrid();
	}
}
