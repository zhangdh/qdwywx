package com.ccoffice.cc.gdgl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ccoffice.util.tools.ConvertMap;

@Controller
@RequestMapping("/cc/gdgl.do")
public class GdglController {
	
	@RequestMapping(params = "method=querySelect")
	public ModelAndView querySelect(HttpServletRequest request) {
		Gdgl gdgl = new Gdgl(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", gdgl.querySelect());
	}
	@RequestMapping(params = "method=queryQuerySelect")
	public ModelAndView queryQuerySelect(HttpServletRequest request) {
		Gdgl gdgl = new Gdgl(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", gdgl.queryQuerySelect());
	}
	@RequestMapping(params = "method=queryReturnSelect")
	public ModelAndView queryReturnSelect(HttpServletRequest request) {
		Gdgl gdgl = new Gdgl(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", gdgl.queryReturnSelect());
	}
	@RequestMapping(params = "method=queryNextSelect")
	public ModelAndView queryNextSelect(HttpServletRequest request) {
		Gdgl gdgl = new Gdgl(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", gdgl.queryNextSelect());
	}
	@RequestMapping(params = "method=queryImages")
	public ModelAndView queryImages(HttpServletRequest request) {
		Gdgl gdgl = new Gdgl(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", gdgl.queryImages());
	}
	@RequestMapping(params = "method=query")
	public ModelAndView query(HttpServletRequest request) {
		Gdgl gdgl = new Gdgl(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", gdgl.query());
	}
	@RequestMapping(params = "method=queryGdcx")
	public ModelAndView queryGdcx(HttpServletRequest request) {
		Gdgl gdgl = new Gdgl(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", gdgl.queryGdcx());
	}
	//待处理
	@RequestMapping(params = "method=queryTodo")
	public ModelAndView queryTodo(HttpServletRequest request) {
		Gdgl gdgl = new Gdgl(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", gdgl.queryTodo());
	}
	
	//待处理
	@RequestMapping(params = "method=dealTodo")
	public ModelAndView dealTodo(HttpServletRequest request) {
		Gdgl gdgl = new Gdgl(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", gdgl.dealTodo());
	}
		
	@RequestMapping(params = "method=saveGd")
	public ModelAndView saveGd(HttpServletRequest request) {
		Gdgl gdgl = new Gdgl(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", gdgl.saveGd());
	}
	
	@RequestMapping(params = "method=queryTj")
	public ModelAndView queryTj(HttpServletRequest request) {
		Gdgl gdgl = new Gdgl(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", gdgl.queryTj());
	}
	
	@RequestMapping(params = "method=exportTj")
	public ModelAndView exportTj(HttpServletRequest request,HttpServletResponse response) {
		Gdgl gdgl = new Gdgl(ConvertMap.getMap(request));
		gdgl.exportTj(request, response);
		return  null;
	}
	
	@RequestMapping(params = "method=exportCx")
	public ModelAndView exportCx(HttpServletRequest request,HttpServletResponse response) {
		Gdgl gdgl = new Gdgl(ConvertMap.getMap(request));
		gdgl.exportCx(request, response);
		return  null;
	}
	
	@RequestMapping(params = "method=queryLxr")
	public ModelAndView queryLxr(HttpServletRequest request,HttpServletResponse response) {
		Gdgl gdgl = new Gdgl(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", gdgl.queryLxr());
	}
	
	@RequestMapping(params = "method=queryReservation")
	public ModelAndView queryReservation(HttpServletRequest request,HttpServletResponse response) {
		Gdgl gdgl = new Gdgl(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", gdgl.queryReservation());
	}
	
	@RequestMapping(params = "method=querySign")
	public ModelAndView querySign(HttpServletRequest request,HttpServletResponse response) {
		Gdgl gdgl = new Gdgl(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", gdgl.querySign());
	}
	@RequestMapping(params = "method=saveVideos")
	public ModelAndView saveVideos(HttpServletRequest request,HttpServletResponse response) {
		Gdgl gdgl = new Gdgl(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", gdgl.saveVideos());
	}
	@RequestMapping(params = "method=queryVideos")
	public ModelAndView queryVideos(HttpServletRequest request,HttpServletResponse response) {
		Gdgl gdgl = new Gdgl(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", gdgl.queryVideos());
	}

	@RequestMapping(params = "method=queryDbInfo")
	public ModelAndView queryDbInfo(HttpServletRequest request, HttpServletResponse response) {
		Gdgl gdgl = new Gdgl(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", gdgl.queryDbInfo());
	}

	@RequestMapping(params = "method=queryWgInfo")
	public ModelAndView queryWgInfo(HttpServletRequest request, HttpServletResponse response) {
		Gdgl gdgl = new Gdgl(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", gdgl.queryWgInfo());
	}
}
