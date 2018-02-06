package com.sz91online.bgms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sz91online.bgms.foundation.web.session.SessionUtil;
import com.sz91online.bgms.module.baby.domain.BabyRequireItemDict;
import com.sz91online.bgms.module.baby.exceptions.EBabyException;
import com.sz91online.bgms.module.baby.service.BabyRequireItemDictService;
import com.sz91online.common.utils.PlStringUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "itemDict")
@Api(value = "/itemDict", description = "需求所用项目管理接口")
@RequiresRoles({ "admin" })
public class BabyRequireItemDictController {

	@Autowired
	private BabyRequireItemDictService babyRequireItemDictService;

	@RequestMapping(method = RequestMethod.POST, value = "add")
	@ApiOperation(value = "添加需求信息字典", httpMethod = "POST", notes = "")
	public @ResponseBody void saveCurEntity(
			@RequestBody @ApiParam(name = "需求信息", value = "传入json格式,不包括id", required = true) BabyRequireItemDict babyRequireItemDict,
			HttpServletRequest request) {
		if (PlStringUtils.isEmpty(babyRequireItemDict.getItemCode())
				|| PlStringUtils.isEmpty(babyRequireItemDict.getItemName())
				|| babyRequireItemDict.getItemPrice() == null) {
			throw EBabyException.MIS_PARAMETER_ERROR;
		}
		babyRequireItemDict.setEnableFlag(true);
		babyRequireItemDictService.saveWithSession(babyRequireItemDict, SessionUtil.getSessionUser().getCode());

	}

	@RequestMapping(method = RequestMethod.DELETE, value = "disable/{itemCode}")
	@ApiOperation(value = "删除需求信息字典", httpMethod = "DELETE", notes = "")
	public @ResponseBody void disableCurEntity(
			@PathVariable @ApiParam(name = "itemCode", value = "itemCode", required = true) String itemCode,
			HttpServletRequest request) {
		// 自动生成authCode
		BabyRequireItemDict babyRequireItemDict = new BabyRequireItemDict();
		babyRequireItemDict.setItemCode(itemCode);
		BabyRequireItemDict resultBean = babyRequireItemDictService.findOne(babyRequireItemDict);
		if (resultBean != null) {
			resultBean.setEnableFlag(false);
			babyRequireItemDictService.updateWithSession(resultBean, SessionUtil.getSessionUser().getCode());
		} else {
			throw EBabyException.SERVICE_NOT_EXIST;
		}
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "active/{itemCode}")
	@ApiOperation(value = "删除需求信息字典", httpMethod = "PATCH", notes = "")
	public @ResponseBody void activeCurEntity(
			@PathVariable @ApiParam(name = "itemCode", value = "itemCode", required = true) String itemCode,
			HttpServletRequest request) {
		// 自动生成authCode
		BabyRequireItemDict babyRequireItemDict = new BabyRequireItemDict();
		babyRequireItemDict.setItemCode(itemCode);
		BabyRequireItemDict resultBean = babyRequireItemDictService.findOne(babyRequireItemDict);
		if (resultBean != null) {
			resultBean.setEnableFlag(true);
			babyRequireItemDictService.updateWithSession(resultBean, SessionUtil.getSessionUser().getCode());
		} else {
			throw EBabyException.SERVICE_NOT_EXIST;
		}
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "modify")
	@ApiOperation(value = "删除需求信息字典", httpMethod = "PATCH", notes = "")
	public @ResponseBody void modify(
			@RequestBody @ApiParam(name = "需求信息", value = "传入json格式,不包括id", required = true) BabyRequireItemDict babyRequireItemDict,
			HttpServletRequest request) {
		if (PlStringUtils.isEmpty(babyRequireItemDict.getItemCode())
				|| PlStringUtils.isEmpty(babyRequireItemDict.getItemName())
				|| babyRequireItemDict.getItemPrice() == null || babyRequireItemDict.getId() == null) {
			throw EBabyException.MIS_PARAMETER_ERROR;
		}
		babyRequireItemDictService.updateWithSession(babyRequireItemDict, SessionUtil.getSessionUser().getCode());
	}

	@RequestMapping(method = RequestMethod.GET, value = "list")
	@ApiOperation(value = "列出需求信息字典", httpMethod = "GET", notes = "")
	public @ResponseBody List<BabyRequireItemDict> list(
			@ApiParam(name = "禁用标识", value = "查已禁用的传false,查未禁用的传true", required = false) @RequestParam(required = false, value = "enableFlag") String enableFlag) {
		if (enableFlag != null && ("true".equals(enableFlag) || "false".equals(enableFlag))) {
			BabyRequireItemDict queryBean = new BabyRequireItemDict();
			queryBean.setEnableFlag(Boolean.parseBoolean(enableFlag));
			return babyRequireItemDictService.findBySelective(queryBean);
		} else {
			return babyRequireItemDictService.findAll();
		}

	}
}
