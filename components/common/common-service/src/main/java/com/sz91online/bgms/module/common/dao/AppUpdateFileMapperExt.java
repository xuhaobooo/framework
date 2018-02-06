package com.sz91online.bgms.module.common.dao;

import org.apache.ibatis.annotations.Param;

import com.sz91online.bgms.module.common.domain.SimpleAppUpdateFile;
import com.sz91online.common.db.service.ISearchableDAO;

public interface AppUpdateFileMapperExt extends ISearchableDAO {
	SimpleAppUpdateFile findLastVersion(@Param(value = "app_id") String appId);
}