package com.ccoffice.task;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import cn.jpush.api.push.PushResult;

import com.ccoffice.util.cache.Cache;
import com.ccoffice.util.db.Db;
import com.ccoffice.util.log.Log;
import com.ccoffice.util.push.JPushToAndroid;

@Configuration
@EnableAsync
@EnableScheduling
public class ReservationPushTask {
	
	@Scheduled(fixedRate=600000)
	public void pushTask(){
		try{
			System.out.println("-----------pushTaskstart--------------");
			Log.info("","ReservationPushTask","pushTask","-----------pushTaskstart--------------");
			StringBuffer sqlStr = new StringBuffer();
			PushResult pushResult = null;
			sqlStr.append("select guid,service_number,repair_address,service_content,reservation_time,repairer_yhid,reservation_time ")
				  .append(" from t_cc_wywx_service where state = '1010304' and  (now()-reservation_time) < interval '2 hour' and remind < 2");
			List _list = Db.getJtN().queryForList(sqlStr.toString());
			for(int i=0;i<_list.size();i++){
				Map _map = (Map)_list.get(i);
				String service_id  = String.valueOf(_map.get("guid"));
				String push_title  = "预约时间提醒";
				String push_content = "工单编号:"+_map.get("service_number")+",预约时间:"+_map.get("reservation_time")+",报修内容:"+_map.get("service_content");
				String repairer_yhid = String.valueOf(_map.get("repairer_yhid"));
				String device = "";
				String pushDeviceId = "";
				if(Cache.isExists(repairer_yhid+"_"+"device_type") && Cache.isExists(repairer_yhid+"_"+"pushDeviceId")){
					device = String.valueOf(Cache.getUserInfo(repairer_yhid,"device_type"));
					pushDeviceId = String.valueOf(Cache.getUserInfo(repairer_yhid,"pushDeviceId"));
				}else{
					_list = Db.getJtN().queryForList("select * from t_push_deviceId where yhid ='"+repairer_yhid+"'");
					if(_list.size() >0){
						pushDeviceId = String.valueOf(((Map)_list.get(0)).get("device_id"));
						device = String.valueOf(((Map)_list.get(0)).get("device_type"));
					}
				}
				if(!"".equals(pushDeviceId) && !"".equals(device)){
					sqlStr.delete(0,sqlStr.length());
					String guid = Db.getGUID();
					sqlStr.append("insert into t_push(guid,service_id,push_yhid,push_device_id,push_title,push_content,push_device,create_time)values('").append(guid).append("','")
					      .append(_map.get("guid")).append("','").append(_map.get("repairer_yhid")).append("','").append(pushDeviceId).append("','").append(push_title).append("','").append(push_content).append("','")
					      .append(device).append("',").append(Db.getDateStr()).append(")");
					
					Db.getJtN().update(sqlStr.toString());
					
					if("iOS".equals(device)){
						
					}else if("Android".equals(device)){
						pushResult = JPushToAndroid.pushMsgToSingleDevice(guid,pushDeviceId,String.valueOf(push_title), String.valueOf(push_content),"2080002",String.valueOf(_map.get("guid")));
						Db.getJtN().update("update t_cc_wywx_service set remind = remind + 1 where guid = '"+service_id+"'");
					}
					sqlStr.delete(0,sqlStr.length());
					sqlStr.append(" update t_push set push_msg_id='").append(pushResult.msg_id).append("',push_no =")
						  .append(pushResult.sendno).append(" where guid ='").append(guid).append("'");
					Db.getJtN().update(sqlStr.toString());
					
				}
			}
			Log.info("","ReservationPushTask","pushTask","-----------pushTaskend--------------");
		}catch(Exception e){
			Log.error("","ReservationPushTask","pushTask","执行定时推送任务时异常"+e.toString());
		}
		
	}
}
