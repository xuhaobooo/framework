package com.sz91online.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@MapperScan("com.sz91online.bgms.module.**.dao")
@ComponentScan("com.sz91online.bgms")
@Configuration
public class ServiceApplication {

}
