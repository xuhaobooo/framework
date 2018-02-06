package com.sz91online.bgms.module.user.service;

import java.util.List;

import com.sz91online.bgms.module.user.domain.Role;
import com.sz91online.bgms.module.user.domain.SimpleRole;
import com.sz91online.common.db.service.IDefaultService;

public interface RoleService extends IDefaultService<Role>{

	List<SimpleRole> finRoleByUserCode(String userCode);

}
