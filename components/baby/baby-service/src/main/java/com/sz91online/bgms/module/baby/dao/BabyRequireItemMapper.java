package com.sz91online.bgms.module.baby.dao;

import com.sz91online.bgms.module.baby.domain.BabyRequireItem;
import com.sz91online.common.db.service.ICrudGenericDAO;
import java.util.List;

public interface BabyRequireItemMapper extends ICrudGenericDAO {
    int deleteByPrimaryKey(Long id);

    int insert(BabyRequireItem record);

    BabyRequireItem selectByPrimaryKey(Long id);

    List<BabyRequireItem> selectAll();

    int updateByPrimaryKey(BabyRequireItem record);
}