package com.ccoffice.cc.md;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ccoffice.util.db.Db;
import com.ccoffice.util.json.JsonData;
import com.ccoffice.util.log.Log;

public class WJDC {
	Map _map = null;
	String yhid = "";
	String groupId = "";
	JsonData json = null;
	public  WJDC(Map map){
		this._map = map;
		this.yhid = map.get("yhid").toString();
		this.groupId = map.get("group_id").toString();
		json = new JsonData();
	}
	public Map queryProject(){
		try{
			List _list = Db.getJtN().queryForList("select id,mc,cjsj,(select mc from t_dm where dm = zt_dm) zt_dm_mc from t_cc_md_project where zt_dm= '1020100' ");
			json.addListEasyUI("table_list_project", _list, null); 
			
		}catch(Exception e){
			Log.error(yhid,"queryProject","","民调-查询民调项目数据时异常"+e.toString());
		}
		return json.getJsonDataEasyUIGrid();
	}
	
	public Map queryQuestionnaire(){
		try{
			String project_id = _map.get("project_id")==null?"":String.valueOf(_map.get("project_id"));
			List _list = null;
			if(!"".equals(project_id)){
				_list = Db.getJtN().queryForList("select id,mc,cjsj,(select mc from t_dm where dm = zt_dm) zt_dm_mc from t_cc_md_questionnaire where id in (select questionnaire_id from t_cc_md_project_questionnaire where project_id='"+project_id+"') and yxbz =1 and zt_dm = '1020200' ");
			}
			
			json.addListEasyUI("table_list_questionnaire", _list, null); 
			
		}catch(Exception e){
			Log.error(yhid,"table_list_questionnaire","","民调-查询问卷数据时异常"+e.toString());
		}
		return json.getJsonDataEasyUIGrid();
	}
	
	public Map openQuestionnaire(){
		Map map = new HashMap();
		try{
			String project_id = _map.get("project_id")==null?"":String.valueOf(_map.get("project_id"));
			String questionnaire_id = _map.get("questionnaire_id")==null?"":String.valueOf(_map.get("questionnaire_id"));
			
			Map project = Db.getJtN().queryForMap("select * from t_cc_md_project where id='"+project_id+"' and yxbz = 1 ");
			
			Map questionnaire = Db.getJtN().queryForMap("select * from t_cc_md_project where id='"+questionnaire_id+"' and yxbz = 1 ");
			
			List questionList = Db.getJtN().queryForList("select * from t_cc_md_question where yxbz = 1 and project_id='"+project_id+"' and questionnaire_id='"+questionnaire_id+"'  order by sx ");
			
			List optionsList = Db.getJtN().queryForList("select * from t_cc_md_question_options where yxbz = 1 and project_id='"+project_id+"' and questionnaire_id='"+questionnaire_id+"'  order by sx ");
			
			map.put("project", project);
			map.put("questionnaire", questionnaire);
			map.put("questionList", questionList);
			map.put("optionsList", optionsList);
			
			map.put("project_id", project_id);
			map.put("project_bh", project.get("bh"));
			map.put("project_num", project.get("num"));
			
			map.put("questionnaire_id", questionnaire_id);
			
		}catch(Exception e){
			Log.error(yhid,"queryProject","","民调-查询民调项目数据时异常"+e.toString());
		}
		return map;
	}
	
