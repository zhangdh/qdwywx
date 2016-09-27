package com.ccoffice.base.cache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ccoffice.util.tools.ConvertMap;

@Controller
@RequestMapping("/base/cache.do")
public class CacheController {
	//获取功能模块
	@RequestMapping(params = "method=getCache")
	public ModelAndView getModule(HttpServletRequest request,HttpServletResponse response){
		Cache cache = new Cache(ConvertMap.getMap(request));
		return new ModelAndView("jsonView",cache.getCache());
	}
}
