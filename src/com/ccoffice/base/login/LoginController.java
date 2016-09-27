package com.ccoffice.base.login;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ccoffice.bean.LoginUser;
import com.ccoffice.util.cache.Cache;
import com.ccoffice.util.db.Db;
import com.ccoffice.util.log.Log;
import com.ccoffice.util.tools.BaseTools;
import com.ccoffice.util.tools.ConvertMap;
import com.ccoffice.util.tools.Encrypt;
import com.ccoffice.util.tools.SysPara;

@Controller
public class LoginController {
	@RequestMapping("/login.do")
	public String login(HttpServletRequest request,HttpServletResponse response){
		Map paraMap = ConvertMap.getMap(request); 
		Map reMap = new HashMap();
		String login_redirect = "/login.jsp";
		String msg = "";
		boolean login = false;
		String username = "";
		String password = "";
		try{
			username = paraMap.get("username")==null?"":String.valueOf(paraMap.get("username"));
			password = paraMap.get("password")==null?"":String.valueOf(paraMap.get("password"));
			if("".equals(username) || "".equals(password)){
				login = false;
				msg = "登录失败,用户名或密码为空";
			}else{
				List ryList = Login.validate(username,Encrypt.MD5(password));
				if(ryList !=null && ryList.size() == 1 ){
					Map ryMap = (Map)ryList.get(0);
					String yhid = ryMap.get("yhid").toString();
					String ccoffice_token = Encrypt.MD5(new StringBuffer().append("*!~*").append(new Date()).append(username).toString()); 
					Login.cacheInfo(ryMap,ccoffice_token,request,yhid);
					Login.cacheKzInfo(yhid);
					Cookie cookie_pass = new Cookie("com_ccoffice_token", ccoffice_token);
					response.addCookie(cookie_pass);
					login_redirect = "/main.jsp";
					login = true;
					msg = "登录成功";
					request.setAttribute("yhid", yhid);
					request.setAttribute("xm", ((LoginUser)Cache.getCacheInfo(yhid)).getName());					
				}else if(ryList ==null || ryList.size()>1){	
					login = false;
					msg = "登录失败,登录验证异常,验证结果:";	
				}else{
					login = false;
					msg = "登录失败,用户名或密码错误";		
				}
			} 
			Login.saveLoginLog(request, username, password, login,"PC");
		}catch(Exception e){
			Log.error(username,"login","LoginController","验证信息时异常"+e.toString());
		}
		request.setAttribute("login", login);
		request.setAttribute("msg", msg);
		
		Log.info(username,"login","LoginController","验证信息成功");
		
		return  login_redirect;
	}
	
	@RequestMapping("/app/loginApp.do")
	public void loginApp(HttpServletRequest request,HttpServletResponse response){
		Map paraMap = ConvertMap.getMap(request); 
		Map reMap = new HashMap();
		String msg = "";
		boolean login = false;
		String username = "";
		String password = "";
		String deviceType = "";
		response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=utf-8"); 
	    PrintWriter writer = null;
		try{
			writer = response.getWriter();
			username = paraMap.get("username")==null?"":String.valueOf(paraMap.get("username"));
			password = paraMap.get("password")==null?"":String.valueOf(paraMap.get("password"));
			deviceType = paraMap.get("deviceType")==null?"":String.valueOf(paraMap.get("deviceType"));
			if("".equals(username) || "".equals(password)){
				login = false;
				msg = "登录失败,用户名或密码为空";
			}else{
				List ryList = Login.validate(username,Encrypt.MD5(password));
				if(ryList !=null && ryList.size() == 1 ){
					Map ryMap = (Map)ryList.get(0);
					String yhid = ryMap.get("yhid").toString();
					String ccoffice_token = Encrypt.MD5(new StringBuffer().append("*!~*").append(new Date()).append(username).toString()); 
					
					Cookie cookie_pass = new Cookie("com_ccoffice_token", ccoffice_token);
					Cookie cookie_pass_name = new Cookie("name",ryMap.get("name")==null?"":URLEncoder.encode(String.valueOf(ryMap.get("name")),"UTF-8"));
					cookie_pass.setPath("/");
					cookie_pass.setMaxAge(365 * 24 * 60 * 60);
					
					cookie_pass_name.setPath("/");
					cookie_pass_name.setMaxAge(365 * 24 * 60 * 60);
					
					response.addCookie(cookie_pass);
					response.addCookie(cookie_pass_name);
					
					
					
					
					Login.cacheInfo(ryMap,ccoffice_token,request,yhid);
					Login.cacheKzInfo(yhid);
					Cache.setCacheInfo(yhid+"_deviceType",deviceType);
					if("Android".equals(deviceType) || "iOS".equals(deviceType)){
						String pushDeviceId = paraMap.get("pushDeviceId")==null?"":String.valueOf(paraMap.get("pushDeviceId"));
						Cache.setCacheInfo(yhid+"_pushDeviceId",pushDeviceId);
						Login.saveLoginDeviceId(yhid,deviceType,pushDeviceId);
					}
					login = true;
					StringBuffer reStr = new StringBuffer();
					reStr.append("{\"login\":true,\"msg\":\"登录成功\",\"yhid\":\"").append(yhid).append("\",\"com_ccoffice_token\":\"")
						 .append(ccoffice_token).append("\",\"xm\":\"").append(((LoginUser)Cache.getCacheInfo(yhid)).getName())
						 .append("\",\"grid_name\":\"").append(ryMap.get("grid_name")).append("\",\"grid_higher_name\":\"")
						 .append(ryMap.get("grid_higher_name")).append("\"}");
			    	//writer.write("{\"login\":true,\"msg\":\"登录成功\",\"yhid\":\""+yhid+"\",\"com_ccoffice_token\":\""+ccoffice_token+"\",\"xm\":\""+((LoginUser)Cache.getCacheInfo(yhid)).getName()+"\"}");
			    	writer.write(reStr.toString());
			    	
				}else if(ryList ==null || ryList.size()>1){	
					login = false;
					writer.write("{\"login\":false,\"msg\":\"登录失败,登录验证异常,验证结果\"}");
				}else{
					login = false;
					writer.write("{\"login\":false,\"msg\":\"登录失败,用户名或密码错误\"}");
				}
			}
			Login.saveLoginLog(request, username, password, login,deviceType);
		}catch(Exception e){
			e.printStackTrace();
			Log.error(username,"login","LoginController","验证信息时异常"+e.toString());
		}finally{
			if(writer != null){
				writer.close();
			}
		}
		Log.info(username,"login","LoginController","验证信息成功");
		 
	}
	@RequestMapping("/exit.do")
	public String  exit(HttpServletRequest request,HttpServletResponse response){
		try{
			Map paraMap = ConvertMap.getMap(request); 
			Map reMap = new HashMap();
			String loginToken = BaseTools.getCookieValue(request,"com_ccoffice_token");
			Cache.removeLoginInfo(loginToken);
		}catch(Exception e){
			Log.error("","exit","LoginController","退出操作时异常"+e.toString());
		}
		return  "/login.jsp";
	}
}
