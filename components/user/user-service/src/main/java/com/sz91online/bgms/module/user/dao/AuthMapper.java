package com.sz91online.bgms.module.user.dao;

import com.sz91online.bgms.module.user.domain.Auth;
import com.sz91online.common.db.service.ICrudGenericDAO;
import java.util.List;

public interface AuthMapper extends ICrudGenericDAO {
    int deleteByPrimaryKey(Long id);

    int insert(Auth record);

    Auth selectByPrimaryKey(Long id);

    List<Auth> selectAll();

    int updateByPrimaryKey(Auth record);
}