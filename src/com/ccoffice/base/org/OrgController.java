package com.ccoffice.base.org;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ccoffice.util.tools.ConvertMap;

@Controller
@RequestMapping("/base/org.do")
public class OrgController {
	// 
	@RequestMapping(params = "method=getOrgTree")
	public ModelAndView getOrgTree(HttpServletRequest request,HttpServletResponse response){
		Map paraMap = ConvertMap.getMap(request); 
		Org org = new Org(paraMap);
		return new ModelAndView("jsonView",org.getOrgTree());
	}
	
	@RequestMapping(params = "method=getGrid")
	public ModelAndView getGrid(HttpServletRequest request,HttpServletResponse response){
		Map paraMap = ConvertMap.getMap(request); 
		Org org = new Org(paraMap);
		return new ModelAndView("jsonView",org.getGrid());
	}
	
	// 
	@RequestMapping(params = "method=saveBm")
	public ModelAndView saveBm(HttpServletRequest request,HttpServletResponse response){
		Map paraMap = ConvertMap.getMap(request); 
		Org org = new Org(paraMap);
		return new ModelAndView("jsonView",org.saveBm());
	}
	
	@RequestMapping(params = "method=getOrgList")
	public ModelAndView getOrgList(HttpServletRequest request,HttpServletResponse response){
		Map paraMap = ConvertMap.getMap(request); 
		Org org = new Org(paraMap);
		return new ModelAndView("jsonView",org.getOrgList());
	}
	@RequestMapping(params = "method=saveGw")
	public ModelAndView saveGw(HttpServletRequest request,HttpServletResponse response){
		Map paraMap = ConvertMap.getMap(request); 
		Org org = new Org(paraMap);
		return new ModelAndView("jsonView",org.saveGw());
	}
	
	@RequestMapping(params = "method=saveYh")
	public ModelAndView saveYh(HttpServletRequest request,HttpServletResponse response){
		Map paraMap = ConvertMap.getMap(request); 
		Org org = new Org(paraMap);
		return new ModelAndView("jsonView",org.saveYh());
	}
	@RequestMapping(params = "method=delOrg")
	public ModelAndView delOrg(HttpServletRequest request,HttpServletResponse response){
		Map paraMap = ConvertMap.getMap(request); 
		Org org = new Org(paraMap);
		return new ModelAndView("jsonView",org.delOrg());
	}
	@RequestMapping(params = "method=getJsList")
	public ModelAndView getJsList(HttpServletRequest request,HttpServletResponse response){
		Map paraMap = ConvertMap.getMap(request); 
		Org org = new Org(paraMap);
		return new ModelAndView("jsonView",org.getJsList());
	}
	@RequestMapping(params = "method=showYh")
	public ModelAndView showYh(HttpServletRequest request,HttpServletResponse response){
		Map paraMap = ConvertMap.getMap(request); 
		Org org = new Org(paraMap);
		return new ModelAndView("jsonView",org.showYh());
	}
	
	@RequestMapping(params = "method=showBm")
	public ModelAndView showBm(HttpServletRequest request,HttpServletResponse response){
		Map paraMap = ConvertMap.getMap(request); 
		Org org = new Org(paraMap);
		return new ModelAndView("jsonView",org.showBm());
	}
	
	
	@RequestMapping(params = "method=modiYh")
	public ModelAndView modiYh(HttpServletRequest request,HttpServletResponse response){
		Map paraMap = ConvertMap.getMap(request); 
		Org org = new Org(paraMap);
		return new ModelAndView("jsonView",org.modiYh());
	}
	@RequestMapping(params = "method=modiPassword")
	public ModelAndView modiPassword(HttpServletRequest request,HttpServletResponse response){
		Map paraMap = ConvertMap.getMap(request); 
		Org org = new Org(paraMap);
		return new ModelAndView("jsonView",org.modiPassword());
	}
	
	@RequestMapping(params = "method=modiSelf")
	public ModelAndView modiSelf(HttpServletRequest request,HttpServletResponse response){
		Map paraMap = ConvertMap.getMap(request); 
		Org org = new Org(paraMap);
		return new ModelAndView("jsonView",org.modiSelf());
	}
}
