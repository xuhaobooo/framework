package com.sz91online.bgms.module.baby.dao;

import com.sz91online.bgms.module.baby.domain.BabyRequireItemDict;
import com.sz91online.common.db.service.ICrudGenericDAO;
import java.util.List;

public interface BabyRequireItemDictMapper extends ICrudGenericDAO {
    int deleteByPrimaryKey(Long id);

    int insert(BabyRequireItemDict record);

    BabyRequireItemDict selectByPrimaryKey(Long id);

    List<BabyRequireItemDict> selectAll();

    int updateByPrimaryKey(BabyRequireItemDict record);
}