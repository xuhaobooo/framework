package com.sz91online.bgms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sz91online.bgms.foundation.web.controller.BaseController;
import com.sz91online.bgms.module.baby.domain.BabyApply;
import com.sz91online.bgms.module.baby.service.BabyApplyService;
import com.sz91online.common.db.service.ICrudService;

import io.swagger.annotations.Api;

//@Controller
//@RequestMapping(value = "babyApply")
//@Api(value = "/babyApply", description = "机构接单申请接口，在用户发布需求后，多个机构可以申请接受此需求，申请需显示在APP上给用户选择和确定")
public class BabyApplyController extends BaseController<BabyApply> {

	@Autowired
	private BabyApplyService babyApplyService;

	@Override
	public ICrudService<BabyApply> getService() {
		return babyApplyService;
	}

}
