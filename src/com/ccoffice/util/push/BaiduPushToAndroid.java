package com.ccoffice.util.push;

import net.sf.json.JSONObject;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.baidu.yun.push.model.PushMsgToSingleDeviceResponse;
import com.ccoffice.util.log.Log;

public class BaiduPushToAndroid {
	public static PushMsgToSingleDeviceResponse pushMsgToSingleDevice(String deviceId,String title,String content,int type,String guid){
		//向单个设备推送信息，deviceId 要推送的设备id（可以是登陆的yhid）,content 推送内容,type 推送消息的类型，待办、通知
		PushKeyPair pushKeyPair = new PushKeyPair("0p2QbZ7MAsff7iwThtgfYzvF","4ab7221243cc6bbb42e50f21892ce712");
		BaiduPushClient baiduPushClient = new BaiduPushClient(pushKeyPair,BaiduPushConstants.CHANNEL_REST_URL);
		PushMsgToSingleDeviceResponse pushResponse = new PushMsgToSingleDeviceResponse();
		baiduPushClient.setChannelLogHandler(new YunLogHandler() {
			@Override
			public void onHandle(YunLogEvent event) {
				System.out.println(event.getMessage());
			}
		});
		try{			
			JSONObject notification = new JSONObject();
			notification.put("title", title);
			notification.put("description",content);
			JSONObject jsonCustormCont = new JSONObject();
			jsonCustormCont.put("guid",guid); //自定义内容，key-value
			jsonCustormCont.put("type",type);
			notification.put("custom_content", jsonCustormCont);

			PushMsgToSingleDeviceRequest pushRequest = new PushMsgToSingleDeviceRequest()
					.addChannelId(deviceId)
					.addMsgExpires(new Integer(3600)) // message有效时间
					.addMessageType(1)// 1：通知,0:透传消息. 默认为0 注：IOS只有通知.
					.addMessage(notification.toString())
					.addDeviceType(3);// deviceType => 3:android, 4:ios
			pushResponse =  baiduPushClient.pushMsgToSingleDevice(pushRequest);
			
			System.out.println("msgId:"+pushResponse.getMsgId());
		}catch(PushClientException  e){
			e.printStackTrace();
			Log.error(deviceId,"pushMsgToSingleDevice","","推送数据是时异常PushClientException:"+e.toString());
			 //ERROROPTTYPE 用于设置异常的处理方式 -- 抛出异常和捕获异常,
            //'true' 表示抛出, 'false' 表示捕获。
		}catch(PushServerException e){
			System.out.println("推送数据是时异常PushServerException:"+e.getRequestId()+";"+e.getErrorCode()+";"+e.getErrorMsg());
			e.printStackTrace();
			Log.error(deviceId,"pushMsgToSingleDevice","","推送数据是时异常PushServerException:"+e.getRequestId()+";"+e.getErrorCode()+";"+e.getErrorMsg());
		}
		return pushResponse;
	}
}
