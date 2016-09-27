package com.ccoffice.cc.cti.hwbb;

import java.util.List;
import java.util.Map;
import com.ccoffice.util.json.JsonData;
import com.ccoffice.util.db.Db;
import com.ccoffice.util.log.Log;
import com.ccoffice.util.page.Page;

public class Hwbb {
	Map _map = null;
	String yhid = "";
	String groupId = "";
	JsonData json = null;
	public  Hwbb(Map _map){
		this._map = _map;
		this.yhid = _map.get("yhid").toString();
		this.groupId = _map.get("group_id").toString();
		json = new JsonData();
	}	
	
	public Map queryLdxd(){	
		try{
			StringBuffer sqlStr = new StringBuffer();
			StringBuffer sqlWhere = new StringBuffer();
			String temp = "";
			temp = _map.get("cx_caller")==null?"":String.valueOf(_map.get("cx_caller"));
			if(!"".equals(temp)){
				sqlWhere.append(" and caller = '").append(temp).append("'");
			}
			temp = _map.get("cx_called")==null?"":String.valueOf(_map.get("cx_called"));
			if(!"".equals(temp)){
				sqlWhere.append(" and called = '").append(temp).append("'");
			}
			temp = _map.get("cx_gh")==null?"":String.valueOf(_map.get("cx_gh"));
			if(!"".equals(temp)){
				sqlWhere.append(" and gh = '").append(temp).append("'");
			}
			temp = _map.get("cx_begintime_start")==null?"":String.valueOf(_map.get("cx_begintime_start"));
			if(!"".equals(temp)){
				sqlWhere.append(" and begintime > '").append(temp).append("'");
			}
			temp = _map.get("cx_begintime_end")==null?"":String.valueOf(_map.get("cx_begintime_end"));
			if(!"".equals(temp)){
				sqlWhere.append(" and begintime < '").append(temp).append(" 23:59:59'");
			}
			Page page = new Page();
			page.setSql("select caller,called,gh,date_format(begintime,'%Y-%c-%d %h:%i:%s') begintime,date_format(endtime,'%Y-%c-%d %h:%i:%s') endtime from  t_cti_bill a where status = 1 "+sqlWhere.toString());
			page.setSqlCount("select count(*) from  t_cti_bill a where status = 1 "+sqlWhere.toString());
			
			page.setNext_page(_map.get("page")==null?"1":_map.get("page").toString());
			page.setPage_size(_map.get("rows")==null?"10":_map.get("rows").toString());
			List _list = Db.getPageDataExt(page);
			json.addListEasyUI("table_list",_list,page);   
		}catch(Exception e){
			Log.error(yhid,"queryLdxd","","查询来电详单数据时异常"+e.toString());
			json.addData("success","false");
			json.addData("msg","查询来电详单数据时异常,"+e.toString());
		}
		return json.getJsonData();
	}
	
