package com.ccoffice.util.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.ccoffice.base.login.Login;
import com.ccoffice.util.cache.Cache;
import com.ccoffice.util.log.Log;
import com.ccoffice.util.tools.BaseTools;
import com.ccoffice.util.tools.ConvertMap;

public class RequestFilter  implements Filter{
	private String irregularityUrl;
	public void destroy() {
		
	}
	public void doFilter(ServletRequest request, ServletResponse response,
		FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		String uri = req.getServletPath();
		//Log.info("","验证请求","",uri);
		if(this.irregularityUrl.indexOf(uri)>-1){
			chain.doFilter(request, response);
		}else{
			String loginToken= BaseTools.getRequestValue(req,"com_ccoffice_token");
			Log.info("","验证请求,token=","",loginToken);
			if(!"".equals(loginToken)){
				 if(Cache.isExists(loginToken)){
					 String yhid = Login.getYhidByToken(loginToken);
					 if(Cache.isExists(yhid+"_token")){
						 String token = String.valueOf(Cache.getCacheInfo(yhid+"_token"));
						 if(token.equals(loginToken)){
							 request.setAttribute("yhid",yhid);
							 chain.doFilter(request, response);
						 }else{
							 //验证失败，服务端保存的token与传递过来的token 不一致，说明用户又进行了登录操作。
							 failValidate(request,response);
						 }
					 }else{
						 failValidate(request,response);
					 }
				 }else{
					
					failValidate(request,response);
				    
					return;
				 }
			}else{
				failValidate(request,response);
				return;
			}
		}
	}
	public void init(FilterConfig filterConfig) throws ServletException {
		this.irregularityUrl = filterConfig.getInitParameter("irregularityUrl");	
	}
	public void failValidate(ServletRequest request, ServletResponse response){
		try{
			response.setCharacterEncoding("UTF-8");
			HttpServletRequest req = (HttpServletRequest)request;
			String uri = req.getServletPath();
			if(uri.indexOf("app")>0){
		    	response.setContentType("text/html; charset=utf-8"); 
			   	PrintWriter writer = response.getWriter();
			   	writer.write("{\"login\":false,\"msg\":\"登录状态验证失败\"}");
			   	writer.close();
			}else{
				request.setAttribute("login", false);
				request.setAttribute("msg", "调用验证失败，请重新登录");
			    PrintWriter writer = response.getWriter();
			    writer.write("<script>");
				writer.write("top.document.location.href='/login.jsp'");
				writer.write("</script>");
				writer.close();
			}
		}catch(Exception e){
			
		}
	}
}