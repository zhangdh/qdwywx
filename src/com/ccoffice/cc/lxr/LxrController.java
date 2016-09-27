package com.ccoffice.cc.lxr;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ccoffice.util.tools.ConvertMap;

@Controller
@RequestMapping("/cc/lxr.do")
public class LxrController {
	
	@RequestMapping(params = "method=init")
	public ModelAndView init(HttpServletRequest request) {
		Lxr lxr = new Lxr(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", lxr.init());
	}
	@RequestMapping(params = "method=queryLxr")
	public ModelAndView queryLxr(HttpServletRequest request) {
		Lxr lxr = new Lxr(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", lxr.queryLxr());
	}
	@RequestMapping(params = "method=saveLxr")
	public ModelAndView saveLxr(HttpServletRequest request) {
		Lxr lxr = new Lxr(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", lxr.saveLxr());
	}
	
	@RequestMapping(params = "method=modiLxr")
	public ModelAndView modiLxr(HttpServletRequest request) {
		Lxr lxr = new Lxr(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", lxr.modiLxr());
	}
	
	@RequestMapping(params = "method=delLxr")
	public ModelAndView delLxr(HttpServletRequest request) {
		Lxr lxr = new Lxr(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", lxr.delLxr());
	}
}
