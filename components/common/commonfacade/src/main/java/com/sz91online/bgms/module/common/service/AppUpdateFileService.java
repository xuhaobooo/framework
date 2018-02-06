package com.sz91online.bgms.module.common.service;

import com.sz91online.bgms.module.common.domain.AppUpdateFile;
import com.sz91online.bgms.module.common.domain.SimpleAppUpdateFile;
import com.sz91online.common.db.service.IDefaultService;

public interface AppUpdateFileService extends IDefaultService<AppUpdateFile> {
	SimpleAppUpdateFile findLastVersion(String appId);
}
