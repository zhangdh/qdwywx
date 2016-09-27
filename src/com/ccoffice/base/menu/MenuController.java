package com.ccoffice.base.menu;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ccoffice.util.tools.ConvertMap;

@Controller
@RequestMapping("/base/menu.do")
public class MenuController {	
	@RequestMapping(params = "method=GetMenu")
	public ModelAndView GetMenu(HttpServletRequest request) {
		Menu menu = new Menu(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", menu.GetMenu());
	}
}
