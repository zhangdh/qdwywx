package com.ccoffice.util.push;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;

import com.ccoffice.util.SysConstants;
import com.ccoffice.util.db.Db;
import com.ccoffice.util.log.Log;

public class JPushToAndroid {
	public static  PushResult pushMsgToSingleDevice(String push_guid,String deviceId,String title,String content,String type,String guid){
		//向单个设备推送信息，deviceId 要推送的设备id（可以是登陆的yhid）,content 推送内容,type 推送消息的类型，待办、通知 
		PushResult pushResult = null;
		try{
			JPushClient jpushClient = new JPushClient(SysConstants.JPush_MasterSecret,SysConstants.JPush_AppKey);
			Map pushMap = new HashMap();
			pushMap.put("guid",guid);
			pushMap.put("type",String.valueOf(type));
			pushResult = jpushClient.sendAndroidNotificationWithRegistrationID(title, content, pushMap, deviceId);
		}catch(APIConnectionException  e){
			e.printStackTrace();
			Log.error(deviceId,"JPushToAndroid.pushMsgToSingleDevice","","推送数据是时异常APIConnectionException:"+e.toString());
			 //ERROROPTTYPE 用于设置异常的处理方式 -- 抛出异常和捕获异常,
            //'true' 表示抛出, 'false' 表示捕获。
		}catch(APIRequestException e){
			System.out.println("推送数据是时异常APIRequestException:"+e.getErrorCode()+","+e.getErrorMessage());
			Db.getJtN().update("update t_push set error_code  = "+e.getErrorCode()+",error_message ='"+e.getErrorMessage()+"' where guid ='"+push_guid+"'");
			Log.error(deviceId,"JPushToAndroid.pushMsgToSingleDevice","","推送数据是时异常APIRequestException:"+e.getErrorCode()+","+e.getErrorMessage());
		}
		return pushResult;
	}
}
