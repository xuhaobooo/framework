package com.sz91online.bgms.module.user.dao;

import com.sz91online.bgms.module.user.domain.Role;
import com.sz91online.common.db.service.ICrudGenericDAO;
import java.util.List;

public interface RoleMapper extends ICrudGenericDAO {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    Role selectByPrimaryKey(Long id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);
}