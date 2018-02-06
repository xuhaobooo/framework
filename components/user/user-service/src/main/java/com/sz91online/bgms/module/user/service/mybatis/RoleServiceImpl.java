package com.sz91online.bgms.module.user.service.mybatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sz91online.bgms.module.user.dao.RoleMapper;
import com.sz91online.bgms.module.user.dao.RoleMapperExt;
import com.sz91online.bgms.module.user.domain.Role;
import com.sz91online.bgms.module.user.domain.SimpleRole;
import com.sz91online.bgms.module.user.service.RoleService;
import com.sz91online.common.db.service.DefaultSearchService;
import com.sz91online.common.db.service.ICrudGenericDAO;
import com.sz91online.common.db.service.ISearchableDAO;

@Service
public class RoleServiceImpl extends DefaultSearchService<Role> implements RoleService  {

	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private RoleMapperExt roleMapperExt;
	
	
	@Override
	public ISearchableDAO getSearchMapper() {
		return roleMapperExt;
	}

	@Override
	public ICrudGenericDAO<Role> getCrudMapper() {
		return roleMapper;
	}

	@Override
	public List<SimpleRole> finRoleByUserCode(String userCode) {
		return roleMapperExt.finRoleByUserCode(userCode);
	}


}
