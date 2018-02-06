package com.sz91online.bgms.application;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.sz91online.bgms.module.**.dao")
@ComponentScan("com.sz91online.bgms")
public class MainControllerApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MainControllerApplication.class, args);
	}

	/*
	 * @Override public void addResourceHandlers(ResourceHandlerRegistry registry) {
	 * registry.addResourceHandler("/**").addResourceLocations("/"); }
	 */
}
