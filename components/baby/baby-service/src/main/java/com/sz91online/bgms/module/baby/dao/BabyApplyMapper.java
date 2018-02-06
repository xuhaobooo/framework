package com.sz91online.bgms.module.baby.dao;

import com.sz91online.bgms.module.baby.domain.BabyApply;
import com.sz91online.common.db.service.ICrudGenericDAO;
import java.util.List;

public interface BabyApplyMapper extends ICrudGenericDAO {
    int deleteByPrimaryKey(Long id);

    int insert(BabyApply record);

    BabyApply selectByPrimaryKey(Long id);

    List<BabyApply> selectAll();

    int updateByPrimaryKey(BabyApply record);
}