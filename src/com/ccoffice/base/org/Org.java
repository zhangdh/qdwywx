package com.ccoffice.base.org;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ccoffice.util.json.JsonData;
import com.ccoffice.util.db.Db;
import com.ccoffice.util.log.Log;
import com.ccoffice.util.page.Page;
import com.ccoffice.util.tools.Encrypt;
import com.ccoffice.util.tools.SysPara;
public class Org {
	Map _map = null;
	String yhid = "";
	JsonData json = null;
	public  Org(Map _map){
		this._map = _map;
		this.yhid = _map.get("yhid").toString();
		json = new JsonData();
	}
	public Map getOrgTree(){	
		try{
			String sql = "select id,orgid,orgname,sjid,lbdm from t_sys_org where yxbz = 1 order by cjsj  ";
			List _list = Db.getJtN().queryForList(sql);
			json.addData("orgData", _list);
		}catch(Exception e){
			Log.error(yhid,"getOrgTree","","得到用户树数据时异常"+e.toString());
			json.addData("result","false");
			json.addData("msg","得到用户树数据时异常");
		}
		return json.getJsonDataMap();
	}
	public Map saveBm(){
		try{
			String bmid = _map.get("cur_bmid")==null?"":String.valueOf(_map.get("cur_bmid"));
			StringBuffer sqlStr = new StringBuffer();
			if(!"".equals(bmid)){
				sqlStr.append("update t_sys_bm set bmmc = '").append(_map.get("bmmc")==null?"":String.valueOf(_map.get("bmmc")))
				      .append("',bmdh = '").append(_map.get("bmdh")==null?"":String.valueOf(_map.get("bmdh")))
				      .append("',bmbz='").append(_map.get("bmbz")==null?"":String.valueOf(_map.get("bmbz")))
				      .append("' where bmid='").append(bmid).append("'");
				Db.getJtN().update(sqlStr.toString());
			}else{
				bmid = String.valueOf(Db.getNextId());
				
				sqlStr.append("insert into t_sys_bm(group_id,bmid,bmmc,sjbmid,bmdh,bmbz,cjsj,yxbz,lrr_yhid)values('0','")
						.append(bmid).append("','").append(_map.get("bmmc")==null?"":String.valueOf(_map.get("bmmc")))
						.append("','").append(_map.get("sjbmid")==null?"":String.valueOf(_map.get("sjbmid")))
						.append("','").append(_map.get("bmdh")==null?"":String.valueOf(_map.get("bmdh")))
						.append("','").append(_map.get("bmbz")==null?"":String.valueOf(_map.get("bmbz")))
						.append("','").append(Db.getStr()).append("',1,'").append(yhid).append("')");
				Db.getJtN().update(sqlStr.toString());
				sqlStr.delete(0,sqlStr.length());
				sqlStr.append("insert into t_sys_org values('0','").append(_map.get("sjid")==null?"":String.valueOf(_map.get("sjid"))).append("_")
					  .append(bmid).append("','").append(bmid).append("','")
					  .append(_map.get("bmmc")==null?"":String.valueOf(_map.get("bmmc"))).append("','")
					  .append(_map.get("sjid")==null?"":String.valueOf(_map.get("sjid"))).append("','")
					  .append(_map.get("sjbmid")==null?"":String.valueOf(_map.get("sjbmid"))).append("','2','")
					  .append(Db.getStr()).append("',1)");
				Db.getJtN().update(sqlStr.toString());
			}
			
			Iterator it = _map.keySet().iterator();
			 while(it.hasNext()){
			 	String key = (String) it.next();
			 	if(key.startsWith("kz")){
			 		String value = _map.get(key).toString();
			 		if(!"".equals(value)){
			 			Db.getJtN().update("delete from t_sys_bm_kz where kz_dm = '"+key.substring(2)+"', and bmid = '"+bmid+"'");
			 			
			 			Db.getJtN().update("insert into t_sys_bm_kz values('"+bmid+"','"+key.substring(2)+"','"+value+"')");
			 		}
			 	}
			 }
		}catch(Exception e){
			Log.error(yhid,"saveBn","","保存部门数据时异常"+e.toString());
			json.addResult(false,"保存部门数据时异常");
			e.printStackTrace();
		}
		return json.getJsonDataMap();
	}
	
