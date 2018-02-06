package com.sz91online.bgms.module.user.dao;

import java.util.List;

import com.sz91online.bgms.module.user.domain.SimpleRole;
import com.sz91online.common.db.service.ISearchableDAO;

public interface RoleMapperExt extends ISearchableDAO {

	List<SimpleRole> finRoleByUserCode(String userCode);
}