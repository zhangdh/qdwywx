package com.ccoffice.util.tools;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class BaseTools {
	
	public static String getCookieValue(HttpServletRequest request, String name) {
		   String cookie_value = "";
		   Cookie[] cookies = request.getCookies();
		   if (cookies != null) {
		     for (int i = 0; i < cookies.length; ++i) {
		       if (cookies[i].getName().equals(name)) {
		         cookie_value = cookies[i].getValue();
		         break;
		       }
		     }
		   }
		   return cookie_value;
	}
	
	public static String getRequestValue(HttpServletRequest request, String name) {
		   //return getCookieValue(request,name);
		   String request_source  = request.getParameter("request_source")==null?"":String.valueOf(request.getParameter("request_source"));
		   //System.out.println("request_source:"+request_source);
		   String url = request.getServletPath();
		   if("1".equals(request_source)){
			   return request.getParameter(name);
		   }else{
			   return getCookieValue(request,name);
		   }
	}
}
