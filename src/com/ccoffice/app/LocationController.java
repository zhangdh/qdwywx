package com.ccoffice.app;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ccoffice.util.tools.ConvertMap;

@Controller
@RequestMapping("/app/location.do")
public class LocationController {
	
	//保存位置信息
	@RequestMapping(params = "method=saveLocation")
	public ModelAndView saveLocation(HttpServletRequest request,HttpServletResponse response){
		Location location = new Location(ConvertMap.getMap(request));
		return new ModelAndView("jsonView",location.saveLocation());
	}	
	
	//保存推送设备列表
	@RequestMapping(params = "method=saveDeviceId")
	public ModelAndView saveDeviceId(HttpServletRequest request,HttpServletResponse response){
		Location location = new Location(ConvertMap.getMap(request));
		return new ModelAndView("jsonView",location.saveDeviceId());
	}	
}
