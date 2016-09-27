<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.List"%>
<%@ include file="/common/common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<% 
	Map project = (Map)request.getAttribute("project");
	Map questionnaire = (Map)request.getAttribute("questionnaire");
	
	List questionList = (List)request.getAttribute("questionList");
	List optionsList = (List)request.getAttribute("optionsList");
	String project_mc = String.valueOf(project.get("mc"));
	String project_sm = String.valueOf(project.get("sm"));
	String project_bh = String.valueOf(project.get("bh"));
%>
<html>
	<head>
		<title>调查问卷</title>
		<script src="${webcontext}/cc/md/wjdc/excuteQuestionnaire.js" type="text/javascript"></script>
		<script>
			var project_mc = '<%=project_mc%>';
			var project_sm = '<%=project_sm%>';
			var project_bh = '<%=project_bh%>';
		</script>
	</head>
	<body>
		<div name="questionnaire_show" id="questionnaire_show">
			<div style="width:100%;height:30px;line-height:30px;text-align:center;background:#F0F0F0">
				<input  type=button value="获取号码" onClick="getPhone();"/>
				<input  type=button value="成功保存" onClick="saveQuestionnaire();"/>
				<input  type=button value="终止访问" onClick="saveQuestionnaire();"/>
				<input  type=button value="访问失败" onClick="saveQuestionnaire();"/>
			当前号码:<label name="phone"></label>抽取状态:<label name="phone_status"></label>
			</div>
			<div style="bgcolor:blue">
				<div style="text-align: center;font-size: 18px;font-weight: bold;"><%=project_mc%></div>
    			<div style="text-align: center;font-size: 15px;margin-top:10px;">(<%=project_sm%>)</div>
			</div>
			<% 
				for(int i=0;i<questionList.size();i++){
					String question_mc = String.valueOf(((Map)questionList.get(i)).get("wt"));
					String question_id = String.valueOf(((Map)questionList.get(i)).get("id"));
					String question_bh = String.valueOf(((Map)questionList.get(i)).get("bh"));
					String question_lx = String.valueOf(((Map)questionList.get(i)).get("lx"));
					String question_sm = String.valueOf(((Map)questionList.get(i)).get("sm"));
			%>
				<div id="<%=question_id%>" name="questions" question_lx="<%=question_lx%>" >
					<div style="font-size:16px;margin-top: 10px;margin-left: 5px;font-weight: bold;">
						<%=question_bh%>、<%=question_mc%>(<%=question_sm %>)
					</div>
					<div style="margin-left: 25px;margin-top:17px;margin-bottom: 17px">
				<% 
					 
					if("1020301".equals(question_lx)){//单选
						for(int j=0;j<optionsList.size();j++){
							Map optionMap = (Map)optionsList.get(j);
							if((question_id).equals(String.valueOf(optionMap.get("question_id")))){
								
				%>
								<input type="radio" project_id = "<%=project.get("id")%>"  name="<%=question_id%>" value="<%=optionMap.get("id")%>"/><%=optionMap.get("mc")%>
				<%
							}
						}
					}else if("1020303".equals(question_lx)) {//问答
				%>
					 <div><textarea  project_id = "<%=project.get("id")%>" id="<%=question_id%>" name="<%=question_id%>" rows="4" style="width:60%;"></textarea></div>
				<% 
					}
				%>
					</div>
				</div>
				 
			<% 
				}
			%>
		</div>
		<div style="width:100%;height:30px;line-height:30px;text-align:center;background:#F0F0F0">
			<input  type=button value="获取号码" onClick="getPhone();"/>
			<input  type=button value="成功保存" onClick="saveQuestionnaire();"/>
			<input  type=button value="终止访问" onClick="saveResult('1020605');"/>
			<input  type=button value="访问失败" onClick="saveResult('1020604');"/>
			当前号码:<label name="phone"></label>抽取状态:<label name="phone_status"></label>
		</div>
		<input type=hidden value="${project_id}" id="project_id" name="project_id">
		<input type=hidden value="${project_bh}" id="project_bh" name="project_bh">
		<input type=hidden value="${project_num}" id="project_num" name="project_num">
		<input type=hidden value="${questionnaire_id}" id="questionnaire_id" name="questionnaire_id">
		<input type=hidden value="" id="phone_id" name="phone_id">
	</body>
</html>
