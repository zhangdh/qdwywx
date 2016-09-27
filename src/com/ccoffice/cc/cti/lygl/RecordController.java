package com.ccoffice.cc.cti.lygl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ccoffice.util.tools.ConvertMap;

@Controller
@RequestMapping("/cti/record.do")
public class RecordController {
	
	//获取录音列表
	@RequestMapping(params = "method=list")
	public ModelAndView queryCode(HttpServletRequest request,HttpServletResponse response){
		Record record = new Record(ConvertMap.getMap(request));
		return new ModelAndView("jsonView",record.list());
	}
	//获取录音列表
	@RequestMapping(params = "method=queryRecordPath")
	public ModelAndView queryRecordPath(HttpServletRequest request,HttpServletResponse response){
		Record record = new Record(ConvertMap.getMap(request));
		return new ModelAndView("jsonView",record.queryRecordPath());
	}
	
}
