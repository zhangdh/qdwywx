package com.ccoffice.txl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ccoffice.util.tools.ConvertMap;

@Controller
@RequestMapping("/txl.do")
public class TxlController {
	// 
	@RequestMapping(params = "method=getTxlTree")
	public ModelAndView getTxlTree(HttpServletRequest request,HttpServletResponse response){
		Map paraMap = ConvertMap.getMap(request); 
		Txl txl = new Txl(paraMap);
		return new ModelAndView("jsonView",txl.getTxlTree());
	}
	
	// 
	@RequestMapping(params = "method=saveTxl")
	public ModelAndView saveTxl(HttpServletRequest request,HttpServletResponse response){
		Map paraMap = ConvertMap.getMap(request); 
		Txl txl = new Txl(paraMap);
		return new ModelAndView("jsonView",txl.saveTxl());
	}
	
	@RequestMapping(params = "method=getTxlList")
	public ModelAndView getTxlList(HttpServletRequest request,HttpServletResponse response){
		Map paraMap = ConvertMap.getMap(request); 
		Txl txl = new Txl(paraMap);
		return new ModelAndView("jsonView",txl.getTxlList());
	}
	@RequestMapping(params = "method=modiTxl")
	public ModelAndView modiTxl(HttpServletRequest request,HttpServletResponse response){
		Map paraMap = ConvertMap.getMap(request); 
		Txl txl = new Txl(paraMap);
		return new ModelAndView("jsonView",txl.modiTxl());
	}
	
	@RequestMapping(params = "method=delTxl")
	public ModelAndView delTxl(HttpServletRequest request,HttpServletResponse response){
		Map paraMap = ConvertMap.getMap(request); 
		Txl txl = new Txl(paraMap);
		return new ModelAndView("jsonView",txl.delTxl());
	}
	@RequestMapping(params = "method=upload")
	public ModelAndView upload(HttpServletRequest request,HttpServletResponse response){
		Map paraMap = ConvertMap.getMap(request); 
		Txl txl = new Txl(paraMap);
		return new ModelAndView("jsonView",txl.upload(request,response));
	}
	@RequestMapping(params = "method=showTxl")
	public ModelAndView showTxl(HttpServletRequest request,HttpServletResponse response){
		Map paraMap = ConvertMap.getMap(request); 
		Txl txl = new Txl(paraMap);
		return new ModelAndView("jsonView",txl.showTxl());
	}
}
