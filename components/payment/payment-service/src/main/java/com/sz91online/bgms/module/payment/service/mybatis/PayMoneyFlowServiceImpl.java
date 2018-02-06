package com.sz91online.bgms.module.payment.service.mybatis;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import com.sz91online.bgms.module.payment.dao.PayMoneyFlowMapper;
import com.sz91online.bgms.module.payment.dao.PayMoneyFlowMapperExt;
import com.sz91online.bgms.module.payment.domain.PayMoneyFlow;
import com.sz91online.bgms.module.payment.domain.SimplePayMoneyFlow;
import com.sz91online.bgms.module.payment.service.PayMoneyFlowService;
import com.sz91online.common.db.service.DefaultSearchService;
import com.sz91online.common.db.service.ICrudGenericDAO;
import com.sz91online.common.db.service.ISearchableDAO;

@Service
@EnableCaching
public class PayMoneyFlowServiceImpl extends DefaultSearchService<PayMoneyFlow> implements PayMoneyFlowService  {

	@Autowired
	private PayMoneyFlowMapper payMoneyFlowMapper;
	
	@Autowired
	private PayMoneyFlowMapperExt payMoneyFlowMapperExt;
	
	
	@Override
	public ISearchableDAO getSearchMapper() {
		return payMoneyFlowMapperExt;
	}

	@Override
	public ICrudGenericDAO<PayMoneyFlow> getCrudMapper() {
		return payMoneyFlowMapper;
	}

	@Override
	public List<SimplePayMoneyFlow> findMine(String code, Date startTime, Date endDate) {
		return payMoneyFlowMapperExt.findMine(code, startTime, endDate);
	}

}