	public Map getOrgList(){
		StringBuffer sqlStr = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		try{
			String id = _map.get("sjid")==null?"":String.valueOf(_map.get("sjid"));
			sqlStr.append("select id,orgid,orgname mc,cjsj,lbdm,(case when lbdm = 1 then '人员' when lbdm = 2 then '部门' when lbdm =3 then '岗位' else '' end ) lb_mc from t_sys_org where sjid = '")
				  .append(id).append("'");
			sqlCount.append("select count(*) from ").append("t_sys_org where sjid = '").append(id).append("'");
			Page page = new Page();
			page.setSql(sqlStr.toString());
			page.setSqlCount(sqlCount.toString());

			
			page.setNext_page(_map.get("page")==null?"1":_map.get("page").toString());
			page.setPage_size(_map.get("rows")==null?"10":_map.get("rows").toString());
			
			List _list = Db.getPageData(page);
			json.addListEasyUI("table_list", _list, page);
		}catch(Exception e){
			Log.error(yhid,"getOrgList","","得到下级数据时异常"+e.toString());
			json.addResult(false,"得到下级数据时异常");
		}
		return json.getJsonDataMap();
	}
	
	public Map delBm(){
		try{
			String curbmid = _map.get("curbmid")==null?"":String.valueOf(_map.get("curbmid"));
			List _list = Db.getJtN().queryForList("select * from t_sys_org where sjid = '"+curbmid+"'");
			if(_list.size()>0){
				json.addResult(false,"删除失败,此部门下有人员,无法删除");
			}else{
				Db.getJtN().update("delete from t_sys_org where orgid = '"+curbmid+"'");
				Db.getJtN().update("delete from t_sys_bm where gwid = '"+curbmid+"'");
				Db.getJtN().update("delete from t_sys_bm_kz where gwid = '"+curbmid+"'");
				json.addResult(true,"删除成功");
			}
		}catch(Exception e){
			Log.error(yhid,"delGw","","删除部门数据时异常"+e.toString());
			json.addResult(false,"删除部门数据时异常");
		}
		return json.getJsonDataMap();
	}
	public Map saveGw(){
		try{
			String gwid = _map.get("gwid")==null?"":String.valueOf(_map.get("gwid"));
			String dbtype = SysPara.getValue("db_type");
			String gwmc = _map.get("gwmc")==null?"":String.valueOf(_map.get("gwmc"));
			
			if(!"".equals(gwid)){
				Db.getJtN().update(new StringBuffer().append("update t_sys_gw set gwmc ='").append(gwmc).append("' where gwid = '").append(gwid).append("'").toString());
				Db.getJtN().update(new StringBuffer().append("update t_sys_org set orgname ='").append(gwmc).append("' where orgid = '").append(gwid).append("'").toString());
			}else{
				
				String sj_bm_id = _map.get("sj_bm_id")==null?"":String.valueOf(_map.get("sj_bm_id"));
				String sj_bm_orgid = _map.get("sj_bm_orgid")==null?"":String.valueOf(_map.get("sj_bm_orgid"));	
				
				String  nextId = String.valueOf(Db.getNextId());
				
				String gw_id  = sj_bm_id+"_"+nextId;
				Db.getJtN().update("insert into t_sys_gw(group_id,gwid,gwmc,gwbz,xh,cjsj,yxbz,lrr_yhid)values(?,?,?,?,?,?,?,?)", new Object[] {
						"0", nextId, gwmc,"","0",Db.getStr(), "1",yhid});
				Db.getJtN().update("insert into t_sys_org(group_id,id,orgid,orgname,sjid,lbdm,cjsj,yxbz)values(?,?,?,?,?,?,?,?)", new Object[] {
						"0",gw_id,nextId,gwmc,sj_bm_id,3,Db.getStr(),1});	
				json.addData("gwmc",gwmc);
				json.addData("orgid",nextId);
				json.addData("id",gw_id);
			}
		}catch(Exception e){
			e.printStackTrace();
			Log.error(yhid,"saveGw","","保存岗位数据时异常"+e.toString());
			json.addResult(false,"保存岗位数据时异常");
		}
		return json.getJsonData();
	}
	public Map delGw(){
		try{
			String curgwid = _map.get("curgwid")==null?"":String.valueOf(_map.get("curgwid"));
			List _list = Db.getJtN().queryForList("select * from t_sys_org where sjid = '"+curgwid+"'");
			if(_list.size()>0){
				json.addResult(false,"删除失败,此岗位下有人员,无法删除");
			}else{
				Db.getJtN().update("delete from t_sys_org where orgid = '"+curgwid+"'");
				Db.getJtN().update("delete from t_sys_gw where gwid = '"+curgwid+"'");
				json.addResult(true,"删除成功");
			}
		}catch(Exception e){
			Log.error(yhid,"delGw","","删除岗位数据时异常"+e.toString());
			json.addResult(false,"删除岗位数据时异常");
		}
		return json.getJsonData();
	}
	
