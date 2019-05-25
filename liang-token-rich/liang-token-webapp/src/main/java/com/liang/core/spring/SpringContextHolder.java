package com.liang.core.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class SpringContextHolder implements ApplicationContextAware,DisposableBean{
	private static ApplicationContext applicationContext = null;

	private static Logger log = LoggerFactory.getLogger(SpringContextHolder.class);
	
	@SuppressWarnings("unchecked")
	public <T> T getBean(String name) {
		assertContextInjected();
		return (T) applicationContext.getBean(name);
	}
	
	public <T> T getBean(Class<T> requiredType) {
		assertContextInjected();
		return (T)applicationContext.getBean(requiredType);
	}
	
	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		log.debug("--------set ApplicationContext ---------" + applicationContext);
		if (SpringContextHolder.applicationContext != null) {
			log.warn("---------set ApplicationContext is overwrited---------"
					+ SpringContextHolder.applicationContext);
		}else {
			applicationContext = arg0;
		}
	}
	
	@Override
	public void destroy() throws Exception {
		SpringContextHolder.clear();
		
	}
	
	public static ApplicationContext getApplicationContext() {
		assertContextInjected();
		return applicationContext;
	}
	private static void assertContextInjected() {
		if (applicationContext == null) {
			throw new IllegalStateException("applicaitonContext not scan in applicationContext.xml:SpringContextHolder");
		}
	}
	
	public static void clear() {
		log.debug("clear SpringContextHolder's ApplicationContext:" + applicationContext);
		applicationContext = null;
	}

	

}
