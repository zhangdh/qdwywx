package com.ccoffice.util.tools;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class BeanTool {
	public static Object getBeans(String BeanName){
		ApplicationContext ac = new ClassPathXmlApplicationContext ("../ccoffice-servlet.xml");
	    return  ac.getBean(BeanName);
	}
	
}
