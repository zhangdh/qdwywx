package com.ccoffice.cc.gdgl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ccoffice.util.tools.ConvertMap;

@Controller
@RequestMapping("/cc/app/gdgl.do")
public class AppGdglController {
	
	@RequestMapping(params = "method=querySelect")
	public ModelAndView querySelect(HttpServletRequest request) {
		AppGdgl gdgl = new AppGdgl(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", gdgl.querySelect());
	}
	@RequestMapping(params = "method=queryQuerySelect")
	public ModelAndView queryQuerySelect(HttpServletRequest request) {
		AppGdgl gdgl = new AppGdgl(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", gdgl.queryQuerySelect());
	}
	//待处理
	@RequestMapping(params = "method=queryTodo")
	public ModelAndView queryTodo(HttpServletRequest request) {
		AppGdgl gdgl = new AppGdgl(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", gdgl.queryTodo());
	}
	
	//待处理
	@RequestMapping(params = "method=dealTodo")
	public ModelAndView dealTodo(HttpServletRequest request) {
		AppGdgl gdgl = new AppGdgl(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", gdgl.dealTodo());
	}
		
	//展现待处理
	@RequestMapping(params = "method=showTodo")
	public ModelAndView showTodo(HttpServletRequest request) {
		AppGdgl gdgl = new AppGdgl(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", gdgl.showTodo());
	}
	
	@RequestMapping(params = "method=saveReservation")
	public ModelAndView saveReservation(HttpServletRequest request,HttpServletResponse response) {
		AppGdgl gdgl = new AppGdgl(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", gdgl.saveReservation());
	}
	
	@RequestMapping(params = "method=saveSign")
	public ModelAndView saveSign(HttpServletRequest request,HttpServletResponse response) {
		AppGdgl gdgl = new AppGdgl(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", gdgl.saveSign());
	}
	
	@RequestMapping(params = "method=uploadImages")
	public ModelAndView uploadImages(HttpServletRequest request,HttpServletResponse response) {
		AppGdgl gdgl = new AppGdgl(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", gdgl.uploadImages(request,response));
	}
	@RequestMapping(params = "method=getImages")
	public ModelAndView getImages(HttpServletRequest request,HttpServletResponse response) {
		AppGdgl gdgl = new AppGdgl(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", gdgl.getImages());
	}
	
	@RequestMapping(params = "method=queryReservation")
	public ModelAndView queryReservation(HttpServletRequest request,HttpServletResponse response) {
		AppGdgl gdgl = new AppGdgl(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", gdgl.queryReservation());
	}
	
	@RequestMapping(params = "method=querySign")
	public ModelAndView querySign(HttpServletRequest request,HttpServletResponse response) {
		AppGdgl gdgl = new AppGdgl(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", gdgl.querySign());
	}
	@RequestMapping(params = "method=queryDb")
	public ModelAndView queryDb(HttpServletRequest request,HttpServletResponse response) {
		AppGdgl gdgl = new AppGdgl(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", gdgl.queryDb());
	}
}
