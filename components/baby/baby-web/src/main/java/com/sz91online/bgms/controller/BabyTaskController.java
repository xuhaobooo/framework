package com.sz91online.bgms.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.sz91online.bgms.module.baby.service.BabyTaskService;

//@Controller
//@RequestMapping(value = "babyTask")
//@Api(value = "/babyTask", description = "机构已接任务列表")
public class BabyTaskController {

	@Autowired
	private BabyTaskService babyTaskService;

	
}
