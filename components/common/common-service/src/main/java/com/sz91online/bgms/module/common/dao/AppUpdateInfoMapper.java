package com.sz91online.bgms.module.common.dao;

import com.sz91online.bgms.module.common.domain.AppUpdateInfo;
import com.sz91online.common.db.service.ICrudGenericDAO;
import java.util.List;

public interface AppUpdateInfoMapper extends ICrudGenericDAO {
    int deleteByPrimaryKey(Long id);

    int insert(AppUpdateInfo record);

    AppUpdateInfo selectByPrimaryKey(Long id);

    List<AppUpdateInfo> selectAll();

    int updateByPrimaryKey(AppUpdateInfo record);
}