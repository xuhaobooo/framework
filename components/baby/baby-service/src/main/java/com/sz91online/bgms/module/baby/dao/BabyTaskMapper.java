package com.sz91online.bgms.module.baby.dao;

import com.sz91online.bgms.module.baby.domain.BabyTask;
import com.sz91online.common.db.service.ICrudGenericDAO;
import java.util.List;

public interface BabyTaskMapper extends ICrudGenericDAO {
    int deleteByPrimaryKey(Long id);

    int insert(BabyTask record);

    BabyTask selectByPrimaryKey(Long id);

    List<BabyTask> selectAll();

    int updateByPrimaryKey(BabyTask record);
}