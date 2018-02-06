package com.sz91online.bgms.module.common.dao;

import com.sz91online.bgms.module.common.domain.DictOption;
import com.sz91online.common.db.service.ICrudGenericDAO;
import java.util.List;

public interface DictOptionMapper extends ICrudGenericDAO {
    int deleteByPrimaryKey(Long id);

    int insert(DictOption record);

    DictOption selectByPrimaryKey(Long id);

    List<DictOption> selectAll();

    int updateByPrimaryKey(DictOption record);
}