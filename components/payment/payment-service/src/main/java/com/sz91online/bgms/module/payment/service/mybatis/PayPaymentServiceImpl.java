package com.sz91online.bgms.module.payment.service.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import com.sz91online.bgms.module.payment.dao.PayPaymentMapper;
import com.sz91online.bgms.module.payment.dao.PayPaymentMapperExt;
import com.sz91online.bgms.module.payment.domain.PayPayment;
import com.sz91online.bgms.module.payment.service.PayPaymentService;
import com.sz91online.common.db.service.DefaultSearchService;
import com.sz91online.common.db.service.ICrudGenericDAO;
import com.sz91online.common.db.service.ISearchableDAO;

@Service
@EnableCaching
public class PayPaymentServiceImpl extends DefaultSearchService<PayPayment> implements PayPaymentService  {

	@Autowired
	private PayPaymentMapper payPaymentMapper;
	
	@Autowired
	private PayPaymentMapperExt payPaymentMapperExt;
	
	
	@Override
	public ISearchableDAO getSearchMapper() {
		return payPaymentMapperExt;
	}

	@Override
	public ICrudGenericDAO<PayPayment> getCrudMapper() {
		return payPaymentMapper;
	}

}
