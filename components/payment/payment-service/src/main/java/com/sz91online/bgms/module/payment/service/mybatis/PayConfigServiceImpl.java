package com.sz91online.bgms.module.payment.service.mybatis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sz91online.bgms.module.payment.dao.PayConfigMapper;
import com.sz91online.bgms.module.payment.dao.PayConfigMapperExt;
import com.sz91online.bgms.module.payment.domain.PayConfig;
import com.sz91online.bgms.module.payment.service.PayConfigService;
import com.sz91online.common.db.service.DefaultSearchService;
import com.sz91online.common.db.service.ICrudGenericDAO;
import com.sz91online.common.db.service.ISearchableDAO;

@Service
public class PayConfigServiceImpl extends DefaultSearchService<PayConfig> implements PayConfigService {

	Logger logger = LoggerFactory.getLogger(PayConfigServiceImpl.class);

	@Autowired
	private PayConfigMapper payConfigMapper;

	@Autowired
	private PayConfigMapperExt payConfigMapperExt;

	@Override
	public ISearchableDAO getSearchMapper() {
		return payConfigMapperExt;
	}

	@Override
	public ICrudGenericDAO<PayConfig> getCrudMapper() {
		return payConfigMapper;
	}

}
