package com.sz91online.bgms.module.baby.service.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sz91online.bgms.module.baby.dao.BabyRequireItemDictMapper;
import com.sz91online.bgms.module.baby.dao.BabyRequireItemDictMapperExt;
import com.sz91online.bgms.module.baby.domain.BabyRequireItemDict;
import com.sz91online.bgms.module.baby.service.BabyRequireItemDictService;
import com.sz91online.common.db.service.DefaultSearchService;
import com.sz91online.common.db.service.ICrudGenericDAO;
import com.sz91online.common.db.service.ISearchableDAO;

@Service
public class BabyRequireItemDictServiceImpl extends DefaultSearchService<BabyRequireItemDict> implements BabyRequireItemDictService {

	@Autowired
	private BabyRequireItemDictMapper babyRequireItemDictMapper;

	@Autowired
	private BabyRequireItemDictMapperExt babyRequireItemDictMapperExt;
	
	@Override
	public ISearchableDAO getSearchMapper() {
		return babyRequireItemDictMapperExt;
	}

	@Override
	public ICrudGenericDAO<BabyRequireItemDict> getCrudMapper() {
		return babyRequireItemDictMapper;
	}

}
