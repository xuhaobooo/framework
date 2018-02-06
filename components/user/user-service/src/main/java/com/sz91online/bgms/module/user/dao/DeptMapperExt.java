package com.sz91online.bgms.module.user.dao;

import java.util.List;

import com.sz91online.bgms.module.user.domain.Dept;
import com.sz91online.common.db.service.ISearchableDAO;

public interface DeptMapperExt extends ISearchableDAO {

	List<Dept> getSubDeptByDeptId(String deptCode);

}