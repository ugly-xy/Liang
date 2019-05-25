package com.zb.core.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextHolder implements ApplicationContextAware,DisposableBean {


	private static ApplicationContext applicationContext = null;

	private static Logger logger = LoggerFactory.getLogger(SpringContextHolder.class);

	public void setApplicationContext(ApplicationContext applicationContext) {
		logger.debug("set ApplicationContext into SpringContextHolder:" + applicationContext);

		if (SpringContextHolder.applicationContext != null) {
			logger.warn("SpringContextHolder's ApplicationContext is overwrited, original ApplicationContextis:"
					+ SpringContextHolder.applicationContext);
		}

		SpringContextHolder.applicationContext = applicationContext; 
	}

	@Override
	public void destroy() throws Exception {
		SpringContextHolder.clear();
	}

	public static ApplicationContext getApplicationContext() {
		assertContextInjected();
		return applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		assertContextInjected();
		return (T) applicationContext.getBean(name);
	}

	public static <T> T getBean(Class<T> requiredType) {
		assertContextInjected();
		return (T) applicationContext.getBeansOfType(requiredType);
	}

	public static void clear() {
		logger.debug("clear SpringContextHolder's ApplicationContext:" + applicationContext);
		applicationContext = null;
	}

	private static void assertContextInjected() {
		if (applicationContext == null) {
			throw new IllegalStateException("applicaitonContext not set,pls set in applicationContext.xml:SpringContextHolder");
		}
	}
}

