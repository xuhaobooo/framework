package com.sz91online.bgms.module.common.dao;

import com.sz91online.bgms.module.common.domain.AppUpdateFile;
import com.sz91online.common.db.service.ICrudGenericDAO;
import java.util.List;

public interface AppUpdateFileMapper extends ICrudGenericDAO {
    int deleteByPrimaryKey(Long id);

    int insert(AppUpdateFile record);

    AppUpdateFile selectByPrimaryKey(Long id);

    List<AppUpdateFile> selectAll();

    int updateByPrimaryKey(AppUpdateFile record);
}