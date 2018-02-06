package com.sz91online.bgms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sz91online.bgms.foundation.web.controller.BaseController;
import com.sz91online.bgms.module.common.domain.DictOption;
import com.sz91online.bgms.module.common.exceptions.ECommonException;
import com.sz91online.bgms.module.common.service.DictOptionService;
import com.sz91online.common.db.service.ICrudService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping("/common/dictOption")
@Api(value = "/alipay", description = "字典接品")
public class DictOptionController {

	@Autowired
	private DictOptionService dictOptionService;

	/**
	 * 查询所有字典并以
	 */
	@RequestMapping(value = "getAllDict", method = RequestMethod.GET)
	@ApiOperation(value = "所有key-value值的获取接口", httpMethod = "GET", notes = "所有字典值，以Map型式返回，key为分类名，值为对象列表")
	public @ResponseBody Map<String, List<DictOption>> getAllDict() {
		List<DictOption> beanList = dictOptionService.findAll();
		Map<String, List<DictOption>> mapDatas = new HashMap<>();

		for (Iterator<DictOption> iterator = beanList.iterator(); iterator.hasNext();) {
			DictOption dictOption = (DictOption) iterator.next();
			List<DictOption> itemList = (List<DictOption>) mapDatas.get(dictOption.getDicClassName());
			if (itemList == null) {
				itemList = new ArrayList<>();
				mapDatas.put(dictOption.getDicClassName(), itemList);
			}
			itemList.add(dictOption);
		}

		return mapDatas;
	}

	/**
	 * 查询所有字典并以
	 */
	@RequestMapping(value = "getDictByClassName/{dictClassName}", method = RequestMethod.GET)
	@ApiOperation(value = "通过分类Code查询相应的字典", httpMethod = "GET", notes = "")
	public @ResponseBody List<DictOption> getOptionGroupByClassName(
			@PathVariable @ApiParam(name = "dictClassName", value = "字典分类Code", required = true) String dictClassName) {
		DictOption selectBean = new DictOption();
		selectBean.setDicClassName(dictClassName);
		List<DictOption> beanList = dictOptionService.findBySelective(selectBean);
		return beanList;
	}

	/*
	 * @RequestMapping(method = RequestMethod.PATCH, value = "current/{id}")
	 * public @ResponseBody DictOption modifyCurEntity(@PathVariable String id,
	 * DictOption dictOption) { throw ECommonException.OPTION_IS_READONLY_ERROR; }
	 */

}
