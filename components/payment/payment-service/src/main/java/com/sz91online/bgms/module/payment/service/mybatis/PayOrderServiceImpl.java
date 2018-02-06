package com.sz91online.bgms.module.payment.service.mybatis;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import com.sz91online.bgms.module.payment.dao.PayOrderMapper;
import com.sz91online.bgms.module.payment.dao.PayOrderMapperExt;
import com.sz91online.bgms.module.payment.domain.PayOrder;
import com.sz91online.bgms.module.payment.domain.SimplePayOrder;
import com.sz91online.bgms.module.payment.enums.BusiTypeEnum;
import com.sz91online.bgms.module.payment.service.PayOrderService;
import com.sz91online.common.db.service.DefaultSearchService;
import com.sz91online.common.db.service.ICrudGenericDAO;
import com.sz91online.common.db.service.ISearchableDAO;

@Service
@EnableCaching
public class PayOrderServiceImpl extends DefaultSearchService<PayOrder> implements PayOrderService {

	@Autowired
	private PayOrderMapper payOrderMapper;

	@Autowired
	private PayOrderMapperExt payOrderMapperExt;

	@Override
	public ISearchableDAO getSearchMapper() {
		return payOrderMapperExt;
	}

	@Override
	public ICrudGenericDAO<PayOrder> getCrudMapper() {
		return payOrderMapper;
	}

	@Override
	public List<SimplePayOrder> findMine(String userCode, Date startTime, Date endTime) {
		List<SimplePayOrder> resultList = payOrderMapperExt.findMine(userCode, startTime, endTime);
		Map<String, Map<String, String>> busyTypeMap = BusiTypeEnum.toMap();
		for (Iterator iterator = resultList.iterator(); iterator.hasNext();) {
			SimplePayOrder simplePayOrder = (SimplePayOrder) iterator.next();
			String payType = simplePayOrder.getBusiType();
			Map<String, String> mapValue = busyTypeMap.get(payType);
			if (mapValue != null) {
				simplePayOrder.setBusiTypeName(mapValue.get("desc"));
			}
		}

		return resultList;
	}

}