	public Map saveYh(){
		try{
			String id = _map.get("cur_yhid")==null?"":String.valueOf(_map.get("cur_yhid"));
			if("".equals(id)){
				 String newYhId = String.valueOf(Db.getNextId());
				 String username = _map.get("username")==null?"":String.valueOf(_map.get("username"));
				 List yhList = Db.getJtN().queryForList("select * from t_sys_yh where username = '"+username+"'");
				 if(yhList.size()>0){
					 json.addResult(false,"保存用户数据时异常,登陆用户名重复");
					 return json.getJsonDataMap();
				 }
				 String name = _map.get("name")==null?"":String.valueOf(_map.get("name"));
				 String password = "123456";//默认密码 为123456 _map.get("password")==null?"":String.valueOf(_map.get("password"));
				 String js = _map.get("js")==null?"":String.valueOf(_map.get("js"));
				 String telphone = _map.get("telphone")==null?"":String.valueOf(_map.get("telphone"));
				 String mobilephone = _map.get("mobilephone")==null?"":String.valueOf(_map.get("mobilephone"));
				 String sex = _map.get("sex")==null?"":String.valueOf(_map.get("sex"));
				 String lxdz = _map.get("lxdz")==null?"":String.valueOf(_map.get("lxdz"));
				 String bz = _map.get("bz")==null?"":String.valueOf(_map.get("bz"));
				 String points = _map.get("points")==null?"":String.valueOf(_map.get("points"));
				 
				 String sjid = _map.get("sjid")==null?"":String.valueOf(_map.get("sjid"));
				 String sjbmid = _map.get("sjbmid")==null?"":String.valueOf(_map.get("sjbmid"));
				 StringBuffer sqlStr = new StringBuffer();
				 sqlStr.append("insert into t_sys_yh(group_id,bmid,jsid,yhid,username,password,name,sex,telephone,mobilephone,lxdz,bz,grid_higher,grid,points,create_time)values(")
				 	   .append("'0','").append(sjbmid).append("','").append(js).append("','").append(newYhId).append("','").append(username)
				 	   .append("','").append(Encrypt.MD5(password)).append("','").append(name).append("','").append(sex).append("','").append(telphone)
				 	   .append("','").append(mobilephone).append("','").append(lxdz).append("','").append(bz).append("','")
				 	   .append(_map.get("grid_higher")).append("','").append(_map.get("grid")).append("','").append(points).append("','")
				 	   .append(Db.getStr()).append("')");
				 Db.getJtN().update(sqlStr.toString());
				 Db.getJtN().update("insert into t_sys_org(group_id,id,orgid,orgname,sjid,sjbmid,lbdm,cjsj,yxbz)values(?,?,?,?,?,?,?,?,?)", new Object[] {
							"0",sjbmid+"_"+newYhId,newYhId,name,sjid,sjbmid,1,Db.getStr(),1});	
				 
				 Iterator it = _map.keySet().iterator();
				 while(it.hasNext()){
				 	String key = (String) it.next();
				 	if(key.startsWith("kz")){
				 		String value = _map.get(key).toString();
				 		if(!"".equals(value)){
				 			Db.getJtN().update("insert into t_sys_yh_kz values('"+newYhId+"','"+key.substring(2)+"','"+value+"')");
				 		}
				 	}
				 }
			}else{
				modiYh();
			}
			
		}catch(Exception e){
			e.printStackTrace();
			Log.error(yhid,"saveYh","","保存用户数据时异常"+e.toString());
			json.addResult(false,"保存用户数据时异常");
			
		}
		return json.getJsonDataMap();
	}
	
