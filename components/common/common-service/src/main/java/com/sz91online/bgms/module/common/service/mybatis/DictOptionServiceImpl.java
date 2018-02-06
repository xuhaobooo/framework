package com.sz91online.bgms.module.common.service.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sz91online.bgms.module.common.dao.DictOptionMapper;
import com.sz91online.bgms.module.common.dao.DictOptionMapperExt;
import com.sz91online.bgms.module.common.domain.DictOption;
import com.sz91online.bgms.module.common.service.DictOptionService;
import com.sz91online.common.db.service.DefaultSearchService;
import com.sz91online.common.db.service.ICrudGenericDAO;
import com.sz91online.common.db.service.ISearchableDAO;

@Service
public class DictOptionServiceImpl extends DefaultSearchService<DictOption> implements DictOptionService {

	@Autowired
	private DictOptionMapper dictOptionMapper;

	@Autowired
	private DictOptionMapperExt dictOptionMapperExt;

	@Override
	public ISearchableDAO getSearchMapper() {
		return dictOptionMapperExt;
	}

	@Override
	public ICrudGenericDAO<DictOption> getCrudMapper() {
		return dictOptionMapper;
	}

}
