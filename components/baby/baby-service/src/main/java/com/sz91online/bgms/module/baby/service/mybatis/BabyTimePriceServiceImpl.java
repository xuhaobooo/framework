package com.sz91online.bgms.module.baby.service.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sz91online.bgms.module.baby.dao.BabyTimePriceMapper;
import com.sz91online.bgms.module.baby.dao.BabyTimePriceMapperExt;
import com.sz91online.bgms.module.baby.domain.BabyTimePrice;
import com.sz91online.bgms.module.baby.service.BabyTimePriceService;
import com.sz91online.common.db.service.DefaultSearchService;
import com.sz91online.common.db.service.ICrudGenericDAO;
import com.sz91online.common.db.service.ISearchableDAO;

@Service
public class BabyTimePriceServiceImpl extends DefaultSearchService<BabyTimePrice> implements BabyTimePriceService {

	@Autowired
	private BabyTimePriceMapper babyTimePriceMapper;

	@Autowired
	private BabyTimePriceMapperExt babyTimePriceMapperExt;

	@Override
	public ISearchableDAO getSearchMapper() {
		return babyTimePriceMapperExt;
	}

	@Override
	public ICrudGenericDAO<BabyTimePrice> getCrudMapper() {
		return babyTimePriceMapper;
	}

}
