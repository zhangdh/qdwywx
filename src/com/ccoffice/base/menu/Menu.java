package com.ccoffice.base.menu;
import java.util.List;
import java.util.Map;

import com.ccoffice.bean.LoginUser;
import com.ccoffice.util.json.JsonData;
import com.ccoffice.util.cache.Cache;
import com.ccoffice.util.db.Db;
import com.ccoffice.util.log.Log;
import com.ccoffice.util.tools.Guid;



public class Menu{
	Map _map = null;
	String yhid = "";
	String groupId = "";
	JsonData json = null;
	public  Menu(Map map){
		this._map = map;
		this.yhid = map.get("yhid").toString();
		this.groupId = map.get("group_id").toString();
		json = new JsonData();
	}	
	
	public Map GetMenu(){
		List _list = null;
		try{
			if("admin".equals(yhid) || "sys".equals(yhid)){
				_list = Db.getJtN().queryForList("select * from t_sys_menu where menu_type='1' and yxbz=1 order by menu_order ");
			}else{
				String jsId = ((LoginUser)Cache.getLoginInfo(yhid)).getJsId();
				StringBuffer sqlStr = new StringBuffer();
				sqlStr.append("select * from  t_sys_menu a ,t_sys_menu_authority b ")
				 	  .append(" where a.menu_id = b.menu_id and a.menu_type='1' and a.yxbz = 1 and b.js_id in ('").append(yhid)
				      .append("','").append(  jsId).append("') order by menu_order ");
				_list = Db.getJtN().queryForList(sqlStr.toString());
			}	
			if(_list.size()==0){
				json.addData("menuStr","");
				return json.getJsonData();
			}
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
			Log.error(yhid,"GetMenu","com.ccoffice.base.menu.Menu","查询菜单数据时异常"+e.toString());
		}
		return json.getJsonDataMap();
	}
}
