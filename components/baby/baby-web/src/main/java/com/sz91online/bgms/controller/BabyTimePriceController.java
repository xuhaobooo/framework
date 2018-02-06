package com.sz91online.bgms.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sz91online.bgms.foundation.web.controller.BaseController;
import com.sz91online.bgms.foundation.web.session.SessionUtil;
import com.sz91online.bgms.module.baby.domain.BabyTimePrice;
import com.sz91online.bgms.module.baby.exceptions.EBabyException;
import com.sz91online.bgms.module.baby.service.BabyTimePriceService;
import com.sz91online.common.db.service.ICrudService;
import com.sz91online.common.utils.PlStringUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "babyTimePrice")
@Api(value = "/babyTimePrice", description = "工时价格管理接口")
@RequiresRoles({ "admin" })
public class BabyTimePriceController {

	@Autowired
	private BabyTimePriceService babyTimePriceService;

	@RequestMapping(method = RequestMethod.GET, value = "list")
	@ApiOperation(value = "列出价格", httpMethod = "GET", notes = "所有价格配置")
	public @ResponseBody List<BabyTimePrice> list() {
		return babyTimePriceService.findAll();
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "modify")
	@ApiOperation(value = "修改价格", httpMethod = "PATCH", notes = "")
	public @ResponseBody void modify(
			@RequestBody @ApiParam(name = "需求信息", value = "传入json格式,不包括id", required = true) BabyTimePrice timePrice) {

		if (PlStringUtils.isEmpty(timePrice.getPriceCode()) || timePrice.getPrice() == null
				|| timePrice.getId() == null) {
			throw EBabyException.MIS_PARAMETER_ERROR;
		}

		if (timePrice.getPrice().compareTo(0.0) <= 0) {
			throw EBabyException.PRICE_CANT_LESS_THAN_ZERO;
		}

		babyTimePriceService.updateWithSession(timePrice, SessionUtil.getSessionUser().getCode());
	}
}
