package com.ccoffice.cc.md;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import com.ccoffice.util.tools.ConvertMap;

@Controller
@RequestMapping("/cc/md/wjdc.do")
public class WJDCController {
	@RequestMapping(params = "method=queryProject")
	public ModelAndView queryProject(HttpServletRequest request) {
		WJDC wjdc = new WJDC(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", wjdc.queryProject());
	}
	
	@RequestMapping(params = "method=queryQuestionnaire")
	public ModelAndView queryQuestionnaire(HttpServletRequest request) {
		WJDC wjdc = new WJDC(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", wjdc.queryQuestionnaire());
	}
	@RequestMapping(params = "method=openQuestionnaire")
	public ModelAndView openQuestionnaire(HttpServletRequest request) {
		WJDC wjdc = new WJDC(ConvertMap.getMap(request));
		return new ModelAndView("/cc/md/wjdc/excuteQuestionnaire.jsp", wjdc.openQuestionnaire());
	}
	
	@RequestMapping(params = "method=saveQuestionnaire")
	public ModelAndView saveQuestionnaire(HttpServletRequest request) {
		WJDC wjdc = new WJDC(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", wjdc.saveQuestionnaire());
	}
	
	@RequestMapping(params = "method=saveResult")
	public ModelAndView saveResult(HttpServletRequest request) {
		WJDC wjdc = new WJDC(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", wjdc.saveResult());
	}
	
	@RequestMapping(params = "method=getQuestionnairePhone")
	public ModelAndView getQuestionnairePhone(HttpServletRequest request) {
		WJDC wjdc = new WJDC(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", wjdc.getQuestionnairePhone());
	}
}