	public Map queryWjld(){	
		try{
			StringBuffer sqlWhere = new StringBuffer();
			StringBuffer sqlStr = new StringBuffer();
			String temp = "";
			
			temp = _map.get("cx_begintime_start")==null?"":String.valueOf(_map.get("cx_begintime_start"));
			if(!"".equals(temp)){
				sqlWhere.append(" and a.begintime > '").append(temp).append("'");
			}
			
			temp = _map.get("cx_begintime_end")==null?"":String.valueOf(_map.get("cx_begintime_end"));
			if(!"".equals(temp)){
				sqlWhere.append(" and a.begintime < '").append(temp).append(" 23:59:59'");
			}
			temp = _map.get("cx_caller")==null?"":String.valueOf(_map.get("cx_caller"));
			if(!"".equals(temp)){
				sqlWhere.append(" and a.caller like '%").append(temp).append("%'");
			}
			
			sqlStr.append("select a.*,b.call_time,b.call_yhid call_name,(case when b.if_call =1 then '已拨打' else '' end )status from v_noanswer a left join t_cc_mbh_noanswer_call b on a.axlh = b.xlh where 1 = 1 ").append(sqlWhere).append(" order by begintime desc ");
			
			Page page = new Page();
			page.setSql(sqlStr.toString());
			page.setSqlCount("select count(*) from v_noanswer a where  1=1  "+sqlWhere.toString());
			page.setNext_page(_map.get("page")==null?"1":_map.get("page").toString());
			page.setPage_size(_map.get("rows")==null?"10":_map.get("rows").toString());
			List _list = Db.getPageDataExt(page);
			json.addListEasyUI("table_list",_list,page);        
		}catch(Exception e){
			Log.error(yhid,"queryWjld","","查询未接来电数据时异常"+e.toString());
			json.addData("success","false");
			json.addData("msg","查询未接来电数据时异常,"+e.toString());
		}
		return json.getJsonData();
	}
	public Map queryHwyhwl(){	
		try{
			StringBuffer sqlWhere = new StringBuffer();
			StringBuffer sqlStr = new StringBuffer();
			String temp = "";
			String cx_tjbc = _map.get("cx_tjbc")==null?"":String.valueOf(_map.get("cx_tjbc"));
			temp = _map.get("cx_begintime_start")==null?"":String.valueOf(_map.get("cx_begintime_start"));
			if(!"".equals(temp)){
				sqlWhere.append(" and begintime > '").append(temp).append("'");
			}
			temp = _map.get("cx_begintime_end")==null?"":String.valueOf(_map.get("cx_begintime_end"));
			if(!"".equals(temp)){
				sqlWhere.append(" and begintime < '").append(temp).append(" 23:59:59'");
			}
			temp = _map.get("cx_calltype")==null?"":String.valueOf(_map.get("cx_calltype"));
			if(!"".equals(temp)){
				sqlWhere.append(" and calltype = '").append(temp).append("'");
			}
			if("".equals(cx_tjbc)){
				sqlStr.append(" select gh,'-' sjd, count(*) sl from t_cti_record where status = 1 ").append(sqlWhere).append(" group by gh order by gh ");
			}else if ("day".equals(cx_tjbc)){
				sqlStr.append("select gh,date_format(begintime,'%Y-%c-%d') sjd,count(*) sl from t_cti_record where status = 1 ").append(sqlWhere).append(" group by gh ,date_format(begintime,'%Y-%c-%d')  order by gh");
			}else if ("month".equals(cx_tjbc)){
				sqlStr.append("select gh,date_format(begintime,'%Y-%c') sjd,count(*) sl from t_cti_record where status = 1 ").append(sqlWhere).append(" group by gh ,date_format(begintime,'%Y-%c')  order by gh");
			}
			List _list = Db.getJtNExt().queryForList(sqlStr.toString());
			json.addListEasyUI("table_list",_list,null);        
		}catch(Exception e){
			Log.error(yhid,"queryHwyhwl","","查询话务员话务量数据时异常"+e.toString());
			json.addData("success","false");
			json.addData("msg","查询话务员话务量数据时异常,"+e.toString());
		}
		return json.getJsonData();
	}
	public Map queryHwysc(){	
		try{
			StringBuffer sqlWhere = new StringBuffer();
			StringBuffer sqlStr = new StringBuffer();
			String temp = "";
			String cx_tjbc = _map.get("cx_tjbc")==null?"":String.valueOf(_map.get("cx_tjbc"));
			temp = _map.get("cx_begintime_start")==null?"":String.valueOf(_map.get("cx_begintime_start"));
			if(!"".equals(temp)){
				sqlWhere.append(" and begintime > '").append(temp).append("'");
			}
			temp = _map.get("cx_begintime_end")==null?"":String.valueOf(_map.get("cx_begintime_end"));
			if(!"".equals(temp)){
				sqlWhere.append(" and begintime < '").append(temp).append(" 23:59:59'");
			}
			temp = _map.get("cx_calltype")==null?"":String.valueOf(_map.get("cx_calltype"));
			if(!"".equals(temp)){
				sqlWhere.append(" and calltype = '").append(temp).append("'");
			}
			if("".equals(cx_tjbc)){
				sqlStr.append(" select gh,'-' sjd,sum(TIMESTAMPDIFF(MINUTE,begintime, endtime)) sl from t_cti_record where status = 1 ").append(sqlWhere).append(" group by gh order by gh ");
			}else if ("day".equals(cx_tjbc)){
				sqlStr.append("select gh,date_format(begintime,'%Y-%c-%d') sjd,sum(TIMESTAMPDIFF(MINUTE,begintime,endtime)) sl from t_cti_record where status = 1 ").append(sqlWhere).append(" group by gh ,date_format(begintime,'%Y-%c-%d')  order by gh");
			}else if ("month".equals(cx_tjbc)){
				sqlStr.append("select gh,date_format(begintime,'%Y-%c') sjd,sum(TIMESTAMPDIFF(MINUTE,begintime,endtime)) sl from t_cti_record where status = 1 ").append(sqlWhere).append(" group by gh ,date_format(begintime,'%Y-%c')  order by gh");
			}
			List _list = Db.getJtNExt().queryForList(sqlStr.toString());
			json.addListEasyUI("table_list",_list,null);         
		}catch(Exception e){
			Log.error(yhid,"queryHwysc","","查询来电详单数据时异常"+e.toString());
			json.addData("success","false");
			json.addData("msg","查询来电详单数据时异常,"+e.toString());
		}
		return json.getJsonData();
	}
	public Map queryPtsl(){	
		try{
			 
		}catch(Exception e){
			Log.error(yhid,"queryPtsl","","查询来电详单数据时异常"+e.toString());
			json.addData("success","false");
			json.addData("msg","查询来电详单数据时异常,"+e.toString());
		}
		return json.getJsonData();
	}
}