	public Map delOrg(){
		try{
			 String orgid = _map.get("orgid")==null?"":String.valueOf(_map.get("orgid"));
			 
			 String id = _map.get("id")==null?"":String.valueOf(_map.get("id"));
			 if(!"".equals(orgid) && !"".equals(id)){
				 List _list = Db.getJtN().queryForList("select *  from t_sys_org where sjid = '"+id+"'");
				 if(_list.size() >0 ){
					 json.addResult(false,"无法删除,您选择的删除项存在下级单位");
				 }else{
					 Db.getJtN().update("delete from t_sys_org where orgid = '"+orgid+"'");
					 Db.getJtN().update("delete from t_sys_yh where yhid = '"+orgid+"'");
					 Db.getJtN().update("delete from t_sys_yh_kz where yhid = '"+orgid+"'");
					 Db.getJtN().update("delete from t_sys_bm where bmid ='"+orgid+"'");
				 }
			 }
		}catch(Exception e){
			Log.error(yhid,"delOrg","","删除组织目录数据时异常"+e.toString());
			json.addResult(false,"删除组织目录时异常");
			e.printStackTrace();
		}
		return json.getJsonData();
	}
	
	public Map getGrid(){
		try{
			String gridId = _map.get("gridId")==null?"":String.valueOf(_map.get("gridId"));
			if(!"".equals(gridId)){
				List _list =  Db.getJtN().queryForList("select name mc,guid dm from t_cc_wywx_grid where type = '1010802' and higher_id='"+gridId+"'");
				json.addSelect("grid",_list,"所属区域");
			}
			
			
		}catch(Exception e){
			Log.error(yhid,"getGrid","","得到区域数据时异常"+e.toString());
			json.addResult(false,"得到区域数据时异常");
			
		}
		return json.getSelectData();
	}
	
	public Map getJsList(){
		try{
			List _list = Db.getJtN().queryForList("select jsid dm,jsmc mc from t_sys_js where yxbz = 1 ");
			json.addSelect("js",_list, "请选择");
			
			_list =  Db.getJtN().queryForList("select name mc,guid dm from t_cc_wywx_grid where type = '1010801'");
			json.addSelect("grid_higher",_list,"所属点部");
		}catch(Exception e){
			Log.error(yhid,"getJsList","","得到角色列表数据时异常"+e.toString());
			json.addResult(false,"得到角色列表数据时异常");
			
		}
		return json.getSelectData();
	}
	
	public Map showYh(){
		try{
			String id = _map.get("id")==null?"":String.valueOf(_map.get("id"));
			StringBuffer sqlStr = new StringBuffer();
			sqlStr.append("select a.*,(select name from t_cc_wywx_grid where guid = a.grid) grid_name,jsid js,a.yhid cur_yhid,(select kzz from t_sys_yh_kz where yhid = a.yhid and kz_dm = '101' ) kz101,(select kzz from t_sys_yh_kz where yhid = a.yhid and kz_dm = '102' ) kz102 from t_sys_yh a where yhid = '").append(id).append("'");
			Map _map = Db.getJtN().queryForMap(sqlStr.toString());
			json.addForm(_map);
		}catch(Exception e){
			Log.error(yhid,"showYh","","得到用户数据时异常"+e.toString());
			json.addResult(false,"得到用户数据时异常");		
		}
		return json.getJsonDataMap();
	}
	
	public Map showBm(){
		try{
			String id = _map.get("id")==null?"":String.valueOf(_map.get("id"));
			StringBuffer sqlStr = new StringBuffer();
			sqlStr.append("select a.*,a.bmid cur_bmid from t_sys_bm a where bmid = '").append(id).append("'");
			Map _map = Db.getJtN().queryForMap(sqlStr.toString());
			json.addForm(_map);
		}catch(Exception e){
			Log.error(yhid,"showBm","","得到部门数据时异常"+e.toString());
			json.addResult(false,"得到用户数据时异常");		
		}
		return json.getJsonDataMap();
	}
	
