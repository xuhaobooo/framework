package com.sz91online.bgms.module.baby.service.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sz91online.bgms.module.baby.dao.BabyPaymentMapper;
import com.sz91online.bgms.module.baby.dao.BabyPaymentMapperExt;
import com.sz91online.bgms.module.baby.domain.BabyPayment;
import com.sz91online.bgms.module.baby.service.BabyPaymentService;
import com.sz91online.common.db.service.DefaultSearchService;
import com.sz91online.common.db.service.ICrudGenericDAO;
import com.sz91online.common.db.service.ISearchableDAO;

@Service
public class BabyPaymentServiceImpl extends DefaultSearchService<BabyPayment> implements BabyPaymentService {

	@Autowired
	private BabyPaymentMapper babyPaymentMapper;

	@Autowired
	private BabyPaymentMapperExt babyPaymentMapperExt;

	@Override
	public ISearchableDAO getSearchMapper() {
		return babyPaymentMapperExt;
	}

	@Override
	public ICrudGenericDAO<BabyPayment> getCrudMapper() {
		return babyPaymentMapper;
	}

}
