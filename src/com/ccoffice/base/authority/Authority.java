package com.ccoffice.base.authority;
import java.util.List;
import java.util.Map;

import com.ccoffice.bean.LoginUser;
import com.ccoffice.util.json.JsonData;
import com.ccoffice.util.cache.Cache;
import com.ccoffice.util.db.Db;
import com.ccoffice.util.log.Log;
import com.ccoffice.util.tools.Guid;



public class Authority{
	Map _map = null;
	String yhid = "";
	String groupId = "";
	JsonData json = null;
	public  Authority(Map map){
		this._map = map;
		this.yhid = map.get("yhid").toString();
		this.groupId = map.get("group_id").toString();
		json = new JsonData();
	}	
	
	public Map GetMenu(){
		try{
			List _list = Db.getJtN().queryForList("select * from t_sys_menu where menu_type = 1 and yxbz = 1 order by menu_id ");
			StringBuffer menuStr = new StringBuffer();
			menuStr.append("[");
			for(int i=0;i<_list.size();i++){
				Map _map = (Map)_list.get(i);
				menuStr.append("{menu_id:").append(_map.get("menu_id")).append(",sjid:").append(_map.get("sjid"))
				       .append(",text:'").append(_map.get("text")).append("',url:'").append(_map.get("url"))
				       .append("',icon:'").append(_map.get("icon")).append("'},");
			}
			String menuString = menuStr.toString();
			menuString = menuString.substring(0,menuString.length()-1);
			menuString = menuString+"]";
			json.addData("menuStr",menuString);
			
		}catch(Exception e){
			Log.error(yhid,"GetMenu","","得到菜单数据时异常"+e.toString());
			json.addResult(false,"得到菜单数据时异常");
		}
		return json.getJsonDataMap();
	}
	
	public Map GetJs(){
		try{
			List _list = Db.getJtN().queryForList("select * from t_sys_js where yxbz = 1 ");
			json.addListEasyUI("js", _list,null);
		}catch(Exception e){
			Log.error(yhid,"GetJs","","得到角色数据时异常"+e.toString());
			json.addResult(false,"得到角色数据时异常");
		}
		return json.getJsonDataEasyUIGrid();
	}
	
	public Map GetAuthority(){
		try{
			String js_id = _map.get("js_id")==null?"":String.valueOf(_map.get("js_id"));
			List _list = null;
			if(!"".equals(js_id)){
				 _list = Db.getJtN().queryForList("select menu_id from t_sys_menu_authority where js_id in ('"+yhid+"','"+js_id+"')");
			}		
			json.addListEasyUI("authority", _list,null);
			
		}catch(Exception e){
			Log.error(yhid,"GetAuthority","","得到授权数据时异常"+e.toString());
			json.addResult(false,"得到授权数据时异常");
		}
		return json.getJsonDataEasyUIGrid();
	}
	
	public Map saveAuthority(){
		try{
			String js_id = _map.get("js_id")==null?"":String.valueOf(_map.get("js_id"));
			String menus = _map.get("menus")==null?"":String.valueOf(_map.get("menus"));
			if(!"".equals(js_id) && !"".equals(menus)){
				Db.getJtN().update("delete from t_sys_menu_authority where js_id = '"+js_id+"'");
				String[] menusArray = menus.split(",");
				for(int i=0;i<menusArray.length;i++){
					Db.getJtN().update("insert into t_sys_menu_authority values('"+Db.getGUID()+"','"+menusArray[i]+"','"+js_id+"',1)");
				}
			}
		}catch(Exception e){
			Log.error(yhid,"saveAuthority","","保存授权数据时异常"+e.toString());
			json.addResult(false,"保存授权数据时异常");
		}
		return json.getJsonDataMap();
	}
}
