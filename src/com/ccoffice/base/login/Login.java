package com.ccoffice.base.login;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ccoffice.bean.LoginUser;
import com.ccoffice.util.cache.Cache;
import com.ccoffice.util.db.Db;
import com.ccoffice.util.log.Log;
import com.ccoffice.util.tools.SysPara;

public class Login {
	public  static List<Map> validate(String username,String password){
		List ryList = null;
		try{
			ryList = Db.getJtN().queryForList("select a.*,(select name from t_cc_wywx_grid where guid = a.grid_higher) grid_higher_name,(select name from t_cc_wywx_grid where guid = a.grid) grid_name from t_sys_yh a where username='"+username+"' and password='"+password+"'");
			//System.out.println(ryList);
		}catch(Exception e){
			e.printStackTrace();
			Log.error("username","validate","","验证用户是否合法时异常"+e.toString());
		}
		return ryList;
	}
	
	public static void cacheInfo(Map yhMap,String ccoffice_token,HttpServletRequest request,String yhid){
		try{
			LoginUser loginUser = new LoginUser();
			loginUser.setDlIp(request.getRemoteAddr());
			loginUser.setDlSj(String.valueOf(new Date()));
			loginUser.setDlToken(ccoffice_token);
			loginUser.setGroupID(String.valueOf(yhMap.get("group_id")));
			loginUser.setUserName(String.valueOf(yhMap.get("username")));
			loginUser.setYhid(yhid);
			loginUser.setBmId(String.valueOf(yhMap.get("bmid")));
			loginUser.setJsId(String.valueOf(yhMap.get("jsid")));
			loginUser.setName(String.valueOf(yhMap.get("name")));
			Cache.setCacheInfo(ccoffice_token, yhid);
			Cache.setCacheInfo(yhid+"_token", ccoffice_token);
			Cache.setCacheInfo(yhid, loginUser);
		}catch(Exception e){
			e.printStackTrace();
			Log.error(yhid,"cacheInfo","","缓存登录用户信息异常:"+e.toString());
		}
	}
	public static void cacheKzInfo(String yhid){
		try{
			String kzDm = SysPara.getValue("cacheKz");
			if(!"".equals(kzDm)){
				List kzList = Db.getJtN().queryForList("select kz_dm,kzz from t_sys_yh_kz where kz_dm in ("+kzDm+") and yhid = ?",new Object[]{yhid});
				for(int j=0;j<kzList.size();j++){
					Map kzMap = (Map)kzList.get(j);
					Cache.setUserInfo(yhid, String.valueOf(kzMap.get("kz_dm")), kzMap.get("kzz"));
				}
			}
		}catch(Exception e){
			Log.error(yhid,"cacheInfo","","缓存登录用户扩展信息异常");
		}
	}
	
	public static void saveLoginLog(HttpServletRequest request,String username,String password,boolean login,String deviceType){
		try{
			String remoteIp = request.getRemoteAddr();
			Db.getJtN().update("insert into t_sys_login_log(login_time,username,password,remoteIp,login,device)values(now(),'"+username+"','"+password+"','"+remoteIp+"','"+login+"','"+deviceType+"')");			
		}catch(Exception e){
			Log.error(username,"saveLoginLog","","保存登录信息异常："+e.toString());
		}
	}
	public static void saveLoginDeviceId(String yhid,String deviceType,String deviceId){
		try{
			Db.getJtN().update("delete from t_push_deviceId where yhid ='"+yhid+"'");
			Db.getJtN().update("insert into t_push_deviceId (guid,yhid,device_type,device_id,create_time)values('"+Db.getGUID()+"','"+yhid+"','"+deviceType+"','"+deviceId+"',"+Db.getDateStr()+")");
		}catch(Exception e){
			Log.error(yhid,"saveLoginDeviceId","","保存登录信息推送设备号时异常："+e.toString());
		}
	}
	public static String getYhidByToken(String token){
		if(Cache.isExists(token)){
			String yhid = String.valueOf(Cache.getCacheInfo(token));
			return yhid;
		}else{
			return "";
		}
		
	}
}
