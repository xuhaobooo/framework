package com.sz91online.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy(value = false)
@Component("appContextUtil")
public class PlApplicationContextUtil implements ApplicationContextAware {
	private static ApplicationContext ctx;

	@Override
	public void setApplicationContext(ApplicationContext appContext) throws BeansException {
		ctx = appContext;
	}

	public static <T> T getSpringBean(String name, Class<T> classType) {
		if (ctx == null) {
			return null;
		}
		return ctx.getBean(name, classType);
	}

	public static <T> T getSpringBean(Class<T> classType) {
		if (ctx == null) {
			return null;
		}
		return ctx.getBean(classType);
	}
	
	public static ApplicationContext getApplicationContext() {
		return ctx;
	}

}
