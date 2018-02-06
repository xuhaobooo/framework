package com.sz91online.bgms.module.baby.dao;

import com.sz91online.bgms.module.baby.domain.BabyEvaluation;
import com.sz91online.common.db.service.ICrudGenericDAO;
import java.util.List;

public interface BabyEvaluationMapper extends ICrudGenericDAO {
    int deleteByPrimaryKey(Long id);

    int insert(BabyEvaluation record);

    BabyEvaluation selectByPrimaryKey(Long id);

    List<BabyEvaluation> selectAll();

    int updateByPrimaryKey(BabyEvaluation record);
}