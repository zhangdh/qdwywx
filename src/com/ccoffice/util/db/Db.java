package com.ccoffice.util.db;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.incrementer.MySQLMaxValueIncrementer;
import org.springframework.jdbc.support.incrementer.OracleSequenceMaxValueIncrementer;
import org.springframework.jdbc.support.incrementer.SqlServerMaxValueIncrementer;
import org.springframework.jdbc.support.incrementer.PostgreSQLSequenceMaxValueIncrementer;
import org.springframework.stereotype.Repository;

import com.ccoffice.util.log.Log;
import com.ccoffice.util.page.Page;
import com.ccoffice.util.tools.Guid;
import com.ccoffice.util.tools.SysPara;
@Repository
public class Db{
	
	public static DataSource getDsExt() {
		return dsExt;
	}

	public static void setDsExt(DataSource dsExt) {
		Db.dsExt = dsExt;
	}

	private static DataSource ds;
	
	private static DataSource dsExt;
	
	
	private static JdbcTemplate jdbcTemplate;
	
	private static JdbcTemplate jdbcTemplateExt;
	
	public static JdbcTemplate getJdbcTemplateExt() {
		return jdbcTemplateExt;
	}

	public static void setJdbcTemplateExt(JdbcTemplate jdbcTemplateExt) {
		Db.jdbcTemplateExt = jdbcTemplateExt;
	}

	private static PostgreSQLSequenceMaxValueIncrementer postgresqlInc;
	private static MySQLMaxValueIncrementer mysqlInc;
	private static SqlServerMaxValueIncrementer sqlserverInc;
	private static OracleSequenceMaxValueIncrementer oracleInc;
	
	public Db(){
	}

	public static String getGUID()
	{
		return Guid.get();
	}

	public static int getNextId()throws Exception{
		if (SysPara.getValue("db_type").equals("postgresql")){
			if (postgresqlInc == null){
				postgresqlInc = new PostgreSQLSequenceMaxValueIncrementer(ds,"t_sys_sequence");
				//postgresqlInc.setCacheSize(10);
			}
			return postgresqlInc.nextIntValue();
		}
		
		
		if (SysPara.getValue("db_type").equals("mysql")){
			if (mysqlInc == null){
				mysqlInc = new MySQLMaxValueIncrementer(ds, "t_sys_sequence", "id");
				mysqlInc.setCacheSize(10);
			}
			return mysqlInc.nextIntValue();
		}
		if (SysPara.getValue("db_type").equals("sqlserver")){
			if (sqlserverInc == null){
				sqlserverInc = new SqlServerMaxValueIncrementer(ds, "t_sys_sequence", "id");
				sqlserverInc.setCacheSize(10);
			}
			return sqlserverInc.nextIntValue();
		}
		if (SysPara.getValue("db_type").equals("oracle")){
			if (oracleInc == null)
				oracleInc = new OracleSequenceMaxValueIncrementer(ds, "sys_sequence");
			return oracleInc.nextIntValue();
		} else{
			throw new Exception("t_sys_para.csdm>db_type不匹配");
		}
	}
	public static JdbcTemplate getJtN(){
		return jdbcTemplate;
	}

	public static JdbcTemplate getJtNExt(){
		return jdbcTemplateExt;
	}
	
