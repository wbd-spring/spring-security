package com.wbd.spring.security.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取 application 的类
 * @author zgh
 *
 */
@Component
public class SpringBeanFactoryUtils implements ApplicationContextAware{

	private static ApplicationContext context = null;
	
	
	public static <T> T getBean(Class<T> type) {
		return context.getBean(type);
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	
		if(context==null) {
			context = applicationContext;
		}
		
	}

}
