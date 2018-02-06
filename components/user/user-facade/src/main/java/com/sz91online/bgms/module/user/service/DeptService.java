package com.sz91online.bgms.module.user.service;

import java.util.List;

import com.sz91online.bgms.module.user.domain.Dept;
import com.sz91online.common.db.service.IDefaultService;

public interface DeptService extends IDefaultService<Dept>{

	/**
	 * 根据dept_code查询所有子部门
	 * @param deptCode
	 * @return
	 */
	List<Dept> getSubDeptByDeptId(String deptCode);
	
}
