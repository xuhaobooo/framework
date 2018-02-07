package com.sz91online.bgms.foundation.web.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.sz91online.bgms.foundation.web.interceptor.PageAndSortInterceptor;

@Configuration
public class InterceptorConfigAdapter extends WebMvcConfigurerAdapter{

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		registry.addInterceptor(new PageAndSortInterceptor()).addPathPatterns("/**/find/**");
	}
	
}
