package com.sz91online.bgms.module.baby.service.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sz91online.bgms.module.baby.dao.BabyRequireItemMapper;
import com.sz91online.bgms.module.baby.dao.BabyRequireItemMapperExt;
import com.sz91online.bgms.module.baby.domain.BabyRequireItem;
import com.sz91online.bgms.module.baby.service.BabyRequireItemService;
import com.sz91online.common.db.service.DefaultSearchService;
import com.sz91online.common.db.service.ICrudGenericDAO;
import com.sz91online.common.db.service.ISearchableDAO;

@Service
public class BabyRequireItemServiceImpl extends DefaultSearchService<BabyRequireItem> implements BabyRequireItemService {

	@Autowired
	private BabyRequireItemMapper babyRequireItemMapper;

	@Autowired
	private BabyRequireItemMapperExt babyRequireItemMapperExt;
	
	@Override
	public ISearchableDAO getSearchMapper() {
		return babyRequireItemMapperExt;
	}

	@Override
	public ICrudGenericDAO<BabyRequireItem> getCrudMapper() {
		return babyRequireItemMapper;
	}

}
