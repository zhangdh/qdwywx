package com.ccoffice.base.code;

import java.util.List;
import java.util.Map;

import com.ccoffice.util.db.Db;
import com.ccoffice.util.json.JsonData;
import com.ccoffice.util.log.Log;
import com.ccoffice.util.page.Page;

public class Code {
	Map _map = null;
	String yhid = "";
	String groupId = "";
	JsonData json = null;
	public  Code(Map _map){
		json = new JsonData();
		this._map = _map;		
		this.yhid = _map.get("yhid").toString();
		this.groupId = _map.get("group_id").toString();
	}
	
	public Map getModule(){	
		try{
			List _list = null;
			if("sys".equals(yhid)){
				_list = Db.getJtN().queryForList("select module_id dm,module_name mc  from t_sys_module");
			}else{
				_list = Db.getJtN().queryForList("select module_id dm,module_name mc  from t_sys_module where group_id = '"+groupId+"'");
			}
			json.addSelect("module",_list,"模块");
		}catch(Exception e){
			Log.error(yhid,"getModule","","得到功能模块时异常"+e.toString());
			json.addData("result","false");
			json.addData("msg","得到功能模块时异常");
		}
		return json.getJsonDataSelect();
	}
	public Map getDmType(){	
		try{
			String moduleId = _map.get("moduleId")==null?"":String.valueOf(_map.get("moduleId"));
			if(!"".equals(moduleId)){
				List _list  = Db.getJtN().queryForList("select dm,mc from t_dm_type where yxbz = 1 and group_id='"+groupId+"' and module_id='"+moduleId+"'");
				json.addSelect("dm_type",_list,"代码类型");
			}			
		}catch(Exception e){
			Log.error(yhid,"getDmType","","得到功能模块时异常"+e.toString());
			json.addData("result","false");
			json.addData("msg","得到功能模块时异常");
		}
		return json.getJsonDataSelect();
	}
	
	
	public Map saveCode(){	
		try{
			String dm_type = _map.get("dm_type")==null?"":String.valueOf(_map.get("dm_type"));
			String dm = _map.get("dm")==null?"":String.valueOf(_map.get("dm"));
			String mc = _map.get("mc")==null?"":String.valueOf(_map.get("mc"));	
			if(!"".equals(mc) && !"".equals(dm)){
				Db.getJtN().update("update t_dm set mc ='"+mc+"' where dm = '"+dm+"'");
			}else{
				if(!"".equals(mc) && !"".equals(dm_type)){
					List _list = Db.getJtN().queryForList("select max(dm) dm from t_dm where dm_type='"+dm_type+"'");
					int curDm ;
					Object maxDm = ((Map)_list.get(0)).get("dm");
					if(maxDm == null){
						curDm = Integer.parseInt(dm_type+"00");
					}else{
						curDm = Integer.parseInt(maxDm.toString())+1;
					}
					String[] s = mc.split(",");
					for(int i=0;i<s.length;i++){
						Db.getJtN().update("insert into t_dm values('"+dm_type+"','"+curDm+"','"+s[i]+"','"+yhid+"',"+Db.getDateStr()+",1)");
						curDm++;
					}
					json.addResult(true,"添加成功");
				}else{
					json.addResult(false,"代码类别或者代码名称为空");
				}
			}			
		}catch(Exception e){
			Log.error(yhid,"saveDm","","添加代码时异常"+e.toString());
			json.addData("success","false");
			json.addData("msg","添加代码时异常");
		}
		return json.getJsonDataMap();
	}
	
	public Map queryCode(){	
		try{
			String dm_type = _map.get("dm_type")==null?"":String.valueOf(_map.get("dm_type"));
			StringBuffer sqlStr = new StringBuffer();
			sqlStr.append("select * from t_dm where yxbz = 1 and dm_type = '").append(dm_type).append("'");
			Page page = new Page();
			page.setSql(sqlStr.toString());
			page.setNext_page(_map.get("page")==null?"1":_map.get("page").toString());
			page.setPage_size(_map.get("rows")==null?"10":_map.get("rows").toString());
			List _list = Db.getPageData(page);
			json.addListEasyUI("table_list",_list, page);
		}catch(Exception e){
			Log.error(yhid,"queryCode","","查询代码列表时异常"+e.toString());
			json.addData("success","false");
			json.addData("msg","查询代码列表,"+e.toString());
		}
		return json.getJsonDataMap();
	}
	public Map delCode(){	
		try{
			String rows = _map.get("rowsdm")==null?"":String.valueOf(_map.get("rowsdm"));
			if(!"".equals(rows)){
				//rows = "("+rows+"'')";
				Db.getJtN().update("update t_dm set yxbz = 0 where dm = '"+rows+"'");
			}
		}catch(Exception e){
			Log.error(yhid,"delCode","","删除代码列表数据时异常"+e.toString());
			json.addData("success","false");
			json.addData("msg","删除代码列表数据时异常,"+e.toString());
		}
		return json.getJsonData();
	}
}
