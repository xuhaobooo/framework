package com.sz91online.bgms.module.common.service.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sz91online.bgms.module.common.dao.AppUpdateInfoMapper;
import com.sz91online.bgms.module.common.dao.AppUpdateInfoMapperExt;
import com.sz91online.bgms.module.common.domain.AppUpdateInfo;
import com.sz91online.bgms.module.common.domain.SimpleAppUpdateFile;
import com.sz91online.bgms.module.common.service.AppUpdateService;
import com.sz91online.common.db.service.DefaultSearchService;
import com.sz91online.common.db.service.ICrudGenericDAO;
import com.sz91online.common.db.service.ISearchableDAO;

@Service
public class AppUpdateServiceImpl extends DefaultSearchService<AppUpdateInfo> implements AppUpdateService {

	@Autowired
	private AppUpdateInfoMapper appUpdateInfoMapper;

	@Autowired
	private AppUpdateInfoMapperExt appUpdateInfoMapperExt;

	@Override
	public ISearchableDAO getSearchMapper() {
		return appUpdateInfoMapperExt;
	}

	@Override
	public ICrudGenericDAO<AppUpdateInfo> getCrudMapper() {
		return appUpdateInfoMapper;
	}

}
