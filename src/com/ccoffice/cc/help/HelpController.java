package com.ccoffice.cc.help;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ccoffice.util.tools.ConvertMap;

@Controller
@RequestMapping("/cc/app/help.do")
public class HelpController {
 
	@RequestMapping(params = "method=query")
	public ModelAndView query(HttpServletRequest request,HttpServletResponse response){
		Help help = new Help(ConvertMap.getMap(request));
		return new ModelAndView("jsonView",help.query());
	}
 
	@RequestMapping(params = "method=queryAll")
	public ModelAndView queryAll(HttpServletRequest request,HttpServletResponse response){
		Help help = new Help(ConvertMap.getMap(request));
		return new ModelAndView("jsonView",help.queryAll());
	}
 
	@RequestMapping(params = "method=save")
	public ModelAndView save(HttpServletRequest request,HttpServletResponse response){
		Help help = new Help(ConvertMap.getMap(request));
		return new ModelAndView("jsonView",help.save());
	}
 
	@RequestMapping(params = "method=delete")
	public ModelAndView delete(HttpServletRequest request,HttpServletResponse response){
		Help help = new Help(ConvertMap.getMap(request));
		return new ModelAndView("jsonView",help.delete());
	}
	@RequestMapping(params = "method=saveSuggestion")
	public ModelAndView saveSuggestion(HttpServletRequest request,HttpServletResponse response){
		Help help = new Help(ConvertMap.getMap(request));
		return new ModelAndView("jsonView",help.saveSuggestion());
	}
}
