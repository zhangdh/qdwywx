package com.ccoffice.cc.cti.hwbb;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ccoffice.util.tools.ConvertMap;

@Controller
@RequestMapping("/cti/hwbb.do")
public class HwbbController {
	
	//来电详单
	@RequestMapping(params = "method=queryLdxd")
	public ModelAndView queryCode(HttpServletRequest request,HttpServletResponse response){
		Hwbb hwbb = new Hwbb(ConvertMap.getMap(request));
		return new ModelAndView("jsonView",hwbb.queryLdxd());
	}
	//未接来电
	@RequestMapping(params = "method=queryWjld")
	public ModelAndView queryWjld(HttpServletRequest request,HttpServletResponse response){
		Hwbb hwbb = new Hwbb(ConvertMap.getMap(request));
		return new ModelAndView("jsonView",hwbb.queryWjld());
	}
	//话务员话务量统计
	@RequestMapping(params = "method=queryHwyhwl")
	public ModelAndView queryHwyhwl(HttpServletRequest request,HttpServletResponse response){
		Hwbb hwbb = new Hwbb(ConvertMap.getMap(request));
		return new ModelAndView("jsonView",hwbb.queryHwyhwl());
	}
	//话务员时长统计
	@RequestMapping(params = "method=queryHwysc")
	public ModelAndView queryHwysc(HttpServletRequest request,HttpServletResponse response){
		Hwbb hwbb = new Hwbb(ConvertMap.getMap(request));
		return new ModelAndView("jsonView",hwbb.queryHwysc());
	}
	//平台受理情况统计
	@RequestMapping(params = "method=queryPtsl")
	public ModelAndView queryPtsl(HttpServletRequest request,HttpServletResponse response){
		Hwbb hwbb = new Hwbb(ConvertMap.getMap(request));
		return new ModelAndView("jsonView",hwbb.queryPtsl());
	}
}
