package com.ccoffice.base.authority;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ccoffice.util.tools.ConvertMap;

@Controller
@RequestMapping("/base/authority.do")
public class AuthorityController {	
	@RequestMapping(params = "method=GetMenu")
	public ModelAndView GetMenu(HttpServletRequest request) {
		Authority authority = new Authority(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", authority.GetMenu());
	}
	
	@RequestMapping(params = "method=GetJs")
	public ModelAndView GetJs(HttpServletRequest request) {
		Authority authority = new Authority(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", authority.GetJs());
	}
	
	@RequestMapping(params = "method=GetAuthority")
	public ModelAndView GetAuthority(HttpServletRequest request) {
		Authority authority = new Authority(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", authority.GetAuthority());
	}
	
	@RequestMapping(params = "method=saveAuthority")
	public ModelAndView saveAuthority(HttpServletRequest request) {
		Authority authority = new Authority(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", authority.saveAuthority());
	}
}
