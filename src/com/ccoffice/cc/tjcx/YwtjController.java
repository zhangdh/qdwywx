package com.ccoffice.cc.tjcx;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ccoffice.util.tools.ConvertMap;

@Controller
@RequestMapping("/cc/ywtj.do")
public class YwtjController {
	
	@RequestMapping(params = "method=getGrid")
	public ModelAndView getGrid(HttpServletRequest request) {
		Ywtj ywtj = new Ywtj(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", ywtj.getGrid());
	}
	
	@RequestMapping(params = "method=queryTj")
	public ModelAndView queryTj(HttpServletRequest request) {
		Ywtj ywtj = new Ywtj(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", ywtj.queryTj());
	}
	
	@RequestMapping(params = "method=querySlgd")
	public ModelAndView querySlgd(HttpServletRequest request) {
		Ywtj ywtj = new Ywtj(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", ywtj.querySlgd());
	}
	@RequestMapping(params = "method=queryRepairerTj")
	public ModelAndView queryRepairerTj(HttpServletRequest request) {
		Ywtj ywtj = new Ywtj(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", ywtj.queryRepairerTj());
	}
	@RequestMapping(params = "method=queryRepairerMyd")
	public ModelAndView queryRepairerMyd(HttpServletRequest request) {
		Ywtj gdgl = new Ywtj(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", gdgl.queryRepairerMyd());
	}
}
