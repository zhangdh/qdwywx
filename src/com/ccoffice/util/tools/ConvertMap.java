package com.ccoffice.util.tools;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ccoffice.bean.LoginUser;
import com.ccoffice.util.cache.Cache;

public class ConvertMap {
	public static Map getMap (HttpServletRequest req){
		Map paraMap  = new HashMap();
		String paramName;
		String value;
		for (Enumeration enu = req.getParameterNames(); enu.hasMoreElements(); paraMap.put(paramName, value))
		{
			paramName = (String)enu.nextElement();
			String values[] = req.getParameterValues(paramName);
			value = "";
			for (int i = 0; i < values.length; i++)
				value = (new StringBuilder(String.valueOf(value))).append(value.equals("") ? "" : "~").append(values[i]).toString();

		}
		String loginToken = BaseTools.getRequestValue(req,"com_ccoffice_token");
		String yhid = String.valueOf(Cache.getCacheInfo(loginToken));
		LoginUser user = (LoginUser)Cache.getCacheInfo(yhid);
		if(user!=null){
			paraMap.put("group_id", user.getGroupID());
			paraMap.put("yhid", user.getYhid());
			paraMap.put("bmid", user.getBmId());
			paraMap.put("jsid", user.getJsId());
		}		
		String serverIp = req.getLocalAddr();
		paraMap.put("serverIp",serverIp);
		return paraMap;
	}
}