	public Map modiYh(){
		try{
			 String id = _map.get("cur_yhid")==null?"":String.valueOf(_map.get("cur_yhid"));
			
			 String name = _map.get("name")==null?"":String.valueOf(_map.get("name"));
			 String password = _map.get("new_password")==null?"":String.valueOf(_map.get("new_password"));
			 String js = _map.get("js")==null?"":String.valueOf(_map.get("js"));
			 String telphone = _map.get("telphone")==null?"":String.valueOf(_map.get("telphone"));
			 String mobilephone = _map.get("mobilephone")==null?"":String.valueOf(_map.get("mobilephone"));
			 String sex = _map.get("sex")==null?"":String.valueOf(_map.get("sex"));
			 String lxdz = _map.get("lxdz")==null?"":String.valueOf(_map.get("lxdz"));
			 String bz = _map.get("bz")==null?"":String.valueOf(_map.get("bz"));
			 
			 String points = _map.get("points")==null?"":String.valueOf(_map.get("points"));
			 
			 String kz101 = _map.get("kz101")==null?"":String.valueOf(_map.get("kz101"));
			 String kz200 = _map.get("kz200")==null?"":String.valueOf(_map.get("kz200"));
			 
			 
			 StringBuffer sqlStr = new StringBuffer();
			 sqlStr.append("update t_sys_yh set name='").append(name).append("',sex='").append(sex).append("',")
			       .append("telephone='").append(telphone).append("',").append("mobilephone='").append(mobilephone).append("',")
			       .append("lxdz='").append(lxdz).append("',bz='").append(bz).append("',jsid='").append(js).append("',grid_higher='")
			       .append(_map.get("grid_higher")).append("',grid='").append(_map.get("grid")).append("',points='").append(points).append("' ")
			       .append("where yhid = '").append(id).append("' and yxbz = 1 ");
			 Db.getJtN().update(sqlStr.toString());
			 
			 Iterator it = _map.keySet().iterator();
			 while(it.hasNext()){
			 	String key = (String) it.next();
			 	if(key.startsWith("kz")){
			 		String value = _map.get(key).toString();
			 		if(!"".equals(value)){
			 			Db.getJtN().update("delete from  t_sys_yh_kz where yhid = '"+id+"' and kz_dm = '"+key.substring(2)+"'");
			 			Db.getJtN().update("insert into t_sys_yh_kz values('"+id+"','"+key.substring(2)+"','"+value+"')");
			 		}
			 	}
			 }
			
			 Db.getJtN().update("update t_sys_org set  orgname = '"+name+"' where orgid ='"+id+"'");
			 

			 
		}catch(Exception e){
			Log.error(yhid,"modiYh","","修改用户数据时异常"+e.toString());
			json.addResult(false,"修改用户数据时异常");
		}
		return json.getJsonDataMap();
	}
	
	public Map modiPassword(){
		try{
			String new_password = _map.get("new_password")==null?"":String.valueOf(_map.get("new_password"));
			if(!"".equals(new_password)){
				Db.getJtN().update("update t_sys_yh set password='"+Encrypt.MD5(new_password)+"' where yhid = '"+yhid+"'");
				json.addResult(true, "修改成功");
			}
		}catch(Exception e){
			Log.error(yhid,"modiPassword","","修改密码时异常"+e.toString());
			json.addResult(false,"修改密码时异常:"+e.toString());		
		}
		return json.getJsonDataResult();
	}
	
	public Map modiSelf(){
		try{
			String name = _map.get("name")==null?"":String.valueOf(_map.get("name"));
			String telphone = _map.get("telphone")==null?"":String.valueOf(_map.get("telphone"));
			String lxdz = _map.get("lxdz")==null?"":String.valueOf(_map.get("lxdz"));
			Db.getJtN().update("update t_sys_yh set name='"+name+"' ,telephone='"+telphone+"',lxdz='"+lxdz+"' where yhid = '"+yhid+"'");
			json.addResult(true, "修改成功");
		}catch(Exception e){
			Log.error(yhid,"modiSelf","","修改个人信息时"+e.toString());
			json.addResult(false,"修改密码时异常:"+e.toString());		
		}
		return json.getJsonDataResult();
	}
}