package com.sz91online.bgms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sz91online.bgms.foundation.web.session.SessionUtil;
import com.sz91online.bgms.module.baby.domain.BabyTaskStep;
import com.sz91online.bgms.module.baby.service.BabyTaskStepService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

//@Controller
//@RequestMapping(value = "babyTaskStep")
//@Api(value = "/babyTaskStep", description = "机构已接任务处理过程")
public class BabyTaskStepController {

	@Autowired
	private BabyTaskStepService babyTaskStepService;

	/**
	 * 新增
	 * 
	 * @param t
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "current")
	@ApiOperation(value = "添加任务过程", httpMethod = "POST", notes = "返回执行保存记录后的记录，包含数据库主键id")
	public @ResponseBody BabyTaskStep saveCurEntity(
			@RequestBody @ApiParam(name = "任务过程信息", value = "传入json格式,不包括id和requireCode", required = true) BabyTaskStep babyTaskStep,
			HttpServletRequest request) {
		// 自动生成authCode
		babyTaskStepService.saveWithSession(babyTaskStep, SessionUtil.getSessionUser().getCode());
		return babyTaskStep;
	}

	@RequestMapping(method = RequestMethod.GET, value = "findByTaskCode/{taskCode}")
	@ApiOperation(value = "查询任务所有过程", httpMethod = "POST", notes = "")
	public List<BabyTaskStep> findStepByTaskCode(
			@PathVariable @ApiParam(name = "taskCode", value = "任务Code", required = true) String taskCode) {

		BabyTaskStep queryBean = new BabyTaskStep();
		queryBean.setTaskCode(taskCode);
		return babyTaskStepService.findBySelective(queryBean);
	}

}
