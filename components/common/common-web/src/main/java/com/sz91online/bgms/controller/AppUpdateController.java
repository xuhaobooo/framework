package com.sz91online.bgms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sz91online.bgms.module.common.domain.SimpleAppUpdateFile;
import com.sz91online.bgms.module.common.service.AppUpdateFileService;
import com.sz91online.bgms.module.common.service.AppUpdateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping("/onlineUpdate")
@Api(value = "/onlineUpdate", description = "自动更新接口")
public class AppUpdateController {

	@Autowired
	private AppUpdateService appUpdateService;

	@Autowired
	private AppUpdateFileService appUpdateFileService;

	@RequestMapping(value = "getLastVersion/{appId}", method = RequestMethod.GET)
	@ApiOperation(value = "通过APPId查询是否有新版本", httpMethod = "GET", notes = "")
	public @ResponseBody Integer getLastVersion(
			@PathVariable @ApiParam(name = "appId", value = "appId", required = true) String appId) {

		SimpleAppUpdateFile result = appUpdateFileService.findLastVersion(appId);
		if (result != null) {
			return result.getVersionCode();
		} else {
			return -1;
		}
	}

}
