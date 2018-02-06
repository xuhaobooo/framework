package com.sz91online.bgms.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.ApiOperation;

@Controller

@RequestMapping(value = "/test")
public class WelcomeController {
	@RequiresRoles("TestRole1")
	@RequestMapping(value = "/index")
	@ApiOperation(value = "测试接口", httpMethod = "GET", notes = "http status 为200时表示成功，否则失败！")
	public @ResponseBody Map<String,String> index() {
		Map result = new HashMap<>();
		result.put("success", "true");
		return result;
		
	}
}
