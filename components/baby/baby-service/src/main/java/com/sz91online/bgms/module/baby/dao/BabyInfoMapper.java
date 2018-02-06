package com.sz91online.bgms.module.baby.dao;

import com.sz91online.bgms.module.baby.domain.BabyInfo;
import com.sz91online.common.db.service.ICrudGenericDAO;
import java.util.List;

public interface BabyInfoMapper extends ICrudGenericDAO {
    int deleteByPrimaryKey(Long id);

    int insert(BabyInfo record);

    BabyInfo selectByPrimaryKey(Long id);

    List<BabyInfo> selectAll();

    int updateByPrimaryKey(BabyInfo record);
}