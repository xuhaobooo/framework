package com.sz91online.bgms.module.baby.dao;

import com.sz91online.bgms.module.baby.domain.BabyTaskStep;
import com.sz91online.common.db.service.ICrudGenericDAO;
import java.util.List;

public interface BabyTaskStepMapper extends ICrudGenericDAO {
    int deleteByPrimaryKey(Long id);

    int insert(BabyTaskStep record);

    BabyTaskStep selectByPrimaryKey(Long id);

    List<BabyTaskStep> selectAll();

    int updateByPrimaryKey(BabyTaskStep record);
}