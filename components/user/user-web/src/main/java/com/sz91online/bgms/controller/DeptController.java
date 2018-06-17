package com.sz91online.bgms.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sz91online.bgms.foundation.web.controller.BaseController;
import com.sz91online.bgms.module.user.domain.Dept;
import com.sz91online.bgms.module.user.exceptions.EUserException;
import com.sz91online.bgms.module.user.service.DeptService;
import com.sz91online.common.db.service.ICrudService;
import com.sz91online.common.utils.PlStringUtils;

import io.swagger.annotations.Api;
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
	protected void prepareDataForInsert(Dept dept, HttpServletRequest request) {
		if (!PlStringUtils.isEmpty(dept.getParentDeptCode())) {
			Dept paramBean = new Dept();
			paramBean.setDeptCode(dept.getParentDeptCode());
			if (this.getService().findOne(paramBean) == null) {
				throw EUserException.PARENT_DEPT_NOT_EXIST_ERROR;
			}
		}
		dept.setDeptCode("DP" + new Date().getTime());
	}
	
	@Override
	protected void prepareDataForUpdate(Dept dept) {
		
		super.prepareDataForUpdate(dept);
		
		if (!PlStringUtils.isEmpty(dept.getParentDeptCode())) {
			Dept paramBean = new Dept();
			paramBean.setDeptCode(dept.getParentDeptCode());
			if (this.getService().findOne(paramBean) == null) {
				throw EUserException.PARENT_DEPT_NOT_EXIST_ERROR;
			}
		}
		if(PlStringUtils.isNotEmpty(dept.getDeptCode())){
			Dept paramBean = new Dept();
			paramBean.setDeptCode(dept.getDeptCode());
			Dept result = this.getService().findOne(paramBean);
			if (result == null) {
				throw EUserException.PARENT_DEPT_NOT_EXIST_ERROR;
			}else if(!result.getDeptCode().equals(dept.getDeptCode())){
				throw EUserException.UPDATE_CODE_IS_READONLY;
			}
		}else{
			throw EUserException.UPDATE_MISS_CODE;
		}
	}

	@Override
	protected void prepareDataForUpdate(String id) {
	}

}
