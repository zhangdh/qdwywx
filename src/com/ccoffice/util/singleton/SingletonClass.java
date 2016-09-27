package com.ccoffice.util.singleton;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SingletonClass {
	private static SqlSessionFactory sqlSessionFactory = null;
	public static SqlSessionFactory getSqlSessionFactory(){
		if(sqlSessionFactory==null){
			String resource = "mybatis-config.xml";
	        InputStream is = SingletonClass.class.getClassLoader().getResourceAsStream(resource);
	        sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        }
		return sqlSessionFactory;
	}
}