	public static JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public static void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		Db.jdbcTemplate = jdbcTemplate;
	}
	public static List getPageData(Page page){
		List _list = null;
		try{
			String sql = String.valueOf(page.getSql());
			int allCount;
			if(Integer.parseInt(page.getAllCount()) ==0){
				if("".equals(page.getSqlCount())){
					int i = sql.indexOf("from");
					page.setSqlCount("select count(*) "+sql.substring(i));
				}
				allCount = getJtN().queryForInt(page.getSqlCount());
			}else{	
				allCount = Integer.parseInt(page.getAllCount());
			}
			int nowPage = Integer.parseInt(page.getNext_page());
			int pageSize = Integer.parseInt(page.getPage_size());
			int start = ((nowPage-1)*pageSize)>=allCount?allCount:((nowPage-1)*pageSize);
			int end = (start+pageSize)>=allCount?allCount:(start+pageSize);
			page.setAllCount(String.valueOf(allCount));
			page.setCurPage(String.valueOf(nowPage));
			page.setAllPage(String.valueOf(allCount/pageSize+1));
			if(SysPara.getValue("db_type").equals("mysql")){
				sql = sql+" limit "+start+","+pageSize;
			}else if(SysPara.getValue("db_type").equals("postgresql")){
				sql = sql+" limit "+pageSize+" offset "+start;
			}
			_list = getJtN().queryForList(sql);
		}catch(Exception e){
			Log.error("","","","获取分页数据时异常"+e.toString());
			e.printStackTrace();
		}
		return _list;
	}
	
	public static List getPageDataExt(Page page){
		List _list = null;
		try{
			String sql = String.valueOf(page.getSql());
			int allCount;
			if(Integer.parseInt(page.getAllCount()) ==0){
				if("".equals(page.getSqlCount())){
					int i = sql.indexOf("from");
					page.setSqlCount("select count(*) "+sql.substring(i));
				}
				allCount = getJtNExt().queryForInt(page.getSqlCount());
			}else{	
				allCount = Integer.parseInt(page.getAllCount());
			}
			int nowPage = Integer.parseInt(page.getNext_page());
			int pageSize = Integer.parseInt(page.getPage_size());
			int start = ((nowPage-1)*pageSize)>=allCount?allCount:((nowPage-1)*pageSize);
			int end = (start+pageSize)>=allCount?allCount:(start+pageSize);
			page.setAllCount(String.valueOf(allCount));
			page.setCurPage(String.valueOf(nowPage));
			page.setAllPage(String.valueOf(allCount/pageSize+1));
			if(SysPara.getValue("db_type").equals("mysql")){
				sql = sql+" limit "+start+","+pageSize;
			}else if(SysPara.getValue("db_type").equals("postgresql")){
				sql = sql+" limit "+pageSize+" offset "+start;
			}
			_list = getJtNExt().queryForList(sql);
		}catch(Exception e){
			Log.error("","","","获取分页数据时异常"+e.toString());
			e.printStackTrace();
		}
		return _list;
	}
	
   public static String getDateStr(){
	   try{
		   if(SysPara.getValue("dbType").equals("mysql")){
			   return "now()";
		   }else if(SysPara.getValue("dbType").equals("sqlsever")){
			   return "getDate()";
		   }else if(SysPara.getValue("dbType").equals("oracle")){
			   return "sysdate";
		   }else if(SysPara.getValue("dbType").equals("postgresql")){
			   return "current_timestamp";
		   }else{
			   return "now()";
		   }
	   }catch(Exception e){
		   Log.error("","","","获取数据库时间字符串时异常"+e.toString());
		   e.printStackTrace();
		   return "now()";
	   }	
   }	
   public static String getStr(){
	   String str = "";
	   try{
		   str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	   }catch(Exception e){
		   Log.error("","","","获取时间字符串时异常"+e.toString());
		   e.printStackTrace();
	   }
	   return str;
   }

public static DataSource getDs() {
	return ds;
}

public static void setDs(DataSource ds) {
	Db.ds = ds;
}

public static MySQLMaxValueIncrementer getMysqlInc() {
	return mysqlInc;
}

public static void setMysqlInc(MySQLMaxValueIncrementer mysqlInc) {
	Db.mysqlInc = mysqlInc;
}

public static SqlServerMaxValueIncrementer getSqlserverInc() {
	return sqlserverInc;
}

public static void setSqlserverInc(SqlServerMaxValueIncrementer sqlserverInc) {
	Db.sqlserverInc = sqlserverInc;
}

public static OracleSequenceMaxValueIncrementer getOracleInc() {
	return oracleInc;
}

public static void setOracleInc(OracleSequenceMaxValueIncrementer oracleInc) {
	Db.oracleInc = oracleInc;
}
	
}
