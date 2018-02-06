package com.sz91online.bgms.controller.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.sz91online.common.exceptions.EBusinessException;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ComponentScan(basePackages = { "com.sz91online.bgms" })
// Loads the spring beans required by the framework
public class MySwaggerConfig {

	/**
	 * Every Docket bean is picked up by the swagger-mvc framework - allowing for
	 * multiple swagger groups i.e. same code base multiple swagger resource
	 * listings.
	 */
	@Bean
	public Docket customDocket() {

		//Docket aDocket = new Docket(DocumentationType.SWAGGER_2).host("120.79.76.25");
		Docket aDocket = new Docket(DocumentationType.SWAGGER_2);

		Contact authorContact = new Contact("WindRanger", "", "3234310@qq.com");
		ApiInfo apiInfo = new ApiInfo("Sz91online WindRanger API Document",
				"本文档描述前台与后台的交互方式、接口和数据。 <br/>后台目前只提供JSON方式数据，http返回请求中的content为json内容，内容只包含业务数据，不包含类似result_code之类的非业务数据。"
						+ "非业务数据通过http header返回，如果header中未包含return_code，表示http请求处理正常，否则取return_code作相应的提示。提示的内容取return_msg,return_msg是URLEncode后的中文字符串，使用前需还原。<br/>"
						+ "通用错误代码列表：<br/>" + EBusinessException.MIS_PARAMETER_ERROR + ":缺少必要参数<br/>" + "",
				"V1.0", "", authorContact, "", "");
		aDocket.apiInfo(apiInfo);

		return aDocket;
	}

	/*
	 * private SpringSwaggerConfig springSwaggerConfig;
	 * 
	 *//**
		 * Required to autowire SpringSwaggerConfig
		 */
	/*
	 * @Autowired public void setSpringSwaggerConfig(SpringSwaggerConfig
	 * springSwaggerConfig) { this.springSwaggerConfig = springSwaggerConfig; }
	 * 
	 *//**
		 * Every SwaggerSpringMvcPlugin bean is picked up by the swagger-mvc framework -
		 * allowing for multiple swagger groups i.e. same code base multiple swagger
		 * resource listings.
		 *//*
			 * @Bean public SwaggerSpringMvcPlugin customImplementation() { return new
			 * SwaggerSpringMvcPlugin(this.springSwaggerConfig).apiInfo(apiInfo()).
			 * includePatterns( ".*?"); }
			 * 
			 * 
			 * 
			 * private ApiInfo apiInfo() { ApiInfo apiInfo = new ApiInfo(
			 * "Lachesis WindRanger API Document",
			 * "WindRanger用于构建医院的接口平台。<br/>已经集成的组件为Authentication, EMR <br/>支持在上运行的产品有MTR, COMS"
			 * , "My Apps API terms of service", "shiwei.hu@lachesis-mh.com",
			 * "My Apps API Licence Type", "My Apps API License URL");
			 * 
			 * 
			 * 
			 * return apiInfo; }
			 */
}