	public Map saveQuestionnaire(){
		try{
			String project_id = _map.get("project_id")==null?"":String.valueOf(_map.get("project_id"));
			String questionnaire_id = _map.get("questionnaire_id")==null?"":String.valueOf(_map.get("questionnaire_id"));
			String project_bh = _map.get("project_bh")==null?"":String.valueOf(_map.get("project_bh"));
			String phone_id = _map.get("phone_id")==null?"":String.valueOf(_map.get("phone_id"));
			String answers = _map.get("answers")==null?"":String.valueOf(_map.get("answers"));
			String tableName = "t_cc_md_question_answer_"+project_bh;
			StringBuffer sqlStr = new StringBuffer();
			
			String[] answersAyyay = answers.split("~");
			for(int i=0;i<answersAyyay.length-1;i++){
				sqlStr.delete(0,sqlStr.length()-1);
				String answer =  answersAyyay[i];
				String[] answerAyyay = answer.split("^");
				String question_id = answerAyyay[0];
				String question_lx =  answerAyyay[1];
				String options_id = "";
				String options_content = "";
				if("1020301".equals(question_lx)){
					//单选
					options_id = answerAyyay[2];
				}else if("1020303".equals(question_lx)){
					//问答
					options_content = answerAyyay[2];
					
				}
				sqlStr.append("insert into ").append(tableName).append("(project_id,questionnaire_id,question_id,question_lx,phone_id,options_id,options_content,yhid,zt_dm,create_time) values('")
					  .append(project_id).append("','").append(questionnaire_id).append("','").append(question_id).append("','").append(question_lx)
					  .append("','").append(phone_id).append("','").append(options_content).append("','").append(yhid).append("','','").append(Db.getStr()).append("')");
				
				Db.getJtN().update(sqlStr.toString());
			}
			//调查成功
			Db.getJtN().update("update t_cc_md_project_phone_"+project_bh+" set zt_dm = '1020603' where id='"+phone_id+"'");
			
			Db.getJtN().update("update t_cc_md_project  set already = already+1 where id='"+project_id+"'");
			json.addResult(true,"保存民调数据成功");
		}catch(Exception e){
			json.addResult(false,"保存民调数据时异常"+e.toString());
			Log.error(yhid,"saveQuestionnaire","","保存民调数据时异常"+e.toString());
		}
		return json.getJsonDataResult();
	}
	
	
	public Map saveResult(){
		try{
			String project_bh = _map.get("project_bh")==null?"":String.valueOf(_map.get("project_bh"));
			String phone_id = _map.get("phone_id")==null?"":String.valueOf(_map.get("phone_id"));
			String zt_dm = _map.get("zt_dm")==null?"":String.valueOf(_map.get("zt_dm"));
			//
			Db.getJtN().update("update t_cc_md_project_phone_"+project_bh+" set zt_dm = '"+zt_dm+"' where id='"+phone_id+"'");
			json.addResult(true,"保存民调数据成功");
		}catch(Exception e){
			json.addResult(false,"保存民调状态时异常"+e.toString());
			Log.error(yhid,"saveResult","","保存民调状态时异常"+e.toString());
		}
		return json.getJsonDataResult();
	}
	
	public Map getQuestionnairePhone(){
		try{
			String project_id = _map.get("project_id")==null?"":String.valueOf(_map.get("project_id"));
			String project_bh = _map.get("project_bh")==null?"":String.valueOf(_map.get("project_bh"));
			String project_num = _map.get("project_num")==null?"":String.valueOf(_map.get("project_num"));
			String tableName = "t_cc_md_project_phone_"+project_bh;
			int num = Db.getJtN().queryForInt("select count(*) from  "+tableName+" where zt_dm = '1020603' ");
			if(num > Integer.parseInt(project_num)){
				json.addResult(false,"抽取失败，已达到需要的成功样本数;");
			}else{
				List _list = Db.getJtN().queryForList("select * from "+tableName+" where zt_dm = '1020601' limit 1 ");
				if(_list.size()>0){
					Map phoneMap = (Map)_list.get(0);
					json.addData("phone", phoneMap.get("phone"));
					json.addData("phone_id", phoneMap.get("id"));
					
					Db.getJtN().update("update t_cc_md_project  set nownum = nownum+1 where id='"+project_id+"'");
					Db.getJtN().update("update "+tableName+" set zt_dm = '1020602'  where id='"+phoneMap.get("id")+"'");
				}else{
					json.addResult(false,"抽取失败，已经没有可用的样本;");
				}
			}
		}catch(Exception e){
			json.addResult(false,"获取民调号码时异常"+e.toString());
			Log.error(yhid,"getQuestionnairePhone","","获取民调号码时异常"+e.toString());
		}
		return json.getJsonDataResult();
	}
}
