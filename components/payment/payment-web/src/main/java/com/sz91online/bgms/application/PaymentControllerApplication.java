package com.sz91online.bgms.application;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@MapperScan("com.sz91online.bgms.module.**.dao")
@ComponentScan("com.sz91online.bgms")
@Configuration
public class PaymentControllerApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(PaymentControllerApplication.class, args);
	}

}
