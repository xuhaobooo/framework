package com.sz91online.bgms.module.user.dao;

import com.sz91online.bgms.module.user.domain.Dept;
import com.sz91online.common.db.service.ICrudGenericDAO;
import java.util.List;

public interface DeptMapper extends ICrudGenericDAO {
    int deleteByPrimaryKey(Long id);

    int insert(Dept record);

    Dept selectByPrimaryKey(Long id);

    List<Dept> selectAll();

    int updateByPrimaryKey(Dept record);
}