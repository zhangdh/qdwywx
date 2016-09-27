$(function(){
	
});


function saveQuestionnaire(){
	if(!vailQuestionnaire()){
		alert("有问题未作答，已用红色框线标出；请完全作答后再保存");
		return false;
	} 
	
	var saveStr = "project_id="+$("#project_id").val()+"&questionnaire_id="+$("#questionnaire_id").val()+"&project_bh="+project_bh+"&phone_id"+$("#phone_id").val();
	
	var answers = "";
	var questions = $("div[name='questions']");
	$.each(questions,function(k,v){
		if(v.question_lx == "1020301"){
			// 单选
			answers = answers +v.id+"^"+v.question_lx+"^"+$("#"+v.id+" input[type=radio]:checked").val();
		}else if(v.question_lx == "1020303"){
			// 问答
			answers = answers +v.id+"^"+v.question_lx+"^"+$("#"+v.id+" textarea").val();
		} 
		answers = answers +"~";
	});	
	saveStr = saveStr +"&answers="+answers;
	
	
				
}

function vailQuestionnaire(){
	var result = true;
	var project_id = $("#project_id").val();
	var questionnaire_id = $("#questionnaire_id").val();
	var questions = $("div[name='questions']");
	$.each(questions,function(k,v){
		if(v.question_lx == "1020301"){
			// 单选
			$("#"+v.id).css("border","");
			if($("#"+v.id+" input[type=radio]:checked").val()== undefined){
				//alert(v.id);
				result = false;
				$("#"+v.id).css("border","solid 1px #FF0000");
			}
		}else if(v.question_lx == "1020303"){
			// 问答
			$("#"+v.id).css("border","");
			if($("#"+v.id+" textarea").val()== ""){
				result = false;
				$("#"+v.id).css("border","solid 1px #FF0000");
			}
		} 
	});	
	return result;
}

function getPhone(){
	var queryStr = "project_id="+$("#project_id").val()+"&questionnaire_id="+$("#questionnaire_id").val()+"&project_bh="+$("#project_bh").val()+"&project_num="+$("#project_num").val();
	sys_post("/cc/md/wjdc.do?method=getQuestionnairePhone",queryStr,function(json){
		//alert(json);
		if(json.result){
			$("input[name=phone_status]").val("抽取成功");
			$("#phone_id").val(json.phone_id);
		}else{
			$("#phone_id").val("");
			alert(json.msg);
		}
	});
}
function saveResult(zt_dm){
	var saveStr = "project_id="+$("#project_id").val()+"&questionnaire_id="+$("#questionnaire_id").val()+"&project_bh="+project_bh+"&phone_id"+$("#phone_id").val();
	sys_post("/cc/md/wjdc.do?method=saveResult",saveStr,function(json){
		if(json.result){
			
		}else{
			
		}
	});
}