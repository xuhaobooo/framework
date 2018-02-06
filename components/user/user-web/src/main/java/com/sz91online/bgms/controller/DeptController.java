package com.sz91online.bgms.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sz91online.bgms.foundation.web.controller.BaseController;
import com.sz91online.bgms.module.user.domain.Dept;
import com.sz91online.bgms.module.user.exceptions.EUserException;
import com.sz91online.bgms.module.user.service.DeptService;
import com.sz91online.common.db.service.ICrudService;
import com.sz91online.common.utils.PlStringUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping(value = "dept")
@Api(value = "/dept", description = "部门相关接口，包括增、删、改、查权限")
@ApiIgnore
public class DeptController extends BaseController<Dept> {

	@Autowired
	private DeptService autService;

	@Override
	public ICrudService<Dept> getService() {
		return autService;
	}

	@Override
	@RequestMapping(method = RequestMethod.POST, value = "current")
	@ApiOperation(value = "添加新部门", httpMethod = "POST", notes = "返回执行保存记录后的记录，包含数据库主键id")
	public @ResponseBody Dept saveCurEntity(
			@RequestBody @ApiParam(name = "部门对象", value = "传入json格式,不包括id和deptCode", required = true) Dept dept,
			HttpServletRequest request) {

		checkParentDept(dept);

		// 自动生成deptCode和默认密码
		dept.setDeptCode("DP" + new Date().getTime());
		return super.saveCurEntity(dept, request);
	}

	@Override
	@RequestMapping(method = RequestMethod.PATCH, value = "current/{id}")
	@ApiOperation(value = "修改新部门", httpMethod = "PATCH", notes = "返回执行保存记录后的记录")
	public @ResponseBody Dept modifyCurEntity(
			@PathVariable @ApiParam(name = "id", value = "数据库主键,id", required = true) String id,
			@RequestBody @ApiParam(name = "部门对象", value = "传入json格式,不包括id和deptCode", required = true) Dept dept) {

		checkParentDept(dept);

		// 不允许修改deptCode和createTime
		return super.modifyCurEntity(id, dept);
	}

	/**
	 * 校验部门数据的合理性
	 * @param dept
	 */
	private void checkParentDept(Dept dept) {
		if (!PlStringUtils.isEmpty(dept.getParentDeptCode())) {
			Dept paramBean = new Dept();
			paramBean.setDeptCode(dept.getParentDeptCode());
			if (this.getService().findOne(paramBean) == null) {
				throw EUserException.PARENT_DEPT_NOT_EXIST_ERROR;
			}
		}
	}

}
