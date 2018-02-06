package com.sz91online.bgms.module.baby.dao;

import com.sz91online.bgms.module.baby.domain.BabyRequire;
import com.sz91online.common.db.service.ICrudGenericDAO;
import java.util.List;

public interface BabyRequireMapper extends ICrudGenericDAO {
    int deleteByPrimaryKey(Long id);

    int insert(BabyRequire record);

    BabyRequire selectByPrimaryKey(Long id);

    List<BabyRequire> selectAll();

    int updateByPrimaryKey(BabyRequire record);
}