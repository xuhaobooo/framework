package com.sz91online.bgms.module.common.service.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sz91online.bgms.module.common.dao.AppUpdateFileMapper;
import com.sz91online.bgms.module.common.dao.AppUpdateFileMapperExt;
import com.sz91online.bgms.module.common.domain.AppUpdateFile;
import com.sz91online.bgms.module.common.domain.SimpleAppUpdateFile;
import com.sz91online.bgms.module.common.service.AppUpdateFileService;
import com.sz91online.common.db.service.DefaultSearchService;
import com.sz91online.common.db.service.ICrudGenericDAO;
import com.sz91online.common.db.service.ISearchableDAO;

@Service
public class AppUpdateFileServiceImpl extends DefaultSearchService<AppUpdateFile> implements AppUpdateFileService {

	@Autowired
	private AppUpdateFileMapper appUpdateFileMapper;

	@Autowired
	private AppUpdateFileMapperExt appUpdateFileMapperExt;

	@Override
	public ISearchableDAO getSearchMapper() {
		return appUpdateFileMapperExt;
	}

	@Override
	public ICrudGenericDAO<AppUpdateFile> getCrudMapper() {
		return appUpdateFileMapper;
	}

	@Override
	public SimpleAppUpdateFile findLastVersion(String appId) {
		return appUpdateFileMapperExt.findLastVersion(appId);
	}
}
