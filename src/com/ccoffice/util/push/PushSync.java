package com.ccoffice.util.push;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.api.push.PushResult;

import com.ccoffice.util.cache.Cache;
import com.ccoffice.util.db.Db;
import com.ccoffice.util.log.Log;

public class PushSync extends Thread{
	Map _map = new HashMap();
	public  PushSync(Map _map){
		this._map = _map;
	}
	
	public void run(){
		String device = "Android";
		String pushDeviceId = "";//140fe1da9ea33cce527
		PushResult pushResult = null;
		List _list = null;
		try{
			String guid = Db.getGUID();
			//pushResult = new PushResult();
			String yhid = _map.get("yhid")==null?"":String.valueOf(_map.get("yhid"));
			String push_content = _map.get("push_content")==null?"":String.valueOf(_map.get("push_content"));
			String push_title = _map.get("push_title")==null?"":String.valueOf(_map.get("push_title"));
			String type = _map.get("type")==null?"":String.valueOf(_map.get("type"));
			if(Cache.isExists(yhid+"_"+"device_type") && Cache.isExists(yhid+"_"+"pushDeviceId")){
				device = String.valueOf(Cache.getUserInfo(yhid,"device_type"));
				pushDeviceId = String.valueOf(Cache.getUserInfo(yhid,"pushDeviceId"));
			}else{
				_list = Db.getJtN().queryForList("select * from t_push_deviceId where yhid ='"+yhid+"'");
				if(_list.size() >0){
					pushDeviceId = String.valueOf(((Map)_list.get(0)).get("device_id"));
					device = String.valueOf(((Map)_list.get(0)).get("device_type"));
				}
			}
			//System.out.println("pushDeviceId:"+pushDeviceId);
			if(!"".equals(pushDeviceId)){
				StringBuffer sqlStr = new StringBuffer();
				
				sqlStr.append("insert into t_push(guid,service_id,push_yhid,push_device_id,push_title,push_content,push_device,create_time)values('").append(guid).append("','")
					  .append(_map.get("guid")).append("','").append(yhid).append("','").append(pushDeviceId).append("','").append(push_title).append("','").append(push_content).append("','")
					  .append(device).append("',").append(Db.getDateStr()).append(")");
				Db.getJtN().update(sqlStr.toString());
				
				Log.info(yhid, "pushMsgToSingleDevice", "","推送消息,yhid="+yhid+",pushDeviceId="+pushDeviceId+",device:"+device);
				if("iOS".equals(device)){
					
				}else{
					pushResult = JPushToAndroid.pushMsgToSingleDevice(guid,pushDeviceId,String.valueOf(_map.get("push_title")), String.valueOf(_map.get("push_content")),type,String.valueOf(_map.get("guid")));
				}
				sqlStr.delete(0,sqlStr.length());
				sqlStr.append(" update t_push set push_msg_id='").append(pushResult.msg_id).append("',push_no =")
					  .append(pushResult.sendno).append(" where guid ='").append(guid).append("'");
				Db.getJtN().update(sqlStr.toString());
			}
		}catch(Exception e){
			Log.error(pushDeviceId,"pushMsgToSingleDevice","","推送数据是时异常"+e.toString());
			e.printStackTrace();
		}
		
	}
}
