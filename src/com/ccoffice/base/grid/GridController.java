package com.ccoffice.base.grid;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ccoffice.util.tools.ConvertMap;

@Controller
@RequestMapping("/base/grid.do")
public class GridController {	
	@RequestMapping(params = "method=init")
	public ModelAndView init(HttpServletRequest request) {
		Grid grid = new Grid(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", grid.init());
	}
	
	@RequestMapping(params = "method=saveGrid")
	public ModelAndView saveGrid(HttpServletRequest request) {
		Grid grid = new Grid(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", grid.saveGrid());
	}
	
	@RequestMapping(params = "method=getGridTree")
	public ModelAndView getGridTree(HttpServletRequest request) {
		Grid grid = new Grid(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", grid.getGridTree());
	}
	@RequestMapping(params = "method=getGridList")
	public ModelAndView getGridList(HttpServletRequest request) {
		Grid grid = new Grid(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", grid.getGridList());
	}
	@RequestMapping(params = "method=delGrid")
	public ModelAndView delGrid(HttpServletRequest request) {
		Grid grid = new Grid(ConvertMap.getMap(request));
		return new ModelAndView("jsonView", grid.delGrid());
	}
}
