package com.ccoffice.base.code;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ccoffice.util.tools.ConvertMap;

@Controller
@RequestMapping("/base/code.do")
public class CodeController {
	//获取功能模块
	@RequestMapping(params = "method=getModule")
	public ModelAndView getModule(HttpServletRequest request,HttpServletResponse response){
		Code code = new Code(ConvertMap.getMap(request));
		return new ModelAndView("jsonView",code.getModule());
	}
	
	//获取代码类型
	@RequestMapping(params = "method=getDmType")
	public ModelAndView getDmType(HttpServletRequest request,HttpServletResponse response){
		Code code = new Code(ConvertMap.getMap(request));
		return new ModelAndView("jsonView",code.getDmType());
	}
	
	//保存代码
	@RequestMapping(params = "method=saveCode")
	public ModelAndView saveCode(HttpServletRequest request,HttpServletResponse response){
		Code code = new Code(ConvertMap.getMap(request));
		return new ModelAndView("jsonView",code.saveCode());
	}
	@RequestMapping(params = "method=queryCode")
	public ModelAndView queryCode(HttpServletRequest request,HttpServletResponse response){
		Code code = new Code(ConvertMap.getMap(request));
		return new ModelAndView("jsonView",code.queryCode());
	}
	@RequestMapping(params = "method=delCode")
	public ModelAndView delCode(HttpServletRequest request,HttpServletResponse response){
		Code code = new Code(ConvertMap.getMap(request));
		return new ModelAndView("jsonView",code.delCode());
	}
	
}
