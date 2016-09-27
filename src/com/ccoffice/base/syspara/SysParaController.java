package com.ccoffice.base.syspara;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ccoffice.util.tools.ConvertMap;

@Controller
@RequestMapping("/base/syspara.do")
public class SysParaController {
	@RequestMapping(params = "method=query")
	public ModelAndView query(HttpServletRequest request) {
		SysPara syspara = new SysPara(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", syspara.query());
	}
	
	@RequestMapping(params = "method=save")
	public ModelAndView save(HttpServletRequest request) {
		SysPara syspara = new SysPara(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", syspara.save());
	}
}
