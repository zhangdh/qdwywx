package com.ccoffice.util.log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log{
	static final Logger LOG = LoggerFactory.getLogger(Log.class);
	public static void writeFileLog(String level ,String yhid,String method,String logid,String desc){
		if("error".equals(level)){
			LOG.error("{"+yhid+"}:"+logid+method+":"+desc);
		}else if("debug".equals(level)){
			LOG.debug("{"+yhid+"}:"+logid+method+":"+desc);
		}else if("trace".equals(level)){
			LOG.trace("{"+yhid+"}:"+logid+method+":"+desc);
		}else if("info".equals(level)){
			LOG.info("{"+yhid+"}:"+logid+method+":"+desc);
		}else{
			LOG.info("{"+yhid+"}:"+logid+method+":"+desc);
		}
	}
	public static void info(String yhid,String method,String logid,String desc){
		LOG.info("{"+yhid+"}:"+logid+":"+method+":"+desc);
	}
	public static void trace(String yhid,String method,String logid,String desc){
		LOG.trace("{"+yhid+"}:"+logid+":"+method+":"+desc);
	}
	public static void debug(String yhid,String method,String logid,String desc){
		LOG.debug("{"+yhid+"}:"+logid+":"+method+":"+desc);
	}
	public static void error(String yhid,String method,String logid,String desc){
		LOG.error("{"+yhid+"}:"+logid+":"+method+":"+desc);
	}
}
