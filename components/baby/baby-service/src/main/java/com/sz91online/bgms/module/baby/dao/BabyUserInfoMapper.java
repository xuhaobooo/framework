package com.sz91online.bgms.module.baby.dao;

import com.sz91online.bgms.module.baby.domain.BabyUserInfo;
import com.sz91online.common.db.service.ICrudGenericDAO;
import java.util.List;

public interface BabyUserInfoMapper extends ICrudGenericDAO {
    int deleteByPrimaryKey(Long id);

    int insert(BabyUserInfo record);

    BabyUserInfo selectByPrimaryKey(Long id);

    List<BabyUserInfo> selectAll();

    int updateByPrimaryKey(BabyUserInfo record);
}